package nc.wangshb.SocketTest;

import nc.wangshb.SocketTest.streamaccessor.IServerAndClientStreamAccessor;
import nc.wangshb.SocketTest.streamaccessor.ObjectStreamAccessor;
import nc.wangshb.SocketTest.streamaccessor.StringStreamAccessor;
import nc.wangshb.excetion.BusinessException;

/**
 * 
 * @author wangshb
 *
 */
public class SocketTestUtil {
	
	/**
	 * 
	 * @param key
	 * @return
	 * @throws BusinessException
	 */
	public static IServerAndClientStreamAccessor getAccessor(int key) throws BusinessException{
		switch(key){
			case SocketTestConstants.ACCESSOR_OBJECT:
				return new ObjectStreamAccessor();
			case SocketTestConstants.ACCESSOR_STRING:
				return new StringStreamAccessor();
			default:
				throw new BusinessException("无法识别的流处理代号");
		}
	}

}
