package com.example.movl_proyectofinal.ui.listar;

import android.app.AlertDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.movl_proyectofinal.R;
import com.example.movl_proyectofinal.ui.crear.CrearFragment;
import com.example.movl_proyectofinal.ui.crear.CrearViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class ListarFragment extends Fragment {
    private ListarViewModel mViewModel;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> nombres;
    ListView list;
    ArrayAdapter<String> adapter;


    public static ListarFragment newInstance() {
        return new ListarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listar, container, false);
        list = root.findViewById(R.id.lListaPacientes);
        nombres = new ArrayList<String>();
        final TextView textView = root.findViewById(R.id.text_listar);
        String aux;
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, nombres);
        for (int i=0; i<=50; i++){
             aux = Integer.toString(i);
            System.out.println(aux);
            database.child("Paciente").child(aux).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String nombre = snapshot.child("nombre").getValue().toString();
                        String sueldo = snapshot.child("sueldo").getValue().toString();
                        String diaPaga = snapshot.child("fechaIngreso").getValue().toString();
                        String area = snapshot.child("area").getValue().toString();
                        String genero = snapshot.child("genero").getValue().toString();
                        String correo = snapshot.child("correo").getValue().toString();
                        String contrasenia = snapshot.child("contrasenia").getValue().toString();
                        String sucursal = snapshot.child("sucursal").getValue().toString();
                        String id = snapshot.getKey().toString();
                        nombres.add("Id: "+id+" \nNombre: "+nombre+" \nSueldo mensual: $"+sueldo+" \nDía de paga: "+diaPaga.substring(0,2)
                                +" \nArea: "+area+" \nSucursal: "+sucursal+" \nGenero: "+genero+" \nCorreo: "+correo+" \nContraseña: "+contrasenia);
                        list.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListarViewModel.class);
        // TODO: Use the ViewModel
    }
}