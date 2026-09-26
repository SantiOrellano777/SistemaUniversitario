package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EstudianteView extends JFrame {

    // ── Constantes finales para dimensiones ────────────────────────────────────
    private static final int ANCHO_VENTANA = 1150;
    private static final int ALTO_VENTANA = 850;
    private static final int ANCHO_CAMPO_BUSQUEDA = 18;
    private static final int ANCHO_CAMPO_AGREGAR = 12;
    private static final int ALTO_FILA_TABLA = 24;

    // ── Constantes finales para textos ─────────────────────────────────────────
    private static final String TITULO_VENTANA = "Gestión de Estudiantes — MVC (Búsqueda + Agregar)";
    private static final String TITULO_PANEL_BUSQUEDA = "Buscar estudiante por nombre";
    private static final String TITULO_PANEL_CARRERA = "Buscar por carrera";
    private static final String TITULO_PANEL_AGREGAR = "Agregar nuevo estudiante";
    private static final String TITULO_PANEL_RESULTADOS = "Resultados";
    private static final String LABEL_NOMBRE = "Nombre:";
    private static final String LABEL_APELLIDO = "Apellido:";
    private static final String LABEL_CARRERA = "Carrera:";
    private static final String LABEL_PROMEDIO = "Promedio:";
    private static final String BOTON_BUSCAR = "Buscar";
    private static final String BOTON_BUSCAR_CARRERA = "Buscar por Carrera";
    private static final String BOTON_LIMPIAR = "Limpiar";
    private static final String BOTON_AGREGAR = "Agregar Estudiante";
    private static final String OPCION_SELECCIONAR = "Seleccionar...";
    private static final String MENSAJE_INICIAL = "Ingrese un nombre o seleccione una carrera y presione Buscar.";
    private static final String MENSAJE_ENCONTRADO_UNO = "Se encontró 1 estudiante.";
    private static final String MENSAJE_ENCONTRADOS_VARIOS = "Se encontraron {0} estudiante(s).";
    private static final String MENSAJE_SIN_RESULTADOS = "No se encontraron estudiantes con ese criterio.";

    // ── Constantes finales para colores ────────────────────────────────────────
    private static final Color COLOR_BOTON_FONDO = new Color(59, 139, 212);
    private static final Color COLOR_BOTON_CARRERA = new Color(76, 175, 80);
    private static final Color COLOR_BOTON_LIMPIAR = new Color(244, 67, 54);
    private static final Color COLOR_BOTON_AGREGAR = new Color(103, 58, 183);
    private static final Color COLOR_BOTON_TEXTO = Color.WHITE;
    private static final Color COLOR_BOTON_INSCRIBIR = new Color(255, 152, 0);
    private static final Color COLOR_ESTADO_TEXTO = Color.GRAY;
    private static final String TITULO_PANEL_CURSO = "Cursos: inscripción y consulta";
private static final String TITULO_PANEL_ESTADO = "Estado de matrícula: buscar y cambiar";
private static final String LABEL_CURSO = "Curso:";
private static final String LABEL_ESTADO = "Nuevo estado:";
private static final String BOTON_BUSCAR_CURSO = "Ver estudiantes del curso";
private static final String BOTON_BUSCAR_ESTADO = "Buscar por estado";
private static final String BOTON_INSCRIBIR_CURSO = "Inscribir en curso";
private static final String BOTON_CAMBIAR_ESTADO = "Cambiar estado";
private static final String NOTA_INSCRIBIR = "(primero busque y seleccione un estudiante en la tabla)";
private static final String NOTA_CAMBIAR_ESTADO = "(\"Cambiar estado\" requiere seleccionar un estudiante en la tabla)";

private static final String TITULO_PANEL_PROFESORES = "Profesores: agregar y asignar a curso";
private static final String LABEL_SALARIO_BASE = "Salario base:";
private static final String LABEL_PROFESOR = "Profesor:";
private static final String LABEL_CURSO_ASIGNAR = "Curso a asignar:";
private static final String BOTON_AGREGAR_PROFESOR = "Agregar Profesor";
private static final String BOTON_VER_CURSOS_PROFESOR = "Ver cursos del profesor";
private static final String BOTON_ASIGNAR_CURSO = "Asignar a curso";

    // ── Columnas de la tabla (constante final) ─────────────────────────────────
    private static final String[] COLUMNAS_TABLA = {"ID", "Nombre", "Apellido", "Carrera", "Promedio", "Estado"};
    private static final int INDICE_PROMEDIO = 4;

    // ── Componentes UI - Búsqueda por nombre ────────────────────────────────────
    private JTextField             txtNombre;
    private JButton                btnBuscar;

    // ── Componentes UI - Búsqueda por carrera ──────────────────────────────────
    private JComboBox<String>      cmbCarrera;
    private JButton                btnBuscarCarrera;
    private JButton                btnLimpiar;
    private JComboBox<String> cmbCurso;
    private JButton btnBuscarCurso;
    private JButton                btnInscribirCurso;
    private JLabel                 lblProfesorAsignado;
    
    private JTextField             txtProfesorNombre;
private JTextField             txtProfesorApellido;
private JSpinner                spinSalarioBase;
private JButton                btnAgregarProfesor;
private JComboBox<String>      cmbProfesor;
private JButton                btnVerCursosProfesor;
private JLabel                 lblCursosProfesor;
private JComboBox<String>      cmbCursoAsignar;
private JButton                btnAsignarCurso;
    
    private JComboBox<String> cmbEstado;
    private JButton btnBuscarEstado;
    private JButton                btnCambiarEstado;

    // ── Componentes UI - Agregar estudiante ────────────────────────────────────
    private JTextField             txtAgregarNombre;
    private JTextField             txtAgregarApellido;
    private JComboBox<String>      cmbAgregarCarrera;
    private JSpinner               spinPromedio;
    private JButton                btnAgregar;

    // ── Componentes UI - Resultados y Estado ────────────────────────────────────
    private JTable                 tblResultados;
    private DefaultTableModel      modeloTabla;
    private JLabel                 lblEstado;
    private JLabel                 lblTotalEstudiantes;

    // ── Controlador ───────────────────────────────────────────────────────────
    private EstudianteController controlador;
    private int idEstudianteSeleccionado = -1;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    // ── Inicialización de componentes ─────────────────────────────────────────

   
    private void initComponentes() {
        setTitle(TITULO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ────────────────────────────────────────────────────────────────────────
        // PANEL SUPERIOR: Búsqueda y Agregar (con GridLayout)
        // ────────────────────────────────────────────────────────────────────────

        // Panel búsqueda por nombre (Fila 1)
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_BUSQUEDA));

        JLabel lblNombre = new JLabel(LABEL_NOMBRE);
        txtNombre = new JTextField(ANCHO_CAMPO_BUSQUEDA);
        btnBuscar = new JButton(BOTON_BUSCAR);
        btnBuscar.setBackground(COLOR_BOTON_FONDO);
        btnBuscar.setForeground(COLOR_BOTON_TEXTO);
        btnBuscar.setFocusPainted(false);

        panelBusqueda.add(lblNombre);
        panelBusqueda.add(txtNombre);
        panelBusqueda.add(btnBuscar);

        // Panel búsqueda por carrera (Fila 2)
        JPanel panelCarrera = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelCarrera.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_CARRERA));

        JLabel lblCarrera = new JLabel(LABEL_CARRERA);
        cmbCarrera = new JComboBox<>();
        cmbCarrera.addItem(OPCION_SELECCIONAR);
        // Se carga después, cuando el controlador esté disponible

        btnBuscarCarrera = new JButton(BOTON_BUSCAR_CARRERA);
        btnBuscarCarrera.setBackground(COLOR_BOTON_CARRERA);
        btnBuscarCarrera.setForeground(COLOR_BOTON_TEXTO);
        btnBuscarCarrera.setFocusPainted(false);

        btnLimpiar = new JButton(BOTON_LIMPIAR);
        btnLimpiar.setBackground(COLOR_BOTON_LIMPIAR);
        btnLimpiar.setForeground(COLOR_BOTON_TEXTO);
        btnLimpiar.setFocusPainted(false);

        panelCarrera.add(lblCarrera);
        panelCarrera.add(cmbCarrera);
        panelCarrera.add(btnBuscarCarrera);
        panelCarrera.add(btnLimpiar);
        
        panelCarrera.add(lblCarrera);
    panelCarrera.add(cmbCarrera);
    panelCarrera.add(btnBuscarCarrera);
    panelCarrera.add(btnLimpiar);

