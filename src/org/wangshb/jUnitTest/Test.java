package org.wangshb.jUnitTest;

import junit.framework.TestCase;

public class Test extends TestCase{

	public static void main(String[] args) {
		
		new Test().testRun();
		new Test().testRun2();
	}
	
	public void testRun(){
		String name="abc";
		
		assertTrue(name=="abc");
		assertTrue(name.equals("abc"));
		
		String otherName= "bc";
		assertTrue("���Բ����",name==otherName);
	}
	
	public void testRun2(){
		String a="abc";
		String b=new String("abc");
		
		assertSame("���Բ����",a,b);
	}

}
