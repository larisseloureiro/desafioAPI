package com.example.estudo.api.config.security;

public class TokenDto {
	private String token;
	private String tipo;
	
	public TokenDto(String token, String tipo) {
		this.token = token;
		this.tipo = tipo;
	}

	public String getToken() {
		return token;
	}

	public String getTipo() {
		return tipo;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
}
