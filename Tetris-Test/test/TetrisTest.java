import static org.junit.Assert.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.psnbtech.BoardPanel;
import org.psnbtech.Clock;
import org.psnbtech.Tetris;
import org.psnbtech.TileType;



public class TetrisTest {

	private Tetris tetris;
	
	private BoardPanel boardPanel;
	
	private TileType currentType ;
	
	private	int currentCol = 0;
	
	private	int currentRow = 0;
	
	private	int currentRotation = 0;
	
	private	int score = 0;
	 
	public float gamespeed = 0f;
	
	public int level = 0;
	
	@Before
	public void setUp() throws Exception {
		tetris = new Tetris();		
		boardPanel = tetris.board;
		tetris.logicTimer = new Clock(this.gamespeed);
		tetris.random = new Random();

		//Sets the game variables to their default values at the start of game
		tetris.resetGame();
	}

	@After
	public void tearDown() throws Exception {
	}

    /*
     * This method test whether the given tile piece position can move to next row
     */
	@Test
	public void testUpdateGame_tile_moving_from_top_to_bottom() {
		
		//Initialize the variables
		
		this.currentType = TileType.TypeL;		
		this.currentCol = 5;		
		this.currentRow = 7;		
		this.currentRotation = 0;		
		this.gamespeed = 2.0f;
		this.score = 100;
		this.level = 2;
		
		//Now set these values to tetris instance
		
		tetris.currentType = this.currentType;
		tetris.currentCol = this.currentCol;
		tetris.currentRow = this.currentRow;
		tetris.currentRotation = this.currentRotation;
		tetris.gameSpeed = this.gamespeed;
		tetris.score = this.score;
		tetris.logicTimer = new Clock(tetris.gameSpeed);
		tetris.level = this.level;
	
		//Call Tetris Update Game	
		
		tetris.updateGame();
		
		assertEquals(this.currentRow + 1, tetris.currentRow);
		assertEquals(this.currentCol, tetris.currentCol);
		assertEquals(this.gamespeed, tetris.gameSpeed, 0.0f);
		assertEquals(this.score, tetris.score);
		assertEquals(this.level, tetris.level);
		assertFalse(tetris.isGameOver);
	}
	
	/**
	 * This method test whether the tile piece is added to the board panel when it reaches bottom of the board
	 */
	@Test
	public void testUpdateGame_add_tile_to_board() {
		
		//Initialize the variables.
		
		this.currentType = TileType.TypeL;		
		this.currentCol = 6;		
		this.currentRow = 20;		
		this.currentRotation = 0;		
		this.gamespeed = 2.0f;
		this.score = 100;
		this.level = 3;
		
		//Now set these values to tetris instance
		
		tetris.currentType = this.currentType;
		tetris.currentCol = this.currentCol;
		tetris.currentRow = this.currentRow;
		tetris.currentRotation = this.currentRotation;
		tetris.gameSpeed = this.gamespeed;
		tetris.score = this.score;
		tetris.logicTimer = new Clock(tetris.gameSpeed);
		tetris.level = this.level;

		//Call Tetris Update Game
		
		tetris.updateGame();
		TileType nextTile = tetris.currentType;
		
		assertEquals(nextTile.getSpawnRow(), tetris.currentRow);
		assertEquals(nextTile.getSpawnColumn(), tetris.currentCol);		
		assertEquals(this.gamespeed + 0.035f, tetris.gameSpeed, 0.0f);
		assertEquals((int) (tetris.gameSpeed*1.70f), tetris.level, 0.0f);
		assertFalse(tetris.isGameOver);
		
		//Current Code is calculating the score incorrectly (Adding 50 to current score irrespective of lines cleared)
		assertEquals("Game Score should not be updated if lines are not cleared", this.score, tetris.score);

	}
	