// Panel cursos: inscripción y consulta (Fila 3)
JPanel panelCurso = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
panelCurso.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_CURSO));

JLabel lblCurso = new JLabel(LABEL_CURSO);
cmbCurso = new JComboBox<>();
cmbCurso.addItem(OPCION_SELECCIONAR);
// Se carga después, cuando el controlador esté disponible

btnBuscarCurso = new JButton(BOTON_BUSCAR_CURSO);
btnBuscarCurso.setBackground(COLOR_BOTON_CARRERA);
btnBuscarCurso.setForeground(COLOR_BOTON_TEXTO);
btnBuscarCurso.setFocusPainted(false);

btnInscribirCurso = new JButton(BOTON_INSCRIBIR_CURSO);
btnInscribirCurso.setBackground(COLOR_BOTON_INSCRIBIR);
btnInscribirCurso.setForeground(COLOR_BOTON_TEXTO);
btnInscribirCurso.setFocusPainted(false);

JLabel lblNotaInscribir = new JLabel(NOTA_INSCRIBIR);
lblNotaInscribir.setForeground(COLOR_ESTADO_TEXTO);

lblProfesorAsignado = new JLabel("Profesor asignado: (ninguno)");

panelCurso.add(lblCurso);
panelCurso.add(cmbCurso);
panelCurso.add(btnBuscarCurso);
panelCurso.add(btnInscribirCurso);
panelCurso.add(lblNotaInscribir);
panelCurso.add(lblProfesorAsignado);


    // Panel estado de matrícula: buscar y cambiar (Fila 4)
JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
panelEstado.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_ESTADO));

JLabel lblEstadoBusqueda = new JLabel(LABEL_ESTADO);
cmbEstado = new JComboBox<>();
cmbEstado.addItem(OPCION_SELECCIONAR);
// Se carga después, cuando el controlador esté disponible

btnBuscarEstado = new JButton(BOTON_BUSCAR_ESTADO);
btnBuscarEstado.setBackground(COLOR_BOTON_CARRERA);
btnBuscarEstado.setForeground(COLOR_BOTON_TEXTO);
btnBuscarEstado.setFocusPainted(false);

btnCambiarEstado = new JButton(BOTON_CAMBIAR_ESTADO);
btnCambiarEstado.setBackground(COLOR_BOTON_AGREGAR);
btnCambiarEstado.setForeground(COLOR_BOTON_TEXTO);
btnCambiarEstado.setFocusPainted(false);

JLabel lblNotaCambiarEstado = new JLabel(NOTA_CAMBIAR_ESTADO);
lblNotaCambiarEstado.setForeground(COLOR_ESTADO_TEXTO);

panelEstado.add(lblEstadoBusqueda);
panelEstado.add(cmbEstado);
panelEstado.add(btnBuscarEstado);
panelEstado.add(btnCambiarEstado);
panelEstado.add(lblNotaCambiarEstado);

    
    panelEstado.add(lblEstadoBusqueda);
panelEstado.add(cmbEstado);
panelEstado.add(btnBuscarEstado);

// Panel profesores: agregar y asignar a curso (Fila 5)
// Panel profesores: agregar y asignar a curso (Fila 5)
JPanel panelProfesores = new JPanel(new GridLayout(2, 1));
panelProfesores.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_PROFESORES));

