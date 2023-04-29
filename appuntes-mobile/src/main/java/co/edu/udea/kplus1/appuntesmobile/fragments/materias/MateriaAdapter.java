package co.edu.udea.kplus1.appuntesmobile.fragments.materias;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;

public class MateriaAdapter extends RecyclerView.Adapter<MateriaAdapter.ViewHolder> {

    private static final String TAG = "MateriaAdapter";
    private final List<Materia> materias;

    public MateriaAdapter(List<Materia> materias) {
        this.materias = materias;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNombreMateria;
        private final TextView textViewNombreProfesor;
        private final TextView textViewNota;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(v1 -> Log.d(TAG, "Element " + getAdapterPosition() + " clicked."));
            textViewNombreMateria = v.findViewById(R.id.textViewNombreMateria);
            textViewNombreProfesor = v.findViewById(R.id.textViewNombreProfesor);
            textViewNota = v.findViewById(R.id.textViewNota);
        }

        public TextView getTextViewNombreMateria() {
            return textViewNombreMateria;
        }

        public TextView getTextViewNombreProfesor() {
            return textViewNombreProfesor;
        }

        public TextView getTextViewNota() {
            return textViewNota;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.materia_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        viewHolder.getTextViewNombreMateria().setText(getItem(position).getSubjectPensum().getName());
        viewHolder.getTextViewNombreProfesor().setText(getItem(position).getSubjectPensum().getName());
        viewHolder.getTextViewNota().setText(getItem(position).getSubjectPensum().getName());
    }

    @Override
    public int getItemCount() {
        return materias.size();
    }

    public Materia getItem(int position) {
        return materias.get(position);
    }

}