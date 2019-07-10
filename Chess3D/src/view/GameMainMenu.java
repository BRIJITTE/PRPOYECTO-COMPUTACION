package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import controller.GameLoop;
import controller.Player;

/**
 *Barra de menú para el juego de ajedrez. 
 */
public class GameMainMenu extends JMenuBar implements ActionListener {

	
	private static final long serialVersionUID = -2675401737436931613L;

	private GameLoop gameController;
	
	private GameFrame gameFrame;
	
	private JMenuItem restart;
	private JMenuItem forfietBlack;
	private JMenuItem forfietWhite;
	
	public GameMainMenu(GameFrame frame, GameLoop controller) {
		gameFrame = frame;
		gameController = controller;
		JMenu mainMenu = new JMenu("Game");
		mainMenu.add(restart = new JMenuItem("Restart"));
		restart.addActionListener(this);
		mainMenu.add(forfietBlack = new JMenuItem("Forfiet Black"));
		forfietBlack.addActionListener(this);
		mainMenu.add(forfietWhite = new JMenuItem("Forfiet White"));
		forfietWhite.addActionListener(this);
		add(mainMenu);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == restart) { //si se seleccionó reinicio
			Player p1 = gameController.getPlayer1();
			int result = JOptionPane.showConfirmDialog(gameFrame, 
					"Are you sure you wish to restart, " + p1.getPlayerName() + "?",
					"Restart?",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.OK_OPTION) { //jugador 1 estuvo de acuerdo
				Player p2 = gameController.getPlayer2();
				result = JOptionPane.showConfirmDialog(gameFrame, 
					"Are you sure you wish to restart, " + p2.getPlayerName() + "?",
					"Restart?",
					JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) //jugador 2 estuvo de acuerdo
					gameController.cancelGame(null);
			}		
		} else if (arg0.getSource() == forfietWhite || arg0.getSource() == forfietBlack) {
			int result = JOptionPane.showConfirmDialog(gameFrame, 
							"Are you sure you wish to forfiet?",
							"Forfiet?",
							JOptionPane.YES_NO_OPTION);
			
			if (result == JOptionPane.OK_OPTION) {
				if (arg0.getSource() == forfietBlack)
					gameController.cancelGame(gameController.getPlayer1());
				else 
					gameController.cancelGame(gameController.getPlayer2());
			}
		}
	}
	
}
