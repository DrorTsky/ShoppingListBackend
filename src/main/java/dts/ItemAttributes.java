package dts;

import java.util.HashMap;
import java.util.Map;

public class ItemAttributes {

	private String key1;
	private String key2;
	private Map<String, Object> map;
	
	public ItemAttributes() {
		System.err.println("in empty const in itemAttr\n");
		this.key1 = "temp";
		this.key2 = "temp";	
		this.map = new HashMap<String, Object>();
	}
	
	public ItemAttributes(Map<String, Object> map) {
		super();
		this.map = map;
	}
	
	public ItemAttributes(String key1, String key2) {
		super();
		System.err.println("in copy const in itemAttr\n");
		this.key1 = key1;
		this.key2 = key2;
		this.map.put("key1", key1);
		this.map.put("key2", key2);
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getKey2() {
		return key2;
	}

	public void setKey2(String key2) {
		this.key2 = key2;
	}
	
	public Map<String, Object> getMap() {
		return map;
	}
	
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}
