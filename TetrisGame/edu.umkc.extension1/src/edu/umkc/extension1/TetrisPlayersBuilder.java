package edu.umkc.extension1;

import java.util.Map;

import tetris.Tetris;
import edu.umkc.tetrisplugin1.ITetris;

public class TetrisPlayersBuilder implements ITetris {

	@Override
	public void buildTetrisApp(int numOfPlayers,  Map<String, Object> tetrisMap) {
		Tetris.initializePlayers(numOfPlayers, tetrisMap);
	}

}
