package edu.umkc.mytetris;

import java.awt.Color;

import edu.umkc.mytetris.logic.TetrisImp.TileType;

public interface TProvided {
		
	public boolean isPaused();
	
	public boolean isGameOver();
	
	public boolean isNewGame();
	
	public TileType getPieceType();
	
	public TileType getNextPieceType();
	
	public int getPieceCol();
	
	public int getPieceRow();
	
	public int getPieceRotation(); 
	
	public Color getBaseColor();
	
	public Color getLightColor();
	
	public Color getDarkColor();
	
	public int getDimension();
	
	public int getSpawnColumn();
	
	public int getSpawnRow();
	
	public int getRows();
	
	public int getCols();
	
	public boolean isTile(int x, int y, int rotation);
	
	public int getLeftInset(int rotation);
	
	public int getRightInset(int rotation);
	
	public int getTopInset(int rotation);
	
	public int getBottomInset(int rotation);
	

}
