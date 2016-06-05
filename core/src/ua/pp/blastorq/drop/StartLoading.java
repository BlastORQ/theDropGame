package ua.pp.blastorq.drop;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.pp.blastorq.drop.tools.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class StartLoading implements Screen{

    private TweenManager manager;
    private SpriteBatch batch;
    private Sprite sprite ,logo;
    Texture f;
    private Drop game;
    private Screen nextScreen;

    public StartLoading(Drop game, Screen nextScreen) {
        this.game = game;
        this.nextScreen = nextScreen;
    }

    @Override
    public void show() {
        f = new Texture("logo.png");
        logo = new Sprite(f);

        sprite = new Sprite(logo);
        sprite.setColor(1, 1, 0, 0);


        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float desiredWidth = width * 0.7f;
        float scale = sprite.getWidth();

        sprite.setSize(800, 480);
        sprite.setPosition(0 , 0);

        setupTween();
        batch = new SpriteBatch();


    }

    private void setupTween(){
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        manager = new TweenManager();

        TweenCallback callback = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(nextScreen);
            }
        };

        Tween.to(sprite, SpriteAccessor.ALPHA, 0.8f).target(1)
                .ease(TweenEquations.easeInOutQuad).repeatYoyo(1, 0.4f)
                .setCallback(callback).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(1, 0.7529f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        sprite.draw(batch);
        batch.end();

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
