package cat.xtec.ioc.objects;

import com.badlogic.gdx.graphics.g2d.Batch;

import cat.xtec.ioc.helpers.AssetManager;

public class Background extends Scrollable {

    private boolean paused;

    public Background(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        paused = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.disableBlending();
        batch.draw(AssetManager.background, position.x, position.y, width, height);
        batch.enableBlending();
    }

    // ADICIÓN ALOS MÉTODOS PARA EL PAUSED

    @Override
    public void act(float delta) {
        if(!paused) {
            super.act(delta);
        }
    }

    public void startPaused() {
        paused = true;
    }
    public void stopPause() { paused = false; }
}
