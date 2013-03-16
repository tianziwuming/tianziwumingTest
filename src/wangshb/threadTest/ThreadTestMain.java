package wangshb.threadTest;

public class ThreadTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Object obj=new Object();
//		WaitThread thread1=new WaitThread("thread1",obj);
//		Thread realThread=new Thread(thread1);
//		realThread.start();
//		
//		//BreakThread thread2=new BreakThread(obj);
//		//new Thread(thread2).start();
//		synchronized(obj){
//			System.out.println("MainThread is Sleepting");
//			try{
//				Thread.sleep(2000);
//			}catch(Exception ex){}
//			System.out.println("MainThread is sleep done");
//			//thread1.getObj();
//		}
//		System.out.println("获得Object对象:"+obj);
//		
//
//		
//		System.out.println("The Main End");
		InterruptTestThread thread=new InterruptTestThread();
		new Thread(thread).start();

	}
	
	/**
	 * 1.调用Object.wait和Objec.notifyAll方法时，必须活的该对象的对象锁，所以需要用到Synchroniezd同步器。
	 * 2.Thread.sleep方法不会释放对象锁，而Object.wait会使当前线程释放对象锁(Synchrnized锁住的对象)
	 * @author wangshubao
	 *
	 */
	public static class WaitThread implements Runnable{

		private Object obj;
		private String name;
		
		public WaitThread(String name,Object obj){
			this.name=name;
			this.obj=obj;
		}
		
		public void run() {
			System.out.println("Thread "+Thread.currentThread().getName()+": 正在运行");
			try{
				synchronized(obj){
					System.out.println("Thread "+name+"sleeping");
					//Thread.sleep(5000);
					obj.wait(5000);
				}
			}catch(InterruptedException ex){
				System.out.println("Thread "+name+" 的wait操作捕捉到InterruptedException异常");
				ex.printStackTrace();
			}catch(IllegalMonitorStateException ex){
				System.out.println("Thread "+name+" 的wait操作捕捉到IllegalMonitorStateException异常");
				ex.printStackTrace();
			}
			System.out.println("Thread "+name+": The End");
		}
		
		public Object getObj(){
			return obj;
		}
		
	}
	
	public static class BreakThread implements Runnable{
		private Object obj;
		public BreakThread(Object obj){
			this.obj=obj;
		}
		public void run(){
			try{
				Thread.sleep(1000);
				synchronized(obj){
					obj.notifyAll();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			System.out.println("Break Thread End");
			//obj.notifyAll();
			
		}
		
	}
	
	public static class InterruptTestThread implements Runnable{

		public void run() {
			RunningThread thread1=new RunningThread(5000);
			Thread realThread=new Thread(thread1);
			
			
			RunningThread thread2=new RunningThread(6000);
			Thread realThread2=new Thread(thread2);

			realThread.start();
			realThread2.start();
			
			System.out.println("realThread.isInterrupted()="+realThread.isInterrupted());
			realThread.interrupt();
			System.out.println("realThread.isInterrupted()="+realThread.isInterrupted());
			//realThread.interrupted();
			try{
				realThread.join();
				realThread2.join();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			System.out.println("join 后realThread.isInterrupted()="+realThread.isInterrupted());
		}
		
	}
	
	
	public static class RunningThread implements Runnable{
		private int count=10000;
		public RunningThread(int count){
			this.count=count;
		}
		public RunningThread(){
			super();
		}
		public void run(){
			for(int i=0;i<count;i++){
				System.out.println("i="+i);
			}
//			synchronized(this){
//				try {
//					wait();
//				} catch (InterruptedException e) {
//					// TODO 自动生成的 catch 块
//					System.out.println("RunningThread 被中断了");
//					e.printStackTrace();
//				}
//			}
		}
	}

}
