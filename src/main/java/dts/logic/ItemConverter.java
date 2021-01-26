package dts.logic;

import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dts.ItemBoundary;
import dts.ItemID;
import dts.Location;
import dts.UserId;
import dts.data.ItemEntity;

@Component
public class ItemConverter {
	
	private ObjectMapper jackson;
	
	@PostConstruct // spring invokes this operation after the constructor and after injections (e.g. using @Autowired)
	public void init() {
		this.jackson = new ObjectMapper();
	}

	public ItemEntity convertToEntity(ItemBoundary boundary) {

		// create new UserEntity
		ItemEntity rv = new ItemEntity();

		rv.setId(boundary.generateEntityId());

		// set entity type
		rv.setType(boundary.getType());

		// set entity name
		rv.setName(boundary.getName());

		// set entity active
		rv.setActive(boundary.getActive());

		// set entity createtimestamp
		rv.setCreatedTimeStamp(boundary.getCreatedTimeStamp());
		
		// set entity createdBy
		rv.setCreatedBy(convertCreatedByToEntity(boundary.getCreatedBy()));

		// set entity location
		String loc = convertLocationToEntity(boundary.getLocation());
		String[] location = loc.split("!!!");
		Double lat = Double.parseDouble(location[0]);
		Double lng = Double.parseDouble(location[1]);
		rv.setLat(lat);
		rv.setLng(lng);
		
		// set entity attr
		rv.setAttr(this.toJsonString(
				boundary.getItemAttributes()));
	
		return rv;
	}
	
	// Created By
	public String convertCreatedByToEntity(UserId createdBy){
		String rv = createdBy.getEmail()+ "!!!" + createdBy.getSpace();
		
		return rv;
	}
	
	public UserId convertCreatedByToBoundary (String entity) {
		if (entity != null) {
			String[] strs = entity.split("!!!");
			return new UserId(strs[0], strs[1]);
		}else {
			return null;
		}
	}
	
	// Location
	public String convertLocationToEntity(Location boundary){
		String rv = boundary.getLat() + "!!!" + boundary.getLng();
		
		return rv;
	}
	
	public Location convertLocationToBoundary (String entity) {
		if (entity != null) {
			String[] doubles = entity.split("!!!");
			return new Location(Double.parseDouble(doubles[0]), Double.parseDouble(doubles[1]));
		}else {
			return null;
		}
	}
	
	
	public ItemBoundary convertToBoundary(ItemEntity entity) {

		// create new ItemBoundary
		ItemBoundary rv = new ItemBoundary();
		// get email and space from entity id
		String itemIdAndSpace[] = entity.getId().split("!!!");

		// set ItemBoundary Id
		rv.setId(new ItemID(itemIdAndSpace[0], itemIdAndSpace[1]));

		// set ItemBoundaty type
		rv.setType(entity.getType());
		
		// set UserBoundary userName
		rv.setName(entity.getName());

		// set Boundary active
		rv.setActive(entity.getActive());

		// set Boundary createtimestamp
		rv.setCreatedTimeStamp(entity.getCreatedTimeStamp());

		// set Boundary createdBy
		rv.setCreatedBy(convertCreatedByToBoundary(entity.getCreatedBy()));

		// set Boundary location
//		rv.setLocation(convertLocationToBoundary(entity.getLocation()));
		rv.setLocation(convertLocationToBoundary(entity.getLat().toString()+"!!!"+entity.getLng().toString()));
		rv.setLocation(new Location(entity.getLat(),entity.getLng()));

		// set Boundary attr
		rv.setItemAttributes(this.convertFromJsonStringToMap(entity.getAttr()));
		
		rv = new ItemBoundary(rv);

		return rv;
	}
	
	// use jackson for unmarshalling
	private Map<String, Object> convertFromJsonStringToMap(String json) {
		try {
			// keep the warning of this operation - as Map must not always be Map<String, Object>
			return this.jackson
				.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// use jackson for marshalling
	public String toJsonString(Map<String, Object> moreDetails) {
		try {
			return this.jackson
					.writeValueAsString(moreDetails);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String convertItemAttrToEntity(Map<String, Object> itemAttributes) {
        try {
            String json = this.jackson.writeValueAsString(itemAttributes);
            System.out.println(json);
            return json;
        } catch (Exception e) {
			throw new RuntimeException(e);
        }

	}

}
