package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import android.graphics.Bitmap;

import java.util.Date;

public class Apunte {

    private String texto;
    private Date fecha;
    private Bitmap foto;

    public Apunte(String texto, Date fecha, Bitmap foto) {
        this.texto = texto;
        this.fecha = fecha;
        this.foto = foto;
    }

    public String getTexto() {
        return texto;
    }

    public Date getFecha() {
        return fecha;
    }

    public Bitmap getFoto() {
        return foto;
    }
}


