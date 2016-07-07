package ua.pp.blastorq.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.pp.blastorq.drop.screens.GameScreen;
import ua.pp.blastorq.drop.screens.Screen2;

public class ScreenTest extends Game {


	public GameScreen gameScreen;
	public Screen2 screen2;
	public SpriteBatch batch;

	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 800;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this);
		screen2 = new Screen2(this , 0 ,0);

		setScreen(new StartLoading(this ,new MainMenuScreen(this)));
	}

	@Override
	public void render () {
	super.render();
	}
}
