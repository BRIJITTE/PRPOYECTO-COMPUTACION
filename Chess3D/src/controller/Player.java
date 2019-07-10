package controller;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.ChessPiece;
import model.pieces.King;

/**
  * Clase de jugador responsable de mantener sus propias piezas de ajedrez.
  * y la dirección general en que viajan los peones. El jugador también tiene
  * Una referencia al jugador contrario para facilitar el acceso.
  * http://forum.jogamp.org/Loading-and-drawing-obj-models-td2708428.html
  *
  */

public class Player {

	/**
	 * La forma en que la cámara debe enfrentarse para este jugador.
	*/
	private float cameraDirection;
	
	/**
	 * Representa la dirección y en la que se mueve este jugador (es decir, en el Estándar 1 o -1)
	 */
	private int direction;
	
	private String playerName;
	
	/**
	 * Lista de todas las piezas propiedad del jugador (incluye piezas capturadas)
	 */
	private ArrayList<ChessPiece> chessPieces;
	
	/**
	 * Lista de todas las piezas que han sido capturadas por este jugador
	 */
	private ArrayList<ChessPiece> capturedPieces;
	
	/**
	 * Referencia al rey para facilitar el acceso
	 */
	private King king;
	
	private int winCount;
	
	/**
	 *El oponente jugador
	 */
	private Player otherPlayer;
	
	public Player(int _direction, boolean requestName, Component frame) {
		direction = _direction;
		cameraDirection = (direction == 1) ? 3.1415f * 3 / 2 : 3.1415f / 2;
		
		chessPieces = new ArrayList<ChessPiece>();
		capturedPieces = new ArrayList<ChessPiece>();
		if (requestName) {
			String playernum = (direction == 1) ? "1" : "2";
			playerName = JOptionPane.showInputDialog(frame, "Jugador numero" + playernum + ". Nombre: ");
		}
	}
	
	public void reset() {
		chessPieces = new ArrayList<ChessPiece>();
		capturedPieces = new ArrayList<ChessPiece>();
		king = null;
	}

	/**
	 * Agrega la pieza al jugador, si es una tienda rey.
         * También por separado para facilitar el acceso.
	 */
	public void addPiece(ChessPiece chessPiece) {
		if (chessPiece.getType().equals("King"))
			king = (King) chessPiece;
		
		chessPieces.add(chessPiece);
	}
	
	/*
	 * -----------------------
	 * Setters and Getters 
	 * -----------------------
	 */
	
	public void pieceCaptured(ChessPiece capturedPiece) {
		capturedPieces.add(capturedPiece);
		chessPieces.remove(capturedPiece);
	}
	
	public void pieceUncaptured(ChessPiece uncapturedPiece) {
		chessPieces.add(uncapturedPiece);
		capturedPieces.remove(uncapturedPiece);
	}
	
	public void addWin() {
		winCount ++;
	}
	
	public int getWins() {
		return winCount;
	}
	
	public ArrayList<ChessPiece> getPieces() {
		return chessPieces;
	}

	public King getKing() {
		return king;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public float getCameraDirection() {
		return cameraDirection;
	}
	
	public void setOtherPlayer(Player _otherPlayer) {
		otherPlayer = _otherPlayer;
	}
	
	public Player getOtherPlayer() {
		return otherPlayer;
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public String getColorTexture() {
		return (direction == 1) ? "White" : "Black";
	}
	
}
