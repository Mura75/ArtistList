package com.example.musicianslist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.musicianslist.models.Artist;
import com.example.musicianslist.models.Cover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Murager on 24.04.2016.
 */
public class DatabaseConnector {

    private static final String DATABASE_NAME = "artist_database";
    private static final String TABLE = "artists";
    private static int DATABASE_VERSION = 5;

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ALBUMS = "albums";
    private static final String DESCRIPTIONS = "descriptions";
    private static final String LINK = "link";
    private static final String TRACKS = "tracks";
    private static final String GENRES = "genres";
    private static final String BIG_IMAGE = "big_image";
    private static final String SMALL_IMAGE = "small_image";

    private static final String CREATE_DATABASE =
            "CREATE TABLE if not exists " + TABLE +
                    "(" + ID + " integer primary key autoincrement," +
                    NAME + " TEXT," +
                    ALBUMS + " INTEGER, " +
                    DESCRIPTIONS + " TEXT, " +
                    LINK + " TEXT, " +
                    TRACKS + " INTEGER, " +
                    GENRES + " TEXT, " +
                    BIG_IMAGE + " TEXT, " +
                    SMALL_IMAGE + " TEXT);";


    private SQLiteDatabase database;

    private DatabaseHelper helper;

    public DatabaseConnector(Context context) {
        helper = new DatabaseHelper(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }


    public void createArtist(Artist artist) {
        open();
        ContentValues newValue = new ContentValues();

        newValue.put(NAME, artist.getName());
        newValue.put(ID, artist.getId());
        newValue.put(ALBUMS, artist.getAlbums());
        newValue.put(TRACKS, artist.getTracks());
        newValue.put(DESCRIPTIONS, artist.getDescription());
        newValue.put(LINK, artist.getLink());
        newValue.put(GENRES, artist.getGenresStr());
        newValue.put(BIG_IMAGE, artist.getCover().getBig());
        newValue.put(SMALL_IMAGE, artist.getCover().getSmall());

        database.insert(TABLE, null, newValue);
        Log.d("Artist_created", "Created");
        close();
    }


    public Artist getArtistById(int id) {
        open();
        Artist artist = null;
        String[] columns = new String[]{ID, NAME, ALBUMS, DESCRIPTIONS, LINK,
                                        TRACKS, GENRES, BIG_IMAGE, SMALL_IMAGE};

        Cursor cursor = database.query(TABLE,
                columns,
                ID + "=?",
                new String[] { String.valueOf(id) },
                null,
                null,
                null,
                null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        artist = new Artist();
        artist.setId(cursor.getInt(0));
        artist.setName(cursor.getString(1));
        artist.setAlbums(cursor.getInt(2));
        artist.setDescription(cursor.getString(3));
        artist.setLink(cursor.getString(4));
        artist.setTracks(cursor.getInt(5));
        artist.setGenresStr(cursor.getString(6));

        Cover cover = new Cover();
        cover.setBig(cursor.getString(7));
        cover.setSmall(cursor.getString(8));

        artist.setCover(cover);

        return artist;
    }

    public List<Artist> getAllArtists() {
        List<Artist> artistList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE;
        Cursor cursor = database.rawQuery(selectQuery, null);
        open();
        while (cursor.moveToNext()) {
            Artist artist = new Artist();
            artist.setId(cursor.getInt(0));
            artist.setName(cursor.getString(1));
            artist.setAlbums(cursor.getInt(2));
            artist.setDescription(cursor.getString(3));
            artist.setLink(cursor.getString(4));
            artist.setTracks(cursor.getInt(5));
            artist.setGenresStr(cursor.getString(6));

            Cover cover = new Cover();
            cover.setBig(cursor.getString(7));
            cover.setSmall(cursor.getString(8));

            artist.setCover(cover);
            artistList.add(artist);
        }

        return artistList;
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context,
                              String name,
                              SQLiteDatabase.CursorFactory factory,
                              int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion,
                              int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }

}
