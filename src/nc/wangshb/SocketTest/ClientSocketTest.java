package nc.wangshb.SocketTest;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import nc.wangshb.SocketTest.streamaccessor.IServerAndClientStreamAccessor;
import nc.wangshb.SocketTest.streamaccessor.ObjectStreamAccessor;
import nc.wangshb.excetion.BusinessException;

/**
 * 
 * @author wangshb 
 * socket测试客户端
 *
 */
public class ClientSocketTest {

	private Socket clientSocket=null;
	private final int DEFAULT_PORT=3456;
	private final String DEFAULT_HOST="127.0.0.1";
	private int port;
	private String host;
	private Object obj;
	/**
	 * 构造方法
	 * @param port
	 */
	public ClientSocketTest(String host,int port){
		this.host=host;
		this.port=port;
	}
	
	public ClientSocketTest(){
		this.port=DEFAULT_PORT;
		this.host=DEFAULT_HOST;
	}
	
	public IServerAndClientStreamAccessor getAccessor(){
		return new ObjectStreamAccessor();
	}
	
	public TestVO[] getObjects(){
		TestVO[] vos=new TestVO[2];
		vos[0]=new TestVO("a","b",1);
		vos[1]=new TestVO("c","d",2);
		System.out.println("客户端输入数据:"+vos.toString());
		return vos;
	}
	
	public void doBusi() throws BusinessException{
		doBusi(true);
	}
	
	public void doBusi(boolean isAutoClose) throws BusinessException{
		initSocket();
		OutputStream out=null;
		try{
			out=clientSocket.getOutputStream();
			IServerAndClientStreamAccessor accessor=this.getAccessor();
			//accessor.setUserObj(this.getObjects());
			//发送accessorNumber以方便服务端初始化流工具
			out.write(accessor.getAccessorNumber());
			/**
			 * ***********************************************
			 * 此处一定要记住flush
			 * ***********************************************
			 */
			out.flush();
			
			Object obj=this.getObj();
			accessor.processClient(out,obj);
		}catch(IOException ex){
			ex.printStackTrace();
			throw new BusinessException(ex.getMessage());
		}finally{
			//wangshb——test 测试不关闭输出流，重复输出数据。
			/**
			 * *****************************************************************
			 * 此处如果调用out.close方法，则socket断开；如果不调用close方法，socket一直连接。
			 * *******************************************************************
			 */
			if(out!=null){
				try{
					if(isAutoClose)
						out.close();
				}catch(Exception ex){}
			}
		}
		//close();
	}
	
	public void close(){
		if(clientSocket==null || clientSocket.isClosed())
			return;
		try{
			clientSocket.close();
		}catch(Exception ex){
		}
	}
	
	private void initSocket() throws BusinessException{
		if(clientSocket==null || clientSocket.isClosed()){
			try{
				clientSocket=new Socket(host,port);
				printSocketInfo();
			} catch (UnknownHostException e) {
				throw new BusinessException("无法解析地址："+host);
			} catch (IOException e) {
				throw new BusinessException(e.getMessage());
			}
		}
	}
	
	private void printSocketInfo(){
		System.out.println("客户端端口:"+clientSocket.getLocalPort());
	}

	//getter和setter方法
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	
	
}
