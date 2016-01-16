package reservationsalle;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.BoxLayout;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import reservationsalle.BDD_General;

public class FenetreUtilisateurs extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private final Action action = new SwingAction();
	private final JScrollPane scrollPane = new JScrollPane();
	private JButton btnModifier;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreUtilisateurs frame = new FenetreUtilisateurs();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public FenetreUtilisateurs() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		DefaultTableModel modele = remplirTable();
		contentPane.setLayout(null);

		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(arg0.getSource() == btnAjouter)
				{
					FenetreAjoutUti fen2 = new FenetreAjoutUti();
					fen2.setModal(true);
					fen2.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					fen2.setVisible(true);
					DefaultTableModel modele2 = remplirTable();
					table.setModel(modele2);

				}
			}
		});
		btnAjouter.setBounds(24, 238, 89, 23);
		contentPane.add(btnAjouter);
		scrollPane.setBounds(37, 37, 335, 126);
		contentPane.add(scrollPane);

		table = new JTable(modele);
		scrollPane.setViewportView(table);

		JButton btnSupprimer = new JButton("Supprimer");
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une ligne.");
				}else{
					int ligneSelectionne = table.getSelectedRow();
					//on r�cup�re la valeur de la premi�re colonne de la ligne s�lectionn�
					table.getValueAt(ligneSelectionne, 0);
					System.out.println(table.getValueAt(ligneSelectionne, 0));
					String suppUti = "DELETE FROM utilisateurs WHERE idUtilisateur ='"+table.getValueAt(ligneSelectionne, 0)+"'";
					Connection con1 = BDD_General.connect();
					Statement stmt;
					try {
						stmt = con1.createStatement();
						int up = stmt.executeUpdate(suppUti);
						DefaultTableModel modele2 = remplirTable();
						table.setModel(modele2);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnSupprimer.setBounds(123, 238, 89, 23);
		contentPane.add(btnSupprimer);

		btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow()==-1){
					JOptionPane.showMessageDialog(null, "Veuillez selectionner une ligne.");
				}else{
				// r�cup�ration de la ligne selectionn�e
				int ligneSelectionne = table.getSelectedRow();
				//on r�cup�re la valeur de la premi�re colonne de la ligne s�lectionn�
				Object o1 = table.getValueAt(ligneSelectionne, 0);
				System.out.println(table.getValueAt(ligneSelectionne, 0));

				FenetreModifUti fen3 = new FenetreModifUti(o1);
				fen3.setModal(true);
				fen3.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				fen3.setVisible(true);
				DefaultTableModel modele3 = remplirTable();
				table.setModel(modele3);
			}
			}
		});
		btnModifier.setBounds(222, 238, 89, 23);
		contentPane.add(btnModifier);


	}

	static DefaultTableModel remplirTable(){

		String[] colo = {"Num�ro", "Nom"};

		String requete1 = "SELECT COUNT(*) AS compt FROM utilisateurs";
		String requete = "SELECT * FROM utilisateurs";

		DefaultTableModel modele;
		modele = new DefaultTableModel();
		int nbUti=0;

		try {
			Connection con1 = BDD_General.connect();
			Statement stmt;

			stmt = con1.createStatement();
			ResultSet resultat1 = null;
			resultat1 = stmt.executeQuery(requete1);
			resultat1.next();

			nbUti = resultat1.getInt("compt");
			String [][] conto = new String[nbUti][2];
			Connection con = BDD_General.connect();

			Statement stmt1 = con.createStatement();
			ResultSet resultats = null;
			resultats = stmt1.executeQuery(requete);

			int i = 0;
			while (resultats.next()){
				conto[i][0]= resultats.getString("idUtilisateur");
				conto[i][1]= resultats.getString("nomUtilisateur");
				//System.out.println(conto[i][1]);
				i++;
			}

			modele = new DefaultTableModel(conto, colo);
			System.out.println("fin");
			return modele;

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		return modele;







	}	
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
