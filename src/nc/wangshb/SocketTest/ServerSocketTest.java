package nc.wangshb.SocketTest;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import nc.wangshb.SocketTest.streamaccessor.IServerAndClientStreamAccessor;
import nc.wangshb.SocketTest.streamaccessor.ObjectStreamAccessor;
import nc.wangshb.excetion.BusinessException;
/**
 * 
 * @author wangshb
 * Socket测试服务端
 *
 */
public class ServerSocketTest {

	private ServerSocket serverSocket=null;
	private final int DEFAULT_PORT=3456;
	private int port;
	private int count=1;
	
	public ServerSocketTest(int port) throws BusinessException{
		this.port=port;
		initServerSocket();
	}
	
	public ServerSocketTest() throws BusinessException{
		this.port=DEFAULT_PORT;
		initServerSocket();
	}
	
	//初始化serverSocket
	public void initServerSocket() throws BusinessException{
		if(serverSocket==null){
			try{
				serverSocket=new ServerSocket(port);
			}catch(java.net.BindException ex){
				throw new BusinessException("端口"+port+"已经被绑定");
			}catch(IOException ex){
				ex.printStackTrace();
				throw new BusinessException(ex.getMessage());
			}
		}
	}
	
	//监听
	public void accept() throws BusinessException{
		if(serverSocket==null)
			throw new BusinessException("服务端socke为正常初始化");
		//有待改善--------------------------------------------
		while(!serverSocket.isClosed()){
			try{
				Socket socket=serverSocket.accept();
				printClientSocket(socket);
				BusiThread busithread=new BusiThread(socket);
				new Thread(busithread,"ServerSocket线程"+count).start();
			}catch(IOException ex){
				close();
				throw new BusinessException(ex.getMessage());
			}
		}
	}
	
	public void printClientSocket(Socket soct){
		String host=soct.getInetAddress().getHostName();
		int port=soct.getPort();
		System.out.println("收到连接:host="+host+", port="+port);
	}
	
	
	public void close(){
		if(serverSocket==null || serverSocket.isClosed())
			return;
		try{
			serverSocket.close();
		}catch(IOException ex){
		}
	}
	
	/**
	 * //获得处理输入输出流的工具类
	 * @return
	 */
	private IServerAndClientStreamAccessor getAccessor(){
		return new ObjectStreamAccessor();
	}
	
	/**
	 * 获得处理输入输出流的工具类_new
	 */
	private IServerAndClientStreamAccessor getAccessor(int number) throws BusinessException{
		IServerAndClientStreamAccessor accessor= SocketTestUtil.getAccessor(number);
		System.out.println("使用流处理器类为："+accessor.getClass().getName());
		return accessor;
	} 
	
	/**
	 * //具体业务类
	 * @param socket
	 * @throws BusinessException
	 */
	private void doBusi(Socket socket) throws BusinessException{
		if(socket==null){
			return;
		}
		
		//第二种方法，处理inputstream
//		InputStream input=null;
//		try{
//			input=socket.getInputStream();
//		}catch(IOException ex){
//			throw new BusinessException(ex.getMessage());
//		}

		/**
		 * //无限期循环获取输入数据，直到客户端断掉连接
		 */
		while(true){
			try{
				//第一种方法：处理socket
				System.out.println("【服务器】：处理socket......");
				processSocket(socket);
				//第二种方法，处理inputstream
				//processInputStream(input);
			}catch(EOFException ex){
				System.out.println("连接断开");
				break;
			}
		}
		
		//begin ncm wangshb 第二次获得输入数据
		//processSocket(socket);

		try{
			socket.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param socket
	 * @throws BusinessException
	 * @throws EOFException
	 */
	private void processSocket(Socket socket) throws BusinessException,EOFException{
		InputStream input=null;
		try{
			input=socket.getInputStream();
		}catch(IOException ex){
			throw new BusinessException(ex.getMessage());
		}
		processInputStream(input);
	}
	
	/**
	 * 
	 * @param input
	 * @throws BusinessException
	 * @throws EOFException
	 */
	private void processInputStream(InputStream input) throws BusinessException,EOFException{
		
		//第一步：读取accessorNumber，用来初始化流处理器
		int accessorNumber=0;
		try{
			accessorNumber=input.read();
			if(accessorNumber==-1){
				throw new EOFException();
			}
		}catch(EOFException ex){
			throw ex;
		}catch(IOException ex){
			ex.printStackTrace();
			throw new BusinessException("获取流处理器信息错误");
		}
		IServerAndClientStreamAccessor accessor=getAccessor(accessorNumber);

		//第二步：用流处理器来处理输入流
		accessor.processServer(input);
		
		Object obj=accessor.getUserObj();
		this.processInputObj(obj);
	}
	
	/**
	 * //输出数据
	 * @param obj
	 */
	private void processInputObj(Object obj){
		SocketTestDemo.printObject(obj, "服务器端获取数据"+(count++)+"：");
		//System.out.println("服务器端获取数据："+obj.toString());
	}
	
	
	/**
	 * 内部类，服务器端线程。
	 */
	class BusiThread implements java.lang.Runnable{
		private Socket socket=null;

		public BusiThread(Socket socket){
			this.socket=socket;
		}
		
		public void run() {
			try{
				doBusi(socket);
			}catch(Exception ex){
				ex.printStackTrace();
				if(socket!=null && !socket.isClosed()){
					try{
						socket.close();
					}catch(Exception e){
					}
				}
			}
		}
		
		
	}
	
}
