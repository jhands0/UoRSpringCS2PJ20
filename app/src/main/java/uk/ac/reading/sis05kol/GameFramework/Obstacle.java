package uk.ac.reading.sis05kol.GameFramework;

import android.graphics.Bitmap;

/**
 * Class obstacle represents the cacti and tumbleweeds that the player will need to avoid
 *
 * This class inherits the GameEntity class and overrides all it's functions
 */
public class Obstacle extends GameEntity {
    Bitmap image; //bitmap image of individual object fetched at runtime
    Boolean cactus;
    Boolean tumble;

    /**
     * Constuctor for class obstacle
     *
     * @param cactus a boolean that is interpreted to assign the obstacle a bitmap image
     * @param tumble is a boolean that is interpreted to assign the obstacle a bitmap image
     */
    Obstacle(Boolean cactus, Boolean tumble){
        super();                                //super constructor
        if (cactus == null || tumble == null) { //if no parameters are parsed in to class
            throw new NullPointerException(); //throw exception as class in neither a cactus or tumbleweed
        }
        this.cactus = cactus;
        this.tumble = tumble;
    }

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
