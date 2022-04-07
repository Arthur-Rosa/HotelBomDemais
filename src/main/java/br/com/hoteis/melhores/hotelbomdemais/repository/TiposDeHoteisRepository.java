package br.com.hoteis.melhores.hotelbomdemais.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.hoteis.melhores.hotelbomdemais.model.TipoHotel;

public interface TiposDeHoteisRepository extends PagingAndSortingRepository<TipoHotel, Long> {
	@Query("SELECT h FROM TipoHotel h WHERE h.nome LIKE %:t% OR h.descricao LIKE %:t% OR h.palavraChave LIKE %:t%")
	public Page<TipoHotel> buscarPorTudo(@Param("t") String t, Pageable pageable);
	
	public List<TipoHotel> findAllByOrderByNomeAsc();
}
