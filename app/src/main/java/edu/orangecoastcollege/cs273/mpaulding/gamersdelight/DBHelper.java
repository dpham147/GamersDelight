package edu.orangecoastcollege.cs273.mpaulding.gamersdelight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    static final String DATABASE_NAME = "GamersDelight";
    private static final String DATABASE_TABLE = "Games";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_RATING = "rating";
    private static final String FIELD_IMAGE_NAME = "image_name";


    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        // TODO:  Define the SQL statement to create a new table with the fields above.
        // TODO:  Primary key should auto increment
        // TODO:  Execute the SQL statement

        String table = "CREATE TABLE" + DATABASE_TABLE + "(" +
                KEY_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIELD_NAME + " TEXT, " +
                FIELD_DESCRIPTION + " TEXT, " +
                FIELD_RATING + " REAL, " +
                FIELD_IMAGE_NAME + " TEXT" + ")";
        database.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        // TODO:  Execute the SQL statement to drop the table
        // TODO:  Recreate the database
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //********** DATABASE OPERATIONS:  ADD, GETALL, EDIT, DELETE

    public void addGame(Game game) {

        // TODO:  Write the code to add a new game to the database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, game.getName());
        values.put(FIELD_DESCRIPTION, game.getDescription());
        values.put(FIELD_RATING, game.getRating());
        values.put(FIELD_IMAGE_NAME, game.getImageName());
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }

    public ArrayList<Game> getAllGames() {
        ArrayList<Game> gameList = new ArrayList<>();

        // TODO:  Write the code to get all games from the database and return in ArrayList
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Game newGame = new Game(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getFloat(3), cursor.getString(4));
                gameList.add(newGame);
            } while (cursor.moveToNext());
        }
        db.close();

        return gameList;
    }

    public void deleteAllGames()
    {
        // TODO:  Write the code to delete all games from the database
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }

    public void updateGame(Game game){

        // TODO:  Write the code to update a game in the database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_NAME, game.getName());
        values.put(FIELD_DESCRIPTION, game.getDescription());
        values.put(FIELD_RATING, game.getRating());
        values.put(FIELD_IMAGE_NAME, game.getImageName());

        db.update(DATABASE_TABLE, values, KEY_FIELD_ID + " = ?",  new String[]{String.valueOf(game.getId())});
    }

    public Game getGame(int id) {

        // TODO:  Write the code to get a specific game from the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_TABLE, null, KEY_FIELD_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        // TODO:  Replace the return null statement below with the game from the database.
        Game game = new Game(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getFloat(3), cursor.getString(4));
        db.close();
        return game;
    }





}
