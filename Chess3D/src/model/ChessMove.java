package model;

import java.awt.Point;

/**
  * Objeto de datos básicos para mantener un posible movimiento de ajedrez. Un ajedrez
  * movimiento se compone de 2 piezas de datos, la pieza que se está moviendo,
  * y la ubicación a la que se moverá la pieza.
  **/

public class ChessMove {

	/**
	 * Original locacion
	 */
	private Point originalLocation;
	
	/**
	 *Si esta pieza se hubiera movido aún.
	 */
	private boolean hadMoved;
	
	/**
	 * La pieza de ubicación puede moverse
	 */
	private Point moveLocation;
	
	/**
	 * Pieza que se esta moviendo
	 */
	private ChessPiece piece;
	
	/**
	 * Pieza que puede haber sido capturada por el movimiento, puede ser nula.
	 */
	private ChessPiece capturedPiece;
	
	/**
	 *@param _moveLocation La ubicación que se está moviendo a
         * @param _piece La pieza que se está moviendo.
	 */
	public ChessMove(Point _moveLocation, ChessPiece _piece) {
		originalLocation = new Point(_piece.getLocation());
		moveLocation = _moveLocation;
		piece = _piece;
	}
	
	public boolean equals(Object obj) {
		ChessMove move = (ChessMove) obj;
		return move.moveLocation.equals(moveLocation) &&
				move.originalLocation.equals(originalLocation) &&
				move.piece == piece;
	}
	
	/**
* Ejecuta el movimiento si aún no se ha hecho. Llamando a esto
* El método supone que el movimiento ha sido verificado como válido, de lo contrario un
* Se lanzará una excepción. Después de que este movimiento ha sido completado,
* caputedPiece se establecerá con la pieza que estaba originalmente en el azulejo
*/
	public void executeMove(boolean checkValid) {
		if (piece.getLocation().equals(moveLocation)) //we have already done the move...
			return;
		
		hadMoved = piece.getHasMoved();
		capturedPiece = piece.movePieceToTile(moveLocation, checkValid);
		if (capturedPiece != null) 
			capturedPiece.getPlayer().pieceCaptured(capturedPiece);	
	}
	
	/**
	 * Deshace el movimiento si aún no se ha deshecho. La pieza se mueve de nuevo a su posición original, y si hubiera
          * Una pieza allí ya, se sustituye.
	 */
	public void undoMove() {
		if (piece.getLocation().equals(originalLocation)) //we have already undone
			return;
		
		piece.movePieceToTile(originalLocation, false);
		piece.setHasMoved(hadMoved);
		if (capturedPiece != null) {
			capturedPiece.getPlayer().pieceUncaptured(capturedPiece);	
			capturedPiece.movePieceToTile(moveLocation, false);
			capturedPiece = null;
		}
	}
	
	/* 
	 * ------------------------------
	 * Getters and Setters start here
	 * ------------------------------
	 */

	public Point getStartLocation() {
		return originalLocation;
	}
	
	public Point getMoveLocation() {
		return moveLocation;
	}
	
	public ChessPiece getPiece() {
		return piece;
	}

	public Object getCapturedPiece() {
		return capturedPiece;
	}
	
}
