package edu.umkc.mytetris.clock;

import edu.umkc.mytetris.logic.TetrisImp.TileType;



public class ClockImp implements IClockImp
{
	private ClockArch _arch;
	
	private float gameSpeed; 

    public ClockImp (){
    }

	public ClockImp(float gameSpeed) {
		this.gameSpeed = gameSpeed;
	}

	public void setArch(ClockArch arch){
		_arch = arch;
	}
	public ClockArch getArch(){
		return _arch;
	}

	/*
  	  Myx Lifecycle Methods: these methods are called automatically by the framework
  	  as the bricks are created, attached, detached, and destroyed respectively.
	*/	
	public void init(){
	    //TODO Auto-generated method stub
	}
	public void begin(){
		//TODO Auto-generated method stub
	}
	public void end(){
		//TODO Auto-generated method stub
	}
	public void destroy(){
		//TODO Auto-generated method stub
	}

	/*
  	  Implementation primitives required by the architecture
	*/
private float millisPerCycle;
	
	
	private long lastUpdate;
	
	
	private int elapsedCycles;
	
	
	private float excessCycles;
	
	
	private boolean isPaused;
	
	
	public void Clock(float cyclesPerSecond) {
		setCyclesPerSecond(cyclesPerSecond);
		reset();
	}
	
	public void setCyclesPerSecond(float cyclesPerSecond) {
		this.millisPerCycle = (1.0f / cyclesPerSecond) * 1000;
	}
	
	
	public boolean isPaused() {
		return isPaused;
	}
	
	
	public boolean peekElapsedCycle() {
		return (elapsedCycles > 0);
	}
	private static final long getCurrentTime() {
		return (System.nanoTime() / 1000000L);
	}


	
    public void addPiece (TileType type,int x,int y,int rotation)   {
		//TODO Auto-generated method stub
		
    }
    public void reset ()   {
		//TODO Auto-generated method stub
    	this.elapsedCycles = 0;
		this.excessCycles = 0.0f;
		this.lastUpdate = getCurrentTime();
		this.isPaused = false;

		
    }
    public void update ()   {
		//TODO Auto-generated method stub
    	//Get the current time and calculate the delta time.
    			long currUpdate = getCurrentTime();
    			float delta = (float)(currUpdate - lastUpdate) + excessCycles;
    			
    			//Update the number of elapsed and excess ticks if we're not paused.
    			if(!isPaused) {
    				this.elapsedCycles += (int)Math.floor(delta / millisPerCycle);
    				this.excessCycles = delta % millisPerCycle;
    			}
    			
    			//Set the last update time for the next update cycle.
    			this.lastUpdate = currUpdate;
    		}

		
    
    public boolean hasElapsedCycle ()   {
		//TODO Auto-generated method stub
    	if(elapsedCycles > 0) {
			this.elapsedCycles--;
			return true;
		}
		return false;

		
    }
    public void setPaused (boolean paused)   {
		//TODO Auto-generated method stub
    	this.isPaused = paused;

		
    }

	@Override
	public boolean isValidAndEmpty(TileType type, int x, int y, int rotation) {
		// TODO Auto-generated method stub
		return false;
	}

}