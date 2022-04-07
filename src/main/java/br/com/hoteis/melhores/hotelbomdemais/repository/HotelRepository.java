package br.com.hoteis.melhores.hotelbomdemais.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.hoteis.melhores.hotelbomdemais.model.Hotel;

public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {

}
