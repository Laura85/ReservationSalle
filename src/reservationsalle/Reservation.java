package reservationsalle;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Reservation {
	public int id;
	private int idCreneau;
	private int idDate;
	private int idUser;
	private int idSalle;
	public Reservation(int id, int idDate, int idUser, int idCreneau, int idSalle){
		this.id = id;
		this.idDate = idDate;
		this.idUser = idUser;
		this.idCreneau = idCreneau;
		this.idSalle = idSalle;
	}
	
	public Reservation(){
	}
	
	public static Reservation createNewReservation(String date, String user, String creneau, String salle) throws ClassNotFoundException{
		Reservation res = new Reservation();
		res.id = -1;
		
	    Connection con;
	    int idCr= -1;
	    int idSa= -1;
	    int idUt= -1;
	    int idDa= -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = BDD.connect();
			String tab[] = creneau.split(" - ");
			String queryString = "SELECT idCreneau as nb FROM creneaux WHERE heureDebut='"+tab[0]+"' AND heureFin='"+tab[1]+"';";
		    Statement stm = (Statement) con.createStatement();
		    ResultSet rs = stm.executeQuery(queryString);
		    rs.next();
		    idCr = rs.getInt("nb");
		    
		    queryString = "SELECT idUtilisateur as nb FROM utilisateurs WHERE nomUtilisateur='"+user+"';";
		    rs = stm.executeQuery(queryString);
		    rs.next();
		    idUt = rs.getInt("nb");
		    
		    queryString = "SELECT idSalle as nb FROM salles WHERE nomSalle='"+salle+"';";
		    rs = stm.executeQuery(queryString);
		    rs.next();
		    idSa = rs.getInt("nb");
		    
		    queryString = "SELECT idDate as nb FROM date WHERE date='"+date+"';";
		    rs = stm.executeQuery(queryString);
		    if(rs.next()){
		    	idDa=rs.getInt("nb");
		    }else{
		    	queryString = "INSERT INTO date(date) VALUES ('"+date+"');";
		    	System.out.println(queryString);
		    	stm.executeUpdate(queryString);
		    	
		    	queryString = "select LAST_INSERT_ID() as nb;";
			    rs = stm.executeQuery(queryString);
			    rs.next();
			    idDa = rs.getInt("nb");
		    }
		    
		    res.id = -1;
		    res.idCreneau = idCr;
		    res.idDate = idDa;
		    res.idSalle = idSa;
		    res.idUser = idUt;
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		
		return res;
	}
	public Reservation getReservationWithId(int id) throws SQLException{
		
		Connection con;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = BDD.connect();
			String queryString = "SELECT idSalle,idCreneau,idDate,idUtilisateur FROM reservations ;";
		    Statement stm = (Statement) con.createStatement();
		    ResultSet rs = stm.executeQuery(queryString);
		    rs.next();
		    this.id = id;
		    this.idCreneau=  rs.getInt("idCreneau");
		    this.idDate=  rs.getInt("idDate");
		    this.idSalle=  rs.getInt("idSalle");
		    this.idUser=  rs.getInt("idUtilisateur");
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}
	
	public void deleteReservation() throws SQLException{
		Connection con;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = BDD.connect();
			String queryString = "DELETE FROM reservations WHERE idReservation ="+this.id+" ;";
		    Statement stm = (Statement) con.createStatement();
		    stm.executeUpdate(queryString);
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public boolean saveReservation() throws SQLException{
		Connection con;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = BDD.connect();
			if(this.id==-1){
				if(this.isFree()){
					String queryString = "INSERT INTO reservations(idSalle,idUtilisateur,idCreneau,idDate) VALUES ("+this.idSalle+","+this.idUser+","+this.idCreneau+","+this.idDate+");";
				    Statement stm = (Statement) con.createStatement();
				    stm.executeUpdate(queryString);
				    return true;
				}else{
					return false;
				}
				//insert
			}else{
				if(this.isFree()){
					String queryString = "UPDATE reservations SET idSalle="+this.idSalle+",idUtilisateur="+this.idUser+",idCreneau="+this.idCreneau+",idDate="+this.idDate+" WHERE idReservation ="+this.id+";";
					System.out.println(queryString);
				    Statement stm = (Statement) con.createStatement();
				    stm.executeUpdate(queryString);
				    return true;
				}else{
					return false;
				}
				//update
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isFree() throws SQLException{
		Connection con;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = BDD.connect();
			String queryString = "SELECT * FROM reservations WHERE idSalle="+this.idSalle+" AND idCreneau="+this.idCreneau+" AND idDate="+this.idDate+";";
		    Statement stm = (Statement) con.createStatement();
		    ResultSet rs = stm.executeQuery(queryString);
		    if(rs.next()){
		    	int idQuery = rs.getInt("idReservation");
		    	if(this.id == idQuery){
		    		return true;
		    	}else{
		    		return false;
		    	}
		    }else{
		    	return true;
		    }
		    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
