package reservationsalle;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import reservationsalle.BDD;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class FenetreAjoutUti extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField TextNom;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			FenetreAjoutUti dialog = new FenetreAjoutUti();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public FenetreAjoutUti() {
		setTitle("Ajout d'un utilisateur");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 0, 0);
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 0, 434, 261);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String ajoututilisateur="INSERT INTO utilisateurs (nomUtilisateur) VALUES ('"+TextNom.getText()+"')";
						
						try {
							// parcours et verification
							String parcours = "SELECT * FROM utilisateurs";
							Connection con = BDD.connect();
							Statement stmt1 = con.createStatement();
							ResultSet resultats = null;
							resultats = stmt1.executeQuery(parcours);
							String nom2 = TextNom.getText();
							int i = 0;
							boolean exist = false;
							while (resultats.next()){
								String nom = resultats.getString("nomUtilisateur");
								if ((nom2.toUpperCase()).equals(nom.toUpperCase())){
									JOptionPane.showMessageDialog(null, "Erreur le nom existe d�j�");
									exist= true;
								}else{
									//System.out.println(conto[i][1]);
									i++;
								}
							}
							
							// mise a jour
							Connection con1 = BDD.connect();
							Statement stmt;
							stmt = con1.createStatement();
							if(exist==false){
								int up = stmt.executeUpdate(ajoututilisateur);
								setVisible(false);
							}
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				okButton.setBounds(134, 227, 70, 23);
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
				cancelButton.setBounds(248, 227, 84, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				setVisible(false);
			}
			{
				JLabel lblNewLabel = new JLabel("Nom");
				lblNewLabel.setBounds(134, 90, 84, 28);
				buttonPane.add(lblNewLabel);
			}
			
			TextNom = new JTextField();
			TextNom.setBounds(230, 94, 86, 20);
			buttonPane.add(TextNom);
			TextNom.setColumns(10);
		}
	}
}
