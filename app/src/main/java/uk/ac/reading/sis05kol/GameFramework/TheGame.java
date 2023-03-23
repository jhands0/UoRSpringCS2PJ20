package uk.ac.reading.sis05kol.GameFramework;

//Other parts of the android libraries that we use
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * TheGame class is created when the startGame() function is executed in MainActivity
 *
 * The class represents everything contained on the screen that is interacted with during the game
 *
 * The class has attributes, player_obj, rival_obj, levelCount, line_no, currentLevel,
 * distanceBetweenObstaclesAndRival, distanceBetweenObstaclesAndPlayer and gameView
 */
public class TheGame extends GameThread {

    //player_obj stores the instance of the player
    public Player player_obj;

    //rival_obj stores the instance of the rival
    public Rival rival_obj;

    //levelCount stores the level currently being played e.g. 1, 2 or 3
    public int levelCount;

    //line_no stores the current line of obstacles that is being displayed on the screen
    public int line_no = 0;

    //currentLevel stores the instance of the current level
    public Level currentLevel;

    //stores an ArrayList containing the distance between all obstacles being displayed and the player
    public ArrayList<Float> distanceBetweenObstaclesAndPlayer;

    //stores an ArrayList containing the distance between all obstacles being displayed and the rival
    public ArrayList<Float> distanceBetweenObstaclesAndRival;

    //stores the GameView object being used
    public GameView gameView;



    //This is run before anything else, so we can prepare things here