JPanel panelProfesoresFila1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
JPanel panelProfesoresFila2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));

JLabel lblProfesorNombre = new JLabel(LABEL_NOMBRE);
txtProfesorNombre = new JTextField(ANCHO_CAMPO_AGREGAR);

JLabel lblProfesorApellido = new JLabel(LABEL_APELLIDO);
txtProfesorApellido = new JTextField(ANCHO_CAMPO_AGREGAR);

JLabel lblSalarioBase = new JLabel(LABEL_SALARIO_BASE);
spinSalarioBase = new JSpinner(new SpinnerNumberModel(1000000, 0, 50000000, 50000));
spinSalarioBase.setPreferredSize(new Dimension(100, 25));

btnAgregarProfesor = new JButton(BOTON_AGREGAR_PROFESOR);
btnAgregarProfesor.setBackground(COLOR_BOTON_AGREGAR);
btnAgregarProfesor.setForeground(COLOR_BOTON_TEXTO);
btnAgregarProfesor.setFocusPainted(false);

JLabel lblProfesor = new JLabel(LABEL_PROFESOR);
cmbProfesor = new JComboBox<>();
cmbProfesor.addItem(OPCION_SELECCIONAR);

btnVerCursosProfesor = new JButton(BOTON_VER_CURSOS_PROFESOR);
btnVerCursosProfesor.setBackground(COLOR_BOTON_CARRERA);
btnVerCursosProfesor.setForeground(COLOR_BOTON_TEXTO);
btnVerCursosProfesor.setFocusPainted(false);

lblCursosProfesor = new JLabel("Cursos del profesor: —");

JLabel lblCursoAsignar = new JLabel(LABEL_CURSO_ASIGNAR);
cmbCursoAsignar = new JComboBox<>();
cmbCursoAsignar.addItem(OPCION_SELECCIONAR);

btnAsignarCurso = new JButton(BOTON_ASIGNAR_CURSO);
btnAsignarCurso.setBackground(COLOR_BOTON_AGREGAR);
btnAsignarCurso.setForeground(COLOR_BOTON_TEXTO);
btnAsignarCurso.setFocusPainted(false);

panelProfesoresFila1.add(lblProfesorNombre);
panelProfesoresFila1.add(txtProfesorNombre);
panelProfesoresFila1.add(lblProfesorApellido);
panelProfesoresFila1.add(txtProfesorApellido);
panelProfesoresFila1.add(lblSalarioBase);
panelProfesoresFila1.add(spinSalarioBase);
panelProfesoresFila1.add(btnAgregarProfesor);

panelProfesoresFila2.add(lblProfesor);
panelProfesoresFila2.add(cmbProfesor);
panelProfesoresFila2.add(btnVerCursosProfesor);
panelProfesoresFila2.add(lblCursosProfesor);
panelProfesoresFila2.add(lblCursoAsignar);
panelProfesoresFila2.add(cmbCursoAsignar);
panelProfesoresFila2.add(btnAsignarCurso);

panelProfesores.add(panelProfesoresFila1);
panelProfesores.add(panelProfesoresFila2);

