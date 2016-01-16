package reservationsalle;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SpringLayout;

public class Accueil extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Accueil frame = new Accueil();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Accueil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnGestionDesRservations = new JButton("Gestion des réservations");
		btnGestionDesRservations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FenetreReservation jreservation = new FenetreReservation();
					jreservation.setVisible(true);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		SpringLayout sl_contentPane = new SpringLayout();
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnGestionDesRservations, -173, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnGestionDesRservations, -133, SpringLayout.EAST, contentPane);
		contentPane.setLayout(sl_contentPane);
		contentPane.add(btnGestionDesRservations);
		
		JButton btnGestionDesUtilisateurs = new JButton("Gestion des utilisateurs");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnGestionDesUtilisateurs, 0, SpringLayout.WEST, btnGestionDesRservations);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnGestionDesUtilisateurs, 0, SpringLayout.EAST, btnGestionDesRservations);
		btnGestionDesUtilisateurs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FenetreUtilisateurs juser = new FenetreUtilisateurs();
				juser.setVisible(true);
			}
		});
		contentPane.add(btnGestionDesUtilisateurs);
		
		JButton btnGestionDesCrneaux = new JButton("Gestion des créneaux");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnGestionDesUtilisateurs, -9, SpringLayout.NORTH, btnGestionDesCrneaux);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnGestionDesCrneaux, 0, SpringLayout.WEST, btnGestionDesRservations);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnGestionDesCrneaux, 0, SpringLayout.EAST, btnGestionDesRservations);
		btnGestionDesCrneaux.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ajouterCreneau jcreneau = new ajouterCreneau();
				jcreneau.setVisible(true);
			}
		});
		contentPane.add(btnGestionDesCrneaux);
		
		JButton btnGestionDesSalles = new JButton("Gestion des salles");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnGestionDesCrneaux, -9, SpringLayout.NORTH, btnGestionDesSalles);
		sl_contentPane.putConstraint(SpringLayout.WEST, btnGestionDesSalles, 0, SpringLayout.WEST, btnGestionDesRservations);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnGestionDesSalles, 0, SpringLayout.EAST, btnGestionDesRservations);
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnGestionDesSalles, 164, SpringLayout.NORTH, contentPane);
		btnGestionDesSalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GdS window = new GdS();
				window.frame.setVisible(true);
			}
		});
		contentPane.add(btnGestionDesSalles);
	}

}
