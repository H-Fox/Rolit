package graphique;

import java.awt.Container;
import java.awt.GridLayout;

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

	private JButton[][] grille;
	
	private ImageIcon BLEU = new ImageIcon(getClass().getResource("icones/CaseBleue.png"));
	private ImageIcon ROUGE = new ImageIcon(getClass().getResource("icones/CaseRouge.png"));
	private ImageIcon VERT = new ImageIcon(getClass().getResource("icones/CaseVerte.png"));
	private ImageIcon JAUNE= new ImageIcon(getClass().getResource("icones/CaseJaune.png"));
	private ImageIcon VIDE = new ImageIcon(getClass().getResource("icones/CaseVide.png"));

	public PlateauGraphique(){
		grille = new JButton[Plateau.dimension][Plateau.dimension];
		setTitle("Rolit");
		setSize(800,450);
		setLayout(new GridLayout(Plateau.dimension,Plateau.dimension));
		Container container = getContentPane();
		for(int i = 0; i < Plateau.dimension; i++) {
			for(int j = 0; j < Plateau.dimension; j++) {				
				grille[i][j] = new JButton();
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
