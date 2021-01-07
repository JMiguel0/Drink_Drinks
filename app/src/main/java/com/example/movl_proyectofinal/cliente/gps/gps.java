package com.example.movl_proyectofinal.cliente.gps;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.movl_proyectofinal.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

    public class gps extends Fragment {
        private GpsViewModel mViewModel;


        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            final View root = inflater.inflate(R.layout.fragment_gps, container, false);
            Button btn = root.findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    abrir(view);
                }
            });
            return root;





            //btEntrar.setOnClickListener(new View.OnClickListener() {

            //  @Override
            //public void onClick(View v) {
            //  Intent i;
            //i = new Intent(getContext(), MapsActivity.class);
            //startActivity(i);


            //}
            //});
        }



        public void abrir (View view){

            Intent abrir = new Intent(getContext(),MapsActivity.class) ;
            startActivity(abrir);

        }


    }