package ua.pp.blastorq.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import ua.pp.blastorq.drop.screens.GameScreen;

/**
 * Created by serhij on 06.07.2016.
 */
public class MainMenuScreen implements Screen {
    ScreenTest game;
    OrthographicCamera camera;
    Texture bg = new Texture("bg/mainmenu_bg.jpg");
    BitmapFont font32 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font48 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font64 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font128 = new BitmapFont(Gdx.files.internal("fonts/BadScript-128.fnt"), false);

    public MainMenuScreen(final ScreenTest gam) {
game = gam;


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0.7529f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        //Чорна грань текста
        font64.setColor(0.3f, 0.5f, 0.3f, 1);
        font128.setColor(0.3f, 0.5f, 0.3f, 1);

        font128.draw(game.batch, "Вітаємо в theDropGame!", 100+1, 300+1);
        font128.draw(game.batch, "Вітаємо в theDropGame!", 100+1, 300-1);
        font128.draw(game.batch, "Вітаємо в theDropGame!", 100-1, 300+1);
        font128.draw(game.batch, "Вітаємо в theDropGame!", 100-1, 300-1);

        font64.draw(game.batch, "Клікни де-небудь щоб почати!", 175+1, 200+1);
        font64.draw(game.batch, "Клікни де-небудь щоб почати!", 175+1, 200-1);
        font64.draw(game.batch, "Клікни де-небудь щоб почати!", 175-1, 200+1);
        font64.draw(game.batch, "Клікни де-небудь щоб почати!", 175-1, 200-1);

        //А тепер сам текст
        font64.setColor(1, 1, 1, 1);
        font128.setColor(1, 1, 1, 1);
        font128.draw(game.batch, "Вітаємо в theDropGame!", 100, 300);
        font64.draw(game.batch, "Клікни де-небудь щоб почати!", 175, 200);


        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
