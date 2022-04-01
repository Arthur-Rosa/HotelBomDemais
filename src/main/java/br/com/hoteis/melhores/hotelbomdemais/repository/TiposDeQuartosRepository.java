package br.com.hoteis.melhores.hotelbomdemais.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.hoteis.melhores.hotelbomdemais.model.TipoQuarto;

public interface TiposDeQuartosRepository extends PagingAndSortingRepository<TipoQuarto, Long> {
	@Query("SELECT h FROM TipoQuarto h WHERE h.nome LIKE %:t% OR h.descricao LIKE %:t% OR h.palavraChave LIKE %:t%")
	public Page<TipoQuarto> buscarPorTudo(@Param("t") String t, Pageable pageable);
}
