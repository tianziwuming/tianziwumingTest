package wangshb.annotationtest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Direct {

	public String[] methodNames();
	public String name() default "blankName";
	public String value();
}
