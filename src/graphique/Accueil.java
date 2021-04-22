package graphique;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class Accueil extends JFrame {

	private JPanel contentPane;
	private JTextField nombreAgentString;

	private ImageIcon LOGO = new ImageIcon(getClass().getResource("Rolit-logo.png"));

	private int nombreAgent = 0;
	private int temps = 0;
	private boolean modeJoueur = false;
	private JLabel tempsText;
	private JTextField inputTemps;
	private JRadioButton btnModeJoueur;

	/**
	 * Create the frame.
	 */
	public Accueil() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Accueil Rolit");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		nombreAgentString = new JTextField();
		nombreAgentString.setBounds(274, 136, 86, 20);
		contentPane.add(nombreAgentString);
		nombreAgentString.setColumns(10);
		nombreAgentString.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					nombreAgent = Integer.parseInt(nombreAgentString.getText());
					temps = Integer.parseInt(inputTemps.getText())*1000;
					modeJoueur = btnModeJoueur.isSelected();
					System.out.println("Returned : "+nombreAgent);
				}
			}

		});

		JLabel question = new JLabel("Combien d'agents voulez vous faire jouer ?");
		question.setBounds(10, 139, 254, 14);
		contentPane.add(question);

		JButton logoRolit = new JButton("");
		logoRolit.setBounds(109, 11, 215, 96);
		contentPane.add(logoRolit);
		logoRolit.setIcon(LOGO);

		tempsText = new JLabel("Temps entre chaque tour (en s) :");
		tempsText.setBounds(10, 180, 254, 14);
		contentPane.add(tempsText);

		inputTemps = new JTextField();
		inputTemps.setColumns(10);
		inputTemps.setBounds(274, 177, 86, 20);
		contentPane.add(inputTemps);
		inputTemps.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					nombreAgent = Integer.parseInt(nombreAgentString.getText());
					temps = (int) Integer.parseInt(inputTemps.getText())*1000;
					modeJoueur = btnModeJoueur.isSelected();
					System.out.println("Returned : "+nombreAgent);
					System.out.println("Returned : "+temps);
				}
			}

		});

		logoRolit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nombreAgent = Integer.parseInt(nombreAgentString.getText());
				temps = (int) Integer.parseInt(inputTemps.getText())*1000;
				modeJoueur = btnModeJoueur.isSelected();
				System.out.println("Clicked : "+nombreAgent);
			}
		});
		
		
		JLabel lblVoulezVousJouer = new JLabel("Voulez vous jouer ?");
		lblVoulezVousJouer.setBounds(10, 224, 254, 14);
		contentPane.add(lblVoulezVousJouer);
		
		btnModeJoueur = new JRadioButton("");
		btnModeJoueur.setBounds(274, 220, 109, 23);
		contentPane.add(btnModeJoueur);
		
		
		
		setLocationRelativeTo(null);
		this.setVisible(true);
		
		
	}

	public int getNombreAgent() {
		return this.nombreAgent;
	}

	public int getTemps() {
		return this.temps;
	}
	public boolean getModeJoueur() {
		return this.modeJoueur;
	}
}
