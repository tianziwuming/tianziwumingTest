package wangshb.waitTest;

public class Test {
	
	public static void main(String [] args){
		try {
			new Test(2).customer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Object fullObj= new Object();
	private Object emptyObj= new Object();
	int[] array;
	int length;
	int size;
	public synchronized void customer() throws InterruptedException{
		while(length==0){
			synchronized(emptyObj){
				emptyObj.wait();
			}
		}
		length--;
		fullObj.notify();
	}
	
	public Test(int size){
		this.size=size;
		array=new int[size];
		length=0;
	}

}
