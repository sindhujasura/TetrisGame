package edu.umkc.tetrisplugin1.handlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.MessageDialog;

//Copy and paste the following import statements
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


import edu.umkc.tetrisplugin1.ITetris;//Change to fit your own.


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	
	
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"My Plug-ins",
				evaluateGreeterExtension());//Copy and replace.
		return null;
	}

	//Copy and paste the following method
  private String evaluateGreeterExtension() {
		String greetings = "";
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor("edu.umkc.tetrisplugin1.startgame");//Change to fit your own
		try {
			int numOfPlayers = 2;
			Map<String, Object> tetrisMap = new HashMap<>();
			
			for (IConfigurationElement e : config) {

				final Object o = e.createExecutableExtension("class");			
				
				if (o instanceof ITetris) {					
					((ITetris) o).buildTetrisApp(numOfPlayers, tetrisMap);;
				}
			}
			return greetings;
		} catch (CoreException ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
//  
//  private void executeExtension(final Object o, int numOfPlayers, Map<String, Object> tetrisMap) {
//	    ISafeRunnable runnable = new ISafeRunnable() {
//	      @Override
//	      public void handleException(Throwable e) {
//	        System.out.println("Exception in client");
//	      }
//
//	      @Override
//	      public void run() throws Exception {
//
//	    	  ((ITetris1) o).buildTetrisApp(numOfPlayers, tetrisMap);
//	    	 
//	      }
//	    };
//	    SafeRunner.run(runnable);
//	  }
 




}