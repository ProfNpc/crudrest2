package com.belval.crudrest.controller;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
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

import com.belval.crudrest.model.Produto;
import com.belval.crudrest.repository.ProdutoRepository2;

import jakarta.transaction.Transactional;

@RestController
public class ProdutoController {

//	private static List<Produto> listaProdutos = new ArrayList<>();
//	private static Integer proxId = 1;
	
//	private static ProdutoRepository repository = 
//			new ProdutoRepository();
	@Autowired
	private ProdutoRepository2 repository;
	

	static {
//		Produto prod = new Produto(1, "Pão", "Pão Francês", 0.5);
//		listaProdutos.add(prod);
	}
	
	@GetMapping("/produtos")
	public ResponseEntity<Iterable<Produto>> obterProdutos() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(repository.findAll());
	}

	//curl POST http://localhost:8080/produtos -H "Content-Type: application/json; Charset=utf-8" -d @produto-mortadela.json
	
	@PostMapping("/produtos")
	public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
		
		repository.save(produto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(produto);
	}
	
	//curl GET http://localhost:8080/produtos/1
	@GetMapping("/produtos/{id}")
	public ResponseEntity<Object> buscarProdutoPorId(@PathVariable Integer id) {
		
		Optional<Produto> produto = repository.findById(id);
		
		if(!produto.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado.");
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(produto.get());
	}
	
	//curl -X DELETE http://localhost:8080/produtos/1
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<Object> apagar(@PathVariable Integer id) {
		
		
		Optional<Produto> produto = repository.findById(id);
		
		if(!produto.isPresent()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado.");
		}
		
		repository.delete(produto.get());
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto apagado com sucesso!");	
	}
	
	//Observação: para métodos que não sejam o GET e o POST é necessário colocar o -X(menos xis maiúsculo)
	//curl -X PUT http://localhost:8080/produtos -H "Content-Type: application/json; Charset=utf-8" -d @produto-mortadela2.json
	@PutMapping("/produtos")
	@Transactional
	public ResponseEntity<Object> atualizarProduto(
			@RequestBody Produto produto) {
		
		//Aqui estamos considerando que o id vem no json de produto
		Optional<Produto> produtoEncontrado = repository.findById(produto.getId());
		
		if (produtoEncontrado.isEmpty()) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body("Produto não encontrado.");
		}
		
		Produto produtoManagedState = produtoEncontrado.get();
		BeanUtils.copyProperties(produto, produtoManagedState);
		
		//Com @Transactional não precisa chamar o método save(),
		//basta alterar os atributos da entidade retornada pelo repository
		//pois ela está no estado "admistrado"(managed state).
		//Nesse estado qualquer alteração de um atributo é detectado pelo 
		//hibernate e será atualizado quando o método terminar.
		//repository.save(produto);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body("Produto atualizado com sucesso.");
	}
	
}
