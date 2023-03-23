package uk.ac.reading.sis05kol.GameFramework;

import android.graphics.Bitmap;

/**
 * Class player stores everything about the player character
 *
 * Contains attributes image and score
 */
public class Player extends GameEntity {
    public Bitmap image;
    public int score;

    Player(){
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
}


