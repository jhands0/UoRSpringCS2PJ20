package uk.ac.reading.sis05kol.GameFramework;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class handles all data about the level that the user is on
 *
 * This class fetches and reads level files containing the level
 *
 * This class can randomly generate a level
 *
 * This class interprets the level from the file, creating a matrix of obstacle objects
 */
public class Level {
    public int levelNo;
    public File levelFile;
    public String fileName = "Level";
    public ArrayList<Integer> line;
    public ArrayList<Obstacle> line_obj;
    public ArrayList<ArrayList<Integer>> levelLine;
    public ArrayList<ArrayList<Obstacle>> levelLine_obj;

    /**
     *
     * The Constructor for the level class
     * This function instantiates all line, line_obj, levelLine and levelLine_obj ArrayList attributes
     *
     * @param number This is the current level number, which the leveNo attribute is set to
     */
    Level(int number) {
        levelNo = number;
        line = new ArrayList<Integer>();
        line_obj = new ArrayList<Obstacle>();
        levelLine = new ArrayList<ArrayList<Integer>>();
        levelLine_obj = new ArrayList<ArrayList<Obstacle>>();
    }

    /**
     * Create a filename from the filename and levelno attributes
     *
     * Then fetches the file and saves it to attribute levelFile
     */
    public void fetchFile() {
        String tempName = fileName + Integer.toString(levelNo) + ".txt";
        levelFile = new File("levels", tempName);
    }

    /**
     *
     * Uses a scanner to scan the levelFile
     *
     * Saves 5 integers to an index in the line array
     *
     * Saves the line to an index in the levelLine array
     */
    public void readFile() {
        try {
            Scanner fileReader = new Scanner(levelFile);
            int count = 0;
            while (fileReader.hasNextLine()) {
                for (int i = 0; i < 5; i++) {
                    line.add(i, fileReader.nextInt()); //gets the next integer in the file and adds it to line
                }
                levelLine.add(count, line); //adds the line to index count of levelLine
                count += 1;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * interpretLines() creates Obstacle objects from the contents of levelLine
     *
     * Saves these objects in line_obj, which all gets added to levelLine_obj
     */
    public void interpretLines() {
        ArrayList<Integer> currentLine;
        for (int i = 0; i < 20; i++) {
            currentLine = levelLine.get(i);
            for (int j = 0; j < 5; j++) {
                switch (currentLine.get(j)) {
                    //if line.get(j) == 0, add nothing to line_obj
                    case 0:
                        line_obj.add(j, null);
                        break;
                    case 1:
                        //if line.get(j) == 1, add a cactus to line_obj
                        Obstacle cactus_obj = new Obstacle(true, false);
                        line_obj.add(j, cactus_obj);
                        break;

                    case 2:
                        //if line.get(j) == 2, add a tumbleweed to line_obj
                        Obstacle tumble_obj = new Obstacle(false, true);
                        line_obj.add(j, tumble_obj);
                        break;
                }
            }
            // add the created line_obj to levelLine_obj
            levelLine_obj.add(i, line_obj);
        }
    }

    /**
     * randomiseLevel() randomly generates 20 line arrays to add to leveLine
     *
     * each line contains either 0, 1 or 2
     *
     * every other line is dependent on the line in front of it, so each line that appears on screen has an upper and lower
     *
     * e.g. a cactus on the lower line can only have a cactus or empty space above it.
     *
     * All these lines are added to levelLine
     */
    public void randomiseLevel() {
        Random rand = new Random();
        for (int i = 0; i < 20; i+=2) { //for every line in the level
            for (int j = 0; j < 5; j++) { //for every number on the lower line
                int randInt = rand.nextInt(3); //type of obstacle: nothing, cactus or tumbleweed
                line.add(j, randInt); // add the number to the line
                }
            levelLine.add(i, line); //add the lower line to the levelLine

            for (int k = 0; k < 5; k++) { // for every number on the upper line
                switch (levelLine.get(i).get(k)) {
                    case 0:
                        //if the lower line index k is 0
                        line.add(k, 0); //add 0 to upper line index k
                        break;

                    case 1:
                        //if the lower line index k is 1
                        int upperObstacle = rand.nextInt(2); //generate number between 0 and 2
                        line.add(k, upperObstacle); //add that number to the upper line index k
                        break;

                    case 2:
                        //if the lower line index k is 2
                        int upperObstacle2 = rand.nextInt(2); //generate number between 0 and 2
                        if (upperObstacle2 == 1) {
                            line.add(k, 2); //add 2 to the upper line index k
                        }
                        else {
                            line.add(k, 0); //add 0 to the upper line index k
                        }
                        break;
                }
            }
            levelLine.add(i+1, line); //add the upper line to levelLine
        }
    }

}
