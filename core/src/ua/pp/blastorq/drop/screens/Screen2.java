package ua.pp.blastorq.drop.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ua.pp.blastorq.drop.ScreenTest;
import ua.pp.blastorq.drop.utils.Button;

/**
 * Created by ������� on 21.06.2016.
 */
public class Screen2 implements Screen {


    Texture img ,buttondwn;
    TextureAtlas atlas;
    ScreenTest game;

    int highscore;
    boolean isPaused;
    OrthographicCamera camera;
    int canStartAgain = 100;
    Texture bg = new Texture("bg/gameover_bg.jpg");
    BitmapFont font32 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font48 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font64 = new BitmapFont(Gdx.files.internal("fonts/BadScript-64.fnt"), false);
    BitmapFont font128 = new BitmapFont(Gdx.files.internal("fonts/BadScript-128.fnt"), false);
    SpriteBatch batch;

    Stage gameStage;
    Button button1;

    private OrthographicCamera gamecam;
    private Viewport gameport;
    int dropsGatchered;
    double barwidth;



    public Screen2(final ScreenTest game ,int dropsGatchered, double barwidth){
        this.dropsGatchered = dropsGatchered;
        this.barwidth = barwidth;

    batch = new SpriteBatch();
        atlas = new TextureAtlas("texture.pack");
        this.game = game;
    img = new Texture("playbutton_64x64.jpg");
        buttondwn = new Texture("playbutton_55x55.jpg");
    gamecam = new OrthographicCamera();
    gameport = new StretchViewport(ScreenTest.V_HEIGHT, ScreenTest.V_WIDTH, gamecam);
    gameStage = new Stage(new StretchViewport(ScreenTest.V_HEIGHT,ScreenTest.V_WIDTH ));

    button1 = new Button(img,800/2 - 64/2,480/2 - 64/2);
    button1.addListener(new InputListener(){
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            gameStage.addActor(button1);
            GameScreen.isnextscr = false;
            game.setScreen(game.gameScreen);
        }

        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            button1.remove();
          Button butdwn = new Button(new Texture("playbutton_55x55.jpg") ,800/2 - 55/2 ,480/2 - 55/2);
            gameStage.addActor(butdwn);

          //  button1.setTouchable(Touchable.enabled);
            return true;
        }


    });
    gameStage.addActor(button1);
    }

    @Override
    public void render(float delta) {


        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);




        font64.setColor(0.3f, 0.3f, 0.5f, 1);
        font128.setColor(0.3f, 0.3f, 0.5f, 1);
        game.batch.begin();
        game.batch.draw(new Texture("bg/gameover_bg.jpg") ,0 ,0 ,800 ,480);
        font128.draw(game.batch, "Pause", 260+1, 320+1);
        font128.draw(game.batch, "Pause", 260+1, 320-1);
        font128.draw(game.batch, "Pause", 260-1, 320+1);
        font128.draw(game.batch, "Pause", 260-1, 320-1);

        font64.draw(game.batch, "Твій рахунок - "+ highscore, 275+1, 210+1);
        font64.draw(game.batch, "Твій рахунок - "+ highscore, 275+1, 210-1);
        font64.draw(game.batch, "Твій рахунок - "+highscore, 275-1, 210+1);
        font64.draw(game.batch, "Твій рахунок - "+highscore, 275-1, 210-1);

        font64.draw(game.batch, "Клікни будь-де щоб спробувати знову", 175+1, 160+1);
        font64.draw(game.batch, "Клікни будь-де щоб спробувати знову", 175+1, 160-1);
        font64.draw(game.batch, "Клікни будь-де щоб спробувати знову", 175-1, 160+1);
        font64.draw(game.batch, "Клікни будь-де щоб спробувати знову", 175-1, 160-1);

        //А тепер сам текст
        font64.setColor(1, 1, 1, 1);
        font128.setColor(1, 1, 1, 1);
        font128.draw(game.batch, "Pause", 260, 320);



        game.batch.end();
        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();
       // batch.begin();
       // batch.draw(img, 0, 0);
        //batch.end();
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
