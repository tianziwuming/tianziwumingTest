package wangshb.annotationtest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationTest {

	public String[] methodNames();
	public String name() default "blankName";
}
