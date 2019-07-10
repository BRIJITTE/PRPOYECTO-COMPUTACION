package model.game_modes;

import model.ChessMove;
import model.board.Board;
import controller.GameLoop;
import controller.Player;

/**
 * 
 *son llamados por la clase GameLoop.
 */
public interface GameMode {

	/**
	 * Crea un objeto de tablero con los jugadores pasados. Esto
*El método devolverá una configuración completa basada en GameMode
	 * 
	 * @param player1
	 * @param player2
	 * @return tablero que se ha configurado con las reglas de GameMode
	 */
	Board initPieces(Player player1, Player player2);

	/**
	 * Se utiliza para determinar si un movimiento es "válido" en el contexto 
* Modo de juego. Este método inspecciona el tablero y devuelve verdadero si el
* el tablero está en un estado válido (para un StandardGame, esto verificará para ver
* si el rey está en jaque)
*
* @return true si el tablero está en un estado válido
	 */
	boolean boardValid(Board board, Player victim, ChessMove lastMove);

	/**
	 * Llamado cuando no hay movimientos para el jugador actual. En este
* punto el juego será un punto muerto o un jugador ha perdido.

* @return true si el jugador ha perdido, false si es un punto muerto
	 */
	boolean hasPlayerLost(Board board, Player victim);

	
	void postMoveAction(final GameLoop gameController, ChessMove lastMove);
	
}
