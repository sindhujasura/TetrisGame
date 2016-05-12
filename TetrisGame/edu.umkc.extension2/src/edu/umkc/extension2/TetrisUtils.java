package edu.umkc.extension2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import tetris.Clock;
import tetris.Tetris;

public class TetrisUtils {
	
	/**
	 * 
	 * @param player1
	 * @param player2
	 */
	public void startGame(Tetris player1, Tetris player2) {
		/*
		 * Initialize our random number generator, logic timer, and new game variables.
		 */
		//player1.random = new Random();
		player1.setRandom(new Random());
		player2.setRandom(new Random());
		
		player1.setNewGame(true);
		player2.setNewGame(true);
		
		player1.setGameSpeed(1.0f);
		
		player2.setGameSpeed(1.0f);
		
		/*
		 * Setup the timer to keep the game from running before the user presses enter
		 * to start it.
		 */
		//this.logicTimer = new Clock(gameSpeed);
		player1.setLogicTimer(new Clock(player1.getGameSpeed()));
		player2.setLogicTimer(new Clock(player2.getGameSpeed()));
		
		player1.getLogicTimer().setPaused(true);
		player2.getLogicTimer().setPaused(true);
	//	int i =0;
		while(true) {
			
			//Get the time that the frame started.
			long start = System.nanoTime();
			
			//Update the logic timer.
			player1.getLogicTimer().update();
			player2.getLogicTimer().update();
			
			/*
			 * If a cycle has elapsed on the timer, we can update the game and
			 * move our current piece down.
			 */
			if(player1.getLogicTimer().hasElapsedCycle()) {
				player1.updateGame();
			}
			
			if(player2.getLogicTimer().hasElapsedCycle()) {
				player2.updateGame();
			}
		
			//Decrement the drop cool down if necessary.
			if(player2.getDropCooldown() > 0) {
				player2.setDropCooldown(player2.getDropCooldown() -1);
			}
			
			//Decrement the drop cool down if necessary.
			if(player1.getDropCooldown() > 0) {
				player1.setDropCooldown(player1.getDropCooldown() -1);
			}
			
			//Display the window to the user.
			player1.renderGame();
			
			player2.renderGame();
			
			/*
			 * Sleep to cap the framerate.
			 */
			long delta = (System.nanoTime() - start) / 1000000L;
			if(delta < Tetris.FRAME_TIME) {
				try {
					Thread.sleep(Tetris.FRAME_TIME - delta);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		
		}
	}
	
	
	/**
	 * 
	 * @param tetris
	 */
	public void setPlayer1Keys(Tetris tetris) {
		/*
		 * Adds a custom anonymous KeyListener to the frame.
		 */
		tetris.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
								
				switch(e.getKeyCode()) {
				
				/*
				 * Drop - When pressed, we check to see that the game is not
				 * paused and that there is no drop cooldown, then set the
				 * logic timer to run at a speed of 25 cycles per second.
				 */
				case KeyEvent.VK_S:
					if(!tetris.isPaused() && tetris.getDropCooldown() == 0) {
						tetris.getLogicTimer().setCyclesPerSecond(25.0f);
					}
					break;
					
				/*
				 * Move Left - When pressed, we check to see that the game is
				 * not paused and that the position to the left of the current
				 * position is valid. If so, we decrement the current column by 1.
				 */
				case KeyEvent.VK_A:
					if(!tetris.isPaused() && tetris.getBoard().isValidAndEmpty(
							tetris.getCurrentType(), tetris.currentCol - 1, tetris.getCurrentRow(), tetris.getCurrentRotation())) {
						//tetris.setCurrentCol(tetris.currentCol-1);
						tetris.currentCol--;
						//currentCol--;
					}
					break;
					
				/*
				 * Move Right - When pressed, we check to see that the game is
				 * not paused and that the position to the right of the current
				 * position is valid. If so, we increment the current column by 1.
				 */
				case KeyEvent.VK_D:
					if(!tetris.isPaused() 
							&& tetris.getBoard().isValidAndEmpty(tetris.getCurrentType(),
																 tetris.currentCol + 1, 
																 tetris.getCurrentRow(), 
																 tetris.getCurrentRotation())) {
						//int col = tetris.currentCol; 
//						tetris.setCurrentCol(tetris.currentCol+1);		
						tetris.currentCol++;
						//tetris.currentCol++;
					}
					break;
					
				/*
				 * Rotate Anticlockwise - When pressed, check to see that the game is not paused
				 * and then attempt to rotate the piece anticlockwise. Because of the size and
				 * complexity of the rotation code, as well as it's similarity to clockwise
				 * rotation, the code for rotating the piece is handled in another method.
				 */
				case KeyEvent.VK_Q:
					if(!tetris.isPaused()) {
						tetris.rotatePiece((tetris.getCurrentRotation() == 0) ? 3 : tetris.getCurrentRotation() - 1);
					}
					break;
				
				/*
			     * Rotate Clockwise - When pressed, check to see that the game is not paused
				 * and then attempt to rotate the piece clockwise. Because of the size and
				 * complexity of the rotation code, as well as it's similarity to anticlockwise
				 * rotation, the code for rotating the piece is handled in another method.
				 */
				case KeyEvent.VK_E:
					if(!tetris.isPaused()) {
						tetris.rotatePiece((tetris.getCurrentRotation() == 3) ? 0 : tetris.getCurrentRotation() + 1);
					}
					break;
					
				/*
				 * Pause Game - When pressed, check to see that we're currently playing a game.
				 * If so, toggle the pause variable and update the logic timer to reflect this
				 * change, otherwise the game will execute a huge number of updates and essentially
				 * cause an instant game over when we unpause if we stay paused for more than a
				 * minute or so.
				 */
				case KeyEvent.VK_P:
					if(!tetris.isGameOver() && !tetris.isNewGame()) {
						tetris.setPaused(!tetris.isPaused()); 
						tetris.getLogicTimer().setPaused(tetris.isPaused());
					}
					break;
				
				/*
				 * Start Game - When pressed, check to see that we're in either a game over or new
				 * game state. If so, reset the game.
				 */
				case KeyEvent.VK_ENTER:
					if(tetris.isGameOver() || tetris.isNewGame()) {
						tetris.resetGame();
					}
					break;
				
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				switch(e.getKeyCode()) {
				
				/*
				 * Drop - When released, we set the speed of the logic timer
				 * back to whatever the current game speed is and clear out
				 * any cycles that might still be elapsed.
				 */
				case KeyEvent.VK_S:
					tetris.getLogicTimer().setCyclesPerSecond(tetris.getGameSpeed());
					tetris.getLogicTimer().reset();
					break;
				}
				
			}
			
		});
	}
		
