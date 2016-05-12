package edu.umkc.mytetris.logic;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;
import edu.umkc.mytetris.TProvided;
import edu.umkc.mytetris.TRequired;
import edu.umkc.mytetris.logic.TetrisImp.TileType;

import java.awt.Color;

public class TetrisArch extends AbstractMyxSimpleBrick implements TProvided
{
    public static final IMyxName msg_TProvided = MyxUtils.createName("edu.umkc.tetris1.TProvided");
    public static final IMyxName msg_TRequired = MyxUtils.createName("edu.umkc.tetris1.TRequired");

    public TRequired OUT_TRequired;

	private ITetrisImp _imp;

    public TetrisArch (){
		_imp = getImplementation();
		
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected ITetrisImp getImplementation(){
        try{
        	TetrisImp tetrisImp = new TetrisImp();
        	tetrisImp.startGame();
			return tetrisImp;    
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void init(){
        _imp.init();
    }
    
    public void begin(){
        OUT_TRequired = (TRequired) MyxUtils.getFirstRequiredServiceObject(this,msg_TRequired);
        if (OUT_TRequired == null){
 			System.err.println("Error: Interface edu.umkc.tetris1.TRequired returned null");
			return;       
        }
        _imp.begin();
    }
    
    public void end(){
        _imp.end();
    }
    
    public void destroy(){
        _imp.destroy();
    }
    
	public Object getServiceObject(IMyxName arg0) {
		if (arg0.equals(msg_TProvided)){
			return this;
		}        
		return null;
	}
  
    //To be imported: Color
    public boolean isPaused ()   {
		return _imp.isPaused();
    }    
    public boolean isGameOver ()   {
		return _imp.isGameOver();
    }    
    public boolean isNewGame ()   {
		return _imp.isNewGame();
    }    
    public TileType getPieceType ()   {
		return _imp.getPieceType();
    }    
    public TileType getNextPieceType ()   {
		return _imp.getNextPieceType();
    }    
    public int getPieceCol ()   {
		return _imp.getPieceCol();
    }    
    public int getPieceRow ()   {
		return _imp.getPieceRow();
    }    
    public int getPieceRotation ()   {
		return _imp.getPieceRotation();
    }    
    public Color getBaseColor ()   {
		return _imp.getBaseColor();
    }    
    public Color getLightColor ()   {
		return _imp.getLightColor();
    }    
    public Color getDarkColor ()   {
		return _imp.getDarkColor();
    }    
    public int getDimension ()   {
		return _imp.getDimension();
    }    
    public int getSpawnColumn ()   {
		return _imp.getSpawnColumn();
    }    
    public int getSpawnRow ()   {
		return _imp.getSpawnRow();
    }    
    public int getRows ()   {
		return _imp.getRows();
    }    
    public int getCols ()   {
		return _imp.getCols();
    }    
    public boolean isTile (int x,int y,int rotation)   {
		return _imp.isTile(x,y,rotation);
    }    
    public int getLeftInset (int rotation)   {
		return _imp.getLeftInset(rotation);
    }    
    public int getRightInset (int rotation)   {
		return _imp.getRightInset(rotation);
    }    
    public int getTopInset (int rotation)   {
		return _imp.getTopInset(rotation);
    }    
    public int getBottomInset (int rotation)   {
		return _imp.getBottomInset(rotation);
    }    
}