	/**
	 * This method test whether any completed lines are cleared when a tile piece is added to board and updates the game
	 */
	@Test
	public void testUpdateGame_clears_line_at_the_end() {
		
		this.currentType = TileType.TypeO;		
		this.currentCol = 8;		
		this.currentRow = 20;		
		this.currentRotation = 0;		
		this.gamespeed = 2.0f;
		this.score = 100;
		this.level = 3;
		
	    /**
		 * Add Bricks to the board such that the next brick would clear the row at the bottom
		 */
		
		//Add a complete row to BoardPanel 
		
		boardPanel.addPiece(TileType.TypeI, 0, 19, 0);
		boardPanel.addPiece(TileType.TypeI, 4, 19, 0);
		
		//Now set this values to tetris instance

		tetris.currentType = this.currentType;
		tetris.currentCol = this.currentCol;
		tetris.currentRow = this.currentRow;
		tetris.currentRotation = this.currentRotation;
		tetris.gameSpeed = this.gamespeed;
		tetris.score = this.score;
		tetris.logicTimer = new Clock(tetris.gameSpeed);
		tetris.level = this.level;

		boolean isRowEmpty = boardPanel.checkLine(19);
		
		//Line should not be full
		
		assertFalse(isRowEmpty);

		//Call Tetris Update Game
		
		tetris.updateGame();
		
		TileType nextTile = tetris.currentType;
		
		assertEquals(nextTile.getSpawnRow(), tetris.currentRow);
		assertEquals(nextTile.getSpawnColumn(), tetris.currentCol);		
		assertEquals(this.gamespeed + 0.035f, tetris.gameSpeed, 0.0f);
		assertEquals((int) (tetris.gameSpeed*1.70f), tetris.level, 0.0f);
		assertFalse(tetris.isGameOver);
		assertEquals(this.score + (50 << 1), tetris.score);
		
		//Tetris should clear the filled row and shift its above rows to the next row.	
		
		isRowEmpty = boardPanel.checkLine(21);
		assertFalse("Lines are not shifted correctly after a row is cleared", isRowEmpty);
	}
	
	/**
	 * This method test the game over scenario in which trying to add a tile piece to the board which is full
	 */
	@Test
	public void testUpdateGame_game_over_scenario() {
		
		//Initialize the variables
		
		this.currentType = TileType.TypeI;		
		this.currentCol = 3;		
		this.currentRow = 3;		
		this.currentRotation = 3;		
		this.gamespeed = 2.0f;
		this.score = 100;
		this.level = 3;
		
		//Now set these values to tetris instance			
		/**
		 * Add Bricks to the board such that the next brick would finish the game
		 */
		boardPanel.addPiece(TileType.TypeI, 3, 18, 3);
		boardPanel.addPiece(TileType.TypeI, 3, 14, 3);
		boardPanel.addPiece(TileType.TypeI, 3, 10, 3);
		boardPanel.addPiece(TileType.TypeI, 3, 6, 0);
		boardPanel.addPiece(TileType.TypeI, 3, 5, 0);
		boardPanel.addPiece(TileType.TypeI, 3, 4, 0);
		boardPanel.addPiece(TileType.TypeI, 3, 3, 0);
		boardPanel.addPiece(TileType.TypeI, 3, 2, 0);
		boardPanel.addPiece(TileType.TypeI, 3, 1, 0);
		boardPanel.addPiece(TileType.TypeI, 3, 0, 0);

		tetris.currentType = this.currentType;
		tetris.currentCol = this.currentCol;
		tetris.currentRow = this.currentRow;
		tetris.currentRotation = this.currentRotation;
		tetris.gameSpeed = this.gamespeed;
		tetris.score = this.score;
		tetris.logicTimer = new Clock(tetris.gameSpeed);
		tetris.level = this.level;

		//Call Tetris Update Game	
		tetris.updateGame();
		
		//Since Board is full, Update game should set the Game over flag to true
		assertTrue(tetris.isGameOver);
		
		TileType nextTile = tetris.currentType;
		
		assertEquals(nextTile.getSpawnRow(), tetris.currentRow);
		assertEquals(nextTile.getSpawnColumn(), tetris.currentCol);		
		assertEquals(this.gamespeed + 0.035f, tetris.gameSpeed, 0.0f);
		assertEquals((int) (tetris.gameSpeed*1.70f), tetris.level, 0.0f);
	}
	
}