package com.openclassrooms.connexion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class resultat extends AppCompatActivity {


    TextView DateD, DateF, Bugdet, Env;
    RecyclerView recyclerView;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        DateD = findViewById(R.id.t_v_DateD);
        DateF = findViewById(R.id.t_v_DateF);
        Bugdet = findViewById(R.id.t_v_Budg);
        Env = findViewById(R.id.t_v_ENv);

        // recupérer la date de fin
       String dateD = getIntent().getStringExtra("DateD") ;
        String dateF = getIntent().getStringExtra("DateF") ;
        String bugdet = getIntent().getStringExtra("Budget") ;
        String env = getIntent().getStringExtra("Env");

        DateD.setText(dateD);
        DateF.setText(dateF);
        Bugdet.setText(bugdet);
        Env.setText(env);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        Query dataQuery = dbRef.child("Data").orderByChild("env").equalTo(env);

        recyclerView = (RecyclerView)findViewById(R.id.recviewR);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(dataQuery, Model.class)
                        .build();

        adapter=new Adapter(options);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}