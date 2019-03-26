package edu.miracostacollege.cs134.gamersdelightparcelable.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    public static final String DATABASE_NAME = "GamersDelight";
    private static final String DATABASE_TABLE = "Games";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_RATING = "rating";
    private static final String FIELD_IMAGE_NAME = "image_name";


    public DBHelper(Context context){
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate (SQLiteDatabase database){
        String table = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_NAME + " TEXT, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_RATING + " REAL, "
                + FIELD_IMAGE_NAME + " TEXT" + ")";
        database.execSQL (table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    //********** DATABASE OPERATIONS:  ADD, UPDATE, EDIT, DELETE

    public void addGame(Game game) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //ADD KEY-VALUE PAIR INFORMATION FOR THE GAME NAME
        values.put(FIELD_NAME, game.getName());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE GAME DESCRIPTION
        values.put(FIELD_DESCRIPTION, game.getDescription());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE GAME RATING
        values.put(FIELD_RATING, game.getRating());

        //ADD KEY-VALUE PAIR INFORMATION FOR THE GAME RATING
        values.put(FIELD_IMAGE_NAME, game.getImageName());

        // INSERT THE ROW IN THE TABLE
        long id = db.insert(DATABASE_TABLE, null, values);

        // UPDATE THE GAME WITH THE NEWLY ASSIGNED ID FROM THE DATABASE
        game.setId(id);

        // CLOSE THE DATABASE CONNECTION
        db.close();
    }

    public List<Game> getAllGames() {
        List<Game> gameList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        // A cursor is the results of a database query (what gets returned)
        Cursor cursor = database.query(
                DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_NAME, FIELD_DESCRIPTION, FIELD_RATING, FIELD_IMAGE_NAME},
                null,
                null,
                null, null, null, null );

        //COLLECT EACH ROW IN THE TABLE
        if (cursor.moveToFirst()){
            do {
                Game game =
                        new Game(cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getFloat(3),
                                cursor.getString(4));
                gameList.add(game);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return gameList;
    }

    public void deleteGame(Game game){
        SQLiteDatabase db = getWritableDatabase();

        // DELETE THE TABLE ROW
        db.delete(DATABASE_TABLE, KEY_FIELD_ID + " = ?",
                new String[] {String.valueOf(game.getId())});
        db.close();
    }

    public void deleteAllGames()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }

    public void updateGame(Game game){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, game.getName());
        values.put(FIELD_DESCRIPTION, game.getDescription());
        values.put(FIELD_RATING, game.getRating());
        values.put(FIELD_IMAGE_NAME, game.getImageName());

        db.update(DATABASE_TABLE, values, KEY_FIELD_ID + " = ?",
                new String[]{String.valueOf(game.getId())});
        db.close();
    }

    public Game getGame(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_NAME, FIELD_DESCRIPTION, FIELD_RATING, FIELD_IMAGE_NAME},
                KEY_FIELD_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null );

        Game game = null;
        if (cursor != null) {
            cursor.moveToFirst();

            game = new Game(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getFloat(3),
                    cursor.getString(4));

            cursor.close();
        }
        db.close();
        return game;
    }





}
