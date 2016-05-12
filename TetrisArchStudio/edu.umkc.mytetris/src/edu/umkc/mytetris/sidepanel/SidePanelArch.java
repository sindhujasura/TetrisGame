package edu.umkc.mytetris.sidepanel;


import edu.uci.isr.myx.fw.AbstractMyxSimpleBrick;
import edu.uci.isr.myx.fw.IMyxName;
import edu.uci.isr.myx.fw.MyxUtils;
import edu.umkc.mytetris.TProvided;

public class SidePanelArch extends AbstractMyxSimpleBrick
{
    public static final IMyxName msg_TProvided = MyxUtils.createName("edu.umkc.tetris1.TProvided");

    public TProvided OUT_TProvided;

	private ISidePanelImp _imp;

    public SidePanelArch (){
		_imp = getImplementation();
		if (_imp != null){
			_imp.setArch(this);
		} else {
			System.exit(1);
		}
	}
    
    protected ISidePanelImp getImplementation(){
        try{
			return new SidePanelImp();    
        } catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void init(){
        _imp.init();
    }
    
    public void begin(){
        OUT_TProvided = (TProvided) MyxUtils.getFirstRequiredServiceObject(this,msg_TProvided);
        if (OUT_TProvided == null){
 			System.err.println("Error: Interface edu.umkc.tetris1.TProvided returned null");
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
		return null;
	}
}