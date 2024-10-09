package com.belval.crudrest.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.belval.crudrest.model.Usuario;
import com.belval.crudrest.repository.UsuarioRepository;

@RestController
public class UsuarioController {
	
	@Autowired
    private UsuarioRepository repository;
	
	@GetMapping("/usuarios")
	public ResponseEntity<Iterable<Usuario>> obterUsuarios() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(repository.findAll());
	}

	//curl POST http://localhost:8080/usuarios -H "Content-Type: application/json; Charset=utf-8" -d @usuario-ze.json
	
	@PostMapping("/usuarios")
	public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario Usuario) {
		
		repository.save(Usuario);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(Usuario);
	}
	
	//curl GET http://localhost:8080/usuarios/1
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Object> buscarUsuarioPorId(@PathVariable Integer id) {
		
		Optional<Usuario> Usuario = repository.findById(id);
		
		if(!Usuario.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Usuario não encontrado.");
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(Usuario.get());
	}
	
	//curl -X DELETE http://localhost:8080/usuarios/1
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<Object> apagar(@PathVariable Integer id) {
		
		
		Optional<Usuario> Usuario = repository.findById(id);
		
		if(!Usuario.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Usuario não encontrado.");
		}
		
		repository.delete(Usuario.get());
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Usuario apagado com sucesso!");	
	}
	
	//Observação: para métodos que não sejam o GET e o POST é necessário colocar o -X(menos xis maiúsculo)
	//curl -X PUT http://localhost:8080/usuario1/1 -H "Content-Type: application/json; Charset=utf-8" -d @usuario2.json
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Object> atualizarUsuario(
			@PathVariable(value = "id")Integer id,
			@RequestBody Usuario Usuario) {
		
		Optional<Usuario> UsuarioEncontrado = repository.findById(id);
		
		if (UsuarioEncontrado.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Usuario não encontrado.");
		}
		
		Usuario.setId(id);
		repository.save(Usuario);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Usuario atualizado com sucesso.");
	}
	
	
}
