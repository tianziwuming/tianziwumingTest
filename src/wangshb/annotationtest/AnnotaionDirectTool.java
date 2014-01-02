package wangshb.annotationtest;

import java.lang.reflect.Method;

public class AnnotaionDirectTool {

	public void execDirectMethods(DirectorSimple simple){
		boolean isAnnotation=simple.getClass().isAnnotation();
		boolean isDirectAnnotation=simple.getClass().isAnnotationPresent(AnnotationTest.class);
		if(isDirectAnnotation){
			AnnotationTest direct=simple.getClass().getAnnotation(AnnotationTest.class);
			String[] methods=direct.methodNames();
			executeMethods(simple,methods);
		}
		try{
			Method method=simple.getClass().getMethod("oppositeMethods", (Class[])null);
			if(method.isAnnotationPresent(AnnotationTest.class)){
				AnnotationTest test=method.getAnnotation(AnnotationTest.class);
				String[] methods=test.methodNames();
				executeMethods(simple,methods);
			}
		}catch(Exception ex){
			 
		}
		
	}
	
	private void executeMethods(Object obj,String[] methods){
		try{
			for(String method:methods){
				Method moth=obj.getClass().getMethod(method, (Class[])null);
				moth.invoke(obj, (Object[])null);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		DirectorSimple simple=new DirectorSimple();
		new AnnotaionDirectTool().execDirectMethods(simple);
	}
}
