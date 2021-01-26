package dts.dal;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import dts.ItemBoundary;
import dts.ItemID;
import dts.ItemIdBoundary;
import dts.UserId;
import dts.data.ItemEntity;
import dts.exceptions.ItemNotFoundException;
import dts.logic.EnhancedItemsService;
import dts.logic.ItemConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Service
public class RdbItemService implements EnhancedItemsService {

	private ItemDao itemDao;
	private String space;
	private ItemConverter itemConverter;
	private AtomicLong keyGenerator;

	private Log log = LogFactory.getLog(RdbItemService.class);
	
	@PostConstruct
	public void init() {
		this.keyGenerator = new AtomicLong(1L);
	}

	@Value("${spring.application.name:ShoppingList}")
	public void setInitProperty(String space) {
		this.space = space;
	}

	@Autowired
	public RdbItemService(ItemDao itemDao) {
		super();
		this.itemDao = itemDao;
	}

	@Autowired
	public void setItemConverter(ItemConverter itemConverter) {
		this.itemConverter = itemConverter;
	}

	@Override
	@Transactional
	public ItemBoundary create(String managerSpace, String managerEmail,ItemBoundary newItem) {
		
		String key = "" + this.keyGenerator.getAndIncrement();
		newItem.setId(new ItemID(key, this.space));
		newItem.setCreatedBy(new UserId(managerEmail,managerSpace));
		System.err.println("----attr---- " + newItem.getItemAttributes());
		newItem.setItemAttributes(newItem.getItemAttributes());
		
		ItemEntity entity = this.itemConverter.convertToEntity(newItem);
		entity = this.itemDao.save(entity);
		
		// creates new and updated item boundary to return
		ItemBoundary rv = this.itemConverter.convertToBoundary(entity);
		
		// Log
		this.log.debug("New item created. Id: " + entity.getId());
		
		return rv;
	}

	@Override
	@Transactional
	public ItemBoundary update(String space, String email, String itemSpace, String id, ItemBoundary newItem) {
		String idToFind = id + "!!!" + itemSpace ;

		ItemEntity oldItem= this.itemDao.findById(idToFind).orElseThrow(()->new ItemNotFoundException("could not found: " + id));
		//		ItemEntity oldItem= this.itemDao.findById(newItem.generateEntityId()).orElseThrow(()->new ItemNotFoundException("could not found: " + itemId));

		if (newItem.getActive() != null) {
			oldItem.setActive(newItem.getActive());
		}

		if (newItem.getItemAttributes() != null) {
			oldItem.setAttr(this.itemConverter.convertItemAttrToEntity(newItem.getItemAttributes()));
		}

		if (newItem.getLocation()!= null) {
//			oldItem.setLocation(this.itemConverter.convertLocationToEntity(newItem.getLocation()));
			oldItem.setLat(newItem.getLocation().getLat());
			oldItem.setLng(newItem.getLocation().getLng());
		}

		if (newItem.getName() != null) {
			oldItem.setName(newItem.getName());
		}

		if (newItem.getType() != null) {
			oldItem.setType(newItem.getType());
		}

		oldItem = this.itemDao.save(oldItem);
		
		// Log
		this.log.debug("New item updated. Id: " + newItem.getId());
		
		return this.itemConverter.convertToBoundary(oldItem);

	}

