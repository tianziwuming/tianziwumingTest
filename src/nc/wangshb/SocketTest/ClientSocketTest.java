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
 * socket���Կͻ���
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
	 * ���췽��
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
		System.out.println("�ͻ�����������:"+vos.toString());
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
			//����accessorNumber�Է������˳�ʼ��������
			out.write(accessor.getAccessorNumber());
			/**
			 * ***********************************************
			 * �˴�һ��Ҫ��סflush
			 * ***********************************************
			 */
			out.flush();
			
			Object obj=this.getObj();
			accessor.processClient(out,obj);
		}catch(IOException ex){
			ex.printStackTrace();
			throw new BusinessException(ex.getMessage());
		}finally{
			//wangshb����test ���Բ��ر���������ظ�������ݡ�
			/**
			 * *****************************************************************
			 * �˴��������out.close��������socket�Ͽ������������close������socketһֱ���ӡ�
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
				throw new BusinessException("�޷�������ַ��"+host);
			} catch (IOException e) {
				throw new BusinessException(e.getMessage());
			}
		}
	}
	
	private void printSocketInfo(){
		System.out.println("�ͻ��˶˿�:"+clientSocket.getLocalPort());
	}

	//getter��setter����
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
