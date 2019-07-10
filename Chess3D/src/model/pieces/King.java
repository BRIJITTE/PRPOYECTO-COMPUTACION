package model.pieces;

import java.util.ArrayList;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import controller.Player;

/**
* Representa al rey, que en un juego estándar da como resultado un
  * Pérdida si es capturado. Dependiendo del juego, un rey no siempre puede ser
  * Importante (como en el suicidio) por lo que la opción de validar cheque
  * se debe pasar, que se utiliza para determinar si el rey puede pasar a
  * verifique la situación
 *
 */
public class King extends ChessPiece {
	
	public King(int x, int y, Board _board, Player _player) {
		super(x, y, _board, _player);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Devuelve todos los movimientos diagonales / adyacentes que el rey puede capturar
	 */
	@Override
	public ArrayList<ChessMove> getPossibleMoves() {
		//obtener movimientos adyacentes inmediatos
		ArrayList<ChessMove> moves = getDiagonalMoves(false, true);
		//obtener movimientos diagonales inmediatos
		moves.addAll(getRankFileMoves(false, true));
		return moves;
	}

	@Override
	public String getType() {
		return "King";
	}

}
