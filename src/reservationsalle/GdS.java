package reservationsalle;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class GdS {

	public JFrame frame;
	private JTextField textFieldAjouter;
	private JTextField modifSalle;
	//private 
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GdS window = new GdS();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public GdS() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		// fini supp
		JPanel panelAjout = new JPanel();
		panelAjout.setBackground(Color.WHITE);
		panelAjout.setBounds(6, 17, 438, 36);
		frame.getContentPane().add(panelAjout);
		panelAjout.setLayout(null);
		
		
		
		JLabel lblSalle = new JLabel("Nom de la salle :");
		lblSalle.setBounds(6, 6, 116, 16);
		panelAjout.add(lblSalle);
		
		textFieldAjouter = new JTextField();
		textFieldAjouter.setBackground(Color.LIGHT_GRAY);
		textFieldAjouter.setBounds(141, 1, 150, 26);
		panelAjout.add(textFieldAjouter);
		textFieldAjouter.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(6, 134, 438, 118);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JComboBox comboBoxSalles = new JComboBox();
	
		
		comboBoxSalles.setBounds(222, 40, 136, 27);
		panel.add(comboBoxSalles);
		
	
		
		
		JButton btnAjouter = new JButton("Ajouter ");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BDD bdd= new BDD();//Création et initialisation de la bdd
				if (bdd.insertSalle(textFieldAjouter.getText())==0)
				{
					javax.swing.JOptionPane.showMessageDialog(null,"Une erreur s'est produite. La salle existe peut être déjà !"); 
				}else
				{
					javax.swing.JOptionPane.showMessageDialog(null,"Ajout effectué"); 
					
				}
			}
		});
		btnAjouter.setBounds(315, 1, 117, 29);
		panelAjout.add(btnAjouter);
		
		
		
		JButton btnMaj = new JButton("afficher les salles");
		btnMaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxSalles.removeAllItems();
				BDD bdd= new BDD();//Création et initialisation de la bdd
				ArrayList salles=bdd.getSalles();
				int i=0;
				while (i<salles.size())
				{
					comboBoxSalles.addItem(salles.get(i));
					i+=1;
				}
			}
		});
		btnMaj.setBounds(30, 17, 136, 29);
		panel.add(btnMaj);
		
		
		JButton ButtonSupp = new JButton("Supprimer");
		ButtonSupp.setBounds(40, 58, 108, 29);
		panel.add(ButtonSupp);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(6, 72, 438, 36);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNomModifier = new JLabel("Nom à modifier");
		lblNomModifier.setBounds(6, 6, 116, 16);
		panel_1.add(lblNomModifier);
		
		modifSalle = new JTextField();
		modifSalle.setColumns(10);
		modifSalle.setBackground(Color.LIGHT_GRAY);
		modifSalle.setBounds(141, 1, 150, 26);
		panel_1.add(modifSalle);
		
		JButton btnModifier = new JButton("Modifier");
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newSalle = modifSalle.getText();
				String oldSalle = (String)comboBoxSalles.getSelectedItem();
				BDD bdd = new BDD();
				bdd.UpdateSalle(newSalle, oldSalle);
					
			}
		});
		btnModifier.setBounds(315, 1, 117, 29);
		panel_1.add(btnModifier);
		
				
				
				ButtonSupp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BDD bdd= new BDD();//Création et initialisation de la bdd
						
						bdd.suppSalle((String)comboBoxSalles.getSelectedItem());
						comboBoxSalles.removeAllItems();
						BDD bddsupp= new BDD();//Création et initialisation de la bdd
						
					}
					
				});
		
		
		
		
	}
}
