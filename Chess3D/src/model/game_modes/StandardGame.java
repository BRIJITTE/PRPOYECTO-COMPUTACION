package model.game_modes;

import javax.swing.JOptionPane;

import model.ChessMove;
import model.board.Board;
import model.board.RectangularBoard;

import model.pieces.King;

import model.pieces.Pawn;

import controller.GameLoop;
import controller.Player;

/**
* Define un StandardGame, donde el tablero es rectangular y
  * El rey no puede estar en jaque.
 * 
 *
 */
public class StandardGame implements GameMode {

	@Override
	public Board initPieces(Player player1, Player player2) {
		
		RectangularBoard board = new RectangularBoard(this);
		
		//si la dirección es -1, establece los peones en la fila 6 de lo contrario configúralos en 1
		int p1PawnLoc = player1.getDirection() == -1 ? 6 : 1;
		int p2PawnLoc = player2.getDirection() == -1 ? 6 : 1;
                
		for (int i = 0;i < 1;i ++) {
			new Pawn(i, p1PawnLoc, board, player1);
			//peón nuevo 
		}
		
		//si la dirección es -1, establezca las piezas especiales en la fila 7; de lo contrario, ajústelos a 0
		int p1pieceLoc = player1.getDirection() == -1 ? 7 : 0;
		int p2pieceLoc = player2.getDirection() == -1 ? 7 : 0;
		
		new King(3, p1pieceLoc, board, player1);
		new King(3, p2pieceLoc, board, player2);
		
		return board;
	}	

	/**
	 * En un juego estándar, este tablero se inspecciona para asegurarse de que el rey no está en jaque
* @return true si el rey no está bajo control, false en caso contrario;
	 */
	@Override
	public boolean boardValid(Board board, Player mover, ChessMove lastMove) {
		return !board.locationPressured(mover.getKing().getLocation(), mover);
	}
	
	@Override
	public boolean hasPlayerLost(Board board, Player victim) {
		return board.locationPressured(victim.getKing().getLocation(), victim);
	}

	@Override
	public void postMoveAction(final GameLoop gameController, ChessMove lastMove) {
		final Player currentPlayer = gameController.getCurrentPlayer();
		if (gameController.getBoard().locationPressured(currentPlayer.getKing().getLocation(), currentPlayer)) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					String message = currentPlayer.getPlayerName() + " is in check";
					JOptionPane.showMessageDialog(gameController.getCanvas(), message);
				}
			});
			t.start();
		}	
	}
	
}