		public void setPlayer2Keys(Tetris tetris) {
			/*
			 * Adds a custom anonymous KeyListener to the frame.
			 */
			tetris.addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
									
					switch(e.getKeyCode()) {
					
					/*
					 * Drop - When pressed, we check to see that the game is not
					 * paused and that there is no drop cooldown, then set the
					 * logic timer to run at a speed of 25 cycles per second.
					 */
					case KeyEvent.VK_K:
						if(!tetris.isPaused() && tetris.getDropCooldown() == 0) {
							tetris.getLogicTimer().setCyclesPerSecond(25.0f);
						}
						break;
						
					/*
					 * Move Left - When pressed, we check to see that the game is
					 * not paused and that the position to the left of the current
					 * position is valid. If so, we decrement the current column by 1.
					 */
					case KeyEvent.VK_L:
						if(!tetris.isPaused() && tetris.getBoard().isValidAndEmpty(
								tetris.getCurrentType(), tetris.currentCol - 1, tetris.getCurrentRow(), tetris.getCurrentRotation())) {
							tetris.setCurrentCol(tetris.currentCol-1);
							//currentCol--;
						}
						break;
						
					/*
					 * Move Right - When pressed, we check to see that the game is
					 * not paused and that the position to the right of the current
					 * position is valid. If so, we increment the current column by 1.
					 */
					case KeyEvent.VK_J:
						if(!tetris.isPaused() && tetris.getBoard().isValidAndEmpty(tetris.getCurrentType(),tetris.currentCol + 1, tetris.getCurrentRow(), tetris.getCurrentRotation())) {
							//tetris.setCurrentCol(tetris.currentCol+1);
							tetris.currentCol++;
						}
						break;
						
					/*
					 * Rotate Anticlockwise - When pressed, check to see that the game is not paused
					 * and then attempt to rotate the piece anticlockwise. Because of the size and
					 * complexity of the rotation code, as well as it's similarity to clockwise
					 * rotation, the code for rotating the piece is handled in another method.
					 */
					case KeyEvent.VK_O:
						if(!tetris.isPaused()) {
							tetris.rotatePiece((tetris.getCurrentRotation() == 0) ? 3 : tetris.getCurrentRotation() - 1);
						}
						break;
					
					/*
				     * Rotate Clockwise - When pressed, check to see that the game is not paused
					 * and then attempt to rotate the piece clockwise. Because of the size and
					 * complexity of the rotation code, as well as it's similarity to anticlockwise
					 * rotation, the code for rotating the piece is handled in another method.
					 */
					case KeyEvent.VK_I:
						if(!tetris.isPaused()) {
							tetris.rotatePiece((tetris.getCurrentRotation() == 3) ? 0 : tetris.getCurrentRotation() + 1);
						}
						break;
						
					/*
					 * Pause Game - When pressed, check to see that we're currently playing a game.
					 * If so, toggle the pause variable and update the logic timer to reflect this
					 * change, otherwise the game will execute a huge number of updates and essentially
					 * cause an instant game over when we unpause if we stay paused for more than a
					 * minute or so.
					 */
					case KeyEvent.VK_U:
						if(!tetris.isGameOver() && !tetris.isNewGame()) {
							tetris.setPaused(!tetris.isPaused()); 
							tetris.getLogicTimer().setPaused(tetris.isPaused());
						}
						break;
					
					/*
					 * Start Game - When pressed, check to see that we're in either a game over or new
					 * game state. If so, reset the game.
					 */
					case KeyEvent.VK_ENTER:
						if(tetris.isGameOver() || tetris.isNewGame()) {
							tetris.resetGame();
						}
						break;
					
					}
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					
					switch(e.getKeyCode()) {
					
					/*
					 * Drop - When released, we set the speed of the logic timer
					 * back to whatever the current game speed is and clear out
					 * any cycles that might still be elapsed.
					 */
					case KeyEvent.VK_S:
						tetris.getLogicTimer().setCyclesPerSecond(tetris.getGameSpeed());
						tetris.getLogicTimer().reset();
						break;
					}
					
				}
				
			});
	}

}
