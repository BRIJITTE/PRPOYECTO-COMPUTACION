package view;

import java.awt.geom.Point2D;

import javax.media.opengl.glu.GLU;
import javax.vecmath.Vector3f;

import controller.util.Animation.Animatable;

/**
 * Esta clase representa una cámara en el espacio 3D.
  * La ubicación de la cámara se define en coordenadas esféricas
  * es una rotación horizontal de [0, 2 * PI] y una rotación vertical [0, PI / 2]
 * http://forum.jogamp.org/Loading-and-drawing-obj-models-td2708428.html
 */
public class GameCamera implements Animatable {

	private GLU glu;
	
	private int screenLength;
	
	private float viewDistance;
	
	private float horizontalRotation;
	private float verticalRotation;
	
	public GameCamera(int _screenLength) {
		screenLength = _screenLength;
		viewDistance = 130;
		verticalRotation = (float) (Math.PI / 4);
		glu = new GLU();
	}
	
	/**
	 * Genera la ubicación de la cámara a partir de la rotación actual definida.
* mediante el uso de las coordenadas esféricas.
	 */
	private Vector3f getLocation() {
		Vector3f loc = new Vector3f();
		loc.x = (float) (Math.cos(horizontalRotation) * Math.cos(verticalRotation) * viewDistance);
		loc.y = (float) (Math.sin(verticalRotation) * viewDistance);
		loc.z = (float) (Math.sin(horizontalRotation) * Math.cos(verticalRotation) * viewDistance);
		return loc;
	}
	
	
	public void lookat() {
		Vector3f loc =  getLocation();
		glu.gluLookAt(loc.x, loc.y, loc.z,
						0, 0, 0, 
						0, 1, 0);	
	}
	
	/**
	 * Convierte un punto 2D en la pantalla en un punto 3D que se interseca
	 */
	public Point2D.Float getClick(Point2D.Float point, Renderer renderer) {
		//posición invertida
		point.y = screenLength - point.y;
		float[] rayStart = new float[3]; //el inicio de nuestro click
		glu.gluUnProject(point.x, point.y, 0, 
					renderer.getModelViewMatrix(), 0, 
					renderer.getProjectionMatrix(), 0, 
					renderer.getViewportDimensions(), 0, 
					rayStart, 0);
		float[] rayDir = new float[3]; //el inicio de nuestro clic en z = 1
		glu.gluUnProject(point.x, point.y, 1,
					renderer.getModelViewMatrix(), 0, 
					renderer.getProjectionMatrix(), 0, 
					renderer.getViewportDimensions(), 0, 
					rayDir, 0);
		rayDir[0] -= rayStart[0];
		rayDir[1] -= rayStart[1];
		rayDir[2] -= rayStart[2];
		
		Point2D.Float retVal = new Point2D.Float();
	    retVal.x = (float) (rayStart[0] - rayDir[0] * rayStart[1] / rayDir[1]);
	    retVal.y = (float) (rayStart[2] - rayDir[2] * rayStart[1] / rayDir[1]);
		return retVal;
	}
	
	/**
	 * Método que se llama durante la animación, la cámara permite
* Animación alrededor de su rotación horizontal.
	 */
	@Override
	public void setValue(String fieldName, float value) {
		if (fieldName.equals("horizontalRotation"))
			horizontalRotation = value;
	}
	
	/*
	 * ------------------------
	 * Setters and Getters
	 * ------------------------
	 */

	public void setScreenLength(int length) {
		screenLength = length;
	}
	
	public void setHorizontalRotation(float val) {
		horizontalRotation = val;
	}
	
	public float getHorizontalRotation() {
		return horizontalRotation;
	}
	
}
