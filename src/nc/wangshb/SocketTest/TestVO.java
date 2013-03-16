package nc.wangshb.SocketTest;

import java.io.Serializable;

public class TestVO implements Serializable{
	
	private String name;
	private String value;
	private int number;
	
	
	public TestVO(String name, String value, int number) {
		super();
		this.name = name;
		this.value = value;
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String toString(){
		return this.getClass().getName()+": name="+name+", value="+value+", number="+number;
	}
	

}
