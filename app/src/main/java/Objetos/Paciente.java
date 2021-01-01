package Objetos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;



public class Paciente {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");

    public String id;
    public String nombre;
    public String genero;
    public String fechaNacim;
    public String edad;
    public String estatura;
    public String peso;

    public Paciente(String id, String nombre, String genero, String fechaNacim, String edad, String estatura, String peso) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.fechaNacim = fechaNacim;
        this.edad = edad;
        this.estatura = estatura;
        this.peso = peso;
    }

    DatabaseReference pacienteRef = ref.child("paciente");

}
