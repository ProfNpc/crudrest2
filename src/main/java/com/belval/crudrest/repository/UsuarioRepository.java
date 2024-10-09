package com.belval.crudrest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.belval.crudrest.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	
    //Select * from Usuario where nome = <parametro>
    //List<Usuario> findByNomeContains(String nome);
    List<Usuario> findByNome(String nome);

}
