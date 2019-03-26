package edu.miracostacollege.cs134.gamersdelightparcelable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

import edu.miracostacollege.cs134.gamersdelightparcelable.model.DBHelper;
import edu.miracostacollege.cs134.gamersdelightparcelable.model.Game;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DBHelper db;
    private List<Game> gamesList;
    private GameListAdapter gamesListAdapter;
    private ListView gamesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);

        /* Only need to run this code once to "seed" the database with some initial values (games)
        // This could also be seeded from a JSON data source and put into the database.
        db.addGame(new Game("League of Legends", "Multiplayer online battle arena", 4.5f, "lol.png"));
        db.addGame(new Game("Dark Souls III", "Action role-playing", 4.0f, "ds3.png"));
        db.addGame(new Game("The Division", "Single player experience", 3.5f, "division.png"));
        db.addGame(new Game("Doom FLH", "First person shooter", 2.5f, "doomflh.png"));
        db.addGame(new Game("Battlefield 1", "Single player campaign", 5.0f, "battlefield1.png"));
        */

        gamesList = db.getAllGames();
        gamesListAdapter = new GameListAdapter(this, R.layout.game_list_item, gamesList);

        gamesListView = findViewById(R.id.gameListView);
        gamesListView.setAdapter(gamesListAdapter);

    }

    public void viewGameDetails(View view) {
            Game selectedGame = (Game) view.getTag();

            Intent detailsIntent = new Intent(this, GameDetailsActivity.class);
            detailsIntent.putExtra("Selected Game", selectedGame);

            startActivity(detailsIntent);
    }

    public void addGame(View view)
    {

        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        RatingBar ratingBar = findViewById(R.id.gameRatingBar);

        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description))
        {
            Toast.makeText(this, "Both name and description of the game must be provided.", Toast.LENGTH_LONG);
            return;
        }
        float rating = ratingBar.getRating();
        Game newGame = new Game(name, description, rating);

        // Add the new game to the database to ensure it is persisted.
        db.addGame(newGame);
        gamesListAdapter.add(newGame);
        // Clear all the entries (reset them)
        nameEditText.setText("");
        descriptionEditText.setText("");
        ratingBar.setRating(0.0f);
    }

    public void clearAllGames(View view)
    {
        gamesList.clear();
        // Permanently delete games from the database, buh bye
        db.deleteAllGames();
        gamesListAdapter.notifyDataSetChanged();
    }

}
