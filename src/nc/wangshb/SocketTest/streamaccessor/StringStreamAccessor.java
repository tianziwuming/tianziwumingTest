package nc.wangshb.SocketTest.streamaccessor;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import nc.wangshb.excetion.BusinessException;

public class StringStreamAccessor implements IServerAndClientStreamAccessor {

	public int getAccessorNumber() {
		// TODO Auto-generated method stub
		return 1;
	}

	public Object getUserObj() {
		// TODO Auto-generated method stub
		return null;
	}

	public void processClient(OutputStream out, Object userobj)
			throws IOException {
		PrintWriter writer=new PrintWriter(new BufferedOutputStream(out),true);
		if(userobj==null || userobj.toString().equals(""))
			return;
		String writeString=userobj.toString();
		try{
			writer.write(writeString);
		}catch(Exception ex){
			try{
				writer.close();
			}catch(Exception e){}
		}

	}

	public void processServer(InputStream in) throws BusinessException,
			EOFException {
		// TODO Auto-generated method stub

	}

}
