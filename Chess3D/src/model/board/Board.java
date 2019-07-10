package model.board;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.media.opengl.GL2;

import model.ChessMove;
import model.ChessPiece;
import model.game_modes.GameMode;
import model.pieces.Pawn;
import view.loaders.structures.Model;
import controller.Player;

/**
  * Clase de tablero abstracto que es responsable de todas las operaciones.
  * en un tablero de ajedrez. La junta es responsable de determinar si
  * Los movimientos son válidos, así como la representación de toda la escena.
  */

public abstract class Board {
	
	/**
	 * Referencia al modo de juego.
	 */
	protected GameMode gameMode;
	
	protected Board(GameMode _gameMode) {
		gameMode = _gameMode;
	}
	
	/**
	 * Genera el modelo 3d para el tablero, para que se pueda agregar como
         * Un modelo para la clase AssetLoader. NOTA: este método es llamado automáticamente por
         * el GameLoop durante la configuración y NUNCA debe llamarse de nuevo. La forma correcta
         * para acceder a este Modelo sería AssetLoader.getInstace (). getModel (getType ())
          *
         * @return el modelo de tablero generado
	 */
	public abstract Model generateBoardModel();
	
	/**
	   * Genera el modelo 3d para el tablero, para que se pueda agregar como
           * Un modelo para la clase AssetLoader. NOTA: este método es llamado automáticamente por
           * el GameLoop durante la configuración y NUNCA debe llamarse de nuevo. La forma correcta
           * para acceder a este Modelo sería AssetLoader.getInstace (). getModel (getType ())
           *
           * @return el modelo de tablero generado
	 */
	public abstract Model generateTileModel();
	
	/**
	 * Cadena utilizada para identificar el tipo de tabla. También se utiliza como un único nombre del modelo
*
       * @return String se usa para identificar el tipo de tabla
	 */
	public abstract String getType();
	
	/**
	 * Convierte la ubicación de mosaico dada en un punto 3D, que se escala al tamaño de las tablas
         *
         * @param loc tile location
         * @return representación 3D de loc
	 */
	public abstract Point2D.Float getRenderPosition(Point loc);
	
	/**
	 * Convierte la ubicación 3d dada en una ubicación de mosaico,
          * que se puede utilizar para acceder a los elementos en el tablero
	 */
	public abstract Point getBoardPosition(Point2D.Float renderPoint);
	
	/**
	 * Devuelve la pieza en las coordenadas dadas, según lo interpretado por el objeto Tablero.
         * @return la pieza de ajedrez en la posición, null de lo contrario
	 */
	public abstract ChessPiece getTile(int x, int y);
	
	
	/**
	 * Establece la pieza en la posición (x, y), y establece el interno
         * Junta de ubicación de la pieza. Si una pieza ya existía se elimina.
         * del tablero, ubicación establecida en (-1, -1) y devuelta
         *
         * @param pieza. Pieza a mover.
         * @param x Ubicación del tablero x
         * @param y Ubicación del tablero y
          @return la pieza que estaba ocupando este azulejo, nulo de lo contrario
	 */
	public abstract ChessPiece setTile(ChessPiece piece, int x, int y);
	
	
	/**
	 *@param x Ubicación del tablero x
        * @param y Ubicación del tablero y
        * @return true si la ubicación dada está dentro de las dimensiones de los tableros, false de lo contrario
	 */
	public abstract boolean isInBounds(int x, int y);
	
	/**
	 * @param x Ubicación del tablero x
         * @param y Ubicación del tablero y
         * @param attacker El jugador que realiza el movimiento.
         * @return true si la ficha en (x, y) contiene una parte del jugador oponente
	 */
	public abstract boolean hasEnemyPiece(int x, int y, Player attacker);
	
