package com.frostox.livechess.activities;

import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.frostox.livechess.R;
import com.frostox.livechess.adapters.TournamentsRecyclerAdapter;
import com.frostox.livechess.entities.Round;
import com.frostox.livechess.entities.Tournament;
import com.frostox.livechess.fragments.RoundFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class TournamentActivityNew extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference roundsRef;
    Query roundQuery;
    TournamentsRecyclerAdapter adapter;
    ImageView imageView;
    MyAdapter spinnerAdapter;
    File appDirectory;
    File bannersDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_activity_new);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.tournament_activity_new_banner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        appDirectory = new File(this.getExternalFilesDir(Environment.getDataDirectory().getAbsolutePath()), "livechess");
        bannersDirectory = new File(appDirectory, "banners");
        if(!appDirectory.exists()) appDirectory.mkdirs();
        if(!bannersDirectory.exists()) bannersDirectory.mkdirs();
        // Setup spinner


        String key = getIntent().getStringExtra("key");

        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("banners/" + key + ".png");
        final File image = new File(bannersDirectory, storageRef.getName());
        if(!image.exists())
            storageRef.getFile(image).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Picasso.with(TournamentActivityNew.this).load(image).into(imageView);
                    Log.d(TournamentActivityNew.class.getName(), "Download Done");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Log.d(TournamentActivityNew.class.getName(), "Download Failed");
                }
            });
        else Picasso.with(TournamentActivityNew.this).load(image).into(imageView);

        String name = getIntent().getStringExtra("name");
        ((TextView) findViewById(R.id.activity_tournament_new_title)).setText(name);
        database = FirebaseDatabase.getInstance();
        roundsRef = database.getReference("tournaments").child(key).child("rounds");
        roundQuery = roundsRef.orderByChild("name");
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        roundQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedHashMap<String, String> rounds = new LinkedHashMap<String, String>();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    rounds.put(postSnapshot.getKey(), postSnapshot.getValue(Round.class).getName());
                }



                spinnerAdapter = new MyAdapter(
                        toolbar.getContext(),
                        rounds);
                spinner.setAdapter(spinnerAdapter);
                spinnerAdapter.notifyDataSetChanged();

                roundQuery.removeEventListener(this);

                roundsRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Round round = dataSnapshot.getValue(Round.class);
                        spinnerAdapter.getRounds().put(dataSnapshot.getKey(), round.getName());
                        spinnerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Round round = dataSnapshot.getValue(Round.class);
                        spinnerAdapter.getRounds().put(dataSnapshot.getKey(), round.getName());
                        spinnerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        spinnerAdapter.getRounds().remove(dataSnapshot.getKey());
                        spinnerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TournamentActivityNew.class.getName(), databaseError.getDetails());
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TournamentActivityNew.class.getName(), databaseError.getDetails());
            }
        });

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // When the given dropdown item is selected, show its contents in the
                // container view.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, RoundFragment.newInstance(getIntent().getStringExtra("key"), new ArrayList<>(spinnerAdapter.getRounds().keySet()).get(position)))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    }





    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;


        LinkedHashMap<String, String> rounds;

        public MyAdapter(Context context, LinkedHashMap<String, String> objects) {
            super(context, android.R.layout.simple_list_item_1);
            this.rounds = objects;
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public int getCount() {
            if(rounds != null) return rounds.size();
            else return 0;
        }

        @Override
        public String getItem(int position) {
            if(rounds != null && rounds.size() > position){
                return rounds.get(position);
            } else return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                convertView  = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }


            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
            textView.setText(new ArrayList<>(rounds.values()).get(position));

            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(new ArrayList<>(rounds.values()).get(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }

        public LinkedHashMap<String, String> getRounds() {
            return rounds;
        }

        public void setRounds(LinkedHashMap<String, String> rounds) {
            this.rounds = rounds;
        }
    }



}
