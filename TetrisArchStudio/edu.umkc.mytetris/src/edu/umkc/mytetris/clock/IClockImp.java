package edu.umkc.mytetris.clock;


import edu.umkc.mytetris.clock.ClockArch;
import edu.umkc.mytetris.logic.TetrisImp.TileType;

public interface IClockImp 
{

	/*
	  Getter and Setter of architecture reference
	*/
    public void setArch (ClockArch arch);
	public ClockArch getArch();
	
	/*
  	  Myx Lifecycle Methods: these methods are called automatically by the framework
  	  as the bricks are created, attached, detached, and destroyed respectively.
	*/	
	public void init();	
	public void begin();
	public void end();
	public void destroy();

	/*
  	  Implementation primitives required by the architecture
	*/
  
    
    public boolean isValidAndEmpty (TileType type,int x,int y,int rotation)  ;        
    public void addPiece (TileType type,int x,int y,int rotation)  ;        
    public void reset ()  ;        
    public void update ()  ;        
    public boolean hasElapsedCycle ()  ;        
    public void setPaused (boolean paused)  ;        
}