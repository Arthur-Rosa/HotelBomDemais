package br.com.hoteis.melhores.hotelbomdemais.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.hoteis.melhores.hotelbomdemais.model.Hotel;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {
	@Query("SELECT h FROM Hotel h WHERE h.nome LIKE %:t% OR h.descricao LIKE %:t%")
	public Page<Hotel> buscarPorTudo(@Param("t") String t, Pageable pageable);
}
