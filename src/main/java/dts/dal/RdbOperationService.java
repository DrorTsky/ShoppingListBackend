package dts.dal;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dts.ItemBoundary;
import dts.OperationBoundary;
import dts.OperationConverter;
import dts.OperationID;
import dts.data.OperationEntity;
import dts.logic.EnhancedOperationService;
import dts.logic.ItemConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
public class RdbOperationService implements EnhancedOperationService{
	
//	private Map<String, OperationEntity> operations;
	private String space;
	private AtomicLong keyGenerator;
	private OperationConverter operationConverter;
	private OperationDao operationDao;
	
	private Log log = LogFactory.getLog(RdbOperationService.class);
	
	public RdbOperationService(OperationDao operationDao) {
		super();
		this.operationDao = operationDao;
	}
	
	@Autowired
	public void setOperationConverter(OperationConverter operationConverter) {
		this.operationConverter = operationConverter;
	}
	
	@PostConstruct
	public void init() {
		//ID counter
		this.keyGenerator = new AtomicLong(1L);
	}
	
	@Value("${spring.application.name:ShoppingList}")
	public void setInitProperty(String space) {
		this.space = space;
		
	}
	
	@Override
	public Object invokeOperation(OperationBoundary operation) {
		
		//creates Id for the operation boundary id
		String key = "" + this.keyGenerator.getAndIncrement();
		//creates new OperationID using our teams space and generated key
		operation.setOperationId(new OperationID(space, key));
		//creates new entity using converter
		OperationEntity entity = this.operationConverter.convertToEntity(operation);
		//stores entity in our database
//		this.operations.put(entity.getOperationId(), entity);
		entity = this.operationDao.save(entity);
		
		OperationBoundary rv = this.operationConverter.convertToBoundary(entity);
		
		return rv;
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		
		//creates an OperationBoundary List using stream by changing every entity in operations to boundary and collecting them
//		List<OperationBoundary> allOperations =  this.operations.values().stream().map(operationConverter::convertToBoundary).collect(Collectors.toList());
		Iterable<OperationEntity> entities =  this.operationDao.findAll();
		
		List<OperationBoundary> rv = new LinkedList<>();
		for(OperationEntity entity : entities) {
			OperationBoundary temp = this.operationConverter.convertToBoundary(entity);
			rv.add(temp);
		}
		
		// Log
		this.log.debug("Getting all operations");
				
		return rv;
	}

	@Override
	public void deleteAllActions(String adminSpace, String adminEmail) {
//		this.operations.clear();		
		// Log
		this.log.debug("Deleting all operations");
		
		this.operationDao.deleteAll();
	}


	@Override
	public List<OperationBoundary> getAllOperationsBoundaries(String adminSpace, String adminEmail, int size,
			int page) {
		// Log
		this.log.debug("Getting all operations boundaries");
		
		// TODO Auto-generated method stub
		return this.operationDao
				.findAll(PageRequest.of(page, size, Direction.DESC, "operationId"))
				.stream() // Stream<ItemEntity>
				.map(this.operationConverter::convertToBoundary)// Stream<ItemBoundary>
				.collect(Collectors.toList());// List<ItemBoundary>
	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<ItemBoundary> getItemChildren(String userSpace, String userEmail, String itemId, String itemSpace,int size, int page) {
//		String seperator = "!!!";
//		String parendId = itemId+seperator+itemSpace;
//
//		return this.itemDao
//				.findAllByParent_itemId(parendId, PageRequest.of(page, size, Direction.DESC, "itemId"))
//				.stream() // Stream<ItemEntity>
//				.map(this.itemConverter::convertToBoundary)// Stream<ItemBoundary>
//				.collect(Collectors.toList());// List<ItemBoundary>
//	}

}
