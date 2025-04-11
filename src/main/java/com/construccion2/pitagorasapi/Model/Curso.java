package com.construccion2.pitagorasapi.Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects; // Necesario si descomentas equals/hashCode


public class Curso {


    private int id;
    private String nombre;
    private String codigo;
    private String profesor;
    private int cupoMaximo;
    private int estudiantesInscritos;
    private String facultad;

    private List<String> prerequisitos;
    private int nivel;
    private String fechaInicio;




    public Curso() {
        this.estudiantesInscritos = 0;
        this.prerequisitos = new ArrayList<>();
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public int getEstudiantesInscritos() {
        return estudiantesInscritos;
    }

    public void setEstudiantesInscritos(int estudiantesInscritos) {
        this.estudiantesInscritos = estudiantesInscritos;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public List<String> getPrerequisitos() {
        // Devuelve la lista (podría ser vacía, pero no null gracias al constructor)
        return prerequisitos;
    }

    public void setPrerequisitos(List<String> prerequisitos) {
        // Aseguramos que la lista nunca sea null internamente
        this.prerequisitos = (prerequisitos != null) ? prerequisitos : new ArrayList<>();
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        // Aquí podrías añadir validación de formato de fecha si quisieras
        this.fechaInicio = fechaInicio;
    }


    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", codigo='" + codigo + '\'' +
                ", profesor='" + profesor + '\'' +
                ", cupoMaximo=" + cupoMaximo +
                ", estudiantesInscritos=" + estudiantesInscritos +
                ", facultad='" + facultad + '\'' +
                ", prerequisitos=" + prerequisitos + // Añadido
                ", nivel=" + nivel +                 // Añadido
                ", fechaInicio='" + fechaInicio + '\'' + // Añadido
                '}';
    }

    // --- Métodos equals() y hashCode() (Opcionales pero recomendados si usas Sets/Maps) ---
    // Basados en 'codigo' (asumiendo que es el identificador único de negocio)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        // Comparamos por código, asumiendo que es único y no nulo
        return Objects.equals(codigo, curso.codigo);
    }

    @Override
    public int hashCode() {
        // Usamos el código para el hashCode
        return Objects.hash(codigo);
    }
}