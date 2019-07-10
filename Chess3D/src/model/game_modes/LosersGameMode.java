package model.game_modes;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import model.board.RectangularBoard;

import model.pieces.King;

import model.pieces.Pawn;

import controller.GameLoop;
import controller.Player;

public class LosersGameMode implements GameMode {

	@Override
	public Board initPieces(Player player1, Player player2) {
		// Crear un tablero estándar de 8,8.
		RectangularBoard board = new RectangularBoard(this);

		// si la dirección es -1, coloca los peones en la fila 6, de lo contrario, ponlos en 1
		int p1PawnLoc = player1.getDirection() == -1 ? 6 : 1;
		int p2PawnLoc = player2.getDirection() == -1 ? 6 : 1;
		for (int i = 0; i < 8; i++) {
			new Pawn(i, p1PawnLoc, board, player1);
			new Pawn(i, p2PawnLoc, board, player2);
		}

		// si la dirección es -1, establezca las piezas especiales en la fila 7 de lo contrario establezca
         // ellos a 0
		int p1pieceLoc = player1.getDirection() == -1 ? 7 : 0;
		int p2pieceLoc = player2.getDirection() == -1 ? 7 : 0;
		
		new King(3, p1pieceLoc, board, player1);
		new King(3, p2pieceLoc, board, player2);

		return board;
	}

	@Override
	public boolean boardValid(Board board, Player victim, ChessMove lastMove) {
		if (lastMove.getCapturedPiece() != null)
			return true;
		
		lastMove.undoMove();
		for (ChessPiece p : victim.getPieces()) {
			if (!p.getCaptureMoves().isEmpty()) {
				lastMove.executeMove(false);
				return false;
			}
		}
			
		return true;
	}

	@Override
	public boolean hasPlayerLost(Board board, Player victim) {
		return victim.getPieces().isEmpty();
	}

	@Override
	public void postMoveAction(final GameLoop gameController, ChessMove lastMove) {
		
		
	}

}
