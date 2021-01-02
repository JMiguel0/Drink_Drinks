package com.example.movl_proyectofinal.ui.eliminar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.example.movl_proyectofinal.R;
import com.example.movl_proyectofinal.ui.crear.CrearFragment;
import com.example.movl_proyectofinal.ui.crear.CrearViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Calendar;

public class EliminarFragment extends Fragment{

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        Button btnBuscar, btnEliminar, btnLimpiar;
private ImageButton btnCalen;
        TextView txtNombre, txtPeso, txtEstatura, txtEdad, txtFechaIngreso, txtArea, txtDoc, txtSex;
        TextInputEditText txtId;
public static String currentPhotoPath, img="", a, d, sex;


private DatePickerDialog.OnDateSetListener listener;
    private EliminarViewModel mViewModel;
    public static EliminarFragment newInstance() {
        return new EliminarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_eliminar, container, false);

        btnLimpiar = root.findViewById(R.id.btnLimpiarEL);
        btnBuscar = root.findViewById(R.id.btnBuscarEL);
        txtId = root.findViewById(R.id.tIEtIDEL);
        txtNombre = root.findViewById(R.id.tVNombrepEL);
        txtEdad = root.findViewById(R.id.tVEdadpEL);
        txtEstatura = root.findViewById(R.id.tVEstaturapEL);
        txtPeso = root.findViewById(R.id.tVPesopEL);
        txtFechaIngreso = root.findViewById(R.id.tVFechapEL);
        txtArea = root.findViewById(R.id.tVAreapEL);
        txtDoc = root.findViewById(R.id.tVDrpEL);
        txtSex = root.findViewById(R.id.tVGeneropEL);
        btnEliminar = root.findViewById(R.id.btnEliminar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = txtId.getText().toString();
                database.child("Paciente").child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String nombre = snapshot.child("nombre").getValue().toString();
                            String fechaIngreso = snapshot.child("fechaIngreso").getValue().toString();
                            String correo = snapshot.child("correo").getValue().toString();
                            String sueldo = snapshot.child("sueldo").getValue().toString();
                            String contra = snapshot.child("contrasenia").getValue().toString();
                            String s = snapshot.child("genero").getValue().toString();
                            String a = snapshot.child("area").getValue().toString();
                            String d = snapshot.child("sucursal").getValue().toString();

                            txtNombre.setText("Nombre "+nombre);
                            txtFechaIngreso.setText("Fecha: "+fechaIngreso);
                            txtEdad.setText("Correo: "+correo);
                            txtEstatura.setText("Sueldo: "+sueldo);
                            txtPeso.setText("Contraseña: "+contra);
                            txtArea.setText("Área: "+a);
                            txtDoc.setText("Sucursal: "+d);
                            txtSex.setText("Genero: "+s);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnEliminar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("Paciente").child(txtId.getText().toString()).removeValue();
                limpiar();
                Toast.makeText(getContext(), "Vendedor eliminado", Toast.LENGTH_LONG).show();
            }
        });

        btnLimpiar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
            }
        });


        return (root);
    }

    public void limpiar(){
        txtId.setText("");
        txtPeso.setText("Contraseña: ");
        txtEstatura.setText("Sueldo: ");
        txtEdad.setText("Correo: ");
        txtFechaIngreso.setText("Fecha de ingreso: ");
        txtNombre.setText("Nombre: ");
        txtSex.setText("Genero: ");
        txtArea.setText("Área: ");
        txtDoc.setText("Sucursal: ");


        a = "";
        d = "";
        sex = "";

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EliminarViewModel.class);
        // TODO: Use the ViewModel
    }
}