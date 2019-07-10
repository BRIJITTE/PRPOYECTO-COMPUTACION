package view.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Map;
import java.util.TreeMap;

import javax.media.opengl.GL2;
import javax.media.opengl.GLException;

import view.loaders.structures.Model;
import view.loaders.structures.Shader;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

/**
* Clase de utilidad utilizada para cargar los recursos necesarios para este proyecto, incluyendo
  * Texturas,  y modelos. Esta clase utiliza el patrón de diseño Singleton.
 */
public class AssetLoader {

	
	private static AssetLoader loader;
	
	
	private Map<String, Texture> textures;
	
	private Map<String, Shader> shaders;
	
	private Map<String, Model> models;
	
	private String currentTexture;
	
	private AssetLoader() {
		textures = new TreeMap<String, Texture>();
		shaders = new TreeMap<String, Shader>();
		models = new TreeMap<String, Model>();
	}
	
	/**
	 * Devuelve el único objeto creado de la clase AssetLoader,
* y si no existe ningún objeto aún crea uno.
	
	 */
	public static AssetLoader getInstance() {
		if (loader == null)
			loader = new AssetLoader();
		
		return loader;
	}
	
	
	/**
	 * Carga un archivo de modelo básico, con el formato (vértice, normal, textureCoord) \ n "
	 */
	public void loadChessModels(GL2 gl) {
		try {
			String[] modelNames = {
					"Bishop.mdl", "Chancellor.mdl", "King.mdl", "Knight.mdl",
					"LameQueen.mdl", "Pawn.mdl", "Queen.mdl", "Rook.mdl"
				};
			ClassLoader cl = getClass().getClassLoader();
			for (String file : modelNames) {
				
				//crear un lector de archivos
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(cl.getResourceAsStream("view/Models/" + file)));
				
				
				int size = Integer.parseInt(reader.readLine());
				
				FloatBuffer buffer = ByteBuffer.allocateDirect(size * 8 * (Float.SIZE / 4)).
		                order(ByteOrder.nativeOrder()).asFloatBuffer();
				
				String currentLine;
				while ((currentLine = reader.readLine()) != null) {
					for (String val : currentLine.split(" ")) 
						buffer.put(Float.parseFloat(val)); 
				}
			
				buffer.rewind();
				Model model = new Model(gl, GL2.GL_QUADS, buffer, size, null);
				models.put(file.split("\\.")[0], model);
				reader.close();
			}
		} catch (IOException ex) { ex.printStackTrace(); }
	}
	
	/**
	 *Agrega un modelo incorporado en la aplicación, para añadir sus modelos creados dinámicamente.
	 */
	public void addModel(Model model, String name) {
		if (models.containsKey(name))
			return;
		
		models.put(name, model);
	}


	public Model getModel(String name) {
		return models.get(name);
	}
	
	
	public void loadChessTextures(GL2 gl) throws GLException, IOException {
		ClassLoader cl = getClass().getClassLoader();
		String[] texturesNames = { "Black.png", "BlackWhite.png", "Green.png", "White.png" };
		for (String file : texturesNames)  { //recorrer todas las imágenes
			Texture tex = TextureIO.newTexture(cl.getResourceAsStream("view/Textures/" + file), true, TextureIO.PNG);
			tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		    tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
		    tex.enable(gl);
		    tex.bind(gl);
			textures.put(file.split("\\.")[0], tex);
		}
	}
	
	/**
	 * Enlazar la textura proporcionada por el nombre
	 */
	public void bindTexture(GL2 gl, String name) {
		if (currentTexture != null && currentTexture.equals(name))
			return;
		
		textures.get(name).bind(gl);
		currentTexture = name;
	}
	
	/**
	 * Carga un archivo de sombreado 
	 */
	public void loadShader(GL2 gl, String path, String name) throws IOException {
			File fs = new File(path + File.pathSeparator + name + ".fs");
			File vs = new File(path + File.pathSeparator + name + ".vs");
			Shader shader = new Shader(gl, vs, fs);
			shaders.put(name, shader);
	}
	
}
