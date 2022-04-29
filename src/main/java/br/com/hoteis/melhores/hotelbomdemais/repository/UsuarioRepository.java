package br.com.hoteis.melhores.hotelbomdemais.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.hoteis.melhores.hotelbomdemais.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

}
