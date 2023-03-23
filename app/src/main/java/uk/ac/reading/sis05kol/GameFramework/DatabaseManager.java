package uk.ac.reading.sis05kol.GameFramework;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Class DatabaseManager allows this class to upload scores to the firebase database
 *
 * attribute database stores a reference of the instance of the database
 */
public class DatabaseManager {
    private DatabaseReference database;

    /**
     * Constructor gets a reference of the instance of the database and saves it to attribute database
     */
    public DatabaseManager() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Writes the name and score inputted to the database
     *
     * @param name the key of the record to be written
     * @param score the value of the record to be written
     */
    public void writeData(String name, int score) {
        database.child("data").child(name).setValue(score);
    }
}