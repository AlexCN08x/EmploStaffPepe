package com.example.emplostaff2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emplostaff2.databinding.ActivityBossDesplegableBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;

public class BossDesplegable extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el layout de la actividad utilizando el enlace de datos generado
        ActivityBossDesplegableBinding binding = ActivityBossDesplegableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarBossDesplegable.toolbar);

        // Obtener la referencia del imageView de la foto de perfil
        imageViewProfile = binding.navView.getHeaderView(0).findViewById(R.id.imagen_perfil_usuario);

        // Configurar el OnClickListener para cambiar la foto de perfil
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Definir los elementos de menú que se consideran como destinos de nivel superior
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_Whorkforce, R.id.nav_BaseData, R.id.nav_Penalties)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_boss_desplegable);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageViewProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        TextView name = findViewById(R.id.variable_header_ID);
        TextView id = findViewById(R.id.variable_header_correo);
        id.setText(MainActivity.Id);

        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        DocumentReference ref = fb.collection("Users").document(MainActivity.Id);
        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String namee = documentSnapshot.getString("Name");
                String lastname = documentSnapshot.getString("LastName");
                name.setText(namee + " " + lastname);
            }
        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_boss_desplegable);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.boss_desplegable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_log_out) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}