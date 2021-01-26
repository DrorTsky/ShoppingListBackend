package dts.logic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dts.ItemBoundary;
import dts.ItemID;
import dts.UserBoundary;
import dts.UserId;
import dts.data.ItemEntity;
import dts.data.UserEntity;
import dts.exceptions.ItemNotFoundException;
import dts.exceptions.ItemsListNotFoundException;
import dts.exceptions.UserNotAdminException;
import dts.exceptions.UserNotFoundException;

//@Service
public class MockupItemService implements ItemsService {

	private Map<String, ItemEntity> items;
	private String space;
	private ItemConverter itemConverter;
	private AtomicLong keyGenerator;

	@PostConstruct
	public void init() {

		this.items = Collections.synchronizedMap(new HashMap<>());
		this.keyGenerator = new AtomicLong(1L);

	}

	@Value("${spring.application.name:ShoppingList}")
	public void setInitProperty(String space) {
		this.space = space;
	}

	@Autowired
	public void setItemConverter(ItemConverter itemConverter) {
		this.itemConverter = itemConverter;
	}

	@Override
	public ItemBoundary create(String managerSpace, String managerEmail,ItemBoundary item) {
		
		String key = "" + this.keyGenerator.getAndIncrement();
		
		item.setId(new ItemID(key, this.space));

		// create new item entity to store in mock-up "database"
		ItemEntity entity = this.itemConverter.convertToEntity(item);

		// stores the entity in the mock-up "database"
		this.items.put(entity.getId(), entity);

		// creates new and updated item boundary to return
		ItemBoundary rv = this.itemConverter.convertToBoundary(entity);

		return rv;
	}




	@Override
	public ItemBoundary update(String space, String email, String itemSpace, String id,ItemBoundary item) {
		// generate entity id
		String idToFind = id + "@@@" + itemSpace;
		// find the specific entity using id
		ItemEntity entity = this.items.get(idToFind);

		if (entity != null) {
			// creates new entity from updated itemBoundary
			ItemEntity updatedEntity = this.itemConverter.convertToEntity(item);
			// updates the itemEntity with given id
			this.items.put(idToFind, updatedEntity);
			// returns updated itemsBoundary
			return this.itemConverter.convertToBoundary(updatedEntity);
		} else {
			// if not found throw exception
			throw new ItemNotFoundException("could not find item with id: " + idToFind);
		}
	}

	@Override
	public List<ItemBoundary> getAll(String space, String email) {
		if (items == null) {
			throw new ItemsListNotFoundException("list of items not found");
		} else {
			List<ItemBoundary> allItems = this.items.values().stream().map(itemConverter::convertToBoundary)
					.collect(Collectors.toList());
			return allItems;
		}
	}

	@Override
	public void deleteAll(String Space, String email) {
		this.items.clear();
		
	}

	@Override
	public ItemBoundary getSpecificItem(String space, String email, String id,String itemSpace) {
		// TODO Auto-generated method stub
		String seperator = "@@@";
		String idToFind = id + seperator + itemSpace;
		
		ItemEntity entity = this.items.get(idToFind);
		
		
		if (entity != null) {
			return this.itemConverter.convertToBoundary(entity);
		} else {
			throw new ItemNotFoundException("could not find item with id: " + id);
		}
	}

	@Override
	public List<ItemBoundary> getAll(String space, String email, int size, int page) {
		// TODO Auto-generated method stub
		return null;
	}

}