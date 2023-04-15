package co.edu.udea.kplus1.appuntesmobile.service.temp;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.kplus1.appuntesmobile.model.Materia;
import co.edu.udea.kplus1.appuntesmobile.model.MateriaPensum;

public class Datos {

    public static List<Materia> getMaterias() {
        List<Materia> materias = new ArrayList<>();
        materias.add(new Materia(1, new MateriaPensum("Logica 1", 1),1, 4, "Profesor 1", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 2", 1),1, 4, "Profesor 2", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 3", 1),1, 4, "Profesor 3", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 4", 1),1, 4, "Profesor 4", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5LogicaLogicaLogicaLogicaLogicaLogicaLogicaLogicaLogicaLogicaLogica", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        materias.add(new Materia(1, new MateriaPensum("Logica 5", 1),1, 4, "Profesor 5", 4.0));
        return materias;
    }
}
