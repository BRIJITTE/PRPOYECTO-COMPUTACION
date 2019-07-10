package controller;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.ChessMove;
import model.board.Board;
import model.game_modes.GameMode;
import view.GameCamera;
import view.Renderer;
import view.loaders.AssetLoader;
import controller.util.Animation;

/**
  * Esta clase controlará el flujo del juego del programa. Esta clase
  * implementa el bucle de ejecución de mantenimiento para esta aplicación, y tiene funciones básicas
  * que controla el flujo de juegos de ajedrez, como ejecutar movimientos, cancelar juegos y configurar JuegosNuevo
  * https://stackoverflow.com/questions/27671817/jogl-efficient-render-loop
  */

public class GameLoop implements Runnable {
	
	 
	private GLCanvas canvas;
	private Renderer renderer;
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private Board board;
	private GameMode gameMode;
	private Stack<ChessMove> allMoves;
	private Player winner;
	private boolean running;
        private float lastRenderTime;
	private CopyOnWriteArrayList<Animation> animations;
	
	/**
	 * Construye un nuevo gameloop, con el lienzo dado.
	 * 
	 */
	public GameLoop(GLCanvas _canvas, JFrame frame) {
		canvas = _canvas;
		player1 = new Player(1, true, frame);
		player2 = new Player(-1, true, frame);
		player1.setOtherPlayer(player2);
		player2.setOtherPlayer(player1);
	}
	
	/**
	 * Configura el juego para el modo de juego dado. Esta
         * Método inicializa los jugadores y el tablero. Esta
         * El método se llama automáticamente, cuando el juego actual termina.
	 *
	 */
	public synchronized void setupNewGame(GameMode _gameMode) {
		// restablecer todas las animaciones y movimientos
		allMoves = new Stack<ChessMove>();
		animations = new CopyOnWriteArrayList<Animation>();
		
		// reiniciar jugador actual
		currentPlayer = player1;
		gameMode = _gameMode;
		player1.reset();
		player2.reset();
		board = gameMode.initPieces(player1, player2);
		
		//Cargar los modelos de tablero, se ignorarán si ya están creados.
		AssetLoader.getInstance().addModel(board.generateBoardModel(), board.getType());
		AssetLoader.getInstance().addModel(board.generateTileModel(), board.getType() + ":Tile");
		running = false;
		winner = null;
		
		//coloque la cámara en la dirección del jugador 1
		if (renderer.getCamera() != null)
			renderer.getCamera().setHorizontalRotation(player1.getCameraDirection());
		
		//start a new game thread
		new Thread(this).start();
	}
	
	/**
        * el método continuará ejecutándose hasta que la ejecución esté configurada en "falso", es decir,
        * El juego ha terminado. Cuando se ejecuta en falso, se mostrará el ganador.
        * y se establecerá un nuevo juego.
	 */
	@Override
	public void run() {
		running = true;
		/*Cada iteración de este hilo actualiza las animaciones y
                 * Muestra el tablero. Dado que las tasas de fotogramas pueden variar, calculamos
                 * el último tiempo de ejecución para actualizar las animaciones de manera uniforme.
		 */
		while (running || !animations.isEmpty()) {
			long startTime = System.nanoTime();
			
			ArrayList<Animation> removeList = new ArrayList<Animation>();
			for (Animation animation : animations)
				if (animation.stepAnimation(lastRenderTime))
					removeList.add(animation);
			
			animations.removeAll(removeList);
			canvas.display();
				
			long endTime = System.nanoTime();	
			lastRenderTime = (float) ((endTime - startTime) / 1.0E9);
		}
		
		String winnerString = (winner == null) ? "Tie" : winner.getPlayerName();
		JOptionPane.showMessageDialog(canvas, 
				"Winner was: " + winnerString + "\nScore is:\n" + player1.getPlayerName() + ":" + player1.getWins() + 
				" " + player2.getPlayerName() + ":" + player2.getWins(),  
				"Game Over", 
				JOptionPane.INFORMATION_MESSAGE);
		
		setupNewGame(gameMode);
	}
		
