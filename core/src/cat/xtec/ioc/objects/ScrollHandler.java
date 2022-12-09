package cat.xtec.ioc.objects;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;
import java.util.Random;

import cat.xtec.ioc.utils.Methods;
import cat.xtec.ioc.utils.Settings;

public class ScrollHandler extends Group {

    // Fons de pantalla
    Background bg, bg_back;

    // Asteroides
    int numAsteroids;
    private ArrayList<Zombie> zombies;

    // Objecte Random
    Random r;


    public ScrollHandler() {

        // Creem els dos fons
        bg = new Background(0, 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);
        bg_back = new Background(bg.getTailX(), 0, Settings.GAME_WIDTH * 2, Settings.GAME_HEIGHT, Settings.BG_SPEED);

        // Afegim els fons al grup
        addActor(bg);
        addActor(bg_back);

        // Creem l'objecte random
        r = new Random();

        // Comencem amb 3 zombies
        numAsteroids = 3;

        // Creem l'ArrayList
        zombies = new ArrayList<Zombie>();

        // Definim una mida aleatòria entre el mínim i el màxim
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;

        // Afegim el primer Zombie a l'Array i al grup
        Zombie zombie = new Zombie(Settings.GAME_WIDTH, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
        zombies.add(zombie);
        addActor(zombie);

        // Des del segon fins l'últim asteroide
        for (int i = 1; i < numAsteroids; i++) {
            // Creem la mida al·leatòria
            newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID) * 34;
            // Afegim l'zombie.
            zombie = new Zombie(zombies.get(zombies.size() - 1).getTailX() + Settings.ASTEROID_GAP, r.nextInt(Settings.GAME_HEIGHT - (int) newSize), newSize, newSize, Settings.ASTEROID_SPEED);
            // Afegim l'asteroide a l'ArrayList
            zombies.add(zombie);
            // Afegim l'asteroide al grup d'actors
            addActor(zombie);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // Si algun element està fora de la pantalla, fem un reset de l'element.
        if (bg.isLeftOfScreen()) {
            bg.reset(bg_back.getTailX());

        } else if (bg_back.isLeftOfScreen()) {
            bg_back.reset(bg.getTailX());

        }

        for (int i = 0; i < zombies.size(); i++) {

            Zombie zombie = zombies.get(i);
            if (zombie.isLeftOfScreen()) {
                if (i == 0) {
                    zombie.reset(zombies.get(zombies.size() - 1).getTailX() + Settings.ASTEROID_GAP);
                } else {
                    zombie.reset(zombies.get(i - 1).getTailX() + Settings.ASTEROID_GAP);
                }
            }
        }
    }

    public boolean collides(KillerSanta nau) {

        // Comprovem les col·lisions entre cada asteroid i la nau
        for (Zombie zombie : zombies) {
            if (zombie.collides(nau)) {
                return true;
            }
        }
        return false;
    }

    // colisión del zombie y el fuego
    //**********
    public Zombie collides(Fire fire){

        for(Zombie i : zombies){
            if(i.collides(fire)){
                return i;
            }
        }

        return null;
    }

    public void reset() {

        // Posem el primer asteroid fora de la pantalla per la dreta
        zombies.get(0).reset(Settings.GAME_WIDTH);
        // Calculem les noves posicions de la resta d'zombies.
        for (int i = 1; i < zombies.size(); i++) {

            zombies.get(i).reset(zombies.get(i - 1).getTailX() + Settings.ASTEROID_GAP);

        }
    }

    public void killerZombie(Zombie zombie){
        zombies.remove(zombie);
        zombie.remove();
    }

    public ArrayList<Zombie> getAsteroids() {
        return zombies;
    }
}