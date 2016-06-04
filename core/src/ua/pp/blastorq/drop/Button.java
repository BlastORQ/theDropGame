package ua.pp.blastorq.drop

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import ua.pp.blastorq.drop.MainMenuScreen;

public class Button extends Actor {
    setTouchable(Touchable.enabled);
    setBounds(getX(), getY(), 128, 128);

    addListener(new InputListener())
    {
        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
        {

            return true;
        }}

    public void touchUp (InputEvent event, float x, float y, int pointer, int button)
    {

    }
}


@Override
public void draw(Batch batch, float parentAlfa)
        {
        batch.draw(Assets.textureKnopka,getX(),getY());
        }

}