package reservationsalle;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class BDD {
	public static Connection connect(){
		String url= "jdbc:mysql://localhost:3306/reservationsalle"+
				"?verifyServerCertificate=false"+
				"&useSSL=true";
		String login = "root";
		String passwd = "Tours37200";
		Connection con= null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(url, login, passwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
		
	}
}