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

import dts.OperationBoundary;
import dts.OperationConverter;
import dts.OperationID;
import dts.data.OperationEntity;

//@Service
public class MockupOperationService implements OperationsService{

	private Map<String, OperationEntity> operations;
	private String space;
	private AtomicLong keyGenerator;
	private OperationConverter operationConverter;
	
	@PostConstruct
	public void init() {
		
		// thread safe Map
		this.operations = Collections.synchronizedMap(new HashMap<>());
		//ID counter
		this.keyGenerator = new AtomicLong(1L);
		
	}
	
	@Value("${spring.application.name:ShoppingList}")
	public void setInitProperty(String space) {
		this.space = space;
		
	}
	
	@Autowired
	public void setOperationConverter(OperationConverter operationConverter) {
		this.operationConverter = operationConverter;
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
		this.operations.put(entity.getId(), entity);
		
		OperationBoundary rv = this.operationConverter.convertToBoundary(entity);
		
		return rv;
	}

	@Override
	public List<OperationBoundary> getAllOperations(String adminSpace, String adminEmail) {
		
		//creates an OperationBoundary List using stream by changing every entity in operations to boundary and collecting them
		List<OperationBoundary> allOperations =  this.operations.values().stream().map(operationConverter::convertToBoundary).collect(Collectors.toList());
		
		return allOperations;
	}

	@Override
	public void deleteAllActions(String adminSpace, String adminEmail) {
		this.operations.clear();		
	}

}