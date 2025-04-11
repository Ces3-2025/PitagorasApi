package com.construccion2.pitagorasapi.DAO;

import com.construccion2.pitagorasapi.Model.Curso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CursoDaoImpl implements CursoDao {
    private static final List<Curso> cursos = Collections.synchronizedList(new ArrayList<>());
    private static int id_Curso = 0;

    @Override
    public Curso add(Curso curso) {

        if (curso.getCupoMaximo() <= 0) {
            return null;
        }
        for (Curso c: cursos) {
            if (c.getCodigo().equalsIgnoreCase(curso.getCodigo())) {
                System.err.println("Error DAO: El código '" + curso.getCodigo() + "' ya existe.");
                return null;
            }
        }
        List<String> prereqCodigos = curso.getPrerequisitos();
        if (prereqCodigos != null && !prereqCodigos.isEmpty()) {
            for (String prereqCodigo : prereqCodigos) {
                if (buscarCodigoInterno(prereqCodigo) == null) {
                    System.err.println("Error DAO: Prerrequisito no encontrado: " + prereqCodigo);
                    return null;
                }
            }
        }
        curso.setId(++id_Curso);
        cursos.add(curso);
        return curso;

    }

    public List<Curso> buscarFacu(String facultad) {
        List<Curso> cursosFacultad = new ArrayList<>();
        for (Curso c: cursos) {
            if (c.getFacultad().equalsIgnoreCase(facultad)) {
                cursosFacultad.add(c);
            }
        }

        return cursosFacultad;
    }

    public List<Curso> buscarruta(String codigoCurso) {
        List<Curso> cursosRuta = new ArrayList<>();
        for (Curso c: cursos) {
            if (c.getCodigo().equalsIgnoreCase(codigoCurso)) {
                cursosRuta.add(c);
            }
        }

        return cursosRuta;
    }

    public List<Curso> getAll() {
        return new ArrayList<>(cursos);
    }

    private Curso buscarCodigoInterno(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }
        for (Curso cursoExistente : cursos) {
            if (cursoExistente.getCodigo() != null && cursoExistente.getCodigo().equalsIgnoreCase(codigo)) {
                return cursoExistente;
            }
        }
        return null;
    }



}
