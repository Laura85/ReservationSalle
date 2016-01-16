package reservationsalle;

import java.awt.BorderLayout;
import java.awt.EventQueue;

//import javafx.scene.shape.Box;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement.ParseInfo;
import com.mysql.jdbc.Statement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ajouterCreneau extends JFrame {
	public void ajouterCreneau() {
		afficherListeCreneaux();
	}

	private JPanel contentPane;
	private JTextField textFieldHeure;
	private JComboBox comboBox;
	private JTextField textFieldHD;
	private static JComboBox cbcr;
	private JButton btnAjouterHeure;
	private JButton btnSupprimerHeure;
	private JLabel lblNewLabel_1;
	private JButton btnModifier;
	private JTextField textFieldHDeb;
	private JTextField textFieldHfin;
	private static JTextField textFieldNHD;
	private static JTextField textFieldNHF;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public ajouterCreneau() {
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 465, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHD = new JLabel("Heure debut:");
		lblHD.setBounds(10, 36, 95, 24);
		contentPane.add(lblHD);

		JLabel lblListeCrneaux = new JLabel("Liste creneaux:");
		lblListeCrneaux.setBounds(10, 81, 95, 24);
		contentPane.add(lblListeCrneaux);



		cbcr = new JComboBox();
		cbcr.setBounds(107, 83, 261, 20);
		contentPane.add(cbcr);

		btnAjouterHeure = new JButton("Ajouter");
		btnAjouterHeure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String HeureDebut = textFieldHDeb.getText();
				String HeureFin = textFieldHfin.getText();
				ajouterUnCreneau(HeureDebut,HeureFin);
			}
		});
		btnAjouterHeure.setBounds(110, 136, 89, 23);
		contentPane.add(btnAjouterHeure);

		btnSupprimerHeure = new JButton("Supprimer");
		btnSupprimerHeure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				supprimerUnCreneau();
			}
		});
		btnSupprimerHeure.setBounds(289, 136, 89, 23);
		contentPane.add(btnSupprimerHeure);

		lblNewLabel_1 = new JLabel("Heure fin:");
		lblNewLabel_1.setBounds(214, 34, 79, 29);
		contentPane.add(lblNewLabel_1);

		btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String NouvelleHeureDebut = textFieldNHD.getText();
				String NouvelleHeureFin = textFieldNHF.getText();
				modifierUnCreneau(NouvelleHeureDebut,NouvelleHeureFin);
			}
		});
		btnModifier.setBounds(188, 251, 89, 23);
		contentPane.add(btnModifier);

		textFieldHDeb = new JTextField();
		textFieldHDeb.setBounds(107, 38, 79, 20);
		contentPane.add(textFieldHDeb);
		textFieldHDeb.setColumns(10);

		textFieldHfin = new JTextField();
		textFieldHfin.setBounds(279, 38, 89, 20);
		contentPane.add(textFieldHfin);
		textFieldHfin.setColumns(10);

		JLabel lblNouvelleHeureDbut = new JLabel("Nouvelle Heure D\u00E9but:");
		lblNouvelleHeureDbut.setBounds(10, 204, 117, 14);
		contentPane.add(lblNouvelleHeureDbut);

		textFieldNHD = new JTextField();
		textFieldNHD.setBounds(126, 201, 86, 20);
		contentPane.add(textFieldNHD);
		textFieldNHD.setColumns(10);

		JLabel lblNouvelleHeureFin = new JLabel("Nouvelle Heure Fin:");
		lblNouvelleHeureFin.setBounds(237, 204, 117, 14);
		contentPane.add(lblNouvelleHeureFin);

		textFieldNHF = new JTextField();
		textFieldNHF.setColumns(10);
		textFieldNHF.setBounds(348, 201, 86, 20);
		contentPane.add(textFieldNHF);




	}

	public static void 	ajouterUnCreneau(String HeureDebut, String HeureFin){
		java.sql.Connection conn = BDD_General.connect();
		java.sql.Statement st;

		String req1 = "SELECT count(*) FROM creneaux WHERE ('" + HeureDebut + "' BETWEEN heureDebut AND heureFin) ";
		req1 += " OR ('" + HeureFin + "' BETWEEN heureDebut AND heureFin)";


		try{

			st = conn.createStatement();					
			ResultSet res1 = st.executeQuery(req1);
			res1.next();
			if(Integer.parseInt(res1.getString(1).toString())>=1)
			{
				javax.swing.JOptionPane.showMessageDialog(null,"Ce cr�neau est d�j� r�serv�. Veuillez choisir un autre !"); 
			}
			else {
				String req= "INSERT INTO creneaux (heureDebut, heureFin) VALUES ( '"+HeureDebut+"', '"+HeureFin+"')";
				int res = st.executeUpdate(req);
				javax.swing.JOptionPane.showMessageDialog(null,"Votre r�servation a bien �t� enregistr�e !"); 

			}


		}catch(Exception e){
			System.out.println(e.getMessage());

		}
		afficherListeCreneaux();
	}

	public static void 	afficherListeCreneaux(){
		
		java.sql.Connection conn = BDD_General.connect();
		java.sql.Statement st;
		
		String req1= "SELECT heureDebut,heureFin FROM creneaux";
		try{

			st = conn.createStatement();					
			ResultSet res1 = st.executeQuery(req1);
			while(res1.next())
			{ 
				int i = 1;
				String s = res1.getString(1)+" - "+res1.getString(2);
				cbcr.addItem(s);;
				i++;
			}
		}catch(Exception e){
			System.out.println(e.getMessage());

		}
	}

	public static void 	supprimerUnCreneau(){
		
		java.sql.Connection conn = BDD_General.connect();
		java.sql.Statement st;

		int index = cbcr.getSelectedIndex();
		String cr = cbcr.getItemAt(index).toString();
		System.out.println(cr);

		String heureD = cr.substring(0, 8);
		System.out.println(heureD);
		String heureF = cr.substring(11);
		System.out.println(heureF);

		String req = "SELECT * FROM creneaux WHERE heureDebut = '" + heureD + "'";
		req += " AND heureFin = '" + heureF + "'";




		//String req1= "SELECT heureDebut,heureFin FROM creneaux";
		try{

			st = conn.createStatement();					
			//ResultSet res1 = st.executeQuery(req1);
			ResultSet res = st.executeQuery(req);
			res.next();
			String req1 = "DELETE FROM creneaux WHERE idCreneau =" + res.getString(1);
			int res1 = st.executeUpdate(req1);	
			cbcr.removeAllItems();
			afficherListeCreneaux();



		}catch(Exception e){
			System.out.println(e.getMessage());

		}
	}


public static void modifierUnCreneau(String NouvelleHeureDebut, String NouvelleHeureFin){
	
	java.sql.Connection conn = BDD_General.connect();
	java.sql.Statement st;
	
	
	
	int index = cbcr.getSelectedIndex();
	String cr = cbcr.getItemAt(index).toString();

	String heureD = cr.substring(0, 8);
	System.out.println(heureD);
	String heureF = cr.substring(11);
	System.out.println(heureF);
	
	String req = "SELECT idCreneau FROM creneaux WHERE heureDebut = '" + heureD + "'";
	req += " AND heureFin = '" + heureF + "'";
	try{
		st = conn.createStatement();
		ResultSet res = st.executeQuery(req);
		res.next();
		String req1 = "UPDATE creneaux SET heureDebut = '"+NouvelleHeureDebut+"', heureFin='"+NouvelleHeureFin+"' WHERE idCreneau="+Integer.parseInt(res.getString(1).toString());
		int res1 = st.executeUpdate(req1);
		cbcr.removeAllItems();
		afficherListeCreneaux();
	}catch(Exception e){
		e.getMessage();
	}
	

	
	
	
	
	
}
}