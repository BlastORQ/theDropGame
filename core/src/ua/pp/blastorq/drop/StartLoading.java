package ua.pp.blastorq.drop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

public class StartLoading implements Screen {
    final Drop game;
    OrthographicCamera camera;
    Texture bg = new Texture("logo.png");
    Screen nextscreen;
    long timeStart = TimeUtils.nanoTime();
    long second = 1000000000;
    int tim;


    public StartLoading(final Drop gam, final Screen next, int time) {
        nextscreen = next;
        game = gam;
        tim = time;
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
        game.batch.draw(bg, 0, 0);
        game.batch.end();
        if(TimeUtils.nanoTime() > timeStart+(tim*second)){
            game.setScreen(nextscreen);
        }
        dispose();

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
    bg.dispose();
    }
}
