package com.construccion2.pitagorasapi.DAO;

import com.construccion2.pitagorasapi.Model.Curso;

import java.util.List;

public interface CursoDao {
    Curso add(Curso curso);

    List<Curso> buscarFacu(String facultad);

    List<Curso> buscarruta(String codigoCurso);


    List<Curso> getAll();
}
