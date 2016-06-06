package ua.pp.blastorq.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.util.Iterator;

import javafx.stage.Stage;

public class GameScreen implements Screen {

    final Drop game;
    OrthographicCamera camera;
    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    Music rainMusic;
    Rectangle bucket;
    Vector3 touchPos;
    Array<Rectangle> raindrops;
    long lastDropTime;

    public int dropsGatchered;
    int speed = 0  ;
    int highscore = 0;

    long second = 1000000000;

    Array<Texture> bgs;
    int bgId = 0;
    int bgId2 = 0;
    int bgCnt = 27;
    int bgL = 0;
    boolean newBgLoaded = true;
    long timeBgInit = 0;

    //Полоска скіки ще лишилось
    Double barWidth = 800.00;

    BitmapFont font32 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font48 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font64 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font128 = new BitmapFont(Gdx.files.internal("fonts/BadScript-128.fnt"), false);

    public GameScreen(final Drop gam) {
        this.game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        touchPos = new Vector3();

        dropImage = new Texture("droplet.png");
        bucketImage = new Texture("bucket.png");

        bgs = new Array<Texture>();
        for(int i=0;i<=bgCnt;i++){
            bgs.add(new Texture("bg/"+i+".jpg"));
        }
        bgId = 0;
        bucket = new Rectangle();
        bucket.x = 800 / 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        raindrops = new Array<Rectangle>();
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }




    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        long timeNow = TimeUtils.nanoTime();

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();


        if(timeNow - 5*second > timeBgInit){
            timeBgInit = timeNow;
            bgId = MathUtils.random(0, bgCnt);
            newBgLoaded = false;
        }
        if(!newBgLoaded){
            bgL -= 1000 * Gdx.graphics.getDeltaTime();
            if(bgL < -800){
                newBgLoaded = true;
                bgL = 0;
                bgId2 = bgId;
            }
        }
        game.batch.draw(bgs.get(bgId), 0, 0);
        game.batch.draw(bgs.get(bgId2), bgL, 0);

        font48.setColor(1, 1, 1, 1);
        font48.draw(game.batch, "Рахунок: " + dropsGatchered, 0, 470);
        game.batch.draw(bucketImage, bucket.x, bucket.y);

        for (Rectangle raindrop : raindrops) {
            game.batch.draw(dropImage, raindrop.x, raindrop.y);
        }

        //Полоска скіки залишилось
        Rectangle wr = new Rectangle();
        Pixmap wpm = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        wpm.setColor(new Color(
                1,
                ((barWidth < 100) ? ((barWidth < 50) ? 0 : 0.5f) : 1),
                ((barWidth < 100) ? ((barWidth < 50) ? 0 : 0.5f) : 1),
                1));
        wpm.fillRectangle(0, 0, 100, 100);
        Texture texture = new Texture(wpm);
        wpm.dispose();
        game.batch.draw(texture, 0, 476, Math.round(barWidth), 5);
        barWidth -= (barWidth>400)?0.7:0.9;


        game.batch.end();

        boolean moveL = false;
        boolean moveR = false;
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            moveL = bucket.x > touchPos.x - 32 - 8;
            moveR = bucket.x < touchPos.x - 32 + 8;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || moveL) bucket.x -= (400 + speed/2) * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || moveR) bucket.x += (400 + speed/2) * Gdx.graphics.getDeltaTime();

        if (bucket.x < 0) bucket.x = 0;
        if (bucket.x > 800 - 64) bucket.x = 800 - 64;

        if (TimeUtils.nanoTime() - lastDropTime > second*(Math.random() + 1)){
            spawnRaindrop();
        }

        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= (200 + (speed * 1.5)) * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) iter.remove();
            if (raindrop.overlaps(bucket)) {
                dropsGatchered++;
                speed = dropsGatchered*2;
                barWidth += (barWidth<800 - 1)
                        ?(10 + speed * 1.5)*(Math.random()/2 + 0.75)
                        :-10;
                iter.remove();
            }
        }
        if(barWidth<=0) {
            game.setScreen(new GameOver(game, dropsGatchered , highscore));
        }
        if(dropsGatchered > highscore)highscore = dropsGatchered;

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
        dropImage.dispose();
        bucketImage.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}



