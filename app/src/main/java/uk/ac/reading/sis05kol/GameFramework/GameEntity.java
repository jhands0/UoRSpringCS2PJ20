package uk.ac.reading.sis05kol.GameFramework;

/**
 * Abstract class game entity is a superclass to Player, Rival and Obstacle
 *
 * Determines behaviour for how it's subclasses move on the screen
 *
 * Contains attributes x, y, xSpeed and ySpeed
 */
abstract public class GameEntity {

    GameEntity() { //constructor
    }

    private float x; //represents the x axis of the GameEntity object
    private float y; //represents the y axis of the GameEntity object
    private float xSpeed; //represents the speed that the GameEntity object is travelling at in the x direction
    private float ySpeed; //represents the speed that the GameEntity object is travelling at in the y direction

    public float getX() {
        return this.x;
    }

    public void setX(float value) {
        this.x = value;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float value) {
        this.y = value;
    }

    public float getXSpeed() {
        return this.xSpeed;
    }

    public void setXSpeed(float value) {
        this.xSpeed = value;
    }

    public float getYSpeed() {
        return this.ySpeed;
    }

    public void setYSpeed(float value) {
        this.ySpeed = value;
    }
}