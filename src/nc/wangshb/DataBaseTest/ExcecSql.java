package nc.wangshb.DataBaseTest;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class ExcecSql {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath=null;
		String databaseFilePath=null;
		if(args.length<2){
			System.out.println("请输入sql文件路径");
		}
		filePath=args[0];
		databaseFilePath=args[1];
		Scanner scanner=null;
		try{
			scanner=new Scanner(new File(filePath));
		}catch(Exception ex){
			ex.printStackTrace();
			return;
		}
		
		
		ExcecSql excsql=new ExcecSql();
		Connection conn=null;
		try{
			try{
				conn=excsql.getConnection(databaseFilePath);
			}catch(Exception ex){
				ex.printStackTrace();
				return;
			}
			Statement stmt=conn.createStatement();
			int LineNum=1;
			while(scanner.hasNextLine()){
				System.out.println("execute Line "+LineNum+++":");
				String line=scanner.nextLine();
				excsql.executeSql(line,stmt);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch(Exception ex){}
		}
	}
	
	public Connection getConnection(String filePath) throws Exception{
		Properties prop=new Properties();
		FileInputStream fileInput=new FileInputStream(filePath);
		prop.load(fileInput);
		//这里需要及时关闭fileINput！！！！！！！！！！！！！！！！！！！！！！！！！！！！
		fileInput.close();
		String driver=prop.getProperty("jdbc.drivers");
		if(driver!=null){
			System.setProperty("jdbc.drivers", driver);
		}
		String jdbcurl=prop.getProperty("jdbc.url");
		String username=prop.getProperty("jdbc.username");
		String password=prop.getProperty("jdbc.password");
		
		Connection conn=DriverManager.getConnection(jdbcurl, username, password);
		return conn;
	}
	
	public static void printResultSet(ResultSet rs){
		if(rs==null)
			return;
		try{
			ResultSetMetaData metadata=rs.getMetaData();
			int size =metadata.getColumnCount();
			for(int i=0;i<size;i++){
				System.out.print(metadata.getColumnLabel(i)+",");
			}
			System.out.println();
			
			while(rs.next()){
				for(int i=0;i<size;i++){
					System.out.print(rs.getObject(i)+",");
				}
				System.out.println();
			}
		}catch(Exception ex){
			
		}
		
	}
	
	//只调用Statement.execute方法，返回的值说明是否有resultset
	public void executeSql(String line,Statement stmt) throws SQLException{
		if(line.trim().toLowerCase().startsWith("select")){
			ResultSet rs=stmt.executeQuery(line);
			printResultSet(rs);
			
			try{
				rs.close();
			}catch(Exception ex){}
		}else if(line.trim().toLowerCase().equals("exit")){
			return;
		}else{
			stmt.executeUpdate(line);
		}
	}
	
	public void executeSql1(String line,Statement stmt) throws SQLException{
		 if(line.trim().toLowerCase().equals("exit")){
			return;
		 }else{
			boolean hasResultSet = stmt.execute(line);
			if(hasResultSet){
				ResultSet rs=stmt.getResultSet();
				printResultSet(rs);
				
				try{
					rs.close();
				}catch(Exception ex){}
			}
		 }
		
	}

}
