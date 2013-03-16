package wangshb.concurrentTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//testExecutor();
		
		//testSameThreadRun();
		
		testCyclicBarrier();
		
		//testCountDownLatch();
	}
	
	
	//当多线程互相等待时，用CyclicBarrier，当一个线程等待多个线程都执行完时，用CountDownLatch。
	//CyclicBarrier.await中的等待线程只有等到CyclicBarrier唤醒时，才会结束。而CountDownLatch.countDown()被调用后直接返回，现成即可结束。
	//CyclicBarrier和CountDownLatch可以互相转换。如： 方法testCyclicBarrier(latch.await()之前的代码)  和  testCountDownLatch方法同时实现了一个线程等待另外两个线程。
	private static void testCyclicBarrier(){
		CyclicBarrier barrier=new CyclicBarrier(2,new Thread(new BarrierThread("CyclicBarrierThread",null,null)));
		CountDownLatch latch=new CountDownLatch(2);
		
		ExecutorService threadPool=java.util.concurrent.Executors.newFixedThreadPool(2);
		BarrierThread thread1=new BarrierThread("Thread1",barrier,latch);
		BarrierThread thread2=new BarrierThread("Thread2",barrier,latch);
		
		threadPool.execute(thread1);
		threadPool.execute(thread2);
		
		try{
			latch.await();
			System.out.println("The End!");
		}catch(Exception ex){
			
		}
		
		//CyclicBarrier可以重复利用，调用reset重新计数，但是CountLatch不能，只能用一次。下面的latch.await方法就直接返回，没有堵塞。
		//barrier.reset();
		//线程被唤醒后，CyclicBarrier的计数器被重置!!!!!!!!!!!!!!!!!!!!!!!
		System.out.println("Current CyclicBarrier waiting count:"+barrier.getNumberWaiting());
		threadPool.execute(thread1);
		threadPool.execute(thread2);
		
		try{
			//这里的await直接返回，不堵塞，因为latch.getCount==0
			latch.await();
			System.out.println("The End!");
		}catch(Exception ex){
			
		}
		
		threadPool.shutdown();
	}
	
	
	private static void testCountDownLatch(){
		CountDownLatch latch=new CountDownLatch(2);
		ExecutorService threadPool=java.util.concurrent.Executors.newFixedThreadPool(3);
		
		LatchThread thread1=new LatchThread("Thread1",latch);
		LatchThread thread2=new LatchThread("Thread2",latch);
		LatchThread thread3=new LatchThread("WaitThread",latch,true);
		
		threadPool.execute(thread1);
		threadPool.execute(thread2);
		threadPool.execute(thread3);
		
		threadPool.shutdown();
	}
	
	private static class LatchThread implements Runnable{
		private String name;
		private CountDownLatch latch;
		boolean waitLatch=false;
		
		public LatchThread(String name,CountDownLatch latch){
			this.name=name;
			this.latch=latch;
		}
		
		public LatchThread(String name,CountDownLatch latch,boolean waitLatch){
			this(name,latch);
			this.waitLatch=waitLatch;
		}
		
		public void run(){
			try{
				if(this.waitLatch){
					latch.await();
					System.out.println("Thread "+name+" awaked and running");
					System.out.println("The END");
				}else{
					System.out.println("Thread "+name+" Running");
					latch.countDown();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	}
	
	private static class BarrierThread implements Runnable{
		private String name;
		private CyclicBarrier barrier;
		private CountDownLatch latch;
		public BarrierThread(String name,CyclicBarrier barrier,CountDownLatch latch){
			this.name=name;
			this.barrier=barrier;
			this.latch=latch;
		}

		public void run() {
			System.out.println("Thread "+name+" running!");
			try{
				if(barrier!=null){
					barrier.await();
					System.out.println("Thread "+name+" awaked!");
				}
			}catch(Exception ex){}
			
			try{
				if(latch!=null){
					if(latch.getCount()>0)
						latch.countDown();
				}
			}catch(Exception ex){}
			
		}
		
	}
	
	//同一个Thread对象实例，不能调用两次start();
	private static void testSameThreadRun(){
		TestThread thread1=new TestThread("thread1");
		Thread thread=new Thread(thread1);
		thread.start();
		
		try {
			//Thread.sleep(1000);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		boolean isAlive= thread.isAlive();
		boolean isInterrupt=thread.isInterrupted();
		boolean isdaomon=thread.isDaemon();
		
		System.out.println("isAlive="+isAlive);
		System.out.println("isInterrupt="+isInterrupt);
		System.out.println("isdaomon="+isdaomon);
		
		thread.start();
	}
	
	//测试线程池ExecutorService静态方法
	private static void testExecutor(){
		//ExecutorService executor=java.util.concurrent.Executors.newFixedThreadPool(2);
		//ExecutorService executor=java.util.concurrent.Executors.newCachedThreadPool();
		//ExecutorService executor=java.util.concurrent.Executors.newSingleThreadExecutor();
		//ExecutorService executor=java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
		ScheduledExecutorService executor=java.util.concurrent.Executors.newScheduledThreadPool(2);

		TestThread thread1=new TestThread("thread1");
		TestThread thread2=new TestThread("thread2");
		TestThread thread3=new TestThread("thread3");
		TestThread thread4=new TestThread("thread4");
		TestThread thread5=new TestThread("thread5");
		
		executor.execute(thread1);
		executor.execute(thread2);
//		try{
//			Thread.sleep(2000);
//		}catch(Exception ex){
//			
//		}
		executor.execute(thread3);
		executor.execute(thread4);
		executor.execute(thread5);
		
		executor.schedule(thread4, 2000, TimeUnit.MILLISECONDS);
		executor.schedule(thread5, 2000, TimeUnit.MILLISECONDS);
		
		executor.scheduleAtFixedRate(thread4, 1000, 2000, TimeUnit.MILLISECONDS);
		//executor.shutdown();
		
	}
	
	private static class TestThread implements Runnable{

		private String name;
		public TestThread(String name){
			this.name=name;
		}
		
		public void run() {
			System.out.println(Thread.currentThread().getName()+"正在执行"+name);
			try{
				//Thread.sleep(1000);
			}catch(Exception ex){
				
			}
		}
		
	}

}
