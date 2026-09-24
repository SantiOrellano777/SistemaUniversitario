package com.miapp.modelo;
import com.miapp.servicios.Inscribible;
import java.util.ArrayList;
import java.util.List;
/**
 * Modelo: representa la entidad Estudiante.
 */
public final class Estudiante extends Persona implements Inscribible {  

    private static int totalEstudiantes = 0;
    public static final int PROMEDIO_MINIMO = 0;
    public static final int PROMEDIO_MAXIMO = 5;
    public static final String CARRERA_PREDETERMINADA = "Sin especificar";
    public static final int MAX_MATERIAS = 5;
    private static final double COSTO_POR_CREDITO = 150000;

    // ── Atributos de instancia ────────────────────────────────────────────────
   
    private String carrera;
    private double promedio;
    private List<Curso> cursosInscritos;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Estudiante(int id, String nombre, String apellido, String carrera, double promedio) {
        super(nombre, apellido, id);
        this.carrera  = carrera;
   
        if (promedio >= PROMEDIO_MINIMO && promedio <= PROMEDIO_MAXIMO) {
            this.promedio = promedio;
        } else {
            this.promedio = 0.0;  // Por defecto si está fuera de rango
        }
        this.cursosInscritos = new ArrayList<>();
        // nuevo: Incrementa el contador estático de estudiantes
        totalEstudiantes++;
    }

    // ── Métodos estáticos (de clase) ──────────────────────────────────────────

    public static int getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public static void reiniciarContador() {
        totalEstudiantes = 0;
    }

    public static int getProximoId() {  
        return totalEstudiantes + 1;
    
    }


    public String getCarrera() { 
        return carrera; 
    }

    public double getPromedio() { 
        return promedio; 
    }

  

    public void setCarrera(String carrera) { 
        this.carrera = carrera; 
    }

    /**
     Valida el promedio antes de asignarlo usando constantes finales
     * @param p promedio a validar (debe estar entre PROMEDIO_MINIMO y PROMEDIO_MAXIMO)
     */
    public void setPromedio(double p) {
        // nuevo: Uso de constantes finales para validación
        if (p >= PROMEDIO_MINIMO && p <= PROMEDIO_MAXIMO) {
            this.promedio = p;
        }
    }
    
    public List<Curso> getCursosInscritos() {
        return cursosInscritos;
    }

    @Override
    public boolean inscribir(Curso curso) {
        if (curso == null) {
            return false;
        }
        if (cursosInscritos.contains(curso)) {
            return false;
        }
        if (cursosInscritos.size() >= MAX_MATERIAS) {
            return false;
        }
        cursosInscritos.add(curso);
        curso.matricular(this);
        return true;
    }
    
    @Override
    public double calcularPago() {
        double total = 0;
        for (Curso c : cursosInscritos) {
            total += c.getCreditos() * COSTO_POR_CREDITO;
        }
        return total;
    }

    /**
     Método final: no puede ser sobrescrito por subclases
     */
    @Override
    public final String toString() {
        return "ID: " + getId()
             + " | Nombre: " + getNombre()
             + " | Apellido: " + getApellido()
             + " | Carrera: " + carrera
             + " | Promedio: " + String.format("%.2f", promedio);
    }
}