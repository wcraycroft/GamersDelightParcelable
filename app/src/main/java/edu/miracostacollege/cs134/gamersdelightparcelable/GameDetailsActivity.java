package edu.miracostacollege.cs134.gamersdelightparcelable;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import edu.miracostacollege.cs134.gamersdelightparcelable.model.Game;


public class GameDetailsActivity extends AppCompatActivity {

    private static final String TAG = GameDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        ImageView gameDetailsImageView = findViewById(R.id.gameDetailsImageView);
        TextView gameDetailsNameTextView = findViewById(R.id.gameDetailsNameTextView);
        TextView gameDetailsDescriptionTextView = findViewById(R.id.gameDetailsDescriptionTextView);
        RatingBar gameDetailsRatingBar = findViewById(R.id.gameDetailsRatingBar);

        Intent detailsIntent = getIntent();
        // Retrieve game object from intent
        Game game = detailsIntent.getParcelableExtra("Selected Game");


        AssetManager am = getAssets();
        try {
            InputStream stream = am.open(game.getImageName());
            Drawable image = Drawable.createFromStream(stream, game.getName());
            gameDetailsImageView.setImageDrawable(image);
        }
        catch (IOException ex)
        {
            Log.e(TAG, "Error loading: " + game.getImageName(), ex);
        }

        gameDetailsNameTextView.setText(game.getName());
        gameDetailsDescriptionTextView.setText(game.getDescription());
        gameDetailsRatingBar.setRating(game.getRating());
    }
}
