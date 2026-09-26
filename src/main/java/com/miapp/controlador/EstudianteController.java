package com.miapp.controlador;

import com.miapp.modelo.Estudiante;
import com.miapp.servicios.IBuscador;
import com.miapp.vista.EstudianteView;

import java.util.ArrayList;
import java.util.List;
//jajajaj
//jajajjaj//jajajajaj//jajaja
import com.miapp.modelo.Curso;
import com.miapp.utilidades.EstadoMatricula;
import com.miapp.modelo.Profesor;


public class EstudianteController implements IBuscador {

    // ── Constantes finales ────────────────────────────────────────────────────
    private static final int CANTIDAD_ESTUDIANTES_INICIALES = 12;
    private static final String MENSAJE_BUSQUEDA_VACIA = "Por favor ingrese un nombre para buscar.";
    private static final String MENSAJE_BUSQUEDA_CARRERA_VACIA = "Por favor seleccione una carrera para buscar.";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";
    private static final String MENSAJE_BUSQUEDA_CURSO_VACIA = "Por favor ingrese el código de un curso para buscar.";
    private static final String MENSAJE_BUSQUEDA_ESTADO_VACIA = "Por favor seleccione un estado de matrícula para buscar.";

    // ── Vista ─────────────────────────────────────────────────────────────────
    private EstudianteView vista;

    // ── Array de estudiantes (fuente de datos) ────────────────────────────────
    private Estudiante[] estudiantes;
    private Curso[] cursos;
    private Profesor[] profesores;
    private int totalProfesores;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteController(EstudianteView vista) {
        this.vista = vista;
        // Primero cargar datos (inicializar estudiantes[])
        cargarDatos();
        // Luego asignar controlador a la vista (ahora es seguro acceder a estudiantes[])
        this.vista.setControlador(this);
    }

    // ── Implementación de la interfaz IBuscador ───────────────────────────────

    @Override
    public void cargarDatos() {
        inicializarEstudiantes();
        inicializarProfesores();
    }

    @Override
    public void buscarEstudiante(String criterio) {
        buscarPorCriterio(criterio);
    }

    @Override
    public void buscarEstudiantePorCarrera(String carrera) {
        buscarPorCarrera(carrera);
    }
    
    @Override
    public void buscarEstudiantePorCurso(String codigoCurso) {
    buscarPorCurso(codigoCurso);
    }

    @Override
    public void buscarEstudiantePorEstado(String estadoMatricula) {
    buscarPorEstado(estadoMatricula);
    }   

    // ── Carga de datos iniciales ──────────────────────────────────────────────

    private void inicializarEstudiantes() {
        estudiantes = new Estudiante[CANTIDAD_ESTUDIANTES_INICIALES];

        // Reinicia el contador estático de Estudiante antes de cargar nuevos datos
        Estudiante.reiniciarContador();

        estudiantes[0]  = new Estudiante(1,  "Ana ","García",        "Ingeniería de Sistemas",  4.5);
        estudiantes[1]  = new Estudiante(2,  "Carlos"," López",      "Ingeniería Civil",        3.8);
        estudiantes[2]  = new Estudiante(3,  "María", "Rodríguez",   "Medicina",                4.9);
        estudiantes[3]  = new Estudiante(4,  "José ","Martínez",     "Derecho",                 3.5);
        estudiantes[4]  = new Estudiante(5,  "Laura ","Sánchez",     "Administración",          4.1);
        estudiantes[5]  = new Estudiante(6,  "Andrés ","Torres",     "Ingeniería de Sistemas",  3.9);
        estudiantes[6]  = new Estudiante(7,  "Valentina ","Gómez",   "Psicología",              4.3);
        estudiantes[7]  = new Estudiante(8,  "Luis ","Herrera",      "Economía",                3.7);
        estudiantes[8]  = new Estudiante(9,  "Sofía ","Díaz",        "Ingeniería Civil",        4.6);
        estudiantes[9]  = new Estudiante(10, "Juliana ","Morales",   "Medicina",                4.8);
        estudiantes[10] = new Estudiante(11, "Ana Milena ","Ruiz",   "Derecho",                 4.0);
        estudiantes[11] = new Estudiante(12, "Carlos Andrés ","Paz", "Administración",          3.6);

        // Log: informa cuántos estudiantes se cargaron usando static getTotalEstudiantes()
        System.out.println("Total de estudiantes cargados: " + Estudiante.getTotalEstudiantes());
        inicializarCursos();
        asignarEstadosYMatriculasDeEjemplo();
    }
    
