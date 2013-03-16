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
 * Socket���Է����
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
	
	//��ʼ��serverSocket
	public void initServerSocket() throws BusinessException{
		if(serverSocket==null){
			try{
				serverSocket=new ServerSocket(port);
			}catch(java.net.BindException ex){
				throw new BusinessException("�˿�"+port+"�Ѿ�����");
			}catch(IOException ex){
				ex.printStackTrace();
				throw new BusinessException(ex.getMessage());
			}
		}
	}
	
	//����
	public void accept() throws BusinessException{
		if(serverSocket==null)
			throw new BusinessException("�����sockeΪ������ʼ��");
		//�д�����--------------------------------------------
		while(!serverSocket.isClosed()){
			try{
				Socket socket=serverSocket.accept();
				printClientSocket(socket);
				BusiThread busithread=new BusiThread(socket);
				new Thread(busithread,"ServerSocket�߳�"+count).start();
			}catch(IOException ex){
				close();
				throw new BusinessException(ex.getMessage());
			}
		}
	}
	
	public void printClientSocket(Socket soct){
		String host=soct.getInetAddress().getHostName();
		int port=soct.getPort();
		System.out.println("�յ�����:host="+host+", port="+port);
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
	 * //��ô�������������Ĺ�����
	 * @return
	 */
	private IServerAndClientStreamAccessor getAccessor(){
		return new ObjectStreamAccessor();
	}
	
	/**
	 * ��ô�������������Ĺ�����_new
	 */
	private IServerAndClientStreamAccessor getAccessor(int number) throws BusinessException{
		IServerAndClientStreamAccessor accessor= SocketTestUtil.getAccessor(number);
		System.out.println("ʹ������������Ϊ��"+accessor.getClass().getName());
		return accessor;
	} 
	
	/**
	 * //����ҵ����
	 * @param socket
	 * @throws BusinessException
	 */
	private void doBusi(Socket socket) throws BusinessException{
		if(socket==null){
			return;
		}
		
		//�ڶ��ַ���������inputstream
//		InputStream input=null;
//		try{
//			input=socket.getInputStream();
//		}catch(IOException ex){
//			throw new BusinessException(ex.getMessage());
//		}

		/**
		 * //������ѭ����ȡ�������ݣ�ֱ���ͻ��˶ϵ�����
		 */
		while(true){
			try{
				//��һ�ַ���������socket
				System.out.println("����������������socket......");
				processSocket(socket);
				//�ڶ��ַ���������inputstream
				//processInputStream(input);
			}catch(EOFException ex){
				System.out.println("���ӶϿ�");
				break;
			}
		}
		
		//begin ncm wangshb �ڶ��λ����������
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
		
		//��һ������ȡaccessorNumber��������ʼ����������
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
			throw new BusinessException("��ȡ����������Ϣ����");
		}
		IServerAndClientStreamAccessor accessor=getAccessor(accessorNumber);

		//�ڶ���������������������������
		accessor.processServer(input);
		
		Object obj=accessor.getUserObj();
		this.processInputObj(obj);
	}
	
	/**
	 * //�������
	 * @param obj
	 */
	private void processInputObj(Object obj){
		SocketTestDemo.printObject(obj, "�������˻�ȡ����"+(count++)+"��");
		//System.out.println("�������˻�ȡ���ݣ�"+obj.toString());
	}
	
	
	/**
	 * �ڲ��࣬���������̡߳�
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
