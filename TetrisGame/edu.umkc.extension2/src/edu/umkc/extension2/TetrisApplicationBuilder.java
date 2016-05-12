package edu.umkc.extension2;

import java.util.HashMap;
import java.util.Map;

import tetris.Tetris;
import edu.umkc.tetrisplugin1.ITetris;

public class TetrisApplicationBuilder implements ITetris {

	@Override
	public void buildTetrisApp(int numOfPlayers,  Map<String, Object> tetrisMap) {
		TetrisUtils tetrisUtils = new TetrisUtils();
		
		if (tetrisMap  == null ) {
			tetrisMap = new HashMap<>();
		}
		
		
		Tetris tetris1 = getTetrisPlayer(tetrisMap, 1);	
		tetrisUtils.setPlayer1Keys(tetris1);
		tetris1.initializeGame();
		
		Tetris tetris2 = getTetrisPlayer(tetrisMap, 2);
		tetrisUtils.setPlayer2Keys(tetris2);
		tetris2.initializeGame();
			
		tetrisUtils.startGame(tetris1, tetris2);
	}

	private Tetris getTetrisPlayer(Map<String, Object> tetrisMap, int playerNum) {
		String playerKey = "Player "+playerNum;
		Tetris tetris = (Tetris) tetrisMap.get(playerKey);
		
		if (tetris == null) {
			//Player Not initialized
			tetris = new Tetris(playerKey);
			tetrisMap.put(playerKey, tetris);
		}
		
		return tetris;
	}

	
}