    private void inicializarCursos() {
    cursos = new Curso[]{
        new Curso("SIS101", 3),
        new Curso("MAT201", 4),
        new Curso("PSI150", 2)
    };
    
    
}
    
    private void inicializarProfesores() {
    profesores = new Profesor[5];
    totalProfesores = 0;
}

    private void asignarEstadosYMatriculasDeEjemplo() {
    if (estudiantes.length > 0 && estudiantes[0] != null) estudiantes[0].inscribir(cursos[0]);
    if (estudiantes.length > 1 && estudiantes[1] != null) estudiantes[1].inscribir(cursos[0]);
    if (estudiantes.length > 2 && estudiantes[2] != null) estudiantes[2].inscribir(cursos[1]);
    if (estudiantes.length > 3 && estudiantes[3] != null) {
        estudiantes[3].inscribir(cursos[1]);
        estudiantes[3].setEstadoMatricula(EstadoMatricula.RETIRADO);
    }
    if (estudiantes.length > 9 && estudiantes[9] != null) {
    estudiantes[9].setEstadoMatricula(EstadoMatricula.EGRESADO);
}
    if (estudiantes.length > 5 && estudiantes[5] != null) estudiantes[5].inscribir(cursos[2]);
}

    // ── Lógica de búsqueda ────────────────────────────────────────────────────

  
    private void buscarPorCriterio(String criterio) {

        // Validación básica usando constante final
        if (criterio == null || criterio.isEmpty()) {
            vista.mostrarError(MENSAJE_BUSQUEDA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();
        String criterioBajo = criterio.toLowerCase();

        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null && (e.getNombre().toLowerCase().contains(criterioBajo) ||
                e.getApellido().toLowerCase().contains(criterioBajo))) {
                resultados.add(e);
            }
        }

        if (resultados.isEmpty()) {
            vista.mostrarEstudiantes(new ArrayList<>()); // mostrará mensaje vacío
        } else if (resultados.size() == 1) {
            // Un solo resultado: usar vista.mostrarEstudiante(fila)
            vista.mostrarEstudiante(convertirAFila(resultados.get(0)));
        } else {
            // Varios resultados: mostrar lista completa ya convertida a filas
            vista.mostrarEstudiantes(convertirAFilas(resultados));
        }
    }

   
    private void buscarPorCarrera(String carrera) {
        // Validación básica usando constante final
        if (carrera == null || carrera.isEmpty() || carrera.equals("Seleccionar...")) {
            vista.mostrarError(MENSAJE_BUSQUEDA_CARRERA_VACIA);
            return;
        }

        List<Estudiante> resultados = new ArrayList<>();

        // Búsqueda exacta por carrera
        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null && e.getCarrera().equalsIgnoreCase(carrera)) {
                resultados.add(e);
            }
        }

        // Mostrar resultados (ya convertidos a filas, no como Estudiante)
        vista.mostrarEstudiantes(convertirAFilas(resultados));
    }
    
    private void buscarPorCurso(String codigoCurso) {
    if (codigoCurso == null || codigoCurso.isEmpty() || codigoCurso.equals("Seleccionar...")) {
        vista.mostrarError(MENSAJE_BUSQUEDA_CURSO_VACIA);
        return;
    }

    List<Estudiante> resultados = new ArrayList<>();

    for (Estudiante e : estudiantes) {
        if (e == null) continue;
        for (Curso c : e.getCursosInscritos()) {
            if (c.getCodigo().equalsIgnoreCase(codigoCurso)) {
                resultados.add(e);
                break;
            }
        }
    }

    vista.mostrarEstudiantes(convertirAFilas(resultados));
}

    private void buscarPorEstado(String estadoMatricula) {
    if (estadoMatricula == null || estadoMatricula.isEmpty() || estadoMatricula.equals("Seleccionar...")) {
        vista.mostrarError(MENSAJE_BUSQUEDA_ESTADO_VACIA);
        return;
    }

    List<Estudiante> resultados = new ArrayList<>();

    for (Estudiante e : estudiantes) {
        if (e != null && e.getEstadoMatricula().name().equalsIgnoreCase(estadoMatricula)) {
            resultados.add(e);
        }
    }

    vista.mostrarEstudiantes(convertirAFilas(resultados));
    }
    
    

    
    private Object[] convertirAFila(Estudiante e) {
    return new Object[]{
        e.getId(),
        e.getNombre(),
        e.getApellido(),
        e.getCarrera(),
        String.format("%.2f", e.getPromedio()),
        e.getEstadoMatricula()
    };
}

  
    private List<Object[]> convertirAFilas(List<Estudiante> lista) {
        List<Object[]> filas = new ArrayList<>();
        for (Estudiante e : lista) {
            filas.add(convertirAFila(e));
        }
        return filas;
    }

    public Estudiante obtenerEstudiantePorId(int id) {
        for (Estudiante e : estudiantes) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public String[] obtenerCarrerasUnicas() {
        List<String> carreras = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            // Validar que el elemento no sea null
            if (e != null) {
                String carrera = e.getCarrera();
                if (!carreras.contains(carrera)) {
                    carreras.add(carrera);
                }
            }
        }
        return carreras.toArray(new String[0]);
    }
    
    public String[] obtenerCodigosCursos() {
    String[] codigos = new String[cursos.length];
    for (int i = 0; i < cursos.length; i++) {
        codigos[i] = cursos[i].getCodigo();
    }
    return codigos;
    }

    public String[] obtenerEstadosMatricula() {
    EstadoMatricula[] valores = EstadoMatricula.values();
    String[] nombres = new String[valores.length];
    for (int i = 0; i < valores.length; i++) {
        nombres[i] = valores[i].name();
    }
    return nombres;
    }

 
    public final int obtenerTotalEstudiantes() {
        return Estudiante.getTotalEstudiantes();
    }

   
    public boolean agregarEstudiante(String nombre, String apellido, String carrera, double promedio) {
        // Validación de datos
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
            carrera == null || carrera.isEmpty()) {
            vista.mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        // Expandir el array si es necesario antes de agregar
        if (estudiantes.length == Estudiante.getTotalEstudiantes()) {
            // El array está lleno, crear uno más grande
            Estudiante[] nuevoArray = new Estudiante[estudiantes.length + 5];
            System.arraycopy(estudiantes, 0, nuevoArray, 0, estudiantes.length);
            estudiantes = nuevoArray;
        }

        // Obtener el índice donde se guardará el nuevo estudiante
        int indiceNuevoEstudiante = Estudiante.getTotalEstudiantes();

        // Crear nuevo estudiante con ID automático basado en el contador static
        int proximoId = Estudiante.getProximoId();
        Estudiante nuevoEstudiante = new Estudiante(proximoId, nombre, apellido, carrera, promedio);

        // Agregar el nuevo estudiante en la posición correcta
        estudiantes[indiceNuevoEstudiante] = nuevoEstudiante;

        // Mostrar mensaje de éxito
        vista.mostrarMensaje("Estudiante agregado correctamente.\nTotal de estudiantes: " +
                            Estudiante.getTotalEstudiantes());

        return true;
    }
    
    public boolean agregarProfesor(String nombre, String apellido, double salarioBase) {
    if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty()) {
        vista.mostrarError("El nombre y el apellido del profesor son obligatorios.");
        return false;
    }

    if (profesores.length == totalProfesores) {
        Profesor[] nuevoArray = new Profesor[profesores.length + 5];
        System.arraycopy(profesores, 0, nuevoArray, 0, profesores.length);
        profesores = nuevoArray;
    }

    int nuevoId = totalProfesores + 1;
    Profesor nuevoProfesor = new Profesor(nombre, apellido, nuevoId, salarioBase);
    profesores[totalProfesores] = nuevoProfesor;
    totalProfesores++;

    vista.mostrarMensaje("Profesor agregado correctamente.\nTotal de profesores: " + totalProfesores);
    return true;
}
    
    public String[] obtenerNombresProfesores() {
    String[] nombres = new String[totalProfesores];
    for (int i = 0; i < totalProfesores; i++) {
        nombres[i] = profesores[i].getNombre() + " " + profesores[i].getApellido();
    }
    return nombres;
}
    private Profesor buscarProfesorPorNombreCompleto(String nombreCompleto) {
    for (int i = 0; i < totalProfesores; i++) {
        String nombreCompletoActual = profesores[i].getNombre() + " " + profesores[i].getApellido();
        if (nombreCompletoActual.equals(nombreCompleto)) {
            return profesores[i];
        }
    }
    return null;
}

