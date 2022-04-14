package br.com.hoteis.melhores.hotelbomdemais.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	
	private String cep;
	private String endereco;
	private String numero;
	private String complemento;
	private String bairro;
	private String estado;
	private String cidade;
	
	private String site;
	private boolean zonaPets;
	private boolean zonaKids;
	private boolean zonaLazer;
	private boolean estacionamento;
	
	@ManyToOne
	private TipoHotel tipoHotel;
	@ManyToOne
	private TipoQuarto tipoQuarto;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private int preco;
	
	// retorna as fotos em vetor de string
	public String[] verFotos() {
		return fotos.split(";");
	}
}
