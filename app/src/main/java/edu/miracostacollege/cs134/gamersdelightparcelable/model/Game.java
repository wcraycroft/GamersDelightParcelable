package edu.miracostacollege.cs134.gamersdelightparcelable.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The <code>Game</code> class maintains information about a video game,
 * including its id number, name, description, rating and image name.
 *
 * @author Michael Paulding
 */
public class Game implements Parcelable {

    //Member variables
    private long mId;
    private String mName;
    private String mDescription;
    private float mRating;
    private String mImageName;

    /**
     * Creates a default <code>Game</code> with an id of -1, empty description,
     * rating of 0.0f and default image name of avatar.png.
     */
    public Game()
    {
        this(-1, "", "", 0.0f, "avatar.png");
    }

    /**
     * Creates a new <code>Game</code> from its id, description and status.
     * @param description The game description
     * @param rating The game rating (out of 5.0)
     */
    public Game(String name, String description, float rating) {
        this(-1, name, description, rating, "avatar.png");
    }

    /**
     * Creates a new <code>Game</code> from its id, description and status.
     * @param description The game description
     * @param rating The game rating (out of 5.0)
     * @param imageName The image file name of the game
     */
    public Game(String name, String description, float rating, String imageName) {
        this(-1, name, description, rating, imageName);
    }

    /**
     * Creates a new <code>Game</code> from its id, description and status.
     * @param id The game id
     * @param description The game description
     * @param rating The game rating (out of 5.0)
     * @param imageName The image file name of the game
     */
    public Game(long id, String name, String description, float rating, String imageName) {
        mId = id;
        mName = name;
        mDescription = description;
        mRating = rating;
        mImageName = imageName;
    }

    /**
     * Gets the unique id of the <code>Game</code>.
     * @return The unique id
     */
    public long getId() {
        return mId;
    }

    /**
     * Sets the unique id of the <code>Game</code>.
     * @param id The unique id
     */
    public void setId(long id) {
        mId = id;
    }

    /**
     * Gets the name of the <code>Game</code>.
     * @return The game name
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the name of the <code>Game</code>.
     * @param name The new game name.
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * Gets the description of the <code>Game</code>.
     * @return The game's description
     */
    public String getDescription () {
        return mDescription;
    }

    /**
     * Sets the description of the <code>Game</code>.
     * @param desc The game's description
     */
    public void setDescription (String desc) {
        mDescription = desc;
    }

    /**
     * Gets the rating of the <code>Game</code>.
     * @return The rating (number of stars) of the game.
     */
    public float getRating() {
        return mRating;
    }

    /**
     * Sets the rating of the <code>Game</code>.
     * @param rating The rating (number of stars) of the game.
     */
    public void setRating(float rating) {
        mRating = rating;
    }

    /**
     * Gets the image file name of the <code>Game</code>.
     * @return The image file name (e.g. lol.png) of the game.
     */
    public String getImageName() {
        return mImageName;
    }

    /**
     * Sets the image file name of the <code>Game</code>.
     * @param imageName The image file name (e.g. lol.png) of the game.
     */
    public void setImageName(String imageName) {
        mImageName = imageName;
    }

    /**
     * A method for displaying a <code>Game</code> as a String in the form:
     *
     * Game{id=1, Name=League of Legends, Description=Multiplayer online battle arena game,
     * Rating=4.5, ImageName=lol.png}
     *
     * @return The formatted String
     */
    @Override
    public String toString() {
        return "Game{" +
                "Id=" + mId +
                ", Name='" + mName + '\'' +
                ", Description='" + mDescription + '\'' +
                ", Rating=" + mRating +
                ", ImageName='" + mImageName + '\'' +
                '}';
    }

    /**
     * Describe any special contents of this object (Files, video, audio)
     * @return Zero, unless there are any special contents
     */
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(mId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeFloat(mRating);
        dest.writeString(mImageName);

    }

    // Mechanism to create a new Game object from a Parcel
    // Private constructor to create a new Game from Parcel
    private Game(Parcel parcel)
    {
        mId = parcel.readLong();
        mName = parcel.readString();
        mDescription = parcel.readString();
        mRating = parcel.readFloat();
        mImageName = parcel.readString();

    }

    public static final Parcelable.Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        // Allows the creation of an array of Game objects from JSON file.
        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
