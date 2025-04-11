package com.construccion2.pitagorasapi.Servlet;

import com.construccion2.pitagorasapi.DAO.CursoDao;
import com.construccion2.pitagorasapi.DAO.CursoDaoImpl;
import com.construccion2.pitagorasapi.Model.Curso;
import com.google.gson.Gson;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/cursos")
public class CursoSrv extends HttpServlet {

    private CursoDao cursoDao;
    private Gson gson;

    public void init() throws ServletException {

        this.cursoDao = new CursoDaoImpl();
        this.gson = new Gson();
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try{
            List<Curso> todosLosCursos = this.cursoDao.getAll();
            System.out.println("Servlet GET: Obteniendo todos los cursos (Cantidad: " + todosLosCursos.size() + ")"); // Log para consola
            String json = this.gson.toJson(todosLosCursos);

            out.print(json);

        }catch (Exception e){
            System.err.println("Error al obtener los cursos: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\": \"Error al obtener los cursos\"}");
            return;
        }



    }


}
