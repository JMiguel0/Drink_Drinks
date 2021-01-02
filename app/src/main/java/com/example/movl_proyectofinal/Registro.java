package com.example.movl_proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Registro extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final EditText txtNombre, txtCorreo, txtContra, txtContra2;
        Button btnRegistro = findViewById(R.id.btnRegistrarse);
        Button btnVolver = findViewById(R.id.btnVolver);
        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContra = findViewById(R.id.txtContra);
        txtContra2 = findViewById(R.id.txtContra2);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((txtNombre.getText().toString().equals("") ||
                        txtContra.getText().toString().equals("") ||
                        txtContra2.getText().toString().equals("") ||
                        txtCorreo.getText().toString().equals(""))){

                    Toast.makeText(view.getContext(), "Requiere llenar los campos", Toast.LENGTH_LONG).show();

                }else {

                    if ( txtContra2.getText().toString().equals(txtContra.getText().toString())){


                    Map<String, Object> datosPaciente = new HashMap<>();

                    datosPaciente.put("nombre", txtNombre.getText().toString());
                    datosPaciente.put("correo", txtCorreo.getText().toString());
                    datosPaciente.put("contrasenia", txtContra.getText().toString());

                    database.child("Cliente").child(UUID.randomUUID().toString()).setValue(datosPaciente);
                    Toast.makeText(view.getContext(), "Registrado satisfactoriamente", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), Login.class);
                    startActivity(intent);
                    }else
                        Toast.makeText(view.getContext(), "La contraseña no coincide", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}