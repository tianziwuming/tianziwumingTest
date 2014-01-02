package wangshb.annotationtest;

import java.io.Serializable;


  @AnnotationTest(methodNames={"method1","method2","method3"})
public class DirectorSimple implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public DirectorSimple(){
		super();
	}
	
	@AnnotationTest(methodNames={"method3","method2","method1"})
	public void oppositeMethods(){
		
	}
	
	public void method1(){
		System.out.println("method 1......");
	}
	
	public void method2(){
		System.out.println("method 2......");
	}

	public void method3(){
		System.out.println("method 3......");
	}
}
