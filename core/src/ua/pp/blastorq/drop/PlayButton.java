package ua.pp.blastorq.drop;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class PlayButton {

    private  float x, y, width, height;

    private TextureRegion buttonUp;
    private TextureRegion buttonDown;

    private Rectangle bounds;

    private boolean isPressed = false;

    public PlayButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp = buttonUp;
        this.buttonDown = buttonDown;

        bounds = new Rectangle(x, y, width, height);
    }

    public void draw(SpriteBatch batch) {
        if (isPressed) {
            batch.draw(buttonDown, x, y, width, height);
        } else {
            batch.draw(buttonUp, x, y, width, height);
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY)) {
            isPressed = true;
            return true;
        }
        return false;
    }

    public  boolean isTouchUp(int screenX, int screenY) {
        if (bounds.contains(screenX, screenY) && isPressed) {
            isPressed = false;

            return true;
        }
        isPressed = false;
        return false;
    }
}
