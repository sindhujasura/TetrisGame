package edu.umkc.mytetris;

import edu.umkc.mytetris.logic.TetrisImp.TileType;

public interface TRequired {
	
	public boolean isValidAndEmpty(TileType type, int x, int y, int rotation);
	
	public void addPiece(TileType type, int x, int y, int rotation);
	
	public void reset();
	
	public void update();
	
	public boolean hasElapsedCycle();
	
	public void setPaused(boolean paused);
	
	
	

}
