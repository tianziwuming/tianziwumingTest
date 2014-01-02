package nc.wangshb.propertiestest;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesTest<K> {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath=System.getProperty("user.dir");
		File file=new File(filePath+"/data/yerres.properties");
		
		File file1=new File("data/yerres.properties");
		boolean fileExists=file1.exists();
		
		File file2=new File("/data/yerres.properties");
		fileExists=file2.exists();
		Properties prop=new Properties();
		try{
			prop.load(new FileInputStream(file));
			String value=prop.getProperty("yer_filemanage_uploadfile");
			System.out.println("yer_filemanage_uploadfile="+value);
		}catch(Exception ex){
			
		}
		

	}
	
	public static<T> void testMth(){
		//PropertiesTest<String>[] test=new PropertiesTest<String>[10];		//ERROR
		//应该这样：
		PropertiesTest<String>[] test=new PropertiesTest[10];
		
		test[0]=new PropertiesTest<String>();
		//test[1]=new PropertiesTest<Integer>();	//ERROR
		
		//if(test[0] instanceof PropertiesTest<String>){	//ERROR
		if(test[0] instanceof PropertiesTest){
			System.out.println("test1");
		}
	}

}