	/**
	 * @param x Ubicación del tablero x
         * @param y Ubicación del tablero y
         * @param attacker El jugador que realiza el movimiento.
         * @param canCapture Si una pieza es capaz de capturar en el mosaico
         * @return true si el espacio está abierto (es decir, no hay ninguna pieza en la baldosa) o si
         * La ficha contiene una pieza enemiga, y el atacante puede capturarla.
	 */
	public abstract boolean isMovableTile(int x, int y, Player attacker, boolean canCapture);
	
	public abstract ArrayList<Point> getAdjacentRankFileTiles(int x, int y);
	
	public abstract ArrayList<Point> getAdjacentDiagonalTiles(int x, int y);
	
	public abstract ArrayList<Point> getKnightMoves(int x, int y);
	
	public abstract ArrayList<Point> getPawnMoves(int x, int y, Pawn pawn);
	 
	public abstract ArrayList<Point> getPawnAttacks(int x, int y, Pawn pawn);
	
	
	public boolean isInBounds(Point loc) {
		return isInBounds(loc.x, loc.y);
	}
	
	/**
	 *  @param loc Punto de ubicación del tablero
	 */
	public boolean hasEnemyPiece(Point loc, Player attacker) {
		return hasEnemyPiece(loc.x, loc.y, attacker);
	}
	
	/**
	 *  @param loc Punto de ubicación del tablero
	 */
	public boolean isMovableTile(Point loc, Player player, boolean canCapture) {
		return isMovableTile(loc.x, loc.y, player, canCapture);
	}

	/**
	 *  @param loc Punto de ubicación del tablero
	 */
	public ArrayList<Point> getAdjacentRankFileTiles(Point loc) {
		return getAdjacentRankFileTiles(loc.x, loc.y);
	}
	
	/**
	 * @param loc Punto de ubicación del tablero
	 */
	public ArrayList<Point> getAdjacentDiagonalTiles(Point loc) {
		return getAdjacentDiagonalTiles(loc.x, loc.y);
	}
	
	/**
	 * @param x Ubicación del tablero x
* @param y Ubicación del tablero y
* @param victim El jugador cuya pieza será tomada.
* @return true si la pieza de la víctima en (x, y) puede tomarse
* por cualquiera de las piezas del oponente.
	 */
	public boolean locationPressured(int x, int y, Player victim) {
		for (ChessPiece oppPiece : victim.getOtherPlayer().getPieces()) {
			for (ChessMove move : oppPiece.getCaptureMoves())
				if (move.getMoveLocation().equals(new Point(x, y)))
					return true;
		}
		return false;
	}
	
	/**
	 *@param victim El jugador cuya pieza será tomada.
	 */
	public boolean locationPressured(Point loc, Player victim) {
		return locationPressured(loc.x, loc.y, victim);
	}
	
	/**
	 * Determina si este movimiento es válido en el contexto del modo de juego. por
* ejemplo, un movimiento que pone a un rey en jaque no es válido. Este método simula
* el movimiento, luego le pide a gameMode que determine si el tablero está en un válido
* estado. La placa se restablece al estado original y el método vuelve.
* La respuesta del modo de juego.
	 */
	public boolean isValidMove(ChessMove move) {
		/* Funciona simulando el movimiento de ajedrez moviendo un gameMode.boardValid ()
* El tablero volverá a su estado original.
		 */
		move.executeMove(false);
		boolean retVal = gameMode.boardValid(this, move.getPiece().getPlayer(), move);
		move.undoMove();
		return retVal;
	}
	
	/**
	 *Devuelve todos los movimientos válidos para el jugador

* @return una matriz de todos los movimientos válidos
	 */
	public ArrayList<ChessMove> getAllMoves(Player player) {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		for (ChessPiece piece : player.getPieces()) 
			moves.addAll(piece.getValidMoves());
		
		return moves;
	}
	
	
	/**
	 * Comprueba si hay algún movimiento válido que puede ser hecho
	 */
	public boolean noPossibleMoves(Player victim) { 
		return getAllMoves(victim).isEmpty();
	}
	
	public abstract void render(GL2 gl, ChessPiece selectedPiece);
	
}
