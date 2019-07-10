package view;

import java.io.IOException;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import model.ChessPiece;
import view.loaders.AssetLoader;
import controller.GameLoop;

/**
 * La clase Render implementa la funcionalidad de un GLEventListener
  * que define los métodos que son llamados por los GLCanvas subyacentes.
 Cualquier llamada a la ventana de repintado, conducirá a una llamada de pantalla () definida aquí
  *
 */
public class Renderer implements GLEventListener {

	private static final float SCALE_FACTOR = 18;
	
	private GameLoop gameController;
	private boolean isInitilized;
	
	private ChessPiece selectedPiece;
	
	private int[] viewportDimensions;
	private float[] projectionMatrix;
	private float[] modelviewMatrix;
	private GameCamera camera;
	
	public Renderer(GameLoop _gameController) {
		gameController = _gameController;
		gameController.setRenderer(this);
		viewportDimensions = new int[4];
		projectionMatrix = new float[16];
		modelviewMatrix = new float[16];
		isInitilized = false;
	}
	
	/**
	 * Llamado cuando la ventana necesita ser repintada. 
* El dibujo se produce en este método.
	 * 
	 */
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT); 
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();	
        
        gl.glScalef(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);
        camera.lookat();
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelviewMatrix, 0);
        
        // renderizar el tablero actual
        gameController.getBoard().render(gl, selectedPiece);
        
        gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		
		
	}

	/**
	 * este método configura todos los diferentes estados, y también carga los modelos / texturas
* en la carpeta 'elementos'
	 * 
	 */
	@Override
	public void init(GLAutoDrawable gLDrawable) {
		System.out.println("STARTED INIT");
        GL2 gl = gLDrawable.getGL().getGL2();
        
        //habilitar la iluminación
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        float[] lp = { 0.25f, 1.0f, 0.25f, 1.0f };
        float ms[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        float msh[] = { 50.0f };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lp, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, ms, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, msh, 0);
        
        
        gl.glEnable(GL2.GL_MULTISAMPLE);
        
        gl.glEnable(GL2.GL_CULL_FACE);
        gl.glClearColor(.5f, .5f, .5f, 1.0f); 
        gl.glClearDepth(1.0f); 
        gl.glEnable(GL2.GL_DEPTH_TEST);  
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.setSwapInterval(1)
        
        gl.glEnable(GL2.GL_NORMALIZE);
        
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        
        
        gl.glEnable(GL2.GL_POLYGON_SMOOTH);
        gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST); 
        
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST); 
        
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);
       
       
        
        try {
        	AssetLoader.getInstance().loadChessTextures(gl);
        	AssetLoader.getInstance().loadChessModels(gl);
        } catch (GLException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        isInitilized = true;
        System.out.println("DONE INIT");
	}
 
    @Override
    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluPerspective(60.0f, (float) width / (float) height, 1f, 10000);
        if (camera == null) {
        	camera = new GameCamera(height);
        	camera.setHorizontalRotation(gameController.getCurrentPlayer().getCameraDirection());
        }
        
        camera.setScreenLength(height);
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projectionMatrix, 0);
        gl.glGetIntegerv(GL2.GL_VIEWPORT, viewportDimensions, 0);
    }

    public void setSelectedPiece(ChessPiece piece) {
    	selectedPiece = piece;
    }
    
    public ChessPiece getSelectedPiece() {
    	return selectedPiece;
    }
    
	public GameCamera getCamera() {
		return camera;
	}

	public float[] getModelViewMatrix() {
		return modelviewMatrix;
	}

	public float[] getProjectionMatrix() {
		return projectionMatrix;
	}

	public int[] getViewportDimensions() {
		return viewportDimensions;
	}

	public synchronized boolean isInitilized() {
		return isInitilized;
	}

}