// Panel agregar estudiante (Fila 6)

        // Panel agregar estudiante (Fila 3)
        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelAgregar.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_AGREGAR));

        JLabel lblAgregarNombre = new JLabel(LABEL_NOMBRE);
        txtAgregarNombre = new JTextField(ANCHO_CAMPO_AGREGAR);

        JLabel lblAgregarApellido = new JLabel(LABEL_APELLIDO);
        txtAgregarApellido = new JTextField(ANCHO_CAMPO_AGREGAR);

        JLabel lblAgregarCarrera = new JLabel(LABEL_CARRERA);
        cmbAgregarCarrera = new JComboBox<>();
        cmbAgregarCarrera.addItem(OPCION_SELECCIONAR);
        // Se carga después, cuando el controlador esté disponible

        JLabel lblAgregarPromedio = new JLabel(LABEL_PROMEDIO);
        spinPromedio = new JSpinner(new SpinnerNumberModel(3.0, 0.0, 5.0, 0.1));
        spinPromedio.setPreferredSize(new Dimension(60, 25));

        btnAgregar = new JButton(BOTON_AGREGAR);
        btnAgregar.setBackground(COLOR_BOTON_AGREGAR);
        btnAgregar.setForeground(COLOR_BOTON_TEXTO);
        btnAgregar.setFocusPainted(false);

        panelAgregar.add(lblAgregarNombre);
        panelAgregar.add(txtAgregarNombre);
        panelAgregar.add(lblAgregarApellido);
        panelAgregar.add(txtAgregarApellido);
        panelAgregar.add(lblAgregarCarrera);
        panelAgregar.add(cmbAgregarCarrera);
        panelAgregar.add(lblAgregarPromedio);
        panelAgregar.add(spinPromedio);
        panelAgregar.add(btnAgregar);

        // Panel superior con GridLayout (6 filas, 1 columna)
JPanel panelSuperior = new JPanel(new GridLayout(6, 1, 5, 5));
panelSuperior.add(panelBusqueda);
panelSuperior.add(panelCarrera);
panelSuperior.add(panelCurso);
panelSuperior.add(panelEstado);
panelSuperior.add(panelProfesores);
panelSuperior.add(panelAgregar);

        // ────────────────────────────────────────────────────────────────────────
        // PANEL CENTRAL: Tabla de resultados
        // ────────────────────────────────────────────────────────────────────────

        modeloTabla = new DefaultTableModel(COLUMNAS_TABLA, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(ALTO_FILA_TABLA);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tblResultados.getSelectionModel().addListSelectionListener((e) -> {
    if (!e.getValueIsAdjusting()) {
        int fila = tblResultados.getSelectedRow();
        if (fila >= 0) {
            idEstudianteSeleccionado = (int) modeloTabla.getValueAt(fila, 0); // columna 0 = ID
        } else {
            idEstudianteSeleccionado = -1;
        }
    }
});

        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createTitledBorder(TITULO_PANEL_RESULTADOS));

        // ────────────────────────────────────────────────────────────────────────
        // PANEL INFERIOR: Estado y Total de estudiantes
        // ────────────────────────────────────────────────────────────────────────

        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));

        lblEstado = new JLabel(MENSAJE_INICIAL);
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblEstado.setForeground(COLOR_ESTADO_TEXTO);

        lblTotalEstudiantes = new JLabel();
        lblTotalEstudiantes.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblTotalEstudiantes.setForeground(Color.BLUE);
        actualizarTotalEstudiantes();

        panelInferior.add(lblEstado, BorderLayout.WEST);
        panelInferior.add(lblTotalEstudiantes, BorderLayout.EAST);

        // ────────────────────────────────────────────────────────────────────────
        // Agregar todo al JFrame
        // ────────────────────────────────────────────────────────────────────────

        add(panelSuperior,    BorderLayout.NORTH);
        add(scroll,           BorderLayout.CENTER);
        add(panelInferior,    BorderLayout.SOUTH);
    }

    // ── Métodos de inicialización ─────────────────────────────────────────────

    /**
     * Carga las carreras disponibles desde el controlador al combo de búsqueda.
     */
    private void cargarCarreras() {
        if (controlador != null) {
            String[] carreras = controlador.obtenerCarrerasUnicas();
            for (String carrera : carreras) {
                cmbCarrera.addItem(carrera);
            }
        }
    }

    /**
     * Carga las carreras disponibles desde el controlador al combo de agregar.
     */
    private void cargarCarrerasAgregar() {
        if (controlador != null) {
            String[] carreras = controlador.obtenerCarrerasUnicas();
            for (String carrera : carreras) {
                cmbAgregarCarrera.addItem(carrera);
            }
        }
    }
    
    private void cargarCursos() {
    if (controlador != null) {
        String[] codigos = controlador.obtenerCodigosCursos();
        for (String codigo : codigos) {
            cmbCurso.addItem(codigo);
        }
    }
}

    private void cargarEstados() {
    if (controlador != null) {
        String[] estados = controlador.obtenerEstadosMatricula();
        for (String estado : estados) {
            cmbEstado.addItem(estado);
        }
    }
}
    
    private void cargarProfesores() {
    if (controlador != null) {
        cmbProfesor.removeAllItems();
        cmbProfesor.addItem(OPCION_SELECCIONAR);
        String[] nombres = controlador.obtenerNombresProfesores();
        for (String nombre : nombres) {
            cmbProfesor.addItem(nombre);
        }
    }
}

