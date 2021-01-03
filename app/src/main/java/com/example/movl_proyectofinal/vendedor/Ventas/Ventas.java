package com.example.movl_proyectofinal.vendedor.Ventas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movl_proyectofinal.R;

public class Ventas extends Fragment {

    private VentasViewModel mViewModel;

    public static Ventas newInstance() {
        return new Ventas();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ventas, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider((ViewModelStoreOwner) this).get(VentasViewModel.class);
        // TODO: Use the ViewModel
    }

}