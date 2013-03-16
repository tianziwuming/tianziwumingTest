package nc.wangshb.SocketTest;

public class SocketTestDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//启动服务端
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
			
			//启动客户端
			ClientSocketTest client=new ClientSocketTest("localhost",1234);
			TestVO[] obj=getObjects();
			printObject(obj,"客户端输入数据1:");
			client.setObj(obj);
			client.doBusi(false);
			
			//Thread.sleep(3000);
			obj[0].setName("e");
			printObject(obj,"客户端输入数据2:");
			client.doBusi(false);
			
			//Thread.sleep(3000);
			obj[1].setName("f");
			printObject(obj,"客户端输入数据3:");
			client.doBusi(false);
			
			//Thread.sleep(3000);
			obj[1].setNumber(3);
			printObject(obj,"客户端输入数据4:");
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
		//System.out.print("客户端输入数据:[");
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
