package com.belval.crudrest.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(unique = true)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	
	private String nivelAcesso;
	
	private byte[] foto;
	
	@Column(name = "data_criacao_produto", columnDefinition = "DATETIME", nullable = false)
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCadastro;
	
	@Column(nullable = false)
	private String status;
	
	public Usuario() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNivelAcesso() {
		return nivelAcesso;
	}

	public void setNivelAcesso(String nivelAcesso) {
		this.nivelAcesso = nivelAcesso;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", nivelAcesso="
				+ nivelAcesso + ", foto=..." + /*Arrays.toString(foto) +*/ ", dataCadastro=" + dataCadastro + ", status="
				+ status + "]";
	}
	
	
}
