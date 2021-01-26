package dts;

public class OperationAttributes {
	

	private String key1;
	
	private String key2;	
	
	
	public String stringifiedOperationAttributes() {
		
		String seperator = "!!!";
		return key1+seperator+key2;
	}
	
	public OperationAttributes() {
		super();
		this.key1 = "temp";
		this.key2 = "temp";
	}
	public OperationAttributes(String entityAttributes) {
		String seperator = "!!!";
		String keys[] = entityAttributes.split(seperator);
		this.key1 = keys[0];
		this.key2 = keys[1];
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
	
	@Override
	public String toString() {
		return "OperationAttributes [key1=" + key1 + ", key2=" + key2 + "]";
	}
}
