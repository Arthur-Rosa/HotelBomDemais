package br.com.hoteis.melhores.hotelbomdemais.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.hoteis.melhores.hotelbomdemais.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long> {
	@Query("SELECT h FROM Administrador h WHERE h.nome LIKE %:t% OR h.email LIKE %:t%")
	public Page<Administrador> buscarPorTudo(@Param("t") String t, Pageable pageable);
	
	public Administrador findByEmailAndSenha(String email, String senha);
}
