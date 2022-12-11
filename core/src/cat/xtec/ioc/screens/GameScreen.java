package cat.xtec.ioc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;


import java.util.ArrayList;

import cat.xtec.ioc.helpers.AssetManager;
import cat.xtec.ioc.helpers.InputHandler;

import cat.xtec.ioc.objects.BonusA;
import cat.xtec.ioc.objects.BonusB;
import cat.xtec.ioc.objects.Explosion;
import cat.xtec.ioc.objects.Fire;
import cat.xtec.ioc.objects.ScrollHandler;
import cat.xtec.ioc.objects.KillerSanta;
import cat.xtec.ioc.objects.Zombie;
import cat.xtec.ioc.utils.Settings;



public class GameScreen implements Screen {

    // Els estats del joc

    public enum GameState {

        READY, RUNNING, GAMEOVER, PAUSED

    }



    private GameState currentState;

    // Objectes necessaris
    private Stage stage;
    private KillerSanta santa;
    private ScrollHandler scrollHandler;

    // Encarregats de dibuixar elements per pantalla
    private ShapeRenderer shapeRenderer;
    private Batch batch;

    // Per controlar l'animació de l'explosió
    private float explosionTime = 0;

    // Preparem el textLayout per escriure text
    private GlyphLayout textLayout;
    private GlyphLayout pounts;

    private ArrayList <Fire> fires;

    private ArrayList<Explosion> explosion;

    private ArrayList<Zombie> zombies;
    private ArrayList<BonusA> bonus1;
    private ArrayList<BonusB> bonus2;

    ////////

    private Label.LabelStyle textStyle;
    private Label text;


    private int score = 0;



    public GameScreen(Batch prevBatch, Viewport prevViewport) {

        // Iniciem la música
        AssetManager.music.play();

        // Creem el ShapeRenderer
        shapeRenderer = new ShapeRenderer();

        // Creem l'stage i assginem el viewport
        stage = new Stage(prevViewport, prevBatch);

        batch = stage.getBatch();

        // Creem la nau i la resta d'objectes
        santa = new KillerSanta(Settings.SPACECRAFT_STARTX, Settings.SPACECRAFT_STARTY, Settings.SPACECRAFT_WIDTH, Settings.SPACECRAFT_HEIGHT);
        scrollHandler = new ScrollHandler();

        // Afegim els actors a l'stage
        stage.addActor(scrollHandler);
        //stage.addActor(spacecraft);
        stage.addActor(santa);
        // Donem nom a l'Actor
        //spacecraft.setName("spacecraft");
        santa.setName("santa");

        fires = new ArrayList<Fire>();
        explosion = new ArrayList<Explosion>();

        // Iniciem el GlyphLayout
        textLayout = new GlyphLayout();
        textLayout.setText(AssetManager.font, "Are you\nready to run for your life?");


        // CARGA DE LA IMAGEN PAUSE DEL ASSETMANAGER
        Image paused = new Image(AssetManager.pause);
        paused.setName("pause");

        // INDICACIÓN DE LA POSICIÓN DEL PAUSE
        paused.setPosition((Settings.GAME_WIDTH) - paused.getWidth() -5, 5);
        stage.addActor(paused);

        currentState = GameState.READY;

        // Assignem com a gestor d'entrada la classe InputHandler
        Gdx.input.setInputProcessor(new InputHandler(this));

        // PUNTAJE TEXT
        textStyle = new Label.LabelStyle(AssetManager.font, null);
        text = new Label(("Points :") + score, textStyle);

        stage.addActor(text);

    }

