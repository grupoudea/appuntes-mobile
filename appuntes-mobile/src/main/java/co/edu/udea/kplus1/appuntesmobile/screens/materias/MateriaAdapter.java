package co.edu.udea.kplus1.appuntesmobile.screens.materias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.R;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;

public class MateriaAdapter extends BaseAdapter {

    private Context mContext;
    private List<Materia> materias;

    public MateriaAdapter(Context context, List<Materia> materias) {
        mContext = context;
        this.materias = materias;
    }

    @Override
    public int getCount() {
        return materias.size();
    }

    @Override
    public Materia getItem(int position) {
        return materias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Materia materia = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.materia_item, parent, false);
        }

        TextView textViewNombreMateria = convertView.findViewById(R.id.textViewNombreMateria);
        TextView textViewNombreProfesor = convertView.findViewById(R.id.textViewNombreProfesor);
        TextView textViewNota = convertView.findViewById(R.id.textViewNota);

        textViewNombreMateria.setText(materia.getSubjectPensum().getName());
        textViewNombreProfesor.setText(String.valueOf(materia.getTeacher()));
        textViewNota.setText(String.valueOf(materia.getFinalGrade()));

        return convertView;
    }
}