package com.openclassrooms.connexion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class AjouterDestination extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    Button Btn_C_Image;
    Button Btn_Ajouter;
    EditText T_V_Lieu;
    EditText T_V_Pays;
    EditText T_V_Env;
    EditText T_V_Budget;
    ImageView I_V_Des;
    ProgressBar P_Bar_Dest;

    private Uri UriImg;
    private StorageReference StorageRef;
    private DatabaseReference DatabaseRef;
    private StorageTask UploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_destination);

        Btn_C_Image = findViewById(R.id.BtnImg);
        Btn_Ajouter = findViewById(R.id.Btn_Valider_ND);
        T_V_Lieu = findViewById(R.id.eT_lieu);
        T_V_Pays = findViewById(R.id.eT_pays);
        T_V_Env = findViewById(R.id.eT_env);
        I_V_Des = findViewById(R.id.I_V_Dest);
        T_V_Budget = findViewById(R.id.eT_budget);
        P_Bar_Dest = findViewById(R.id.ProgressB_Dest);

        StorageRef = FirebaseStorage.getInstance().getReference();
        DatabaseRef = FirebaseDatabase.getInstance().getReference("Data");

        Btn_C_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choisirImage();
            }
        });
        Btn_Ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UploadTask != null && UploadTask.isInProgress()) {
                    Toast.makeText(AjouterDestination.this, "En cours de téléchargement", Toast.LENGTH_SHORT).show();
                } else {
                    Uplaod(UriImg);
                }
            }
        });
    }

    private void choisirImage() {
        // accès à la galerie tel
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        //démarre le intent : for result car on attend un résultat du lancement de l'activité
        // lance le intent et on récupère dans pick_image... et si il retourne 1 on sait que c'est cet intent car on peut en lancer plusieurs
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // vérifie si une image est récupéré
        // resultCode == RESULT_OK ==> permet de savoir si j'ai bien sélectionner l'image
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            // accès à l'image à partir de data
            // URI c'est comme URL ==> permet de savoir où se trouve l'image
            UriImg = data.getData();
            // met l'image dans image view
            I_V_Des.setImageURI(UriImg);
            I_V_Des.setVisibility(View.VISIBLE);

        }
    }

    private String getFileExtension(Uri uri) {
        // getContentResolver() ==> obtenir le type de données d'un fichier
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        // renvoie l'extension de uri
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void Uplaod(Uri uri) {

        if (UriImg != null) { // si on a sélectionné une image
            // ajouter à la base avec les variables définit en haut
            StorageReference fileReference = StorageRef.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            UploadTask = fileReference.putFile(uri)

                    // si succès
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override

                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            P_Bar_Dest.setProgress(0);
                                        }
                                    }, 500);
                                    Toast.makeText(AjouterDestination.this, "Téléchargement réussi !", Toast.LENGTH_LONG).show();
                                    Model model = new Model(T_V_Pays.getText().toString().trim(),T_V_Lieu.getText().toString().trim(),T_V_Env.getText().toString().trim(), uri.toString(), T_V_Budget.getText().toString().trim());

                                    String mosId = DatabaseRef.push().getKey();
                                    DatabaseRef.child(mosId).setValue(model);

                                    T_V_Pays.getText().clear() ;
                                    T_V_Lieu.getText().clear();
                                    T_V_Env.getText().clear();
                                    T_V_Budget.getText().clear();
                                    I_V_Des.setVisibility(View.INVISIBLE);

                                }
                            });

                        }
                    })

                    // Appelé lorsque la tâche a échoué avec une exception
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AjouterDestination.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            P_Bar_Dest.setProgress((int) progress);
                        }
                    });
        } else {
            // si aucune image a été sélectionné
            Toast.makeText(AjouterDestination.this, "Aucune image ", Toast.LENGTH_SHORT).show();
        }
    }
}