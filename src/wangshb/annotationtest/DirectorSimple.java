package wangshb.annotationtest;

@Direct(value="abcd",methodNames={"method1","method2","method3"})
@AnnotationTest("abc")
public class DirectorSimple {
	
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
