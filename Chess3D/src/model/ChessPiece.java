package model;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.media.opengl.GL2;

import model.board.Board;
import view.loaders.AssetLoader;
import controller.Player;
import controller.util.Animation.Animatable;

/**
  * Plantilla básica para cualquier pieza de ajedrez, que proporciona la base.
  * Métodos para mover la pieza.
  *
  */
public abstract class ChessPiece implements Animatable {

	private float drawLocationX;
	private float drawLocationY;
	protected Point location;
	protected boolean hasMoved;
	protected Player player;
	protected Board board;
	
	/**
	 * Inicializa las variables básicas de una pieza de ajedrez y lo agrega al tablero
	 */
	public ChessPiece(int x, int y, Board _board, Player _player) {
		location = new Point(x, y);
		Point2D.Float drawLoc = _board.getRenderPosition(location);
		drawLocationX = drawLoc.x;
		drawLocationY = drawLoc.y;
		
		board = _board;
		player = _player;
		board.setTile(this, x, y);
		player.addPiece(this);
		hasMoved = false;
	}
	
	/**
      * En el archivo de rangos de direcciones de las tablas. Solo devolverá todos los movimientos.
      * hasta (e incluyendo) una pieza enemiga y mientras la pieza siga siendo válida
         *  
      * @param cont true devuelve la lista de todos los movimientos en las direcciones planas
      * @param canCapture true si esta pieza puede capturar
      * @return una serie de los movimientos válidos posibles en la dirección plana
	 */
	protected ArrayList<ChessMove> getRankFileMoves(boolean cont, boolean canCapture) {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		ArrayList<Point> adj = board.getAdjacentRankFileTiles(location.x, location.y);
		Point currentPos;
		for (int i = 0;i < adj.size();i ++) {
			currentPos = adj.get(i);
			while (board.isMovableTile(currentPos, player, canCapture)) {
				ChessMove move = new ChessMove(currentPos, this);
				moves.add(move);
				
				if (board.hasEnemyPiece(currentPos, player))
					break;
				
				if (!cont) break;
				currentPos = board.getAdjacentRankFileTiles(currentPos).get(i);
			}
		}
		return moves;
	}
	
	/**
         * En las direcciones diagonales de las tablas. Solo devolverá todos los movimientos.
          * hasta (e incluyendo) una pieza enemiga y mientras la pieza siga siendo válida
*
* @param cont true devuelve la lista de todos los movimientos en las direcciones diagonales
* @param canCapture true si esta pieza puede capturar
* @return una serie de los movimientos válidos posibles en la dirección diagonal
	 */
	protected ArrayList<ChessMove> getDiagonalMoves(boolean cont, boolean canCapture) {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		ArrayList<Point> adj = board.getAdjacentDiagonalTiles(location.x, location.y);
		Point currentPos;
		for (int i = 0;i < adj.size();i ++) {
			currentPos = adj.get(i);
			while (board.isMovableTile(currentPos, player, canCapture)) {
				ChessMove move = new ChessMove(currentPos, this);
				moves.add(move);
				
				if (board.hasEnemyPiece(currentPos, player))
					break;
				
				if (!cont) break;
				currentPos = board.getAdjacentDiagonalTiles(currentPos).get(i);
			}
		}
		return moves;
	}
	
	/**
	 * Mueve esta pieza a la posición, mientras verifica internamente
         * si los movimientos son válidos (comparando la ubicación dada con los movimientos posibles)
         *
         * @ throws IllegalArgumentException si esta posición no puede moverse a la ubicación dada
         * @param localiza la ubicación para moverte a
         * @param checkValid
         * @return la pieza de ajedrez que estaba en la posición original
	 */
	public ChessPiece movePieceToTile(Point loc, boolean checkValid) {
		if (!checkValid || getValidMoves().contains(new ChessMove(loc, this))) {
			if (checkValid)
				hasMoved = true;
			
			Point2D.Float drawLoc = board.getRenderPosition(loc);
			drawLocationX = drawLoc.x;
			drawLocationY = drawLoc.y;
			return board.setTile(this, loc.x, loc.y);
		}
		
		throw new IllegalArgumentException("Bad Position");
	}
	
	/**
	 * Anulado por todas las clases de piezas de ajedrez, que devuelve una matriz de ajedrez.
         * Movimientos que esta pieza puede hacer.
          *
         * @return Una serie de todos los movimientos posibles que esta pieza puede hacer.
	 */
	public abstract ArrayList<ChessMove> getPossibleMoves();
	
	/**
	 * Subconjunto de getPossibleMoves () que devuelve todos los movimientos que dar como resultado una captura
         *
         * @return Una serie de todos los movimientos posibles que esta pieza puede hacer.
         * Eso resulta en que tome una pieza.
	 */
	public ArrayList<ChessMove> getCaptureMoves() {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		for (ChessMove move : getPossibleMoves()) //loop through all moves...
			if (board.hasEnemyPiece(move.getMoveLocation(), player))
				moves.add(move); //add it
		
		return moves;
	}
	
	/**
	 * Subconjunto de getPossibleMoves () que contiene todos los movimientos que
         * Son válidos para el tipo de modo de juego. Por ejemplo, getPossibleMoves ()
         * para un rey devolvería las 8 casillas a su alrededor, mientras que getValidMoves ()
         * solo devolvería los movimientos que no ponen a este rey en jaque (para un juego estándar)
         *
         * @return Una serie de movimientos de ajedrez válidos.
	 */
	public ArrayList<ChessMove> getValidMoves() {
		ArrayList<ChessMove> moves = new ArrayList<ChessMove>();
		for (ChessMove move : getPossibleMoves())
			if (board.isValidMove(move))
				moves.add(move);
		
		return moves;
	}
	
	/**
	 * Used to determine a pieces type without using reflection (ie instanceof)
	 * 
	 * @return A string that contains the name of the piece, ie the Pawn
	 * class would return "Pawn"
	 */
	public abstract String getType();
	
	public void setHasMoved(boolean hadMoved) {
		hasMoved = hadMoved;
	}
	
	/**
	 * @return true if this piece has moved
	 */
	public boolean getHasMoved() {
		return hasMoved;
	}
	
	/**
	 * @return the owning player
	 */
	public Player getPlayer() { 
		return player;
	}
	
	/**
	 * Establece la ubicación de esta tabla de piezas en la ubicación.
	 */
	public void setLocation(Point _location) {
		location.setLocation(_location);
	}

	/**
	 * @return Point representando la ubicación del tablero de este objeto
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * Representa esta pieza al contexto actual de openGL. El drawX y
         * drawY son proporcionados por el tablero, mientras que el modelo se recupera de
         * La clase AssetLoader. El modelo utilizado es solo el nombre de la pieza.
         * (es decir, getType ())
	 * 
	 */
	public void render(GL2 gl) {
		AssetLoader.getInstance().bindTexture(gl, player.getColorTexture());
		
		gl.glPushMatrix();
			gl.glTranslatef(drawLocationX, 0, drawLocationY);
			AssetLoader.getInstance().getModel(getType()).render(gl);
		gl.glPopMatrix();
	}
	
	/**
	 *Para que esta pieza pueda ser animable, debe sobrecargar este método.
         * Se pueden animar dos campos en la clase, drawLocationX y drawLocationY
         * ¿Cuáles son las posiciones que esta pieza se representará en la pantalla.
	 * 
	 */
	@Override
	public void setValue(String fieldName, float value) {
		if (fieldName.equals("drawLocationX"))
			drawLocationX = value;
		else if (fieldName.equals("drawLocationY"))
			drawLocationY = value;
	}
	
}
