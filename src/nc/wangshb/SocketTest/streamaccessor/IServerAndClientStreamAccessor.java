package nc.wangshb.SocketTest.streamaccessor;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import nc.wangshb.excetion.BusinessException;

public interface IServerAndClientStreamAccessor {
	public int getAccessorNumber();

	public void processClient(OutputStream out,Object userobj) throws IOException;
	
	public void processServer(InputStream in) throws BusinessException,EOFException;
	
	public Object getUserObj();
}
