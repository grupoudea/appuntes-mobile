package co.edu.udea.kplus1.appuntesmobile.service.temp;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.model.Estudiante;
import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.model.MateriaUniversidad;

public class Datos {

    public static int getEstudianteSession() {
        return 1;
    }

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

    public static List<MateriaUniversidad> getMateriasPensum() {
        List<MateriaUniversidad> materias = new ArrayList<>();
        materias.add(new MateriaUniversidad(1, "L1", "Logica 1", 10));
        materias.add(new MateriaUniversidad(2, "L2", "Logica 2", 10));
        materias.add(new MateriaUniversidad(3, "L3", "Logica 3", 10));
        materias.add(new MateriaUniversidad(4, "L4", "Logica 4", 10));
        materias.add(new MateriaUniversidad(5, "L5", "Logica 5", 10));
        return materias;
    }
}
