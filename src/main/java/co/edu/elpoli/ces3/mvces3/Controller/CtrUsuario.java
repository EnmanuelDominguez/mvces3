package co.edu.elpoli.ces3.mvces3.Controller;

import co.edu.elpoli.ces3.mvces3.DTO.DTOUsuarios;
import co.edu.elpoli.ces3.mvces3.modelo.Usuario;

import javax.tools.DocumentationTool;
import java.sql.SQLException;
import java.util.ArrayList;

public class CtrUsuario {

    private Usuario modelUsuario;

    public CtrUsuario(){
        modelUsuario = new Usuario();
    }

    public DTOUsuarios addUsuario(DTOUsuarios usuario){
        try {
            Usuario nuevoUsuario = modelUsuario.create(usuario);
            return new DTOUsuarios(nuevoUsuario.getId(), nuevoUsuario.getCorreo(), nuevoUsuario.getNombre(), nuevoUsuario.getContrasena());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<DTOUsuarios> getAllUsuarios() {
        try {
            ArrayList<Usuario> usuarios = modelUsuario.all();
            ArrayList<DTOUsuarios> dtoUsuarios = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                DTOUsuarios dtoUsuario = new DTOUsuarios(
                        usuario.getId(),
                        usuario.getCorreo(),
                        usuario.getNombre(),
                        usuario.getContrasena()
                );
                dtoUsuarios.add(dtoUsuario);
            }

            return dtoUsuarios;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DTOUsuarios getUsuarioById(int usuarioId) {
        try {
            Usuario usuario = modelUsuario.findById(usuarioId);
            if (usuario != null) {
                return new DTOUsuarios(usuario.getId(), usuario.getCorreo(), usuario.getNombre(), usuario.getContrasena());
            } else {
                throw new RuntimeException("El usuario no se encuentra en la base de datos");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DTOUsuarios updateUsuario(int usuarioId, DTOUsuarios updateUsuario) {
        try {
            Usuario usuario = new Usuario(
                    usuarioId,
                    updateUsuario.getCorreo(),
                    updateUsuario.getNombre(),
                    updateUsuario.getContrasena()

            );

            Usuario updated = modelUsuario.update(usuario);
            return new DTOUsuarios(updated.getId(), updated.getCorreo(), updated.getNombre(),updated.getContrasena());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUsuario(int usuarioId) {
        try {
            modelUsuario.delete(usuarioId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}