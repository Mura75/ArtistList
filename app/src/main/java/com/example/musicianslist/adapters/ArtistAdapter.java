package com.example.musicianslist.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.musicianslist.R;
import com.example.musicianslist.activities.ArtistActivity;
import com.example.musicianslist.models.Artist;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Murager on 24.04.2016.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{

    private List<Artist> artistList;

    public ArtistAdapter(List<Artist> artistList) {
        this.artistList = artistList;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_artist, parent, false);
        ArtistViewHolder holder = new ArtistViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.bindArtist(artist);

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        CardView cvArtist;
        ImageView ivCoverSmall;
        TextView tvName;
        TextView tvGenres;
        ProgressBar progressBar;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            cvArtist = (CardView)itemView.findViewById(R.id.cvArtist);
            ivCoverSmall = (ImageView)itemView.findViewById(R.id.ivCoverSmall);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvGenres = (TextView)itemView.findViewById(R.id.tvGenres);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
        }

        public void bindArtist(final Artist artist) {
            tvName.setText(artist.getName());
            tvGenres.setText(artist.getGenresStr());
            Log.d("Small_img_url", artist.getCover().getSmall());
            Picasso.with(context)
                    .load(artist.getCover().getSmall())
                    .into(ivCoverSmall, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });

            cvArtist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ArtistActivity.class);
                    intent.putExtra("artist", new Gson().toJson(artist));
                    context.startActivity(intent);
                }
            });
        }
    }
}
