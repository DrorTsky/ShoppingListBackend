package dts.logic;

import java.util.List;

import org.springframework.stereotype.Service;

import dts.ItemBoundary;
import dts.ItemID;
import dts.UserBoundary;
import dts.UserId;

public interface ItemsService {

	public ItemBoundary create(String managerSpace, String managerEmail, ItemBoundary newItem);

	public ItemBoundary update(String space , String email,String itemSpace,String id,ItemBoundary newItem);

	public ItemBoundary getSpecificItem(String space , String email,String itemSpace,String id);

	public List<ItemBoundary> getAll(String space,String email,int size, int page);
	
	public List<ItemBoundary> getAll(String space, String email);

	public void deleteAll(String Space,String email);
}