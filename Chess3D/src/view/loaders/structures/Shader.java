package view.loaders.structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.media.opengl.GL2;

/**
*Clase de sombreado que representa un sombreador opengl, es decir, un
  * Vértice y fragmento de sombreado.
  *
  * Actualmente esto no se usa.
  * https://www.coding-daddy.xyz/node/16
 */
public class Shader {

	private int shaderprogram;
	private int vertexShader;
	private int fragmentShader;
	
	public Shader(GL2 gl, File vs, File fs) throws IOException {
		createShader(gl, vs, fs);
	}
	
	private void createShader(GL2 gl, File vs, File fs) throws IOException {
		vertexShader = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		fragmentShader = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
		
		// Empieza a leer el código del vértice
		BufferedReader brv = new BufferedReader(new FileReader(vs));
		String src = "";
		String line;
		while ((line = brv.readLine()) != null) 
		  src += line + "\n";
		
		
		gl.glShaderSource(vertexShader, 1, new String[] { src }, null);
		gl.glCompileShader(vertexShader);

		//Empieza a leer código de fragmento
		BufferedReader brf = new BufferedReader(new FileReader(fs));
		src = "";
		while ((line = brf.readLine()) != null) 
		  src += line + "\n";	
		
		
		gl.glShaderSource(fragmentShader, 1, new String[] { src }, null);
		gl.glCompileShader(fragmentShader);
		
		//crear programa de sombreado
		shaderprogram = gl.glCreateProgram();
		gl.glAttachShader(shaderprogram, vertexShader);
		gl.glAttachShader(shaderprogram, fragmentShader);
		gl.glLinkProgram(shaderprogram);
		gl.glValidateProgram(shaderprogram);
		
		brv.close();
		brf.close();
		
		useShader(gl);
	}
	
	public void useShader(GL2 gl) {
		gl.glUseProgram(shaderprogram);
	}
	
}
