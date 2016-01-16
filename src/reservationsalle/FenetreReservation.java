package reservationsalle;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import javax.swing.JButton;
import javax.swing.BoxLayout;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FenetreReservation extends JFrame {

	private JPanel contentPane;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreReservation frame = new FenetreReservation();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 */
	public FenetreReservation() throws ClassNotFoundException {
		
		DefaultTableModel model = remplirTable();
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 509, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setSize(600, 300);
		table_1 = new JTable(model);
		getContentPane().setLayout(new BorderLayout());
	    //On ajoute le bouton au content pane de la JFrame
		//Au nord
		this.getContentPane().add(new JScrollPane(table_1),BorderLayout.CENTER);
	    //Au sud
		JPanel contboutons = new JPanel();
	    contboutons.setLayout(new BorderLayout());
	    this.getContentPane().add(contboutons,BorderLayout.SOUTH);
	    
		JPanel boutons = new JPanel();
	    boutons.setLayout(new BoxLayout(boutons, BoxLayout.LINE_AXIS));
	    JButton button_1 = new JButton("Ajouter");
	    button_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
				FormulaireReservation dialog = new FormulaireReservation(null,"Ajout",true,null);
				dialog.setModal(true);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				try {
					DefaultTableModel model = remplirTable();
					table_1.setModel(model);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    });
	    boutons.add(button_1);
	    JButton button_2 = new JButton("Modifier");
	    button_2.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		int ligneSelectionne = table_1.getSelectedRow();
	    		if(ligneSelectionne!=-1){
	    			//on récupére la valeur de la première colonne de la ligne sélectionné
		    		String idReservation = (String) table_1.getValueAt(ligneSelectionne, 0);
		    		Reservation reser = new Reservation();
		    		
					try {
						reser.getReservationWithId(Integer.parseInt(idReservation));
						FormulaireReservation dialog = new FormulaireReservation(null,"Ajout",true,reser);
						dialog.setModal(true);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
						try {
							DefaultTableModel model = remplirTable();
							table_1.setModel(model);
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (NumberFormatException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						
	    		}
	    		
	    		
	    	}
	    });
	    boutons.add(button_2);
	    JButton button = new JButton("Supprimer");
	    button.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		int ligneSelectionne = table_1.getSelectedRow();
	    		if(ligneSelectionne!=-1){
	    			//on récupére la valeur de la première colonne de la ligne sélectionné
		    		String idReservation = (String) table_1.getValueAt(ligneSelectionne, 0);
		    		Reservation reser = new Reservation();
		    		try {
						reser.getReservationWithId(Integer.parseInt(idReservation));
						reser.deleteReservation();
						DefaultTableModel model = remplirTable();
						table_1.setModel(model);
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		}
	    		    		
	    	}
	    });
	    boutons.add(button);
	    contboutons.add(boutons,BorderLayout.CENTER);
	}

	public static DefaultTableModel remplirTable() throws ClassNotFoundException{
		  Connection con;
	      String col[] = { "N°", "Salle", "Date","Début du créneau","Fin du créneau","Utilisateur" };
	      String cont[][]=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = BDD.connect();
			String queryString = "SELECT COUNT(*) as nb FROM reservations ;";
		    Statement stm = (Statement) con.createStatement();
		    ResultSet rs = stm.executeQuery(queryString);
		    rs.next();
		    int nbLignes = rs.getInt("nb");
		    cont = new String[nbLignes][6];
			queryString = "SELECT idReservation, sa.nomSalle, cr.heureDebut, cr.heureFin, da.date,ut.nomUtilisateur FROM reservations re, salles sa, creneaux cr, date da, utilisateurs ut WHERE re.idSalle=sa.idSalle AND re.idCreneau=cr.idCreneau AND re.idDate=da.idDate AND re.idUtilisateur=ut.idUtilisateur;";
		    stm = (Statement) con.createStatement();
		    rs = stm.executeQuery(queryString);
		      
		      int i = 0;
		      while (rs.next()) {
		        int id = rs.getInt("idReservation");
		        String salle = rs.getString("nomSalle");
		        String hdeb = rs.getString("heureDebut");
		        String hfin = rs.getString("heureFin");
		        String utilisateur = rs.getString("nomUtilisateur");
		        Date date = rs.getDate("date");
		        
		        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		        
		        cont[i][0] = id + "";
		        cont[i][1] = salle;
		        cont[i][2] = (dateFormat.format(date)).toString();
		        cont[i][3] = hdeb;
		        cont[i][4] = hfin;
		        cont[i][5] = utilisateur;
		        i++;
		      }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    DefaultTableModel model = new DefaultTableModel(cont, col);
	    return model;
	}
}
