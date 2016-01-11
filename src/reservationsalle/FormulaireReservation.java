package reservationsalle;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class FormulaireReservation extends JDialog {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the dialog.
	 */
	public FormulaireReservation(JFrame parent, String title, boolean modal,Reservation reservation) {
		super(parent, title, modal);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[85px][][][grow]", "[14px][][][grow][grow][grow][grow][][][]"));
		
		//création liste
		String url= "jdbc:mysql://localhost:3307/reservationsalle"+
				"?verifyServerCertificate=false"+
				"&useSSL=true";
		String login = "root";
		String passwd = "password";
	    Connection con;
	    String creneau[] = null;
	    String utilisateur[] = null;
	    String salle[] = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(url, login, passwd);
			
			String queryString = "SELECT COUNT(*) as nb FROM creneaux ;";
		    Statement stm = (Statement) con.createStatement();
		    ResultSet rs = stm.executeQuery(queryString);
		    rs.next();
		    int nbLignesCr = rs.getInt("nb");
		    creneau = new String[nbLignesCr];
		    
		    queryString = "SELECT COUNT(*) as nb FROM utilisateurs ;";
		    rs = stm.executeQuery(queryString);
		    rs.next();
		    int nbLignesUt = rs.getInt("nb");
		    utilisateur = new String[nbLignesUt];
		    
		    queryString = "SELECT COUNT(*) as nb FROM salles ;";
		    rs = stm.executeQuery(queryString);
		    rs.next();
		    int nbLignesSa = rs.getInt("nb");
		    salle = new String[nbLignesSa];
		    
		    
			queryString = "SELECT heureDebut, heureFin FROM creneaux;";
		    rs = stm.executeQuery(queryString);
		      
		    int i = 0;
		    while (rs.next()) {
		    	String hdeb = rs.getString("heureDebut");
			    String hfin = rs.getString("heureFin");
			       
			    creneau[i] = hdeb + " - " + hfin;
			 
		        i++;
		    }
		    
		    queryString = "SELECT nomSalle FROM salles;";
		    rs = stm.executeQuery(queryString);
		      
		    i = 0;
		    while (rs.next()) {
			       
			    salle[i] = rs.getString("nomSalle");
			 
		        i++;
		    }
		    
		    queryString = "SELECT nomUtilisateur FROM utilisateurs;";
		    rs = stm.executeQuery(queryString);

		    i=0;
		    while (rs.next()) {
			       
			    utilisateur[i] = rs.getString("nomUtilisateur");
			 
		        i++;
		    }
		    
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JLabel lblCrneauHoraire = new JLabel("Créneau horaire :");
		contentPane.add(lblCrneauHoraire, "cell 2 3,alignx trailing,aligny center");
		
		JComboBox comboBoxCr = new JComboBox(creneau);
		contentPane.add(comboBoxCr, "cell 3 3,growx");
				
		JLabel lblSalle = new JLabel("Salle :");
		contentPane.add(lblSalle, "cell 2 5,alignx trailing");
		
		JComboBox comboBoxSa = new JComboBox(salle);
		contentPane.add(comboBoxSa, "cell 3 5,growx");
		
		JLabel lblUtilisateur = new JLabel("Utilisateur :");
		contentPane.add(lblUtilisateur, "cell 2 6,alignx trailing");
		
		JComboBox comboBoxUt = new JComboBox(utilisateur);
		contentPane.add(comboBoxUt, "cell 3 6,growx");
		

		JLabel lblDate = new JLabel("Date :");
		contentPane.add(lblDate, "cell 2 4,alignx trailing");
		
		UtilDateModel model = new UtilDateModel();
		
		
		if(reservation !=null){
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = (Connection) DriverManager.getConnection(url, login, passwd);
				String queryString = "SELECT sa.nomSalle, cr.heureDebut, cr.heureFin, da.date,ut.nomUtilisateur FROM reservations re, salles sa, creneaux cr, date da, utilisateurs ut WHERE re.idSalle=sa.idSalle AND re.idCreneau=cr.idCreneau AND re.idDate=da.idDate AND re.idUtilisateur=ut.idUtilisateur AND idReservation="+reservation.id+";";
			    Statement stm = (Statement) con.createStatement();
			    ResultSet rs = stm.executeQuery(queryString);
			    rs.next();
			    String nomSalle = rs.getString("nomSalle");
			    String hd = rs.getString("heureDebut");
			    String hf = rs.getString("heureFin");
			    Date date = rs.getDate("date");
			    String nomUser = rs.getString("nomUtilisateur");
			    
			    comboBoxCr.setSelectedItem(hd+" - "+hf);
			    comboBoxSa.setSelectedItem(nomSalle);
			    comboBoxUt.setSelectedItem(nomUser);
			    
			    
			    SimpleDateFormat df = new SimpleDateFormat("yyyy");
			    String year = df.format(date);
			    df = new SimpleDateFormat("MM");
			    String month = df.format(date);
			    df = new SimpleDateFormat("dd");
			    String day = df.format(date);
			    
			    model.setDate(Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
			    
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			
		}

		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

	    
		contentPane.add(datePicker, "cell 3 4,growx");
		 
		
		
		
		//frame.add(datePicker);
		JFormattedTextField formattedTextField = new JFormattedTextField();
		
		
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String creneau = (String) (comboBoxCr.getSelectedItem());
				String salle = (String) (comboBoxSa.getSelectedItem());
				String utilisateur = (String) (comboBoxUt.getSelectedItem());
				if(datePicker.getModel().getValue()!=null){
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String date = dateFormat.format((Date)(datePicker.getModel().getValue()));
					try {
						Reservation reservation1 = Reservation.createNewReservation(date,utilisateur,creneau,salle);
						if(reservation != null){
							reservation1.id = reservation.id;
						}
						boolean correct = reservation1.saveReservation();
						if (!correct){
							JOptionPane.showMessageDialog(parent, "L'enregistrement n'a pas été effectué, le créneau n'est pas disponible pour cette salle à cette date.");
						}
						setVisible(false);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		contentPane.add(btnOk, "cell 2 9");
		
		JButton btnNewButton = new JButton("Annuler");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		contentPane.add(btnNewButton, "cell 3 9");
	}


}
