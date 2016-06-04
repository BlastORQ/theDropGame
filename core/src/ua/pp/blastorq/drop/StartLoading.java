package ua.pp.blastorq.drop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.TimeUtils;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import ua.pp.blastorq.drop.tools.*;
import aurelienribon.tweenengine.TweenManager;

public class
StartLoading implements Screen {
    final Drop game;
    OrthographicCamera camera;
    private TweenManager manager;
    private SpriteBatch batch;
    private Sprite sprite;

    Texture bg = new Texture("logo.png");
    Screen nextscreen;
    final long timeStart = TimeUtils.nanoTime();
    long second = 1000000000;
    int time;
    double alpha = 0;
    Sprite logo;
    TextureAtlas atlas;


    public StartLoading(final Drop gam, final Screen next, int tim) {
        nextscreen = next;
        game = gam;
        time = tim;
        atlas = new TextureAtlas(Gdx.files.internal("logo/texture.pack"), true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        logo = new Sprite(atlas.findRegion("logo"));
    }

    @Override
    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("logo/logo.pack") , true);
        sprite = new Sprite(atlas.findRegion("logo"));
        sprite.setColor(1, 1, 1, 0);


        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float desiredWidth = width * 0.7f;
        float scale = desiredWidth / sprite.getWidth();

        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height / 2)
                - (sprite.getHeight() / 2));

        setupTween();
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        sprite.draw(game.batch);
        game.batch.end();


    }

    private void setupTween(){
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(nextscreen);
            }
        };

        Tween.to(sprite, SpriteAccessor.ALPHA, 0.8f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.4f)
                .setCallback(callback).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
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
