package wangshb.annotationtest;

import java.lang.reflect.Method;
import java.util.Hashtable;

public class AnnotaionDirectTool {

	public void execDirectMethods(DirectorSimple simple){
		boolean isDirectAnnotation=simple.getClass().isAnnotationPresent(Direct.class);
		if(isDirectAnnotation){
			Direct direct=simple.getClass().getAnnotation(Direct.class);
			System.out.println("direct_name="+direct.name());
			System.out.println("direct_value="+direct.value());
			String[] methods=direct.methodNames();
			executeMethods(simple,methods);
		}
		
		System.out.println();
		
		boolean isDirectAnnotation1=simple.getClass().isAnnotationPresent(AnnotationTest.class);
		if(isDirectAnnotation1){
			AnnotationTest direct=simple.getClass().getAnnotation(AnnotationTest.class);
			String value=direct.value();
			System.out.println("Annotation_value="+value);
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
		new AnnotaionDirectTool().execDirectMethods(new DirectorSimple());
		Hashtable<String,String> ht=new Hashtable<String,String>();
		//ht.put(null, "abc");
		ht.put("abc", null);
	}
}

