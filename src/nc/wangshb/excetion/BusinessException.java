package nc.wangshb.excetion;

public class BusinessException extends Exception {
	
    /**
     * BusinessException ������ע�⡣
     */
    public BusinessException() {
        super();
    }

	public BusinessException(String msg){
		super(msg);
	}
}
