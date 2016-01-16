package test.junit;

import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.Assert;
import junit.framework.TestCase;
import reservationsalle.BDD;

public class ClasseTestSalle extends TestCase {

	public void testInsertSalle() throws SQLException {
		
		BDD bdd= new BDD();
		bdd.insertSalle("WindowsA");
		String sql="SELECT nomSalle FROM salles;";
		ResultSet rs=bdd.st.executeQuery(sql);
		Assert.assertEquals("WindowsA", rs.next());
		bdd.suppSalle("WindowsA");

	}
	
	public void testUpdateSalle() throws SQLException {
		
		BDD bdd= new BDD();
		bdd.insertSalle("WindoB");
		bdd.UpdateSalle("WindowsB", "WindoB");
		String sql="SELECT nomSalle FROM salles WHERE nomSalle = WindowsB;";
		ResultSet rs=bdd.st.executeQuery(sql);
		Assert.assertEquals(true, rs.next());
		bdd.suppSalle("WindowsB");


	}
	
	public void testDeleteSalle() throws SQLException {
		
		BDD bdd= new BDD();
		bdd.insertSalle("Pascal");
		bdd.suppSalle("Pascal");
		String sql="SELECT nomSalle FROM salles WHERE nomSalle = Pascal;";
		ResultSet rs=bdd.st.executeQuery(sql);
		Assert.assertEquals(false, rs.next());

	}

}