    /**
     * Constuctor for TheGame
     *
     * Creates a new instance of player and sets it's position to -100, -100
     * sets the player_obj.image Bitmap to the player image
     *
     * Creates a new instance of rival and sets it's position to -100, -100
     * sets the rival_obj.image Bitmap to a random rival image
     *
     * @param gameView
     */
    public TheGame(GameView gameView) {
        //House keeping
        super(gameView);

        Random rand = new Random(); //creates random object rand
        this.gameView = gameView; //sets gameView to input parameter gameView

        player_obj = new Player(); //instantiates player
        player_obj.setX(-100); //sets player's x coord to -100
        player_obj.setY(-100); //sets player's y coord to -100
        player_obj.setXSpeed(0);
        player_obj.setYSpeed(0);
        player_obj.image = BitmapFactory.decodeResource
                (gameView.getContext().getResources(),
                        R.drawable.cowboy_l); //sets player_obj.image to cowboy_l

        rival_obj = new Rival(); //instantiates rival
        rival_obj.setX(-100); //sets rival's x coord to -100
        rival_obj.setY(-100); //sets rival's y coord to -100
        rival_obj.setXSpeed(0);
        rival_obj.setYSpeed(0);
        int rivalSpriteNo = rand.nextInt(3); //sets rivalSpriteNo to a randomInteger between 0 and 2
        if (rivalSpriteNo == 0) {
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival1_l); //set rival_obj.image to rival1_l
        } else if (rivalSpriteNo == 1) {
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival2_l); //set rival_obj.image to rival2_l
        } else {
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival3_l); //set rival_obj.image to rival3_l
        }

        levelCount = 1; //sets levelCount to 1
        currentLevel = new Level(levelCount); //instantiates currentLevel with parameter 1
        if (levelCount != 3) {
            currentLevel.fetchFile(); //fetches file
            currentLevel.readFile(); //reads file, converting it into a matrix of integers
            currentLevel.interpretLines(); //converts matrix of integers into matrix of obstacle objects
        } else {
            currentLevel.randomiseLevel(); //randomises the level, storing it into a matrix of integers
            currentLevel.interpretLines(); //converts matrix of integers into matrix of obstacle objects
        }
    }


    //This is run before a new game (also after an old game)

    /**
     * setupBeginning() runs before the game is loaded
     *
     * The function sets the x and y coords of the player and rival in relation to the canvas dimensions
     *
     * The function creates a loads the level matrixes up, containing all the objects needed in the level
     * The function puts all the objects at coordinate -100, -100
     * The function applies a bitmap image to all objects that are needed in the level
     *
     * The function calls addObstacles(), which current line of obstacles in the middle of the screen
     *
     * @param level_no
     */
    @Override
    public void setupBeginning(int level_no) {

        player_obj.setX(mCanvasWidth / 2); //sets player's x coord to the middle of the canvas screen
        player_obj.setY(mCanvasHeight - (mCanvasHeight / 5)); //sets player's y coord to the bottom fifth of the screen

        rival_obj.setX(mCanvasWidth / 2); //sets rivals's x coord to the middle of the canvas screen
        rival_obj.setY(mCanvasHeight - (mCanvasHeight / 6)); //sets rival's y coord to the bottom sixth of the screen

        levelCount = level_no; //sets levelCount to 1
        currentLevel = new Level(levelCount); //instantiates currentLevel with parameter 1
        if (levelCount != 3) {
            currentLevel.fetchFile(); //fetches file
            currentLevel.readFile(); //reads file, converting it into a matrix of integers
            currentLevel.interpretLines(); //converts matrix of integers into matrix of obstacle objects
        } else {
            currentLevel.randomiseLevel(); //randomises the level, storing it into a matrix of integers
            currentLevel.interpretLines(); //converts matrix of integers into matrix of obstacle objects
        }

        for (int i = 0; i < 20; i++) { //for every row of the matrix
            for (int j = 0; j < 5; j++) { //for every column in the row
                if (currentLevel.levelLine_obj.get(i).get(j) != null) { //if the object at that element is not null
                    currentLevel.levelLine_obj.get(i).get(j).setX(-100); //set obstacle's x coord to -100
                    currentLevel.levelLine_obj.get(i).get(j).setY(-100); //set obstacle's y coord to -100
                    currentLevel.levelLine_obj.get(i).get(j).setXSpeed(0);
                    currentLevel.levelLine_obj.get(i).get(j).setYSpeed(0);
                    if (currentLevel.levelLine_obj.get(i).get(j).cactus) { //if obstacle is a cactus
                        currentLevel.levelLine_obj.get(i).get(j).image = BitmapFactory.decodeResource
                                (gameView.getContext().getResources(), R.drawable.cactus_l); //obstacle.image = cactus_l
                    } else if (currentLevel.levelLine_obj.get(i).get(j).tumble) { //if obstacle is a tumbleweed
                        currentLevel.levelLine_obj.get(i).get(j).image = BitmapFactory.decodeResource
                                (gameView.getContext().getResources(), R.drawable.tumbleweed); //obstacle.image = tumbleweed
                    }
                }
            }
        }

        addObstacles(); //calls addObstacles to add all obstacle of line 0 and 1 to the middle of the screen
    }

    /**
     * add all obstacle of line line_no and line_no + 1 to the middle of the screen
     */
    public void addObstacles() {
        for (int i = 0; i < 5; i++) { //for all index in lineLine.get(line_no)
            if (currentLevel.levelLine_obj.get(line_no).get(i) != null) {
                currentLevel.levelLine_obj.get(line_no).get(i).setX(mCanvasWidth / 5 * i); //set object's x to position 5th of canvas width * i
                currentLevel.levelLine_obj.get(line_no).get(i).setY(mCanvasHeight / 2); //set object's y to middle of screen
            }
            if (currentLevel.levelLine_obj.get(line_no + 1).get(i) != null) {
                currentLevel.levelLine_obj.get(line_no + 1).get(i).setX(mCanvasWidth / 5 * i); //set object's x to position 5th of canvas width
                currentLevel.levelLine_obj.get(line_no + 1).get(i).setY(
                        currentLevel.levelLine_obj.get(line_no).get(i).getY() +
                                currentLevel.levelLine_obj.get(line_no).get(i).image.getHeight() //set object's x to above the previous lines y coord
                );
            }

            if (currentLevel.levelLine_obj.get(line_no).get(i) != null) {
                float distance1 = (currentLevel.levelLine_obj.get(line_no).get(i).getX() - player_obj.getX()) *
                        (currentLevel.levelLine_obj.get(line_no).get(i).getX() - player_obj.getX()) +
                        (currentLevel.levelLine_obj.get(line_no).get(i).getY() - player_obj.getY()) *
                                (currentLevel.levelLine_obj.get(line_no).get(i).getY() - player_obj.getY());
                distanceBetweenObstaclesAndPlayer.add(i, distance1); //finds the distance between the player and obstacle for all items in the line

                float distance2 = (currentLevel.levelLine_obj.get(line_no).get(i).getX() - rival_obj.getX()) *
                        (currentLevel.levelLine_obj.get(line_no).get(i).getX() - rival_obj.getX()) +
                        (currentLevel.levelLine_obj.get(line_no).get(i).getY() - rival_obj.getY()) *
                                (currentLevel.levelLine_obj.get(line_no).get(i).getY() - rival_obj.getY());
                distanceBetweenObstaclesAndRival.add(i, distance2); //finds the distance between the rival and obstacle for all items in the line
            }
        }
    }

    /**
     * Draws all objects on the screen according to their x and y coordinates
     *
     * @param canvas
     */
    @Override
    protected void doDraw(Canvas canvas) {
        //If there isn't a canvas to do nothing
        //It is ok not understanding what is happening here
        if (canvas == null) return;

        //House keeping
        super.doDraw(canvas);


        canvas.drawBitmap(player_obj.image,
                player_obj.getX() - player_obj.image.getWidth() / 2,
                player_obj.getY() - player_obj.image.getHeight() / 2,
                null); //draws the bitmap image of the player on the screen

        canvas.drawBitmap(rival_obj.image,
                rival_obj.getX() - rival_obj.image.getWidth() / 2,
                rival_obj.getY() - rival_obj.image.getHeight() / 2,
                null); //draws the bitmap image of the rival on the screen

        for (int i = 0; i < 5; i++) { //for every item in the line
            if (currentLevel.levelLine_obj.get(line_no).get(i) != null) {
                canvas.drawBitmap(currentLevel.levelLine_obj.get(line_no).get(i).image,
                        currentLevel.levelLine_obj.get(line_no).get(i).getX() -
                                currentLevel.levelLine_obj.get(line_no).get(i).image.getWidth() / 2,
                        currentLevel.levelLine_obj.get(line_no).get(i).getY() -
                                currentLevel.levelLine_obj.get(line_no).get(i).image.getHeight() / 2,
                        null); //draws every obstacle in the lower line onto the screen
            }
            if (currentLevel.levelLine_obj.get(line_no + 1).get(i) != null) {
                canvas.drawBitmap(currentLevel.levelLine_obj.get(line_no + 1).get(i).image,
                        currentLevel.levelLine_obj.get(line_no + 1).get(i).getX() -
                                currentLevel.levelLine_obj.get(line_no + 1).get(i).image.getWidth() / 2,
                        currentLevel.levelLine_obj.get(line_no + 1).get(i).getY() -
                                currentLevel.levelLine_obj.get(line_no + 1).get(i).image.getHeight() / 2,
                        null); //draws every obstacle in the upper line onto the screen
            }
        }
    }


    //This is run whenever the phone is touched by the user

    /**
     * Moves the player's x value to the x coordinate of point the user touched
     *
     * @param x
     * @param y
     */
    @Override
    protected void actionOnTouch(float x, float y) {
        //Move the player to the x position of the touch
        player_obj.setX(x);
    }


    //This is run just before the game "scenario" is printed on the screen

    /**
     * Updates the game every second until, game state is set to WIN or LOSE
     *
     * This function interprets the rival's move choice from rival.chooseMove()
     *
     * This function decreases the rival's y position if it is above 1/6 the the canvas height
     *
     * This function swaps the image of the rival to it's right facing image if rival_obj.image == rival_l
     * This function swaps the image of the rival to it's left facing side if rival_obj.image == rival_r
     *
     * This function moves all obstacles down by 1
     *
     * This function calculates the new distances between the player and all obstacles, and the rival and all obstacles
     *
     * If the obstacle is a cactus
     * This function swaps the image of all objects to cactus_l if obstacle.image == cactus_r
     * This function swaps the image of all objects to cactus_r if obstacle.image == cactus_l
     *
     * This function saves the scores of the player to the database and sets the game state to LOSE if:
     * The distance between player and obstacles array contains a 0
     *
     * This function saves the scores of the rival to the database if:
     * The distance between rival and obstacles array contains a 0
     *
     * This function adds the y value of all obstacles to an array
     * This function checks the array to see if it contains the bottom of the level
     * If so:
     * This function puts all obstacles in the line to position -100, -100
     * This function updates the score of the player and rival, adding all the integers in the line
     * This function add new obstacles and increments line_no by 2
     *
     * This function adds all the score of the player and rival to the database if line_no = 20
     *
     * @param secondsElapsed
     */
    @Override
    protected void updateGame(float secondsElapsed) {

        int rivalChoice = rival_obj.chooseMove(); //calls rival_obj.chooseMove()
        switch (rivalChoice) {
            case 0:
                break;

            case 1:
                if (rival_obj.getX() != 0) { //prevent clipping through the bounderies of the canvas
                    rival_obj.setX(rival_obj.getX() - 10);
                } else {
                    rival_obj.setX(rival_obj.getX() + 10);
                }
                break;

            case 2:
                if (rival_obj.getX() != mCanvasWidth) { //prevent clipping through the bounderies of the canvas
                    rival_obj.setX(rival_obj.getX() + 10);
                } else {
                    rival_obj.setX(rival_obj.getX() - 10);
                }
                break;

            case 3:
                if (rival_obj.getY() < mCanvasHeight - (mCanvasHeight / 6)) { //don't jump if already in the air
                    if (rival_obj.getX() != 0) { //prevent clipping through the bounderies of the canvas
                        rival_obj.setX(rival_obj.getX() - 5);
                    } else {
                        rival_obj.setX(rival_obj.getX() + 5);
                    }
                } else {
                    if (rival_obj.getX() != 0) { //prevent clipping though the bounderies of the canvas
                        rival_obj.setY(rival_obj.getY() - 30);
                        rival_obj.setX(rival_obj.getX() - 5);
                    } else {
                        rival_obj.setY(rival_obj.getY() - 30);
                        rival_obj.setX(rival_obj.getX() + 5);
                    }
                }
                break;

            case 4:
                if (rival_obj.getY() < mCanvasHeight - (mCanvasHeight / 6)) { //don't jump if already in the air
                    if (rival_obj.getX() != 0) { //prevent clipping through the bounderies of the canvas
                        rival_obj.setX(rival_obj.getX() + 5);
                    } else {
                        rival_obj.setX(rival_obj.getX() - 5);
                    }
                } else {
                    if (rival_obj.getX() != 0) { //prevent clipping through the bounderies if the canvas
                        rival_obj.setY(rival_obj.getY() - 30);
                        rival_obj.setX(rival_obj.getX() + 5);
                    } else {
                        rival_obj.setY(rival_obj.getY() - 30);
                        rival_obj.setX(rival_obj.getX() - 5);
                    }
                }
                break;
        }

        if (rival_obj.getY() < mCanvasHeight - (mCanvasHeight / 6)) {
            rival_obj.setY(rival_obj.getY() + 1); //moves the rival down by 1 if above the ground
        }

        if (rival_obj.image == BitmapFactory.decodeResource(gameView.getContext().getResources(),
                R.drawable.rival1_l)) { //if rival image == rival1_l
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival1_r); //swap the rival image to rival1_r
        } else if (rival_obj.image == BitmapFactory.decodeResource(gameView.getContext().getResources(),
                R.drawable.rival2_l)) { //if rival image == rival2_l
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival2_r); //swap the rival image to rival2_r
        } else {
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival3_r); //swap the rival image to rival3_r
        }

        if (rival_obj.image == BitmapFactory.decodeResource(gameView.getContext().getResources(),
                R.drawable.rival1_r)) { //if rival image == rival1_r
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival1_l); //swap image to rival1_l
        } else if (rival_obj.image == BitmapFactory.decodeResource(gameView.getContext().getResources(),
                R.drawable.rival2_r)) { //if rival image == rival2_r
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival2_l); //swap image to rival2_l
        } else {
            rival_obj.image = BitmapFactory.decodeResource
                    (gameView.getContext().getResources(),
                            R.drawable.rival3_l); //swap image to rival3_l
        }

        int i;
        // move all obstacles
        for (i = 0; i < 5; i++) { //for all obstacles in line
            if (currentLevel.levelLine_obj.get(line_no).get(i) != null) {
                currentLevel.levelLine_obj.get(line_no).get(i).setY(
                        currentLevel.levelLine_obj.get(line_no).get(i).getY() + 1
                ); //move obstacle at index i of lower line down by 1
            }
            if (currentLevel.levelLine_obj.get(line_no + 1).get(i) != null) {
                currentLevel.levelLine_obj.get(line_no + 1).get(i).setY(
                        currentLevel.levelLine_obj.get(line_no + 1).get(i).getY() + 1
                ); //move obstacle at index i of upper line down by 1
            }
        }

        for (i = 0; i < 5; i++) {
            if (currentLevel.levelLine_obj.get(line_no).get(i) != null) {
                float distance1 = ((currentLevel.levelLine_obj.get(line_no).get(i).getX() - player_obj.getX()) *
                        (currentLevel.levelLine_obj.get(line_no).get(i).getX() - player_obj.getX())) +
                        ((currentLevel.levelLine_obj.get(line_no).get(i).getY() - player_obj.getY()) *
                                (currentLevel.levelLine_obj.get(line_no).get(i).getY() - player_obj.getY()));
                distanceBetweenObstaclesAndPlayer.add(i, distance1); //get the distances between all obstacles and the player

                float distance2 = ((currentLevel.levelLine_obj.get(line_no).get(i).getX() - rival_obj.getX()) *
                        (currentLevel.levelLine_obj.get(line_no).get(i).getX() - rival_obj.getX())) +
                        ((currentLevel.levelLine_obj.get(line_no).get(i).getY() - rival_obj.getY()) *
                                (currentLevel.levelLine_obj.get(line_no).get(i).getY() - rival_obj.getY()));
                distanceBetweenObstaclesAndRival.add(i, distance2); //get the distances between all obstacles and the rival
            }

        }

        for (int j = 0; j < 5; j++) {
            if (currentLevel.levelLine_obj.get(line_no).get(j) != null) {
                if (currentLevel.levelLine_obj.get(line_no).get(j).cactus) { //if the obstacle in lower line index i is a cactus
                    if (currentLevel.levelLine_obj.get(line_no).get(j).image == BitmapFactory.decodeResource
                            (gameView.getContext().getResources(), R.drawable.cactus_l)) { //if image is equal to cactus_l
                        currentLevel.levelLine_obj.get(line_no).get(j).image = BitmapFactory.decodeResource
                                (gameView.getContext().getResources(), R.drawable.cactus_r); //swap image with cactus_r
                    } else if (currentLevel.levelLine_obj.get(line_no).get(j).image == BitmapFactory.decodeResource
                            (gameView.getContext().getResources(), R.drawable.cactus_r)) { //if image is equal to cactus_r
                        currentLevel.levelLine_obj.get(line_no).get(j).image = BitmapFactory.decodeResource
                                (gameView.getContext().getResources(), R.drawable.cactus_l); //swap image with cactus_l
                    }
                }
            }

            if (currentLevel.levelLine_obj.get(line_no+1).get(j) != null) {
                if (currentLevel.levelLine_obj.get(line_no + 1).get(j).cactus) { //if the obstacle in upper line index i is a cactus
                    if (currentLevel.levelLine_obj.get(line_no + 1).get(j).image == BitmapFactory.decodeResource
                            (gameView.getContext().getResources(), R.drawable.cactus_l)) { //if image is equal to cactus_l
                        currentLevel.levelLine_obj.get(line_no + 1).get(j).image = BitmapFactory.decodeResource
                                (gameView.getContext().getResources(), R.drawable.cactus_r); //swap image with cactus_r
                    } else if (currentLevel.levelLine_obj.get(line_no + 1).get(j).image == BitmapFactory.decodeResource
                            (gameView.getContext().getResources(), R.drawable.cactus_r)) { //if image is equal to cactus_r
                        currentLevel.levelLine_obj.get(line_no + 1).get(j).image = BitmapFactory.decodeResource
                                (gameView.getContext().getResources(), R.drawable.cactus_l); //swap image with cactus_l
                    }
                }
            }
        }

        float zero = 0;
        if (distanceBetweenObstaclesAndPlayer.contains(zero)) { //if the array of distances between obstacles and player contains 0
            DatabaseManager database = new DatabaseManager(); //create new database object
            database.writeData("Player", player_obj.score); //writes the name player and player.score to the database
            database.writeData("Rival", rival_obj.score); //writes the name rival and rival.score to the database
            setState(GameThread.STATE_LOSE); //sets state to lose
        }

        ArrayList<Float> obstaclesYAxis = new ArrayList<Float>(); //creates a new array containing the y position of all obstacles in lower line

        for (i = 0; i < 5; i++) {
            if (currentLevel.levelLine_obj.get(line_no + 1).get(i) != null) {
                obstaclesYAxis.add(i, currentLevel.levelLine_obj.get(line_no + 1).get(i).getY()); //adds the obstacle's y coord to the array
            }
        }


        if (obstaclesYAxis.contains(mCanvasHeight)) { //if any obstacles have reached the end
            for (i = 0; i < 5; i++) {
                if (currentLevel.levelLine_obj.get(line_no + 1).get(i) != null) {
                    //set the position of the obstacles to -100, -100
                    currentLevel.levelLine_obj.get(line_no + 1).get(i).setY(-100);
                    currentLevel.levelLine_obj.get(line_no).get(i).setX(-100);
                }
            }

            int sumOfScores = 0;
            for (i = 0; i < 5; i++) {
                sumOfScores += currentLevel.levelLine.get(line_no).get(i); //adds the levelLine.get(line_no).get(i) value to the score
                sumOfScores += currentLevel.levelLine.get(line_no + 1).get(i); //adds the levelLine.get(line_no + 1).get(i) value to the score
            }


            player_obj.score += sumOfScores; //adds sum of scores to the player_obj.score
            rival_obj.score += sumOfScores; //adds sum of scores to the rival_obj.score
            setScore(player_obj.score); //sets the score to the updated score
            line_no += 2; //increases line_no by 2
            addObstacles(); //adds the next set of obstacles
        }


        if (line_no == 20) { //if line_no == 0
            if (!distanceBetweenObstaclesAndPlayer.contains(zero)) { //if the distanceBetweenObstaclesAndPlayer array does not contain zero
                DatabaseManager database = new DatabaseManager(); //creates new database object
                database.writeData("Player", player_obj.score); //uploads player score
                database.writeData("Rival", rival_obj.score); //uploads rival score
                setState(GameThread.STATE_WIN); //sets state to WIN
            }
        }
    }

}

// This file is part of the course "Begin Programming: Build your first mobile game" from futurelearn.com
// Copyright: University of Reading and Karsten Lundqvist
// It is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// It is is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// 
// You should have received a copy of the GNU General Public License
// along with it.  If not, see <http://www.gnu.org/licenses/>.
