package uk.ac.reading.sis05kol.GameFramework;

import android.graphics.Bitmap;
import java.util.Random;

/**
 * The rival class represents the rival that the player character is trying to beat
 *
 * Contains attributes image and score
 */
public class Rival extends GameEntity {
    public Bitmap image;
    public int score;

    Rival(){
        super();
    } //super constructor

    public float getX() {
        return super.getX();
    }

    public void setX(float value) {
        super.setX(value);
    }

    public float getY() {
        return super.getY();
    }

    public void setY(float value) {
        super.setY(value);
    }

    public float getXSpeed() {
        return super.getXSpeed();
    }

    public void setXSpeed(float value) {
        super.setXSpeed(value);
    }

    public float getYSpeed() {
        return super.getYSpeed();
    }

    public void setYSpeed(float value) {
        super.setYSpeed(value);
    }

    /**
     *
     * chooseMove() randomly generates a number from 0 to 4, which is then passed to TheGame
     *
     * The number is interpreted at runtime to decide how the rival moves
     *
     * @return rand.nextInt(5)
     */
    public int chooseMove() {
        Random rand = new Random();
        return rand.nextInt(5);
    }
}
