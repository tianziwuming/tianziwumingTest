package nc.wangshb.SocketTest.streamaccessor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import nc.wangshb.SocketTest.SocketTestConstants;
import nc.wangshb.excetion.BusinessException;

public class ObjectStreamAccessor implements IServerAndClientStreamAccessor {
	
	Serializable [] clientObjects=null;
	Object[] serverObjects=null;
	
	
	public ObjectStreamAccessor(){
		super();
	}
	
	public void setClientObjects(Serializable[] objects){
		clientObjects=objects;
	}

	
	/**
	 * //客户端调用
	 */
	public void processClient(OutputStream out,Object userobj) throws IOException {
		ObjectOutputStream objOut=new ObjectOutputStream(new BufferedOutputStream(out));
		try{
			objOut.writeObject(userobj);
			objOut.flush();
		}catch(IOException ex){
			
			throw ex;
		}finally{
			try{
				objOut.close();
			}catch(Exception e){}
			
		}
	}

	
	/**
	 * //服务器端调用
	 */
	public void processServer(InputStream in) throws BusinessException,EOFException {
		//初始化流
		ObjectInputStream objInput=null;
		try{
			objInput=new ObjectInputStream(new BufferedInputStream(in));
		}catch(EOFException ex){
			throw ex;
		}catch(IOException ex){
			throw new BusinessException(ex.getMessage());
		}
		
		//读取数据
		try{
			Object obj=objInput.readObject();
			if(obj instanceof Object[]){
				serverObjects=(Object[]) obj;
			}else{
				serverObjects=new Object[]{obj};
			}
		}catch(Exception ex){
			
			throw new BusinessException(ex.getMessage());
		}finally{
			try{
				objInput.close();
			}catch(Exception e){}
			
		}
	}

//		public void setUserObj(Object obj) {
//			if(obj instanceof Array){
//				clientObjects=(Serializable[])obj;
//			}else{
//				clientObjects=new Serializable[]{(Serializable) obj};
//			}
//			
//		}
	
	public Object getUserObj(){
		return this.serverObjects;
	}

	public int getAccessorNumber() {
		return SocketTestConstants.ACCESSOR_OBJECT;
	}

}
