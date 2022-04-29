package br.com.hoteis.melhores.hotelbomdemais.model;



import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class Erro {
	private HttpStatus statusCode;
	private String mensagem;
	private String exception;
	
	public Erro(HttpStatus statusCode, String mensagem, String exception) {
		this.exception = exception;
		this.mensagem = mensagem;
		this.statusCode = statusCode;
	}
}
