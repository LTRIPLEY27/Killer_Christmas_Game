package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Explosion {
    private float delta;
    private Animation kaboom;
    private float x;
    private float y;
    private float width;
    private float height;

    public Explosion (Animation animation, float x, float y, float width, float height, float delta){
        this.kaboom = animation;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.delta = delta;
    }


    public boolean isFinished(){
        return kaboom.isAnimationFinished(delta);
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public Animation getAnim() {
        return kaboom;
    }

    public void setAnim(Animation animation) {
        this.kaboom = animation;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}

