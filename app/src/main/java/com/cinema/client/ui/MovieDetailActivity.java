package com.cinema.client.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.cinema.client.R;
import com.cinema.client.data.movie.Movie;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    public static final String ARG_KEY_MOVIE_BUNDLE = "movie_key_bundle";
    public static final String ARG_KEY_MOVIE = "movie_key";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageView imgMoviePoster;
    private TextView tvMovieName;
    private TextView tvMovieAbout;
    private TextView tvMovieInfo;
    private TextView tvMovieLabel;
    private Button btnBuyTicket;
    private VideoView videoViewTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_page);

        mAuth = FirebaseAuth.getInstance();

        imgMoviePoster = (ImageView)findViewById(R.id.movie_poster);
        tvMovieName = (TextView)findViewById(R.id.movie_name);
        tvMovieAbout = (TextView)findViewById(R.id.about_movie);
        tvMovieInfo = (TextView)findViewById(R.id.movie_info);
        tvMovieLabel = (TextView)findViewById(R.id.movie_trailer_label);
        btnBuyTicket = (Button)findViewById(R.id.movie_buy_ticket);
        videoViewTrailer = (VideoView)findViewById(R.id.movie_trailer_video);

        Movie movie = (Movie)getIntent().getBundleExtra(ARG_KEY_MOVIE_BUNDLE).getSerializable(ARG_KEY_MOVIE);

        if(movie == null){
            Toast.makeText(this,"Can`t get movie",Toast.LENGTH_SHORT).show();
            finish();
        }

        Picasso.get().load(movie.getPosterUrl()).into(imgMoviePoster);
        tvMovieName.setText(movie.getTitle());
        tvMovieAbout.setText(movie.getShortDescription());
        tvMovieInfo.setText(movie.getFullDescription());
    }

}
