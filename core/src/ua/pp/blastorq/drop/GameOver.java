package ua.pp.blastorq.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.awt.Rectangle;


/**
 * Created by Мама on 24.04.2016.
 */
public  class GameOver implements Screen {


    final Drop game;
    boolean isPaused;
    OrthographicCamera camera;
    Texture bg = new Texture("bg/gameover_bg.jpg");
    int canStartAgain = 100;
    int score , highscore;


    public GameOver(final Drop gam, int scor , int highscor) {
        game = gam;
        score = scor;
        highscore = highscor;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Game over!", 100, 150);
        game.font.draw(game.batch, "Your score is "+score, 100, 125);
        game.font.draw(game.batch, "Your highscore is "+highscore, 100, 115);
        game.batch.draw(bg, 0, 0);

        game.font.draw(game.batch, "Tap anywhere to try again", 100, 100);
        game.batch.end();

        if (Gdx.input.isTouched() && canStartAgain<=0){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }else{
            canStartAgain--;
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
