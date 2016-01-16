package reservationsalle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.fabric.xmlrpc.base.Array;

public class BDD {

	Connection conn = null;
	String url= "jdbc:mysql://localhost:3333/reservationsalles"+
			"?verifyServerCertificate=false"+
			"&useSSL=true";
	String login = "root";
	String passwd = "password";
	java.sql.Statement st = null;
	
	
	/**
	 * Initialisation des paramètres de configuration
	 */
	BDD()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url,login,passwd);
			st = conn.createStatement();
		}catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Modification de la salle
	void UpdateSalle(String newSalle, String oldSalle)
	{
		
		try {
			String sql="UPDATE salles SET nomSalle='"+newSalle+"' WHERE nomSalle='"+oldSalle+"';";
			st.executeUpdate(sql);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			javax.swing.JOptionPane.showMessageDialog(null,e.getMessage());
			
		}
	}
	
	
	// Insertion de la salle
	int insertSalle(String nomSalle)
	{
		
		try {
			String sql="INSERT INTO salles (nomSalle) VALUES ('"+ nomSalle +"');";
			String sqlExistence="SELECT idSalle FROM salles WHERE nomSalle='"+nomSalle+"';";
			
			ResultSet rs=st.executeQuery(sqlExistence);
			if (rs.next())
			{
				return 0;
			}
			else
			{
				return st.executeUpdate(sql);
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			javax.swing.JOptionPane.showMessageDialog(null,e.getMessage());
			return -1;
		}
	}
	
	// Suppression de la salle

	int suppSalle(String nomSalle)
	{
		
		try {
			String sql="DELETE FROM salles WHERE nomSalle = '"+ nomSalle +"';";
			
			return st.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			javax.swing.JOptionPane.showMessageDialog(null,e.getMessage());
			return -1;
		}
	}
	
	
	// Récupération de la salle
	
	ArrayList getSalles()
	{
		
		try {
			String sql="SELECT nomSalle FROM salles;";
			//String sqlExistence="SELECT idSalle FROM salles WHERE nomSalle="+nomSalle+";";
			
			//rs=st.executeQuery(sqlExistence);
			//rs.getInt(0);
			ArrayList salles = new ArrayList<String>();
			ResultSet rs=st.executeQuery(sql);
			
			while(rs.next()){
		         //Retrieve by column name
		         String nomSalle  = rs.getString("nomSalle");
		         salles.add(nomSalle);
		         
		         
		      }
			return salles;
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			javax.swing.JOptionPane.showMessageDialog(null,e.getMessage());
			return null;
		}
	}
	
}
