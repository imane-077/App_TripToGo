package com.openclassrooms.connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.openclassrooms.connexion.client.recherche.FormDestination;

public class menuAdmin extends AppCompatActivity {

    Button ajoutDest;
    Button voirDest;
    Button retourAc;
    Button voirCli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        ajoutDest = findViewById(R.id.ajoutDest);
        voirDest = findViewById(R.id.btnVoirDes);
        retourAc = findViewById(R.id.Btn_R_Ac);
        voirCli = findViewById(R.id.btnVoirCli);

        ajoutDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menuAdmin.this, AjouterDestination.class);
                startActivity(i);
            }
        });

        voirDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menuAdmin.this, VisiteleSite.class);
                startActivity(i);
            }
        });

        voirCli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menuAdmin.this, Affichage_clients.class);
                startActivity(i);
            }
        });

        /*retourAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menuAdmin.this, Accueil.class);
                startActivity(i);
            }
        });*/



    }
}