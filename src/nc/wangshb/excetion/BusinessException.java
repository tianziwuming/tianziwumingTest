package nc.wangshb.excetion;

public class BusinessException extends Exception {
	
    /**
     * BusinessException 构造子注解。
     */
    public BusinessException() {
        super();
    }

	public BusinessException(String msg){
		super(msg);
	}
}
