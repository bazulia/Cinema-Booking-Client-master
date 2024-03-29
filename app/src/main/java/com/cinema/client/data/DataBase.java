package com.cinema.client.data;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cinema.client.data.movie.Movie;
import com.cinema.client.ui.adapters.MoviesAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class DataBase {

    List<Movie> movies = new ArrayList<>();
    List<Movie> soon = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    int j = 0;


    public void createMovie() {
        db.collection("Movie").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Movie movie = documentSnapshot.toObject(Movie.class);
                    String name = movie.getTitle();
                    String p = movie.getPosterUrl();
                    System.out.println(name);
                    System.out.println(p);

                    Intent i = new Intent("NEW_MOVIES");

                    movies.add(movie);

                    Bundle b = new Bundle();

                    b.putSerializable("movies", movies.get(j));
                    i.putExtra("bundle",b);

                    db.getApp().getApplicationContext().sendBroadcast(i);
                    System.out.println(movies);
                    j++;

                }

            }
        });
    }

    public List<Movie> getMovies() {
        createMovie();
        System.out.println(movies.isEmpty());
        return movies;
    }

    public void createSoon() {
        db.collection("Soon").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Movie movie = documentSnapshot.toObject(Movie.class);
                    String name = movie.getTitle();
                    String p = movie.getPosterUrl();
                    System.out.println(name);
                    System.out.println(p);

                    Intent i = new Intent("SOON_MOVIES");

                    soon.add(movie);

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("soon", soon.get(j));
                    i.putExtra("bundle2",bundle);

                    db.getApp().getApplicationContext().sendBroadcast(i);
                    System.out.println(soon);
                    j++;

                }

            }
        });
    }

    public void createUser(String email, String nick){
        Map<String, Object> user = new HashMap<>();
        user.put("nickname", nick);
        user.put("email", email);
        user.put("phone", "");
        user.put("card", "");
        user.put("favorite", "");
        user.put("ticket", "");

        db.collection("Users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

}
