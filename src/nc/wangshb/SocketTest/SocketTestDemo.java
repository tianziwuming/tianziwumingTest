package nc.wangshb.SocketTest;

public class SocketTestDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//���������
			 new Thread(){

				public void run() {
					try{
						ServerSocketTest server=new ServerSocketTest(1234);
						server.accept();
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
				}
				 
			 }.start();
			
			//�����ͻ���
			ClientSocketTest client=new ClientSocketTest("localhost",1234);
			TestVO[] obj=getObjects();
			printObject(obj,"�ͻ�����������1:");
			client.setObj(obj);
			client.doBusi(false);
			
			//Thread.sleep(3000);
			obj[0].setName("e");
			printObject(obj,"�ͻ�����������2:");
			client.doBusi(false);
			
			//Thread.sleep(3000);
			obj[1].setName("f");
			printObject(obj,"�ͻ�����������3:");
			client.doBusi(false);
			
			//Thread.sleep(3000);
			obj[1].setNumber(3);
			printObject(obj,"�ͻ�����������4:");
			client.doBusi(false);
			
			Thread.sleep(3000);
			client.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}
	
	public static TestVO[] getObjects(){
		TestVO[] vos=new TestVO[2];
		vos[0]=new TestVO("a","b",1);
		vos[1]=new TestVO("c","d",2);
		//System.out.print("�ͻ�����������:[");
		return vos;
	}
	
	
	public static void printObject(Object obj,String warning){
		Object[] objs=null;
		if(obj instanceof Object[]){
			objs=(Object[])obj;
		}else{
			objs=new Object[]{obj};
		}
		System.out.print(warning+"[");
		for(Object vo:objs){
			System.out.print(vo.toString()+",");
		}
		System.out.println("]");
		
	}

}
