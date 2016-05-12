package edu.umkc.mytetris.clock;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;
import edu.umkc.mytetris.TRequired;
import edu.umkc.mytetris.logic.TetrisImp.TileType;

public class ClockArch extends AbstractMyxSimpleBrick implements TRequired
{
    public static final IMyxName msg_TRequired = MyxUtils.createName("edu.umkc.tetris1.TRequired");


	private IClockImp _imp;

    public ClockArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected IClockImp getImplementation(){
        try{
			return new ClockImp();    
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void init(){
        _imp.init();
    }
    
    public void begin(){
        _imp.begin();
    }
    
    public void end(){
        _imp.end();
    }
    
    public void destroy(){
        _imp.destroy();
    }
    
	public Object getServiceObject(IMyxName arg0) {
		if (arg0.equals(msg_TRequired)){
			return this;
		}        
		return null;
	}
  
    
    public boolean isValidAndEmpty (TileType type,int x,int y,int rotation)   {
		return _imp.isValidAndEmpty(type,x,y,rotation);
    }    
    public void addPiece (TileType type,int x,int y,int rotation)   {
		_imp.addPiece(type,x,y,rotation);
    }    
    public void reset ()   {
		_imp.reset();
    }    
    public void update ()   {
		_imp.update();
    }    
    public boolean hasElapsedCycle ()   {
		return _imp.hasElapsedCycle();
    }    
    public void setPaused (boolean paused)   {
		_imp.setPaused(paused);
    }    
}