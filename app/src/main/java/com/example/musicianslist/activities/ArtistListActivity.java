package com.example.musicianslist.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.musicianslist.R;
import com.example.musicianslist.adapters.ArtistAdapter;
import com.example.musicianslist.database.DatabaseConnector;
import com.example.musicianslist.models.Artist;
import com.example.musicianslist.utils.ApiInterface;
import com.example.musicianslist.utils.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ArtistListActivity extends MainActivity {

    private List<Artist> artistList;

    private RecyclerView rvArtist;

    private ArtistAdapter adapter;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Activity_list", "onCreate");

        gson = new Gson();
        artistList = new ArrayList<>();
        bindViews();
        setAdapter();
        getArtistList();

        if (isOnline()) {
            getArtistList();
        }
        else {
            new GetAllArtists().execute();
        }
    }

    private void bindViews() {
        rvArtist = (RecyclerView)findViewById(R.id.rvArtist);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvArtist.setLayoutManager(manager);
    }

    private void setAdapter() {
        adapter = new ArtistAdapter(artistList);
        rvArtist.setAdapter(adapter);
    }

    private void getArtistList() {
        RestClient client = new RestClient();

        Call<List<Artist>> call = client.getApiInterface().getAllMusicianList("");

        Log.d("Artist_request", call.toString());

        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Response<List<Artist>> response, Retrofit retrofit) {
                Log.d("Artist_response", response.isSuccess() + " " + response.message());
                if (response.isSuccess()) {
                    artistList.addAll(response.body());
                    Log.d("Artist_list", artistList.toString());
                    new SaveArtist(artistList);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private class SaveArtist extends AsyncTask<Void, Void, Void> {

        List<Artist> artists;

        DatabaseConnector connector = new DatabaseConnector(ArtistListActivity.this);

        public SaveArtist(List<Artist> artists) {
            this.artists = artists;
        }

        @Override
        protected Void doInBackground(Void... params) {
            connector.open();
            Log.d("Artists_bg", artists.toString());
            for (Artist a : artists) {
                connector.createArtist(a);
            }
            connector.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private class GetAllArtists extends AsyncTask<Void, Void, Void> {

        DatabaseConnector connector = new DatabaseConnector(ArtistListActivity.this);

        @Override
        protected Void doInBackground(Void... params) {
            connector.open();
            artistList = connector.getAllArtists();
            Log.d("All_artists", artistList.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            connector.close();
            setAdapter();
            if (artistList.isEmpty()) {
                Toast.makeText(ArtistListActivity.this, "No data, please check internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