private Curso buscarCursoPorCodigo(String codigo) {
    for (Curso c : cursos) {
        if (c.getCodigo().equalsIgnoreCase(codigo)) {
            return c;
        }
    }
    return null;
}

public boolean asignarProfesorACurso(String nombreProfesor, String codigoCurso) {
    if (nombreProfesor == null || nombreProfesor.isEmpty() || nombreProfesor.equals("Seleccionar...")) {
        vista.mostrarError("Seleccione un profesor válido.");
        return false;
    }
    if (codigoCurso == null || codigoCurso.isEmpty() || codigoCurso.equals("Seleccionar...")) {
        vista.mostrarError("Seleccione un curso válido.");
        return false;
    }

    Profesor profesor = buscarProfesorPorNombreCompleto(nombreProfesor);
    Curso curso = buscarCursoPorCodigo(codigoCurso);

    if (profesor == null || curso == null) {
        vista.mostrarError("No se encontró el profesor o el curso seleccionado.");
        return false;
    }

    curso.setProfesor(profesor);
    vista.mostrarMensaje("Profesor " + nombreProfesor + " asignado al curso " + codigoCurso + ".");
    return true;
}

public String obtenerCursosDeProfesor(String nombreProfesor) {
    Profesor profesor = buscarProfesorPorNombreCompleto(nombreProfesor);
    if (profesor == null) {
        return "Profesor no encontrado.";
    }

    StringBuilder sb = new StringBuilder();
    for (Curso c : cursos) {
        if (c.getProfesor() == profesor) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(c.getCodigo());
        }
    }
    return sb.length() > 0 ? sb.toString() : "(sin cursos asignados)";
}