	@Override
	@Transactional(readOnly = true)
	public ItemBoundary getSpecificItem(String space, String email, String itemSpace, String id) {
		String seperator = "!!!";
		String idToFind = id + seperator + itemSpace;
		Optional<ItemEntity> optional = this.itemDao.findById(idToFind);

		if (optional.isPresent() && optional != null) {
			ItemEntity entity = optional.get();
			
			// Log
			this.log.debug("Getting specific item with Id: " + entity.getId());
			
			return this.itemConverter.convertToBoundary(entity);
			
		} else {
			// Log
			this.log.error("Error while trying to get specific item");
			
			throw new ItemNotFoundException("could not find item with id: " + id);
		}		
	}


	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAll(String space, String email) {
		Iterable<ItemEntity> entities = this.itemDao.findAll();

		List<ItemBoundary> rv = new LinkedList<>();
		for (ItemEntity e : entities) {
			ItemBoundary boundary = this.itemConverter.convertToBoundary(e);
			rv.add(boundary);
		}
		
		// Log
		this.log.debug("Getting all items");
		
		return rv;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getAll(String space, String email, int size, int page) {
		
		// Log
		this.log.debug("Getting all items");
		
		return this.itemDao
				.findAll(PageRequest.of(page, size, Direction.DESC, "id")) // Page<ItemEnity>
				.getContent() // List<ItemEnity>
				.stream() // Stream<ItemEnity>
				.map(this.itemConverter::convertToBoundary)// Stream<ItemBoundary>
				.collect(Collectors.toList());// List<ItemBoundary>

	}

	@Override
	@Transactional
	public void deleteAll(String Space, String email) {
		// Log
		this.log.debug("Deleting all items");
		
		this.itemDao.deleteAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getItemChildren(String userSpace, String userEmail, String id, String itemSpace,int size, int page) {
		// Log
		this.log.debug("Getting all item children");
		
		String seperator = "!!!";
		String parendId = id+seperator+itemSpace;
		
		return this.itemDao
				.findAllByParent_id(parendId, PageRequest.of(page, size, Direction.DESC, "id"))
				.stream() // Stream<ItemEntity>
				.map(this.itemConverter::convertToBoundary)// Stream<ItemBoundary>
				.collect(Collectors.toList());// List<ItemBoundary>
	}


	@Override
	@Transactional(readOnly = true)
	public List<ItemBoundary> getItemParent(String userSpace, String userEmail, String id, String itemSpace,int size, int page) {
		// Log
		this.log.debug("Getting item parent");
		
		String seperator = "!!!";
		String childId = id+seperator+itemSpace;
		ItemEntity child = 
				this.itemDao
				.findById(childId)
				.orElseThrow(()->new ItemNotFoundException("could not find parent by id:" + id+seperator+itemSpace));

		return this.itemDao.findByChildren_id(childId, PageRequest.of(page, size, Direction.DESC, "id")).stream()
				.map(this.itemConverter::convertToBoundary)// Stream<ItemBoundary>
				.collect(Collectors.toList());// List<ItemBoundary>
	}


	@Override
	@Transactional
	public void connectItems(String adminSpace, String adminEmail, String id, String itemSpace,
			ItemIdBoundary itemIdBoundary) {
		// Log
		this.log.debug("Connecting items");
		
		String seperator = "!!!";
		ItemEntity parent = 
				this.itemDao
				.findById(id+seperator+itemSpace)
				.orElseThrow(()->new ItemNotFoundException("could not find original by id:" + id+seperator+itemSpace));

		String childId= itemIdBoundary.getEntityLikeID();
		ItemEntity child = 
				this.itemDao
				.findById(childId)
				.orElseThrow(()->new ItemNotFoundException("could not find response by id:" + childId));
		parent.addChildren(child);
		child.setParent(parent);

		this.itemDao
		.save(child);

	}

	@Override
	@Transactional(readOnly=true)
	public List<ItemBoundary> getAllItemByNamePattern(String space, String email, String pattern, int size, int page) {
		// Log
		this.log.debug("Getting all items by name pattern");
		
		return this.itemDao.findAllByNameLike(pattern,PageRequest.of(page, size, Direction.DESC, "id"))
				.stream()
				.map(this.itemConverter::convertToBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly=true)
	public List<ItemBoundary> getAllItemByTypePattern(String space, String email, String type, int size, int page) {
		// Log
		this.log.debug("Getting all items by type like");
		
		return this.itemDao.findAllByTypeLike(type,PageRequest.of(page, size, Direction.DESC, "id"))
				.stream()
				.map(this.itemConverter::convertToBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly=true)
	public List<ItemBoundary> getAllItemsInRange(String space, String email,double lng, double lat, int distance, int size, int page) {
		// Log
		this.log.debug("Getting all items by range");
		
		double minLng = lng - distance;
		double maxLng = lng + distance;
		double minLat = lat - distance;
		double maxLat = lat + distance;
		return this.itemDao.findAllByLngBetweenAndLatBetween(minLng,maxLng,minLat,maxLat,PageRequest.of(page, size, Direction.DESC, "id"))
				.stream()
				.map(this.itemConverter::convertToBoundary)
				.collect(Collectors.toList());
	}


}
