package graphique;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import constantes.EtatsCase;
import plateau.Plateau;

public class PlateauGraphique extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] posChoisie = new int[2];

	private JButton[][] grille;
	
	private ImageIcon BLEU = new ImageIcon(getClass().getResource("CaseBleue.png"));
	private ImageIcon ROUGE = new ImageIcon(getClass().getResource("CaseRouge.png"));
	private ImageIcon VERT = new ImageIcon(getClass().getResource("CaseVerte.png"));
	private ImageIcon JAUNE= new ImageIcon(getClass().getResource("CaseJaune.png"));
	private ImageIcon VIDE = new ImageIcon(getClass().getResource("CaseVide.png"));
	
	Plateau plateau;

	public PlateauGraphique(Plateau _plateau){
		plateau = _plateau;
		grille = new JButton[Plateau.dimension][Plateau.dimension];
		setTitle("Rolit");
		setSize(300,300);
		setLayout(new GridLayout(Plateau.dimension,Plateau.dimension));
		Container container = getContentPane();
		for(int i = 0; i < Plateau.dimension; i++) {
			for(int j = 0; j < Plateau.dimension; j++) {	
				final int x = i;
				final int y = j;
				grille[i][j] = new JButton();
				grille[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(Main.Main.tourJoueur) {
							posChoisie = new int[2];
							posChoisie[0] = x;
							posChoisie[1] = y;
							Main.Main.jouerJoueur(posChoisie);
							System.out.println("Button clicked : "+x+", "+y);
						}
					}
				});
				container.add(grille[i][j]);
			}
		}

		this.repaint();

		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}


	public void afficherGraphiquement(Plateau plateau) {
		for(int i = 0; i < Plateau.dimension; i++) {
			for(int j = 0; j < Plateau.dimension; j++) {
				
				switch(plateau.getGrille()[i][j].getEtat()) {
				case EtatsCase.BLEU:
					grille[i][j].setIcon(BLEU);
					break;
				case EtatsCase.VERT:
					grille[i][j].setIcon(VERT);
					break;
				case EtatsCase.JAUNE:
					grille[i][j].setIcon(JAUNE);
					break;
				case EtatsCase.ROUGE:
					grille[i][j].setIcon(ROUGE);
					break;
				case EtatsCase.VIDE:
					grille[i][j].setIcon(VIDE);
					break;
				}
				this.repaint();
			}
		}

	}
}
