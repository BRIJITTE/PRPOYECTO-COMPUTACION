package view.loaders.structures;

import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import view.loaders.AssetLoader;

/**
* Esta clase representa un modelo 3d, y es responsable de mantener
  * una referencia a un búfer de opengl y el nombre de la textura que se debe enlazar 
  *
 */
public class Model {

	
	private int type;
	private int vboMesh;
	private FloatBuffer buffer;
	private int size;
	private String texture;
	
	public Model(GL2 gl, int _type, FloatBuffer _buffer, int _size, String textureName) {
		type = _type;
		texture = textureName;
		size = _size;
		vboMesh = -1;
		if (gl == null)
			buffer = _buffer;
		else
			createBuffer(gl, _buffer);
	}
	
	/**
	 *Envía los datos del búfer
	 */
	private void createBuffer(GL2 gl, FloatBuffer buffer) {
		if (vboMesh != -1)
			return;
		
		int[] vbo = new int[1];
		gl.glGenBuffers(1, vbo, 0);
		vboMesh = vbo[0];
		
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboMesh); 
		gl.glBufferData(GL2.GL_ARRAY_BUFFER, 8 * size * 4, buffer, GL2.GL_STATIC_DRAW);
	}

	
	private void bindData(GL2 gl) {
		gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vboMesh); 
		gl.glVertexPointer(3, GL2.GL_FLOAT, 8 * (Float.SIZE / 8), 0);
		gl.glNormalPointer(GL2.GL_FLOAT, 8 * (Float.SIZE / 8), 3 * (Float.SIZE / 8));
		gl.glTexCoordPointer(2, GL2.GL_FLOAT, 8 * (Float.SIZE / 8), 6 * (Float.SIZE / 8));
	}
	
	/**
	 * Representa el modelo. 
	 */
	public void render(GL2 gl) {
		if (vboMesh == -1) { 
			createBuffer(gl, buffer);
			buffer = null;
		}
		
		bindData(gl);
		if (texture != null)
			AssetLoader.getInstance().bindTexture(gl, texture);
		
		gl.glDrawArrays(type, 0, size);
	}
	
}
