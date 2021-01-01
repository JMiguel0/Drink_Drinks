package com.example.movl_proyectofinal.ui.editar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditarFragment extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    Button btnBuscar, btnEditar, btnLimpiar;
    private ImageButton btnCalen;
    TextInputEditText txtId, txtNombre, txtPeso, txtEstatura, txtEdad, txtFechaIngreso;
    public static String currentPhotoPath, img="", a, d, sex;
    private Spinner spnArea, spnDr, spnGenero;
    DatePickerDialog dpd;
    Calendar c;
    private static int anio, mes, dia;
    static int bnd = 0, idp;

    private DatePickerDialog.OnDateSetListener listener;

    private EditarViewModel mViewModel;
    public static EditarFragment newInstance() {
        return new EditarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_editar, container, false);
        SpinnerComponent(root);

        btnEditar = root.findViewById(R.id.btnEditar);
        btnLimpiar = root.findViewById(R.id.btnLimpiarED);
        btnBuscar = root.findViewById(R.id.btnBuscarEL);
        txtId = root.findViewById(R.id.tIEtIDEL);
        txtNombre = root.findViewById(R.id.tIEtNombreED);
        txtEdad = root.findViewById(R.id.tIEtEdadED);
        txtEstatura = root.findViewById(R.id.tIEtEstaturaED);
        txtPeso = root.findViewById(R.id.tIEtPesoED);
        txtFechaIngreso = root.findViewById(R.id.tIEtFechaED);
        btnCalen = root.findViewById(R.id.ibtnFecha);

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
                            String edad = snapshot.child("edad").getValue().toString();
                            String estatura = snapshot.child("estatura").getValue().toString();
                            String peso = snapshot.child("peso").getValue().toString();
                            String s = snapshot.child("genero").getValue().toString();
                            String a = snapshot.child("area").getValue().toString();
                            String d = snapshot.child("doc").getValue().toString();

                            txtNombre.setText(nombre);
                            txtFechaIngreso.setText(fechaIngreso);
                            txtEdad.setText(edad);
                            txtEstatura.setText(estatura);
                            txtPeso.setText(peso);

                            spnArea.setSelection(obtenerPosicion(spnArea, a));
                            spnDr.setSelection(obtenerPosicion(spnDr, d));
                            spnGenero.setSelection(obtenerPosicion(spnGenero, s));

                            bnd = 1;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                limpiar();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bnd == 1){

                    if (txtId.getText().toString().equals("") || txtNombre.getText().toString().equals("") ||
                            txtFechaIngreso.getText().toString().equals("") || txtEdad.getText().toString().equals("") ||
                            txtEstatura.getText().toString().equals("") || txtPeso.getText().toString().equals("")){

                        Toast.makeText(getContext(), "Requiere llenar los campos", Toast.LENGTH_LONG).show();

                    }else{
                    /*Integer.parseInt(etId.getText().toString()),
                            a, d, etNombre.getText().toString().toUpperCase(), sex,
                            etFecha.getText().toString(), etEdad.getText().toString().toUpperCase(),
                            etEstatura.getText().toString().toUpperCase(), etPeso.getText().toString().toUpperCase(),
                            img)*/

                        Map<String, Object> pacienteMap = new HashMap<>();
                        pacienteMap.put("nombre",txtNombre.getText().toString());
                        pacienteMap.put("edad", txtEdad.getText().toString());
                        pacienteMap.put("fechaIngreso", txtFechaIngreso.getText().toString());
                        pacienteMap.put("estatura", txtEstatura.getText().toString());
                        pacienteMap.put("peso", txtPeso.getText().toString());
                        pacienteMap.put("area", a);
                        pacienteMap.put("doc", d);
                        pacienteMap.put("genero", sex);

                        database.child("Paciente").child(txtId.getText().toString()).updateChildren(pacienteMap);



                        bnd = 0;
                        Toast.makeText(getContext(), "Editado satisfactoriamente", Toast.LENGTH_LONG).show();
                        limpiar();
                    }

                }else{
                    Toast.makeText(getContext(), "Ingrese el identificador del paciente a editar", Toast.LENGTH_LONG).show();
                    bnd = 0;
                }
            }
        });

        btnCalen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bnd == 1){
                    c = Calendar.getInstance();
                    anio = c.get(Calendar.YEAR);
                    mes = c.get(Calendar.MONTH);
                    dia = c.get(Calendar.DAY_OF_MONTH);

                    dpd = new DatePickerDialog(getContext(), (DatePickerDialog.OnDateSetListener) listener, anio, mes, dia);
                    dpd.show();

                }else{
                    Toast.makeText(getContext(), "Ingrese el identificador del paciente a editar", Toast.LENGTH_LONG).show();
                    bnd = 0;
                }
            }
        });

        return (root);
    }

    public void limpiar(){
        txtId.setText("");
        txtPeso.setText("");
        txtEstatura.setText("");
        txtEdad.setText("");
        txtFechaIngreso.setText("");
        txtNombre.setText("");

        ArrayAdapter<CharSequence> areaAdapter, drAdapter, generoAdapter;

        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opciones, android.R.layout.simple_spinner_item);
        drAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o0, android.R.layout.simple_spinner_item);
        generoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sx, android.R.layout.simple_spinner_item);

        a = "";
        d = "";
        sex = "";
        bnd = 0;

        spnArea.setAdapter(areaAdapter);
        spnDr.setAdapter(drAdapter);
        spnGenero.setAdapter(generoAdapter);
    }

    private void SpinnerComponent(View root){

        ArrayAdapter<CharSequence> areaAdapter, drAdapter, generoAdapter;

        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.opciones, android.R.layout.simple_spinner_item);
        drAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o0, android.R.layout.simple_spinner_item);
        generoAdapter = ArrayAdapter.createFromResource(getContext(), R.array.sx, android.R.layout.simple_spinner_item);

        spnArea = root.findViewById(R.id.spnAreaNP);
        spnArea.setAdapter(areaAdapter);

        spnDr = root.findViewById(R.id.spnDrNP);
        spnDr.setAdapter(drAdapter);

        spnGenero = root.findViewById(R.id.spnGeneroNP);
        spnGenero.setAdapter(generoAdapter);

        spnArea.setOnItemSelectedListener( this);
        spnDr.setOnItemSelectedListener(this);
        spnGenero.setOnItemSelectedListener( this);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditarViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spnAreaNP:
                ArrayAdapter<CharSequence> areaAdapter;

                switch (position){
                    case 1:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o1, android.R.layout.simple_spinner_item);
                        break;
                    case 2:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o2, android.R.layout.simple_spinner_item);
                        break;
                    case 3:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o3, android.R.layout.simple_spinner_item);
                        break;
                    case 4:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o4, android.R.layout.simple_spinner_item);
                        break;
                    default:
                        areaAdapter = ArrayAdapter.createFromResource(getContext(), R.array.o0, android.R.layout.simple_spinner_item);
                        break;
                }

                if (position!=0){
                    a = parent.getItemAtPosition(position).toString();
                }else{
                    a = "";
                }

                spnDr.setAdapter(areaAdapter);

                break;

            case R.id.spnDrNP:

                if (position!=0){
                    d = parent.getItemAtPosition(position).toString();
                }else{
                    d = "";
                }

                break;

            case R.id.spnGeneroNP:
                if (position!=0){
                    sex = parent.getItemAtPosition(position).toString();
                }else{
                    sex ="";
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static int obtenerPosicion(Spinner spinner, String item){
        int posicion = 0;
        for (int i = 0; i<spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)){
                posicion = i;
            }
        }
        return posicion;
    }
}