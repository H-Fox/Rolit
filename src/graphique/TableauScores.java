package graphique;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TableauScores extends JFrame {

	private JPanel contentPane;
	
	private ImageIcon BLEU = new ImageIcon(getClass().getResource("CaseBleue.png"));
	private ImageIcon ROUGE = new ImageIcon(getClass().getResource("CaseRouge.png"));
	private ImageIcon VERT = new ImageIcon(getClass().getResource("CaseVerte.png"));
	private ImageIcon JAUNE= new ImageIcon(getClass().getResource("CaseJaune.png"));


	/**
	 * Create the frame.
	 */
	public TableauScores(Map<String,Integer> scores) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Scores");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		List<JLabel> textScores = new ArrayList<>();
		List<JButton> iconScores = new ArrayList<>();
		int i = 0;
		int pos = 100;
		for (Map.Entry m : scores.entrySet()) {
			textScores.add(new JLabel("Agent "+m.getKey()+" : "+m.getValue()));
			textScores.get(i).setBounds(52,pos,372,14);
			contentPane.add(textScores.get(i));
			pos += 35;
			i++;
        }
		i = 0;
		pos = 97;
		for (Map.Entry m : scores.entrySet()) {
			String couleur = m.getKey().toString();
			switch(couleur) {
			case "Bleu":
				iconScores.add(new JButton());
				iconScores.get(i).setBounds(12,pos,26,23);
				iconScores.get(i).setIcon(BLEU);
				contentPane.add(iconScores.get(i));
				pos += 35;
				i++;
				break;
			case "Jaune":
				iconScores.add(new JButton());
				iconScores.get(i).setBounds(12,pos,26,23);
				iconScores.get(i).setIcon(JAUNE);
				contentPane.add(iconScores.get(i));
				pos += 35;
				i++;
				break;
			case "Vert":
				iconScores.add(new JButton());
				iconScores.get(i).setBounds(12,pos,26,23);
				iconScores.get(i).setIcon(VERT);
				contentPane.add(iconScores.get(i));				
				pos += 35;
				i++;
				break;
			case "Rouge":
				iconScores.add(new JButton());
				iconScores.get(i).setBounds(12,pos,26,23);
				iconScores.get(i).setIcon(ROUGE);
				contentPane.add(iconScores.get(i));				
				pos += 35;
				i++;
				break;
				
			}
        }
		

		
		JLabel lblTableauDesScores = new JLabel("Tableau des scores");
		lblTableauDesScores.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblTableauDesScores.setHorizontalAlignment(SwingConstants.CENTER);
		lblTableauDesScores.setBounds(0, 0, 434, 85);
		contentPane.add(lblTableauDesScores);
		
		setLocationRelativeTo(null);
		this.setVisible(true);
		
	}

}
