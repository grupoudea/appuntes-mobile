package co.edu.udea.kplus1.appuntesmobile.service.temp;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.model.Estudiante;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.model.MateriaUniversidad;

public class Datos {

    public static List<Materia> getMaterias() {
        List<Materia> materias = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Materia materia = new Materia(i, i,
                    new MateriaUniversidad(i, "codigo_" + i, "materia_" + i, i), i,
                    new Estudiante(i, "Nombre_" + i, "id_" + i, "correo_" + i, "celular_" + i),
                    i, "profesor_" + i);
            materias.add(materia);
        }
        return materias;
    }
}
