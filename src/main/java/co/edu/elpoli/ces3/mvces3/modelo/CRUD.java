package co.edu.elpoli.ces3.mvces3.modelo;

import co.edu.elpoli.ces3.mvces3.DTO.DTOUsuarios;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUD {
    Usuario create(DTOUsuarios usuario) throws SQLException;

    public ArrayList<Usuario> all();

    public Usuario findById(int id) throws SQLException;

    Usuario update(Usuario usuario) throws SQLException;

    void delete(int usuarioId) throws SQLException;
}