public String obtenerProfesorDeCurso(String codigoCurso) {
    Curso curso = buscarCursoPorCodigo(codigoCurso);
    if (curso == null || curso.getProfesor() == null) {
        return "(ninguno)";
    }
    return curso.getProfesor().getNombre() + " " + curso.getProfesor().getApellido();
}

public boolean inscribirEstudianteEnCurso(int idEstudiante, String codigoCurso) {
    if (codigoCurso == null || codigoCurso.isEmpty() || codigoCurso.equals("Seleccionar...")) {
        vista.mostrarError("Seleccione un curso válido.");
        return false;
    }

    Estudiante estudiante = obtenerEstudiantePorId(idEstudiante);
    Curso curso = buscarCursoPorCodigo(codigoCurso);

    if (estudiante == null || curso == null) {
        vista.mostrarError("No se encontró el estudiante o el curso.");
        return false;
    }

    boolean exito = estudiante.inscribir(curso);
    if (exito) {
        vista.mostrarMensaje("Estudiante inscrito en el curso " + codigoCurso + ".");
    } else {
        vista.mostrarError("No se pudo inscribir (ya está inscrito o alcanzó el máximo de materias).");
    }
    return exito;
}

public boolean cambiarEstadoEstudiante(int idEstudiante, String nuevoEstado) {
    if (nuevoEstado == null || nuevoEstado.isEmpty() || nuevoEstado.equals("Seleccionar...")) {
        vista.mostrarError("Seleccione un estado válido.");
        return false;
    }

    Estudiante estudiante = obtenerEstudiantePorId(idEstudiante);
    if (estudiante == null) {
        vista.mostrarError("No se encontró el estudiante.");
        return false;
    }

    try {
        EstadoMatricula estado = EstadoMatricula.valueOf(nuevoEstado);
        estudiante.setEstadoMatricula(estado);
        vista.mostrarMensaje("Estado actualizado a " + nuevoEstado + ".");
        return true;
    } catch (IllegalArgumentException ex) {
        vista.mostrarError("Estado no válido.");
        return false;
    }
}
}