package com.example.movl_proyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Objetos.Cliente;

public class Login extends AppCompatActivity {
    Button btniniciar;
    Button btnregistro;
    EditText txtCorreo, txtContra;
    boolean existe=false;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btniniciar = findViewById(R.id.btn_iniciar);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContra = findViewById(R.id.txtContra);
        btniniciar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                existe=false;

                if (txtCorreo.getText().toString().equals("") || txtContra.getText().toString().equals("")){
                    Toast.makeText(view.getContext(), "Faltan datos por llenar", Toast.LENGTH_SHORT).show();
                }else{
                    if (txtCorreo.getText().toString().equals("admin@hotmail.com") && txtContra.getText().toString().equals("root")){
                        Toast.makeText(view.getContext(), "Admin inicio", Toast.LENGTH_SHORT).show();
                        limpiar();
                        existe=true;
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        database.child("Cliente").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (final DataSnapshot dataSnapshot : snapshot.getChildren()){

                                    Cliente obj = dataSnapshot.getValue(Cliente.class);
                                    String correo = obj.getCorreo();
                                    String contra = obj.getContrasenia();

                                    if (txtCorreo.getText().toString().equals(correo) && txtContra.getText().toString().equals(contra)){
                                        existe=true;
                                            Toast.makeText(view.getContext(), "Cliente inicio", Toast.LENGTH_SHORT).show();
                                        limpiar();


                                    }
                                }/////
                                database.child("Paciente").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for (final DataSnapshot dataSnapshot : snapshot.getChildren()){

                                            Cliente obj = dataSnapshot.getValue(Cliente.class);
                                            String correo = obj.getCorreo();
                                            String contra = obj.getContrasenia();

                                            if (txtCorreo.getText().toString().equals(correo) && txtContra.getText().toString().equals(contra)){
                                                existe=true;
                                               Intent intent = new Intent(view.getContext(), Vendedor.class);
                                               startActivity(intent);
                                                Toast.makeText(view.getContext(), "Vendedor inició", Toast.LENGTH_SHORT).show();
                                                limpiar();
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }

            }
        });
        btnregistro = findViewById(R.id.btn_registro);
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Registro.class);
                startActivity(intent);
            }
        });
    }

    public void limpiar(){
        txtCorreo.setText("");
        txtContra.setText("");
    }
}