	/**
	 
* Ejecuta un movimiento de ajedrez, configura una animación,
* Este movimiento también comprueba si hay
* es cualquier movimiento que le queda al otro para hacer, de lo contrario el juego termina
* Si no hay un juego en ejecución, este método
* no hace nada.
	 *
	 */
	public synchronized void executeMove(ChessMove move) {
		if (!running) 
			return;
		
		//añadir movimiento
		allMoves.push(move);
		//en realidad ejecuta el movimiento
		move.executeMove(true);
		currentPlayer = currentPlayer.getOtherPlayer();
		//Compruebe si hay movimientos a la izquierda
		boolean noMoves = board.noPossibleMoves(currentPlayer);
		if (noMoves) {
			//Comprueba el modo de juego para ver si el jugador ha perdido//
			if (gameMode.hasPlayerLost(board, currentPlayer)) 
				cancelGame(currentPlayer.getOtherPlayer());
			else //o simplemente atado
				cancelGame(null);
			
			return;
		}
		gameMode.postMoveAction(this, move);
		
		//Crea animaciones basadas en ubicaciones de movimiento.
		Point2D.Float start = board.getRenderPosition(move.getStartLocation());
		Point2D.Float end = board.getRenderPosition(move.getMoveLocation());
		
		animations.add(new Animation(move.getPiece(), "drawLocationX", start.x, end.x, 1));
		animations.add(new Animation(move.getPiece(), "drawLocationY", start.y, end.y, 1));
		//girar la cámara
		GameCamera c = renderer.getCamera();
		animations.add(new Animation(c, "horizontalRotation", c.getHorizontalRotation(), 
				currentPlayer.getCameraDirection(), 2));
	}
	
	/**
	Deshace un movimiento de ajedrez, configura una animación,
       * Si no hay un juego en ejecución, o si hay movimientos para Deshacer
       *este método no hace nada.
       *
	*/
	public synchronized void undoLastMove() {
		if (!running) 
			return;
		
		if (allMoves.empty())
			return;
		
		//obtener el último movimiento
		ChessMove lastMove = allMoves.pop();
		//deshacerlo
		lastMove.undoMove();
		//jugadores de intercambio
		currentPlayer = currentPlayer.getOtherPlayer();
		if (!allMoves.isEmpty())
			gameMode.postMoveAction(this, allMoves.peek());
		
		//crear animación
		Point2D.Float start = board.getRenderPosition(lastMove.getMoveLocation());
		Point2D.Float end = board.getRenderPosition(lastMove.getStartLocation());
		
		animations.add(new Animation(lastMove.getPiece(), "drawLocationX", start.x, end.x, 1));
		animations.add(new Animation(lastMove.getPiece(), "drawLocationY", start.y, end.y, 1));
		//girar la cámara
		GameCamera c = renderer.getCamera();
		animations.add(new Animation(c, "horizontalRotation", c.getHorizontalRotation(), 
				currentPlayer.getCameraDirection(), 2));
	}
	
	/**
	 * Cancela el juego, lo que resultará en ningún movimiento,
         * Poder hacerse, pero las animaciones terminarán.
          El ganador del juego, puede ser nulo, en cuyo caso el
         * el juego era un empate
	 */
	public void cancelGame(Player _winner) {
		running = false;
		winner = _winner;
		if (_winner != null)
			_winner.addWin();
	}
	
	/*
	 * -------------------------------------------
	 * Getters and Setters start here
	 * -------------------------------------------
	 */
	
	public void addAnimation(Animation anim) {
		animations.add(anim);
	}
	
	public void setRenderer(Renderer _renderer) {
		renderer = _renderer;
	}
	
	public Board getBoard() {
		return board;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		 return player2;
	}
	
	public GLCanvas getCanvas() {
		return canvas;
	}
	
}
