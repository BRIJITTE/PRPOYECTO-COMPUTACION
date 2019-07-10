package view;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import controller.GameLoop;
import controller.InputHandler;

/**
 * Define un marco para el juego de ajedrez. Esta clase
  * Reúne todas las partes que se necesitan para
  * iniciar un juego de ajedrez, como el lienzo, el renderizador,
  * Gestor de entrada, y gameloop. Para comenzar realmente el juego.
 *
 */
public class GameFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3248983927774474631L;
	
	private GameLoop gameController;
	private GLCanvas canvas;
	
	public GameFrame() {
		GLProfile profile = GLProfile.get(GLProfile.GL2);
    	GLCapabilities capabilities = new GLCapabilities(profile);
    	capabilities.setSampleBuffers(true);
    	capabilities.setNumSamples(4);
    	capabilities.setDepthBits(24);
    	
		
    	canvas = new GLCanvas(capabilities);
    	
    	setTitle("Chess!");
    	setJMenuBar(new GameMainMenu(this, gameController));
		add(canvas);
		setSize(900, 600);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//crear gameloop
    	gameController = new GameLoop(canvas, this);
    	Renderer renderer = new Renderer(gameController);
    	canvas.addGLEventListener(renderer);
    	
    	InputHandler in = new InputHandler(gameController, renderer);
		canvas.addKeyListener(in);
		canvas.addMouseListener(in);
	}
	
	/*
	 * -------------------
	 * Getters and Setters
	 * -------------------
	 */
	
	public GameLoop getGameController() {
		return gameController;
	}
	
	public GLCanvas getCanvas() {
		return canvas;
	}
	
}
