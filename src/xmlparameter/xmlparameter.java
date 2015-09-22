package xmlparameter;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import org.testng.annotations.*;

public class xmlparameter {

	Connection con;

	@Test
	@Parameters({ "dbconfig", "poolsize" })
	public void createConnection(String dbconfig, int poolsize) {
		System.out.println("\t---1.Data from XML file---");
		System.out.println("dbconfig : " + dbconfig);
		System.out.println("poolsize : " + poolsize);

		Properties prop = new Properties();
		InputStream input = null;

		try {
		  //get properties file from project classpath
			input = getClass().getClassLoader().getResourceAsStream(dbconfig);

		  prop.load(input);
		  
		  System.out.println("\t---2.Data from db.properties file---");
		  String drivers = prop.getProperty("jdbc.driver");
		  String connectionURL = prop.getProperty("jdbc.url");
		  String username = prop.getProperty("jdbc.username");
		  String password = prop.getProperty("jdbc.password");

		  System.out.println("drivers : " + drivers);
		  System.out.println("connectionURL : " + connectionURL);
		  System.out.println("username : " + username);
		  System.out.println("password : " + password);

		  Class.forName(drivers);
		  con = DriverManager.getConnection(connectionURL, username, password);
		  System.out.println("\t---3.Data from MySQL DB---");
		  Statement stmt1 = con.createStatement();
		  ResultSet rs = stmt1.executeQuery("select * from emp");
		  while(rs.next()){
		  	System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
		  }
		  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