    private void drawElements() {

        // Recollim les propietats del Batch de l'Stage
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        // Pintem el fons de negre per evitar el "flickering"
        //Gdx.gl20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        //Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicialitzem el shaperenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Definim el color (verd)
        shapeRenderer.setColor(new Color(0, 1, 0, 1));

        // Pintem la nau
        shapeRenderer.rect(santa.getX(), santa.getY(), santa.getWidth(), santa.getHeight());

        // Recollim tots els Asteroid
        zombies = scrollHandler.getAsteroids();

        bonus1 = scrollHandler.getCoins();
        bonus2 = scrollHandler.getCoinsB();

        Zombie zombie;
        BonusA bonusA;
        BonusB bonusB;


        for (int i = 0; i < zombies.size(); i++) {

            zombie= zombies.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(zombie.getX() + zombie.getWidth() / 2, zombie.getY() + zombie.getWidth() / 2, zombie.getWidth() / 2);
        }

        //////////////////////////////////////////////////////
        for (int i = 0; i < bonus1.size(); i++) {

            bonusA = bonus1.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(bonusA.getX() + bonusA.getWidth() / 2, bonusA.getY() + bonusA.getWidth() / 2, bonusA.getWidth() / 2);
        }

        //////////////////////////////////////////////////////
        for (int i = 0; i < bonus2.size(); i++) {

            bonusB = bonus2.get(i);
            switch (i) {
                case 0:
                    shapeRenderer.setColor(1, 0, 0, 1);
                    break;
                case 1:
                    shapeRenderer.setColor(0, 0, 1, 1);
                    break;
                case 2:
                    shapeRenderer.setColor(1, 1, 0, 1);
                    break;
                default:
                    shapeRenderer.setColor(1, 1, 1, 1);
                    break;
            }
            shapeRenderer.circle(bonusB.getX() + bonusB.getWidth() / 2, bonusB.getY() + bonusB.getWidth() / 2, bonusB.getWidth() / 2);
        }
        shapeRenderer.end();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        // Dibuixem tots els actors de l'stage
        stage.draw();

        // Depenent de l'estat del joc farem unes accions o unes altres
        switch (currentState) {

            case GAMEOVER:
                updateGameOver(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case READY:
                updateReady();
                break;
                // NUEVO ESTADO 'PAUSE'
            case PAUSED:
                textLayout.setText(AssetManager.font, "Paused");
                //LLAMADO AL MÉTODO PAUSE PARA PARAR TODO EL PROGRAMA
                updatePaused(delta);
                break;
        }

        //drawElements();

    }

    private void updateReady() {

        // Dibuixem el text al centre de la pantalla
        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        //stage.addActor(textLbl);
        batch.end();

    }

    // MÉTODO PARA INVOCAR LA PAUSE
    private void updatePaused(float delta){
        stage.act(delta);
        batch.begin();
        //DIBUJAMOS LA ETIQUETA
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH / 2) - textLayout.width / 2, (Settings.GAME_HEIGHT / 2) - textLayout.height / 2);
        batch.end();
    }

    private void updateRunning(float delta) {
        stage.act(delta);

        if (scrollHandler.collides(santa)) {
            // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
            AssetManager.explosionSound.play();
            //stage.getRoot().findActor("santa").remove();
            stage.getRoot().findActor("santa").remove();
            textLayout.setText(AssetManager.font, "Game Over :'(");
            currentState = GameState.GAMEOVER;
        }

        // VERIFICA COLISIONES DEL
        if(fires.size() > 0) {

            for(Fire i : fires){
                BonusA bonn = scrollHandler.collidesOk(i);
                BonusB bonni = scrollHandler.collidesOkB(i);
                if (bonn != null){
                    AssetManager.bonusSound.play();
                    score += 30;
                    text.setText("Score + Bonus : " + score);
                    scrollHandler.getBonus(bonn);
                    scrollHandler.addNewBonus(bonn);
                    scrollHandler.reset();
                }

                if (bonni != null){
                    AssetManager.bonusSound.play();
                    score += 50;
                    text.setText("Score + Bonus: " + score );
                    scrollHandler.getBonusB(bonni);
                    scrollHandler.addNewBonusB(bonni);
                    scrollHandler.reset();
                }
                Zombie zombie = scrollHandler.collides(i);
                if(zombie != null){
                    AssetManager.explosionSound.play();
                    score += 10;
                    text.setText("Score : " + score);
                    explosion.add(new Explosion(AssetManager.explosionAnim, (zombie.getX() + zombie.getWidth() / 2) - 32, zombie.getY() + zombie.getHeight() / 2 - 32, 64f, 64f, delta));
                    fires.remove(i);
                    i.remove();
                    scrollHandler.killerZombie(zombie);
                    scrollHandler.addNewZombie(zombie);
                    // REINICIA EL ELEMENTO
                    scrollHandler.reset();
                    break;
                }

            }

        }

    }


    private void updateGameOver(float delta) {
        stage.act(delta);

        batch.begin();
        AssetManager.font.draw(batch, textLayout, (Settings.GAME_WIDTH - textLayout.width) / 2, (Settings.GAME_HEIGHT - textLayout.height) / 2);
        // Si hi ha hagut col·lisió: Reproduïm l'explosió i posem l'estat a GameOver
        batch.draw((TextureRegion) AssetManager.explosionAnim.getKeyFrame(explosionTime, false), (santa.getX() + santa.getWidth() / 2) - 32, santa.getY() + santa.getHeight() / 2 - 32, 64, 64);
        batch.end();

        explosionTime += delta;

    }

    public void reset() {

        // Posem el text d'inici
        textLayout.setText(AssetManager.font, "Are you\nready to run for your life?");
        // Cridem als restart dels elements.
        santa.reset();
        scrollHandler.reset();

        // Posem l'estat a 'Ready'
        currentState = GameState.READY;

        // Afegim la nau a l'stage
        stage.addActor(santa);

        // Posem a 0 les variables per controlar el temps jugat i l'animació de l'explosió
        explosionTime = 0.0f;

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

        /// LLAMAMOS A PAUSAR LA MÍCA Y SETTEAR EL ESTADO DEL JUEGO
        AssetManager.music.pause();

        if(getCurrentState() == GameState.RUNNING) {
            setCurrentState(GameState.PAUSED);
        }
    }

    @Override
    public void resume() {
        AssetManager.music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public KillerSanta getSpacecraft() {
        return santa;
    }

    public Stage getStage() {
        return stage;
    }

    public ScrollHandler getScrollHandler() {
        return scrollHandler;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameState currentState) {

        switch (currentState) {
            case PAUSED:
                // CONDICIONAMOS EL VOLUMEN A LA PAUSA
                AssetManager.music.setVolume(0.03f);
                hidePositions();
                santa.startPause();
                scrollHandler.setPause();

        }
        this.currentState = GameState.RUNNING;
        Fire fire = new Fire(santa.getX() + santa.getWidth(), santa.getY() + santa.getHeight()/2, 9, 2, Settings.SPACECRAFT_VELOCITY * 2);
        stage.getRoot().addActor(fire);
        fires.add(fire);
        AssetManager.explosionSound.play();
    }


}
