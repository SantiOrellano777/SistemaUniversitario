/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Estudiante
 */

//jajaja
public class Curso {
    private String codigo;
    private int creditos;
    private List<Estudiante> estudiantesInscritos;
    private Profesor profesor;
    
    
    public Curso(String codigo, int creditos) {
        this.codigo = codigo;
        this.creditos = creditos;
        this.estudiantesInscritos = new ArrayList<>();
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    
    public List<Estudiante> getEstudiantesInscritos() {
        return estudiantesInscritos;
    }
    
    public Profesor getProfesor() {
    return profesor;
}

    public void setProfesor(Profesor profesor) {
    this.profesor = profesor;
    }
    
    void matricular(Estudiante estudiante) {
        if (!estudiantesInscritos.contains(estudiante)) {
            estudiantesInscritos.add(estudiante);
        }
    }

    
    @Override
public String toString() {
    String nombreProfesor = (profesor != null) ? profesor.getNombre() + " " + profesor.getApellido() : "(ninguno)";
    return "Curso [codigo=" + codigo + ", creditos=" + creditos + ", profesor=" + nombreProfesor + "]";
}
}
