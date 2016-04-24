package com.example.musicianslist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musicianslist.R;
import com.example.musicianslist.models.Artist;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class ArtistActivity extends AppCompatActivity {

    private Artist artist;

    private Toolbar toolbar;
    private ImageView ivArtistCover;
    private TextView tvArtistName;
    private TextView tvArtistGenres;
    private TextView tvArtistAlbums;
    private TextView tvArtistTracks;
    private TextView tvArtistLink;
    private TextView tvArtistDescriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        artist = new Gson().fromJson(getIntent().getStringExtra("artist"), Artist.class);
        bindViews();
        initActionBar();
        setValues(artist);
    }

    private void bindViews() {
        toolbar = (Toolbar)findViewById(R.id.toolbarArtist);
        ivArtistCover = (ImageView) findViewById(R.id.ivArtistCover);
        tvArtistName = (TextView) findViewById(R.id.tvArtistName);
        tvArtistGenres = (TextView) findViewById(R.id.tvArtistGenres);
        tvArtistAlbums = (TextView) findViewById(R.id.tvArtistAlbums);
        tvArtistTracks = (TextView) findViewById(R.id.tvArtistTracks);
        tvArtistLink = (TextView) findViewById(R.id.tvArtistLink);
        tvArtistDescriptions = (TextView) findViewById(R.id.tvArtistDescription);
        tvArtistLink.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    private void setValues(Artist artist) {
        tvArtistTracks.setText("Tracks : " + artist.getTracks());
        tvArtistAlbums.setText("Albums : " + artist.getAlbums());
        tvArtistDescriptions.setText(artist.getDescription());
        tvArtistGenres.setText(artist.getGenres().toString());
        tvArtistName.setText(artist.getName());
        tvArtistLink.setText("Link: " + artist.getLink());
        Picasso.with(this).load(artist.getCover().getBig()).into(ivArtistCover);
    }
}
