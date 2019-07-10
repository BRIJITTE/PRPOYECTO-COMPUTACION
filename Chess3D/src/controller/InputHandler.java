package controller;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import model.ChessMove;
import model.ChessPiece;
import model.board.Board;
import view.GameCamera;
import view.Renderer;

/**
 * Maneja cualquier pulsación de tecla o clic del mouse que ocurra
  * Dentro del marco de ajedrez. Los métodos interpretan los clics, y
  * pasa los comandos al GameLoop o al Renderer
  *https://www.coding-daddy.xyz/node/11?fbclid=IwAR0E4R226mecn5g6JvbwHOhvj3uQIsruDfqDqVS_oM6N8ofoj1mdMeHh9z4

  *
  */
public class InputHandler implements KeyListener, MouseListener {

	/**
	 * Referencias al GameLoop y al Renderer
	 */
	private GameLoop gameController;
	private Renderer renderer;
	
	public InputHandler(GameLoop _gameController, Renderer _renderer) {
		gameController = _gameController;
		renderer = _renderer;
	}
	
	/**
	 * Este método interpreta un clic dentro de la ventana de ajedrez.
         * Utiliza la cámara, para encontrar dónde se traduce ese clic.
         * Espacio 3D (es decir, donde se interseca con el tablero). Este método maneja los casos.
         * Donde ya hay una pieza seleccionada, y que hacer con esa pieza.
	 * 
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		//llegar a camara
		GameCamera camera = renderer.getCamera();
		Point2D.Float fpoint = new Point2D.Float();
		fpoint.x = arg0.getPoint().x;
		fpoint.y = arg0.getPoint().y;
		Board board = gameController.getBoard();
		//obtener la ubicación donde la ubicación de clic se cruza con la placa 3D
		Point clickLoc = board.getBoardPosition(camera.getClick(fpoint, renderer));
		//get the chess peice at that tile
		ChessPiece piece = board.getTile(clickLoc.x, clickLoc.y);
		if (renderer.getSelectedPiece() != null) { //Si una pieza aún no está seleccionada ...
			//crear un objeto ChessMove que mueva la pieza seleccionada a la ubicación seleccionada
			ChessMove move = new ChessMove(clickLoc, renderer.getSelectedPiece());
			if (renderer.getSelectedPiece().getValidMoves().contains(move)) { //Si este ChessMove es válido para la pieza..
				gameController.executeMove(move);  //ejecutar el movimiento
				renderer.setSelectedPiece(null); //la pieza ya no está seleccionada
			} else if (renderer.getSelectedPiece() == piece) { //Si seleccionamos la pieza ya seleccionada ...
				renderer.setSelectedPiece(null); //la pieza ya no está seleccionada
			} else if (piece != null && piece.getPlayer() == gameController.getCurrentPlayer()) { 
				if (piece.getValidMoves().size() != 0) //Si tiene algún movimiento válido ...
					renderer.setSelectedPiece(piece); //Establecer pieza seleccionada
			}
		//Si la pieza está seleccionada y es una pieza de nuestro jugador actual.
		} else if (piece != null && piece.getPlayer() == gameController.getCurrentPlayer()) { 
			if (piece.getValidMoves().size() != 0) //Si tiene algún movimiento válido ...
				renderer.setSelectedPiece(piece); //Establecer pieza seleccionada
		} else {
			renderer.setSelectedPiece(null);
		}
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent arg0) { }

	/**
       * Este método captura cualquier pulsación de tecla, que actualmente solo maneja deshacer
	 */
	@Override
	public void keyPressed(KeyEvent arg0) { 
		if (arg0.getKeyCode() == KeyEvent.VK_Z && arg0.isControlDown()) {
			gameController.undoLastMove();
			renderer.setSelectedPiece(null);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) { }

	@Override
	public void keyTyped(KeyEvent arg0) { }

}
