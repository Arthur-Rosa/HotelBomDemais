package br.com.hoteis.melhores.hotelbomdemais.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import br.com.hoteis.melhores.hotelbomdemais.util.HashUtil;
import lombok.Data;

//cria getters & setters
@Data
//mapeia a entidade para o JPA
@Entity
public class Administrador {
	// chave primaria e auto incremento
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@NotEmpty
		private String nome;
		// define a coluna email como indice unico
		@Column(unique = true)
		@Email
		private String email;
		@NotEmpty
		private String senha;
		
		// metodo set que aplica o hash na senha
		public void setSenha(String senha) {
			this.senha = HashUtil.hash(senha);
		}
		
		public void setSenhaComHash(String hash) {
			this.senha = hash;
		}
}
