package tests.core_tests;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import model.ChessMove;
import model.board.Board;
import model.game_modes.StandardGame;

import model.pieces.Pawn;

import org.junit.Before;
import org.junit.Test;

import controller.Player;

public class TestUndo {

	private Board chessBoard;
	private Player player1;
	private Player player2;
	
	@Before
	public void setup() {
		player1 = new Player(1, false, null);
		player2 = new Player(-1, false, null);
		player1.setOtherPlayer(player2);
		player2.setOtherPlayer(player1);;
	}
	
	@Test
	public void testBasicUndoMethods() {
		setup();
		chessBoard = new StandardGame().initPieces(player1, player2);
		Pawn p = new Pawn(4, 1, chessBoard, player1);
		ChessMove move = new ChessMove(new Point(4, 3), p);
		move.executeMove(true);
		assertTrue(p.getLocation().equals(new Point(4, 3)));
		move.undoMove();
		assertTrue(p.getLocation().equals(new Point(4, 1)));
	}
	
	@Test
	public void testCaptureUndoMethods() {
		setup();
		chessBoard = new StandardGame().initPieces(player1, player2);
		Pawn p = new Pawn(4, 1, chessBoard, player1);
		
		ChessMove move = new ChessMove(new Point(5, 2), p);
		move.executeMove(true);
		assertTrue(p.getLocation().equals(new Point(5, 2)));
	
		move.undoMove();
		assertTrue(p.getLocation().equals(new Point(4, 1)));
		
	}
	
}
