package edu.umkc.mytetris.logic;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

import edu.umkc.mytetris.boardpanel.BoardPanelImp;
import edu.umkc.mytetris.clock.ClockImp;
import edu.umkc.mytetris.sidepanel.SidePanelImp;

import java.awt.Color;




public class TetrisImp extends JFrame implements ITetrisImp
{
	private TetrisArch _arch;

	public void setArch(TetrisArch arch){
		_arch = arch;
	}
	public TetrisArch getArch(){
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
  
    //To be imported: Color
	private static final long serialVersionUID = -4722429764792514382L;

	private static final long FRAME_TIME = 1000L / 50L;
	
	private static final int TYPE_COUNT = TileType.values().length;
		
	private BoardPanelImp board;
	
	private SidePanelImp side;
	
	private boolean isPaused;
	
	private boolean isNewGame;
	
	private boolean isGameOver;
	
	private int level;
	
	private int score;
	
	private Random random;
	
	private ClockImp logicTimer;
				
	private TileType currentType;
	
	private TileType nextType;
		
	private int currentCol;
	
	private int currentRow;
	
	private int currentRotation;
		
	private int dropCooldown;
	
	private float gameSpeed;
	
	
	

	public TetrisImp() {
		
		super("TetrisImp");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		
		this.board = new BoardPanelImp(this);
		this.side = new SidePanelImp(this);
		
		
		add(board, BorderLayout.CENTER);
		add(side, BorderLayout.EAST);

addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
								
				switch(e.getKeyCode()) {
				
				
				case KeyEvent.VK_S:
					if(!isPaused && dropCooldown == 0) {
						logicTimer.setCyclesPerSecond(25.0f);
					}
					break;
					
				
				case KeyEvent.VK_A:
					if(!isPaused && board.isValidAndEmpty(currentType, currentCol - 1, currentRow, currentRotation)) {
						currentCol--;
					}
					break;
					
				
				case KeyEvent.VK_D:
					if(!isPaused && board.isValidAndEmpty(currentType, currentCol + 1, currentRow, currentRotation)) {
						currentCol++;
					}
					break;
					
				
				case KeyEvent.VK_Q:
					if(!isPaused) {
						rotatePiece((currentRotation == 0) ? 3 : currentRotation - 1);
					}
					break;
				
				
				case KeyEvent.VK_E:
					if(!isPaused) {
						rotatePiece((currentRotation == 3) ? 0 : currentRotation + 1);
					}
					break;
					
				
				case KeyEvent.VK_P:
					if(!isGameOver && !isNewGame) {
						isPaused = !isPaused;
						logicTimer.setPaused(isPaused);
					}
					break;
				
				
				case KeyEvent.VK_ENTER:
					if(isGameOver || isNewGame) {
						resetGame();
					}
					break;
				
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				switch(e.getKeyCode()) {
				
				
				case KeyEvent.VK_S:
					logicTimer.setCyclesPerSecond(gameSpeed);
					logicTimer.reset();
					break;
				}
				
			}
			
		});
		
	
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void startGame() {
		
		this.random = new Random();
		this.isNewGame = true;
		this.gameSpeed = 1.0f;
		
		
		this.logicTimer = new ClockImp(gameSpeed);
		logicTimer.setPaused(true);
		
		while(true) {
			
			long start = System.nanoTime();
			
			
			logicTimer.update();
			
			
			if(logicTimer.hasElapsedCycle()) {
				updateGame();
			}
		
			
			if(dropCooldown > 0) {
				dropCooldown--;
			}
			
			
			renderGame();
			
			
			long delta = (System.nanoTime() - start) / 1000000L;
			if(delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void updateGame() {
	
		if(board.isValidAndEmpty(currentType, currentCol, currentRow + 1, currentRotation)) {
			
			currentRow++;
		} else {
			
			board.addPiece(currentType, currentCol, currentRow, currentRotation);
			
			
			int cleared = board.checkLines();
			if(cleared > 0) {
				score += 50 << cleared;
			}
			
			
			gameSpeed += 0.035f;
			logicTimer.setCyclesPerSecond(gameSpeed);
			logicTimer.reset();
			
			
			dropCooldown = 25;
			
			level = (int)(gameSpeed * 1.70f);
			
			
			spawnPiece();
		}		
	}
	private void renderGame() {
		board.repaint();
		side.repaint();
	}
	private void resetGame() {
		this.level = 1;
		this.score = 0;
		this.gameSpeed = 1.0f;
		this.nextType = TileType.values()[random.nextInt(TYPE_COUNT)];
		this.isNewGame = false;
		this.isGameOver = false;		
		board.clear();
		logicTimer.reset();
		logicTimer.setCyclesPerSecond(gameSpeed);
		spawnPiece();
	}
		
	
	private void spawnPiece() {
		
		this.currentType = nextType;
		this.currentCol = currentType.getSpawnColumn();
		this.currentRow = currentType.getSpawnRow();
		this.currentRotation = 0;
		this.nextType = TileType.values()[random.nextInt(TYPE_COUNT)];
		
		
		if(!board.isValidAndEmpty(currentType, currentCol, currentRow, currentRotation)) {
			this.isGameOver = true;
			logicTimer.setPaused(true);
		}		
	}
	private void rotatePiece(int newRotation) {
		
		int newColumn = currentCol;
		int newRow = currentRow;
		
		
		int left = currentType.getLeftInset(newRotation);
		int right = currentType.getRightInset(newRotation);
		int top = currentType.getTopInset(newRotation);
		int bottom = currentType.getBottomInset(newRotation);
		
		
		if(currentCol < -left) {
			newColumn -= currentCol - left;
		} else if(currentCol + currentType.getDimension() - right >= BoardPanelImp.COL_COUNT) {
			newColumn -= (currentCol + currentType.getDimension() - right) - BoardPanelImp.COL_COUNT + 1;
		}
		
		
		if(currentRow < -top) {
			newRow -= currentRow - top;
		} else if(currentRow + currentType.getDimension() - bottom >= BoardPanelImp.ROW_COUNT) {
			newRow -= (currentRow + currentType.getDimension() - bottom) - BoardPanelImp.ROW_COUNT + 1;
		}
		
		
		if(board.isValidAndEmpty(currentType, newColumn, newRow, newRotation)) {
			currentRotation = newRotation;
			currentRow = newRow;
			currentCol = newColumn;
		}
		
	}
	public int getScore() {
		return score;
	}
	
	
	public int getLevel() {
		return level;
	}

	public enum TileType {

		
		
		
		TypeI(new Color(BoardPanelImp.COLOR_MIN, BoardPanelImp.COLOR_MAX, BoardPanelImp.COLOR_MAX), 4, 4, 1, new boolean[][] {
			{
				false,	false,	false,	false,
				true,	true,	true,	true,
				false,	false,	false,	false,
				false,	false,	false,	false,
			},
			{
				false,	false,	true,	false,
				false,	false,	true,	false,
				false,	false,	true,	false,
				false,	false,	true,	false,
			},
			{
				false,	false,	false,	false,
				false,	false,	false,	false,
				true,	true,	true,	true,
				false,	false,	false,	false,
			},
			{
				false,	true,	false,	false,
				false,	true,	false,	false,
				false,	true,	false,	false,
				false,	true,	false,	false,
			}
		}),
		
		
		TypeJ(new Color(BoardPanelImp.COLOR_MIN, BoardPanelImp.COLOR_MIN, BoardPanelImp.COLOR_MAX), 3, 3, 2, new boolean[][] {
			{
				true,	false,	false,
				true,	true,	true,
				false,	false,	false,
			},
			{
				false,	true,	true,
				false,	true,	false,
				false,	true,	false,
			},
			{
				false,	false,	false,
				true,	true,	true,
				false,	false,	true,
			},
			{
				false,	true,	false,
				false,	true,	false,
				true,	true,	false,
			}
		}),
		
		
		TypeL(new Color(BoardPanelImp.COLOR_MAX, 127, BoardPanelImp.COLOR_MIN), 3, 3, 2, new boolean[][] {
			{
				false,	false,	true,
				true,	true,	true,
				false,	false,	false,
			},
			{
				false,	true,	false,
				false,	true,	false,
				false,	true,	true,
			},
			{
				false,	false,	false,
				true,	true,	true,
				true,	false,	false,
			},
			{
				true,	true,	false,
				false,	true,	false,
				false,	true,	false,
			}
		}),
		
		
		TypeO(new Color(BoardPanelImp.COLOR_MAX, BoardPanelImp.COLOR_MAX, BoardPanelImp.COLOR_MIN), 2, 2, 2, new boolean[][] {
			{
				true,	true,
				true,	true,
			},
			{
				true,	true,
				true,	true,
			},
			{	
				true,	true,
				true,	true,
			},
			{
				true,	true,
				true,	true,
			}
		}),
		
		
		TypeS(new Color(BoardPanelImp.COLOR_MIN, BoardPanelImp.COLOR_MAX, BoardPanelImp.COLOR_MIN), 3, 3, 2, new boolean[][] {
			{
				false,	true,	true,
				true,	true,	false,
				false,	false,	false,
			},
			{
				false,	true,	false,
				false,	true,	true,
				false,	false,	true,
			},
			{
				false,	false,	false,
				false,	true,	true,
				true,	true,	false,
			},
			{
				true,	false,	false,
				true,	true,	false,
				false,	true,	false,
			}
		}),
		
		/**
		 * Piece TypeT.
		 */
		TypeT(new Color(128, BoardPanelImp.COLOR_MIN, 128), 3, 3, 2, new boolean[][] {
			{
				false,	true,	false,
				true,	true,	true,
				false,	false,	false,
			},
			{
				false,	true,	false,
				false,	true,	true,
				false,	true,	false,
			},
			{
				false,	false,	false,
				true,	true,	true,
				false,	true,	false,
			},
			{
				false,	true,	false,
				true,	true,	false,
				false,	true,	false,
			}
		}),
		
		/**
		 * Piece TypeZ.
		 */
		TypeZ(new Color(BoardPanelImp.COLOR_MAX, BoardPanelImp.COLOR_MIN, BoardPanelImp.COLOR_MIN), 3, 3, 2, new boolean[][] {
			{
				true,	true,	false,
				false,	true,	true,
				false,	false,	false,
			},
			{
				false,	false,	true,
				false,	true,	true,
				false,	true,	false,
			},
			{
				false,	false,	false,
				true,	true,	false,
				false,	true,	true,
			},
			{
				false,	true,	false,
				true,	true,	false,
				true,	false,	false,
			}
		});
		private Color baseColor;
		
		
		private Color lightColor;
		
		
		private Color darkColor;
		
		
		private int spawnCol;
		
		
		private int spawnRow;
		
		
		private int dimension;
		
		
		private int rows;
		
		private int cols;
		private boolean[][] tiles;
		private TileType(Color color, int dimension, int cols, int rows, boolean[][] tiles) {
			this.baseColor = color;
			this.lightColor = color.brighter();
			this.darkColor = color.darker();
			this.dimension = dimension;
			this.tiles = tiles;
			this.cols = cols;
			this.rows = rows;
			
			this.spawnCol = 5 - (dimension >> 1);
			this.spawnRow = getTopInset(0);
		}
		
		 public Color getBaseColor ()   {
				//TODO Auto-generated method stub
				return baseColor;
		    }
		    public Color getLightColor ()   {
				//TODO Auto-generated method stub
				return lightColor;
		    }
		    public Color getDarkColor ()   {
				//TODO Auto-generated method stub
				return darkColor;
		    }
		    public int getDimension ()   {
				//TODO Auto-generated method stub
				return dimension;
		    }
		    public int getSpawnColumn ()   {
				//TODO Auto-generated method stub
				return spawnCol;
		    }
		    public int getSpawnRow ()   {
				//TODO Auto-generated method stub
				return spawnRow;
		    }
		    public int getRows ()   {
				//TODO Auto-generated method stub
				return rows;
		    }
		    public int getCols ()   {
				//TODO Auto-generated method stub
				return cols;
		    }
		    public boolean isTile (int x,int y,int rotation)   {
				//TODO Auto-generated method stub
		    	return tiles[rotation][y * dimension + x];

		    }
		    public int getLeftInset (int rotation)   {
				//TODO Auto-generated method stub
		    	for(int x = 0; x < dimension; x++) {
					for(int y = 0; y < dimension; y++) {
						if(isTile(x, y, rotation)) {
							return x;
						}
					}
				}
				return -1;

		    }
		    public int getRightInset (int rotation)   {
				//TODO Auto-generated method stub
		    	for(int x = dimension - 1; x >= 0; x--) {
					for(int y = 0; y < dimension; y++) {
						if(isTile(x, y, rotation)) {
							return dimension - x;
						}
					}
				}
				return -1;

		    }
		    public int getTopInset (int rotation)   {
				//TODO Auto-generated method stub
		    	for(int y = 0; y < dimension; y++) {
					for(int x = 0; x < dimension; x++) {
						if(isTile(x, y, rotation)) {
							return y;
						}
					}
				}
				return -1;

		    }
		    public int getBottomInset (int rotation)   {
				//TODO Auto-generated method stub
		    	for(int y = dimension - 1; y >= 0; y--) {
					for(int x = 0; x < dimension; x++) {
						if(isTile(x, y, rotation)) {
							return dimension - y;
						}
					}
				}
				return -1;

		    }
	}


		

    public boolean isPaused ()   {
		//TODO Auto-generated method stub
		return isPaused;
    }
    public boolean isGameOver ()   {
		//TODO Auto-generated method stub
		return isGameOver;
    }
    public boolean isNewGame ()   {
		//TODO Auto-generated method stub
		return isNewGame;
    }
    public TileType getPieceType ()   {
		//TODO Auto-generated method stub
		return currentType;
    }
    public TileType getNextPieceType ()   {
		//TODO Auto-generated method stub
		return nextType;
    }
    public int getPieceCol ()   {
		//TODO Auto-generated method stub
		return currentCol;
    }
    public int getPieceRow ()   {
		//TODO Auto-generated method stub
		return currentRow;
    }
    public int getPieceRotation ()   {
		//TODO Auto-generated method stub
		return currentRotation;
    }
	@Override
	public Color getBaseColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Color getLightColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Color getDarkColor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getDimension() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getSpawnColumn() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getSpawnRow() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getRows() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getCols() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isTile(int x, int y, int rotation) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getLeftInset(int rotation) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getRightInset(int rotation) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getTopInset(int rotation) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getBottomInset(int rotation) {
		// TODO Auto-generated method stub
		return 0;
	}
   
}