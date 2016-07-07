package ua.pp.blastorq.drop.screens;

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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

import ua.pp.blastorq.drop.*;
import ua.pp.blastorq.drop.utils.Button;

/**
 * Created by ������� on 21.06.2016.
 */
public class GameScreen implements Screen {

    ScreenTest game;
    OrthographicCamera camera;
    Texture dropImage;
    Texture bucketImage;
    Sound dropSound;
    public static int scrW = Gdx.graphics.getWidth();
    public static int scrH = Gdx.graphics.getHeight();
    Music rainMusic;
    Rectangle bucket;
    public static boolean isnextscr;
    Vector3 touchPos;
    Array<Rectangle> raindrops;
    long lastDropTime;


    public int dropsGatchered ,k;
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
    Double barWidth = (double)scrW;

    BitmapFont font32 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font48 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font64 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font128 = new BitmapFont(Gdx.files.internal("fonts/BadScript-128.fnt"), false);

    Texture img;
    SpriteBatch batch;

    Stage gameStage;
    Button button1;
    double g;
    private OrthographicCamera gamecam;
    private Viewport gameport;



    public GameScreen(final ScreenTest game){
        this.game = game;
    batch = new SpriteBatch();
    img = new Texture("ps.jpg");
    gamecam = new OrthographicCamera();
    gameport = new StretchViewport(ScreenTest.V_HEIGHT, ScreenTest.V_WIDTH, gamecam);
    gameStage = new Stage(new StretchViewport(ScreenTest.V_HEIGHT,ScreenTest.V_WIDTH ));

    button1 = new Button(img,800 - 64,480-64);
    button1.setTouchable(Touchable.enabled);
    button1.addListener(new InputListener(){
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            gameStage.addActor(button1);

            game.setScreen(new Screen2(game ,dropsGatchered ,g));

        }

        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
          isnextscr = true;
           button1.remove();
            Button butndwn = new Button(new Texture("ps_55x55.jpg") ,800 - 55 ,480 - 55);
            gameStage.addActor(butndwn);



            return true;
        }


    });
    gameStage.addActor(button1);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, scrW, scrH);

        touchPos = new Vector3();

        dropImage = new Texture("droplet.png");
        bucketImage = new Texture("bucket.png");


        bgs = new Array<Texture>();
        for(int i=0;i<=bgCnt;i++){
            bgs.add(new Texture("bg/"+i+".jpg"));
        }
        bgId = 0;
        bucket = new Rectangle();
        bucket.x = scrW/ 2 - 64 / 2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        raindrops = new Array<Rectangle>();
    }

    @Override
    public void render(float delta) {

        // ������ ����� � ��������
        Gdx.gl.glClearColor(0,0,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        long timeNow = TimeUtils.nanoTime();
k = MathUtils.random(200 ,300);
        Gdx.app.log("" + k ,"");
        camera.update();
      batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(timeNow - 5*second > timeBgInit){
            timeBgInit = timeNow;
            bgId = MathUtils.random(0, bgCnt);
            newBgLoaded = false;
        }
        if(!newBgLoaded){
            bgL -= 1000 * Gdx.graphics.getDeltaTime();
            if(bgL < -scrW){
                newBgLoaded = true;
                bgL = 0;
                bgId2 = bgId;
            }
        }
        batch.draw(bgs.get(bgId), 0, 0 , scrW , scrH);
        batch.draw(bgs.get(bgId2), bgL, 0 , scrW , scrH);

        font48.setColor(1, 1, 1, 1);
        font48.draw(batch, "Рахунок: " + dropsGatchered, 0, scrH - 10);
        batch.draw(bucketImage, bucket.x, bucket.y);

        for (Rectangle raindrop : raindrops) {
           batch.draw(dropImage, raindrop.x, raindrop.y);
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
        batch.draw(texture, 0, scrH - 4 , Math.round(barWidth), 5);

            barWidth -= (barWidth > scrW / 2) ? 1.5 : 0.9;


            batch.end();

            boolean moveL = false;
            boolean moveR = false;
            if (barWidth <= 0) game.setScreen(new GameOver(game, dropsGatchered, highscore));


            if (Gdx.input.isTouched()&& !isnextscr ) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                moveL = bucket.x > touchPos.x - 32 - 8;
                moveR = bucket.x < touchPos.x - 32 + 8;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || moveL)
                bucket.x -= (400 + speed / 2) * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || moveR)
                bucket.x += (400 + speed / 2) * Gdx.graphics.getDeltaTime();


            if (bucket.x < 0) bucket.x = 0;
            if (bucket.x > scrW - 64) bucket.x = scrW - 64;

            if (TimeUtils.nanoTime() - lastDropTime > second * (Math.random() + 1)) {
                spawnRaindrop();

            }


            Iterator<Rectangle> iter = raindrops.iterator();
            while (iter.hasNext()) {
                Rectangle raindrop = iter.next();
                raindrop.y -=  k * Gdx.graphics.getDeltaTime();
                if (raindrop.y + 64 < 0) iter.remove();
                if (raindrop.overlaps(bucket)) {
                    dropsGatchered++;
                    speed = dropsGatchered * 2;
                    barWidth += (barWidth < 800 - 1)
                            ? (10 + speed * 1.5) * (Math.random() / 2 + 0.75)
                            : -10;
                    iter.remove();
                }
            }
            if (dropsGatchered > highscore) highscore = dropsGatchered;

            gameStage.act(Gdx.graphics.getDeltaTime());
            gameStage.draw();



    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, scrW - 64);
        raindrop.y = scrH;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(gameStage);
    }


    @Override
    public void hide() {

        // ��� ����� ����������� �������� ����� �� �������� ���� �����
    }

    @Override
    public void pause() {
    }


    @Override
    public void resume() {
    }


    @Override
    public void dispose() {
        // never called automatically
    }
}
