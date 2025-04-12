package com.construccion2.pitagorasapi.Servlet;

import com.construccion2.pitagorasapi.DAO.CursoDao;
import com.construccion2.pitagorasapi.DAO.CursoDaoImpl;
import com.construccion2.pitagorasapi.Model.Curso;
import com.google.gson.*;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
        String nombreFacultad = request.getParameter("nombre");


        if (nombreFacultad == null || nombreFacultad.trim().isEmpty()){
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
        }else{
            System.out.println("Servlet GET: Buscando por facultad: " + nombreFacultad);
            List<Curso> cursosFacultad = this.cursoDao.buscarFacu(nombreFacultad);
            System.out.println("Servlet GET: Obteniendo cursos de la facultad " + nombreFacultad+ " (Cantidad: " + cursosFacultad.size() + ")"); // Log para consola
            String json = this.gson.toJson(cursosFacultad);
            out.print(json);

        }





    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        JsonObject responseJsonOutput = new JsonObject();
        try {

            JsonObject cursoJson = this.getParamsFromBody(req);

            // 3. Crear objeto Curso y setear campos manualmente
            Curso cursoNuevo = new Curso();
            // Extraer cada campo del JSON (con cuidado por si faltan)
            if (cursoJson.has("nombre")) cursoNuevo.setNombre(cursoJson.get("nombre").getAsString());
            if (cursoJson.has("codigo")) cursoNuevo.setCodigo(cursoJson.get("codigo").getAsString());
            if (cursoJson.has("profesor")) cursoNuevo.setProfesor(cursoJson.get("profesor").getAsString());
            if (cursoJson.has("cupoMaximo")) cursoNuevo.setCupoMaximo(cursoJson.get("cupoMaximo").getAsInt());
            if (cursoJson.has("facultad")) cursoNuevo.setFacultad(cursoJson.get("facultad").getAsString());
            if (cursoJson.has("nivel")) cursoNuevo.setNivel(cursoJson.get("nivel").getAsInt());
            if (cursoJson.has("fechaInicio")) cursoNuevo.setFechaInicio(cursoJson.get("fechaInicio").getAsString());

            //
            if (cursoJson.has("prerequisitos") && cursoJson.get("prerequisitos").isJsonArray()) {
                List<String> prereqs = new ArrayList<>();
                JsonArray arrayJson = cursoJson.getAsJsonArray("prerequisitos");
                for (JsonElement elemento : arrayJson) {
                    prereqs.add(elemento.getAsString());
                }
                cursoNuevo.setPrerequisitos(prereqs);
            }


            Curso cursoAgregado = this.cursoDao.add(cursoNuevo);


            if (cursoAgregado != null) {

                resp.setStatus(HttpServletResponse.SC_CREATED); // 201
                out.print(this.gson.toJson(cursoAgregado)); // Usamos gson para convertir el objeto de vuelta
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
                responseJsonOutput.addProperty("error", "Validación fallida al agregar (código duplicado, cupo inválido o prerrequisito no existe).");
                out.print(responseJsonOutput.toString());
            }

        } catch (Exception e) {
            // Error general (ej: JSON mal formado, error al leer, etc.)
            System.err.println("Error en doPost: " + e.getMessage());
            if (!resp.isCommitted()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 por defecto para errores de input/parsing
            }
            responseJsonOutput.addProperty("error", "Error procesando la petición: " + e.getMessage());
            out.print(responseJsonOutput.toString());
        } finally {
            out.flush();
        }
    }
    private JsonObject getParamsFromBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();
        return JsonParser.parseString(sb.toString()).getAsJsonObject();
    }



}