private void cargarCursosParaAsignar() {
    if (controlador != null) {
        String[] codigos = controlador.obtenerCodigosCursos();
        for (String codigo : codigos) {
            cmbCursoAsignar.addItem(codigo);
        }
    }
}

    // ── Eventos ───────────────────────────────────────────────────────────────

    /**
     * Método que encapsula la inicialización de eventos.
     */
    private void initEventos() {
        // Evento: buscar por nombre
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.buscarEstudiante(txtNombre.getText().trim());
            }
        });

        txtNombre.addActionListener((ActionEvent e) -> btnBuscar.doClick());

        // Evento: buscar por carrera
        btnBuscarCarrera.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String carriSelected = (String) cmbCarrera.getSelectedItem();
                if (carriSelected != null && !carriSelected.equals(OPCION_SELECCIONAR)) {
                    controlador.buscarEstudiantePorCarrera(carriSelected);
                } else {
                    mostrarError("Seleccione una carrera válida.");
                }
            }
        });

        // Evento: limpiar búsqueda
        btnLimpiar.addActionListener((ActionEvent e) -> {
            limpiarBusqueda();
        });
        
        
    // Evento: ver estudiantes del curso (y mostrar su profesor asignado)
btnBuscarCurso.addActionListener((ActionEvent e) -> {
    if (controlador != null) {
        String cursoSeleccionado = (String) cmbCurso.getSelectedItem();
        controlador.buscarEstudiantePorCurso(cursoSeleccionado);
        lblProfesorAsignado.setText("Profesor asignado: " + controlador.obtenerProfesorDeCurso(cursoSeleccionado));
    }
});


    btnBuscarEstado.addActionListener((ActionEvent e) -> {
        if (controlador != null) {
        String estadoSeleccionado = (String) cmbEstado.getSelectedItem();
        controlador.buscarEstudiantePorEstado(estadoSeleccionado);
        }
    });
    
    // Evento: inscribir estudiante seleccionado en el curso elegido
btnInscribirCurso.addActionListener((ActionEvent e) -> {
    if (controlador != null) {
        if (idEstudianteSeleccionado == -1) {
            mostrarError("Primero busque y seleccione un estudiante en la tabla.");
            return;
        }
        String cursoSeleccionado = (String) cmbCurso.getSelectedItem();
        controlador.inscribirEstudianteEnCurso(idEstudianteSeleccionado, cursoSeleccionado);
    }
});

// Evento: cambiar estado del estudiante seleccionado
btnCambiarEstado.addActionListener((ActionEvent e) -> {
    if (controlador != null) {
        if (idEstudianteSeleccionado == -1) {
            mostrarError("Primero busque y seleccione un estudiante en la tabla.");
            return;
        }
        String estadoSeleccionado = (String) cmbEstado.getSelectedItem();
        controlador.cambiarEstadoEstudiante(idEstudianteSeleccionado, estadoSeleccionado);
    }
});

