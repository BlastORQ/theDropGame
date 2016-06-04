package ua.pp.blastorq.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {

	SpriteBatch batch;
	BitmapFont font;



	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new SplashScreen( this));

	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
		font.dispose();
	}
}