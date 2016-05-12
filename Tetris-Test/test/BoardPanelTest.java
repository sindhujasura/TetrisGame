import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.psnbtech.BoardPanel;
import org.psnbtech.Clock;
import org.psnbtech.Tetris;
import org.psnbtech.TileType;

public class BoardPanelTest {

	private BoardPanel boardPanel;
	
	private Tetris tetris;

	private TileType type;

	private int x;

	private int y;

	private int rotation;

	@Before
	public void setUp() throws Exception {
		tetris = new Tetris();
		tetris.logicTimer = new Clock(0.0f);
		tetris.random = new Random();

		//Sets the game variables to their default values at the start
		tetris.resetGame();
		boardPanel = new BoardPanel(tetris);
	}
/*
 * In this method we test the method by giving the column value greater than the limit value 
 *   
 */

    @Test
	public void testIsValidAndEmpty_with_invalid_columns() {

        type = TileType.TypeO;
        //Column Values Greater then given limit
        x = 11;
        y = 0;
        rotation = 0;

        boolean validPiece = boardPanel.isValidAndEmpty(type, x, y, rotation);

        assertFalse(validPiece);
	}
    /*
     * In this method we test the method by giving the row value greater than limit value
     */

    @Test
	public void testIsValidAndEmpty_with_invalid_rows() {

        type = TileType.TypeO;
        x = 10;
        //Rows Values Greater then given limit
        y = 23;
        rotation = 0;

        boolean validPiece = boardPanel.isValidAndEmpty(type, x, y, rotation);

        assertFalse(validPiece);
	}
/*
 * In this method we test the method by giving row value and column value greater than the limit
 */
    @Test
	public void testIsValidAndEmpty_with_valid_rows_and_columns() {

        type = TileType.TypeO;
        //Valid Columns
        x = 8;

        //Valid ROWS
        y = 18;
        rotation = 0;

        boolean validPiece = boardPanel.isValidAndEmpty(type, x, y, rotation);

        assertTrue(validPiece);
	}
    
/*
 * This test should return false, as brick are occupied at the given co-ordinates
 */
    
    @Test
    public void testIsValidAndEmpty_with_occupied_tiles() {
        
        type = TileType.TypeI;
        //Valid Columns
        x = 6;

        //Valid Row
        y = 18;
        rotation = 1;
        
        //Add row to the board panel
        boardPanel.addPiece(type, x, y, rotation);

        //Add Tile To boardPanel
        boolean validPiece = boardPanel.isValidAndEmpty(type, x, y, rotation);

        //Since we have already added the brick at given x,y. ValidPiece should return False.
        assertFalse("'isValidAndEmpty' should retrun false if bricks exists at the given co-ordinates.",validPiece);
    }
    
    /*
     * In this method we add the tile type in the given co-ordinates and check whether the  tile 
     * is added to  the board panel....
     */

   
	@Test
	public void testAddPiece_with_all_TileTypes() {
        x = 4;
        y = 18;
        rotation = 0;

        //Loop through each tile in TileType
        for (TileType tileType : TileType.values()) {

            type = tileType;
            
            boardPanel.addPiece(type, x, y, rotation);

            for(int col = 0; col < type.getDimension(); col++) {
                for(int row = 0; row < type.getDimension(); row++) {
                    if(type.isTile(col, row, rotation)) {
                        TileType tile = boardPanel.getTile(col + x,row + y);
                        Assert.assertNotNull(tile);
                        Assert.assertEquals(type, tile);
                        Assert.assertEquals(type.getBaseColor(), tile.getBaseColor());
                        Assert.assertEquals(type.getDimension(), tile.getDimension());
                    }
                }
            }
        }
	}
	/*
	 * In this method we add the tile types and test whether the line is cleared at the given co-ordinates
	 */
	@Test
	public void testCheckLines() {
		
		//Add a complete row to BoardPanel 
		boardPanel.addPiece(TileType.TypeI, 0, 19, 0);
		boardPanel.addPiece(TileType.TypeI, 4, 19, 0);
		boardPanel.addPiece(TileType.TypeO, 8, 19, 0);		
		
		
		int completedLines = boardPanel.checkLines();
		
		//Check if the line is cleared by BoardPanel
		boolean isEmptyLine = boardPanel.isValidAndEmpty(TileType.TypeI, 0, 19, 0);
		
		Assert.assertTrue(isEmptyLine);
		assertEquals(1,completedLines);     
    
	}

}
