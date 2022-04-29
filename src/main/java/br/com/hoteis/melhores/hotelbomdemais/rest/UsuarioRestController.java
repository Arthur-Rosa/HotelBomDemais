package br.com.hoteis.melhores.hotelbomdemais.rest;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.hoteis.melhores.hotelbomdemais.annotation.Privado;
import br.com.hoteis.melhores.hotelbomdemais.annotation.Publico;
import br.com.hoteis.melhores.hotelbomdemais.model.Erro;
import br.com.hoteis.melhores.hotelbomdemais.model.Usuario;
import br.com.hoteis.melhores.hotelbomdemais.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController {
	@Autowired
	private UsuarioRepository repUsuario;

	@Publico
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario u) {
		try {
			// imsere usuario no banco
			repUsuario.save(u);
			// retorna um codigo http 201 e informa como acessar um recurso inserido
			// e acrescenta no corpo da resposta o objeto inserio
			return ResponseEntity.created(URI.create("/api/usuario/" + u.getId())).body(u);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, "Registro duplicado", e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			Erro erro = new Erro(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getClass().getName());
			return new ResponseEntity<Object>(erro, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Publico
	@RequestMapping("/{id}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable("id") Long id) {
		Optional<Usuario> u = repUsuario.findById(id);
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@Privado
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario, @PathVariable("id") Long id){
		if(id != usuario.getId()) {
			throw new RuntimeException("ID Inválido");
		}
		repUsuario.save(usuario);
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> excluir(@PathVariable Long idUsuario){
		repUsuario.deleteById(idUsuario);
		return ResponseEntity.noContent().build();
	}
	
}
