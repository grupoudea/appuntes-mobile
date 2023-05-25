package co.edu.udea.kplus1.appuntesmobile.fragments.apuntes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.fragments.materias.MateriaAdapter;
import co.edu.udea.kplus1.appuntesmobile.model.Apunte;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;

public class ApunteAdapter extends RecyclerView.Adapter<ApunteAdapter.ApunteViewHolder> {

    private List<Apunte> mApuntesList=new ArrayList<>();
    private static List<Apunte> apuntes = new ArrayList<>();
    private static NavController navController;
    public ApunteAdapter(List<Apunte> apuntes, NavController navController) {
        ApunteAdapter.apuntes = apuntes;
        ApunteAdapter.navController = navController;
    }


    public ApunteAdapter(List<Apunte> apuntesList) {
        mApuntesList = apuntesList;
    }

    @NonNull
    @Override
    public ApunteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_apunte, parent, false);
        return new ApunteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ApunteViewHolder holder, int position) {
        Apunte apunte = mApuntesList.get(position);
        holder.bind(apunte);
    }

    @Override
    public int getItemCount() {
        return mApuntesList.size();
    }

    public static class ApunteViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTexto;
        private TextView mTextViewFecha;
        private ImageView mImageView;



        public ApunteViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewTexto = itemView.findViewById(R.id.editText);
            mTextViewFecha = itemView.findViewById(R.id.editText);
            mImageView = itemView.findViewById(R.id.imageView);

        }

        public void bind(Apunte apunte) {
            mTextViewTexto.setText(apunte.getContenido());

        }
    }
}