// Evento: agregar nuevo profesor
btnAgregarProfesor.addActionListener((ActionEvent e) -> {
    if (controlador != null) {
        String nombre = txtProfesorNombre.getText().trim();
        String apellido = txtProfesorApellido.getText().trim();
        double salario = (double) (int) spinSalarioBase.getValue();

        if (controlador.agregarProfesor(nombre, apellido, salario)) {
            txtProfesorNombre.setText("");
            txtProfesorApellido.setText("");
            spinSalarioBase.setValue(1000000);
            cargarProfesores();
        }
    }
});

// Evento: ver los cursos de un profesor
btnVerCursosProfesor.addActionListener((ActionEvent e) -> {
    if (controlador != null) {
        String profesorSeleccionado = (String) cmbProfesor.getSelectedItem();
        if (profesorSeleccionado == null || profesorSeleccionado.equals(OPCION_SELECCIONAR)) {
            mostrarError("Seleccione un profesor válido.");
            return;
        }
        String cursos = controlador.obtenerCursosDeProfesor(profesorSeleccionado);
        lblCursosProfesor.setText("Cursos del profesor: " + cursos);
    }
});

// Evento: asignar profesor a un curso
btnAsignarCurso.addActionListener((ActionEvent e) -> {
    if (controlador != null) {
        String profesorSeleccionado = (String) cmbProfesor.getSelectedItem();
        String cursoSeleccionado = (String) cmbCursoAsignar.getSelectedItem();
        controlador.asignarProfesorACurso(profesorSeleccionado, cursoSeleccionado);
    }
});

        // Evento: agregar nuevo estudiante
        btnAgregar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String nombre = txtAgregarNombre.getText().trim();
                String apellido = txtAgregarApellido.getText().trim();
                String carrera = (String) cmbAgregarCarrera.getSelectedItem();
                double promedio = (double) spinPromedio.getValue();

                if (controlador.agregarEstudiante(nombre, apellido, carrera, promedio)) {
                    // Limpiar formulario
                    txtAgregarNombre.setText("");
                    txtAgregarApellido.setText("");
                    cmbAgregarCarrera.setSelectedIndex(0);
                    spinPromedio.setValue(3.0);
                    actualizarTotalEstudiantes();
                }
            }
        });
    }

    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        modeloTabla.addRow(fila);
        setEstado(MENSAJE_ENCONTRADO_UNO);
    }

    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            setEstado(MENSAJE_SIN_RESULTADOS);
            return;
        }
        for (Object[] fila : filas) {
            modeloTabla.addRow(fila);
        }
        setEstado(String.format(MENSAJE_ENCONTRADOS_VARIOS, filas.size()));
    }

    /**
     * Muestra un mensaje de error en la barra de estado.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        setEstado("Error: " + mensaje);
    }

    /**
     * Muestra un mensaje de información/éxito en la barra de estado.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
        setEstado(mensaje);
    }

    /**
     * Devuelve el texto ingresado en el campo de nombre.
     */
    public String getNombreBuscado() {
        return txtNombre.getText().trim();
    }

   
    public void setControlador(EstudianteController controlador) {
    this.controlador = controlador;
    cargarCarreras();
    cargarCarrerasAgregar();
    cargarCursos();
    cargarEstados();
    cargarProfesores();
    cargarCursosParaAsignar();
    actualizarTotalEstudiantes();
}


    private void actualizarTotalEstudiantes() {
        int total = (controlador != null) ? controlador.obtenerTotalEstudiantes() : 0;
        lblTotalEstudiantes.setText("Total de estudiantes: " + total);
    }

    /**
     * Limpia todos los campos de búsqueda y la tabla.
     */
    private void limpiarBusqueda() {
    txtNombre.setText("");
    cmbCarrera.setSelectedIndex(0);
    cmbCurso.setSelectedIndex(0);
    cmbEstado.setSelectedIndex(0);
    limpiarTabla();
    setEstado(MENSAJE_INICIAL);
}

    /**
     * Limpia todas las filas de la tabla.
     */
    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    /**
     * Actualiza el texto del label de estado.
     */
    private void setEstado(String texto) {
        lblEstado.setText(texto);
    }
}