package co.edu.elpoli.ces3.mvces3.Servlet;

import co.edu.elpoli.ces3.mvces3.Controller.CtrUsuario;
import co.edu.elpoli.ces3.mvces3.DTO.DTOUsuarios;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "usuarioServlet", value = "/usuario")
public class UsuarioServlet extends MyServlet {
    private String message;

    private GsonBuilder gsonBuilder;

    private Gson gson;

    private ArrayList<DTOUsuarios> users;

    CtrUsuario ctr = new CtrUsuario();

    public void init() {
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        ArrayList<Object> usuarios = new ArrayList<>();

        DTOUsuarios usuario1 = new DTOUsuarios();
        usuario1 .id = 10;
        usuario1 .setNombre("Enmanuel");
        usuario1 .setCorreo("enmanuel123@gmail.com");
        usuario1 .setContrasena("12345678");

        usuarios.add(usuario1);

        for (int i = 0; i < usuarios.size(); i++)
        {
            System.out.println(usuarios.get(i));
        }
        message = "mensaje de validación!";
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        JsonObject body = this.getParamsFromPost(req);
        DTOUsuarios std = new DTOUsuarios(
                body.get("correo").getAsString(),
                body.get("nombre").getAsString(),
                body.get("contrasena").getAsString()
        );

        DTOUsuarios nuevoUsuario = ctr.addUsuario(std);

        out.print(gson.toJson(nuevoUsuario));
        out.flush();


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        String usuarioIdParam = req.getParameter("id");

        if (usuarioIdParam != null && !usuarioIdParam.isEmpty()) {
            int usuarioId = Integer.parseInt(usuarioIdParam);
            DTOUsuarios usuario = ctr.getUsuarioById(usuarioId);
            out.print(gson.toJson(usuario));
        } else {
            ArrayList<DTOUsuarios> usuarios = ctr.getAllUsuarios();
            out.print(gson.toJson(usuarios));
        }

        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        JsonObject body = gson.fromJson(stringBuilder.toString(), JsonObject.class);
        int usuarioId = body.get("id").getAsInt();

        DTOUsuarios updatedUsuario = new DTOUsuarios(
                body.get("correo").getAsString(),
                body.get("nombre").getAsString(),
                body.get("contrasena").getAsString()
        );

        DTOUsuarios resultado = ctr.updateUsuario(usuarioId, updatedUsuario);

        out.print(gson.toJson(resultado));
        out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        resp.setContentType("application/json");

        int usuarioId = Integer.parseInt(req.getParameter("id"));

        ctr.deleteUsuario(usuarioId);

        out.print(gson.toJson("Eliminado"));
        out.flush();
    }


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        switch (method){
            case "PATCH":
                this.doPatch(req, resp);
                break;
            default:
                super.service(req, resp);
        }

    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Metodo Patch");
    }

}
