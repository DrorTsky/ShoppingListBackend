package dts.logic;

import java.util.List;


import dts.ItemBoundary;
import dts.ItemIdBoundary;

public interface EnhancedItemsService extends ItemsService {

	public List<ItemBoundary> getItemChildren(String userSpace, String userEmail, String id, String itemSpace,int size, int page);

	public List<ItemBoundary> getItemParent(String userSpace, String userEmail, String id, String itemSpace,int size, int page);

	public void connectItems(String adminSpace, String adminEmail, String id, String itemSpace, ItemIdBoundary itemIdBoundary);
	
	public List<ItemBoundary> getAllItemByNamePattern(String space, String email, String pattern, int size, int page);

	public List<ItemBoundary> getAllItemByTypePattern(String space, String email, String type, int size, int page);

	public List<ItemBoundary> getAllItemsInRange(String space, String email, double lng, double lat, int distance, int size, int page);

}
