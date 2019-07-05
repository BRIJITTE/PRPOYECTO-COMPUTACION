package tests.core_tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;

import model.ChessMove;
import model.board.Board;
import model.board.RectangularBoard;
import model.game_modes.StandardGame;

import model.pieces.King;

import model.pieces.Pawn;

import org.junit.Before;
import org.junit.Test;

import controller.Player;

/**
 * These tests test the functionality of the Board, and the various 
 * chess pieces.
 * 
 * @author Nicholas
 *
 */
public class TestMoves {

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
	public void testRectangularBoardMethods() {
		setup();
		chessBoard = new StandardGame().initPieces(player1, player2);
		//check adjacent 
		ArrayList<Point> adj = chessBoard.getAdjacentRankFileTiles(4, 4);
		assertTrue(adj.contains(new Point(3, 4)));
		assertTrue(adj.contains(new Point(4, 5)));
		assertTrue(adj.contains(new Point(5, 4)));
		assertTrue(adj.contains(new Point(4, 3)));
		ArrayList<Point> dia = chessBoard.getAdjacentDiagonalTiles(4, 4);
		assertTrue(dia.contains(new Point(3, 3)));
		assertTrue(dia.contains(new Point(3, 5)));
		assertTrue(dia.contains(new Point(5, 5)));
		assertTrue(dia.contains(new Point(5, 3)));
		
		//check bounds
		assertTrue(chessBoard.isInBounds(2, 2));
		assertTrue(!chessBoard.isInBounds(8, 7));
		assertTrue(!chessBoard.isInBounds(-1, 7));
		
		
		//check hasEnemyPiece() method
		assertTrue(chessBoard.hasEnemyPiece(1, 1, player2)); //Player1
		assertTrue(chessBoard.hasEnemyPiece(1, 1, player2)); //Player1 
		assertTrue(chessBoard.hasEnemyPiece(7, 7, player1)); //Player2 
		assertTrue(!chessBoard.hasEnemyPiece(4, 4, player2)); //Player2 
	}
	
	@Test
	public void testPawnMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		King K = new King(5, 6, chessBoard, player1);
		Pawn P1 = new Pawn(1, 1, chessBoard, player1);
		Pawn P2 = new Pawn(2, 1, chessBoard, player1);
		
		King k = new King(4, 3, chessBoard, player2);
		
		/* 5 . . . . .    player1 = Capital
		 * 4 . . . . .    player2 = Lower
		 * 3 . . . . . 
		 * 2 . . b . . 
		 * 1 .P1 P2 . .
		 * 0 . . . . .
		 */
		assertEquals(3, P1.getPossibleMoves().size());
		assertTrue(P1.getPossibleMoves().contains(new ChessMove(new Point(2, 2), P1))); //P1 attack on diagonal
		assertTrue(P1.getPossibleMoves().contains(new ChessMove(new Point(1, 2), P1))); //P1 move 1
		assertTrue(P1.getPossibleMoves().contains(new ChessMove(new Point(1, 3), P1))); //P1 jump 2
		assertEquals(0, P2.getPossibleMoves().size()); //P2 shouldn't be able to move
	}
	
	@Test
	public void testKnightMoves() {
		setup();
		chessBoard = new RectangularBoard(new StandardGame());
		King Ki = new King(5, 6, chessBoard, player1);
		
		
		/* 5 . . . . .    player1 = Capital
		 * 4 . . . . .    player2 = Lower
		 * 3 x . x . . 
		 * 2 . . . x . 
		 * 1 . K . . .
		 * 0 . . . x .
		 *   0 1 2 3 4
		 */
	
	
	
}
}
