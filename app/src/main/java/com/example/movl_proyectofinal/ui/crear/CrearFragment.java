package com.example.movl_proyectofinal.ui.crear;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
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
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.movl_proyectofinal.R;
import com.example.movl_proyectofinal.ui.creditos.CreditosFragment;
import com.example.movl_proyectofinal.ui.creditos.CreditosViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CrearFragment extends Fragment implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private CrearViewModel mViewModel;
    Button btnGuardar, btnLimpiar;
    private ImageButton btnCalen;
    public static String currentPhotoPath, img="", a, d, sex;
    private Spinner spnArea, spnDr, spnGenero;
    TextInputEditText txtId, txtNombre, txtPeso, txtEstatura, txtEdad, txtFechaIngreso;

    DatePickerDialog dpd;
    Calendar c;
    private static int anio, mes, dia;
    private DatePickerDialog.OnDateSetListener listener;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public static CrearFragment newInstance() {
        return new CrearFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crear, container, false);

        btnGuardar = root.findViewById(R.id.btnGuardar);
        btnLimpiar = root.findViewById(R.id.btnLimpiar);
        txtId = root.findViewById(R.id.txtId);
        txtNombre = root.findViewById(R.id.txtNombre);
        txtEdad = root.findViewById(R.id.txtEdad);
        txtEstatura = root.findViewById(R.id.txtEstatura);
        txtPeso = root.findViewById(R.id.txtPeso);
        txtFechaIngreso = root.findViewById(R.id.txtFechaNacim);
        btnCalen = root.findViewById(R.id.ibtnFecha);

        Componentes(root);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtId.getText().toString().equals("") || txtNombre.getText().toString().equals("") ||
                        txtFechaIngreso.getText().toString().equals("") || txtEdad.getText().toString().equals("") ||
                        txtEstatura.getText().toString().equals("") || txtPeso.getText().toString().equals("")){

                    Toast.makeText(getContext(), "Requiere llenar los campos", Toast.LENGTH_LONG).show();

                }else {

                    Map<String, Object> datosPaciente = new HashMap<>();

                    datosPaciente.put("nombre", txtNombre.getText().toString());
                    datosPaciente.put("correo", txtEdad.getText().toString());
                    datosPaciente.put("fechaIngreso", txtFechaIngreso.getText().toString());
                    datosPaciente.put("sueldo", txtEstatura.getText().toString());
                    datosPaciente.put("contrasenia", txtPeso.getText().toString());
                    datosPaciente.put("area", a);
                    datosPaciente.put("sucursal", d);
                    datosPaciente.put("genero", sex);

                    database.child("Paciente").child(txtId.getText().toString()).setValue(datosPaciente);
                    Toast.makeText(getContext(), "Agregado satisfactoriamente", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                spnArea.setAdapter(areaAdapter);
                spnDr.setAdapter(drAdapter);
                spnGenero.setAdapter(generoAdapter);
            }
        });

        btnCalen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                anio = c.get(Calendar.YEAR);
                mes = c.get(Calendar.MONTH)+1;
                dia = c.get(Calendar.DAY_OF_MONTH);

                dpd = new DatePickerDialog(getContext(), listener, anio, mes, dia);
                dpd.show();
                txtFechaIngreso.setText(dia+" /"+mes+"/"+anio);
            }
        });

        return (root);
    }

    private void Componentes(View root){

        SpinnerComponent(root);

    }


    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // mViewModel = ViewModelProviders.of(this).get(CrearViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        txtFechaIngreso.setText(dayOfMonth+"/"+(month+1)+"/"+year);
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

        spnArea.setOnItemSelectedListener(this);
        spnDr.setOnItemSelectedListener( this);
        spnGenero.setOnItemSelectedListener( this);



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
}