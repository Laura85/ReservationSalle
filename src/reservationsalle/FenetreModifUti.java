package reservationsalle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import reservationsalle.BDD;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class FenetreModifUti extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textModifUti;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Object o1 = null;
			FenetreModifUti dialog = new FenetreModifUti(o1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FenetreModifUti(Object o1) {
		setTitle("Modification d'un utilisateur");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 434, 228);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblNomModifi = new JLabel("Nom modifi\u00E9");
		lblNomModifi.setBounds(60, 61, 79, 25);
		contentPanel.add(lblNomModifi);
		
		textModifUti = new JTextField();
		textModifUti.setBounds(167, 63, 129, 20);
		contentPanel.add(textModifUti);
		textModifUti.setColumns(10);
		
		JLabel lblVeuillezSaisirLe = new JLabel("Veuillez saisir le nouveau nom");
		lblVeuillezSaisirLe.setBounds(143, 11, 226, 20);
		contentPanel.add(lblVeuillezSaisirLe);
		{
			
			
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 228, 434, 33);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					
						try {
							// parcours et verification
							String parcours = "SELECT * FROM utilisateurs";
							Connection con = BDD.connect();
							Statement stmt1 = con.createStatement();
							ResultSet resultats = null;
							resultats = stmt1.executeQuery(parcours);
							String nom2 = textModifUti.getText();
							int i = 0;
							boolean exist = false;
							while (resultats.next()){
								String nom = resultats.getString("nomUtilisateur");
								if ((nom2.toUpperCase()).equals(nom.toUpperCase())){
									JOptionPane.showMessageDialog(null, "Erreur le nom existe déjà");
									exist= true;
								}else{
									//System.out.println(conto[i][1]);
									i++;
								}
							}
							
							String modifUti = "UPDATE utilisateurs SET nomUtilisateur = '"+textModifUti.getText()+"' WHERE idUtilisateur = "+o1+"";
							Connection con1 = BDD.connect();
							Statement stmt;
							stmt = con1.createStatement();
							if(exist==false){
								int up = stmt.executeUpdate(modifUti);
								setVisible(false);
							}
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						
					}
				});
				okButton.setBounds(101, 5, 80, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setBounds(248, 5, 74, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
