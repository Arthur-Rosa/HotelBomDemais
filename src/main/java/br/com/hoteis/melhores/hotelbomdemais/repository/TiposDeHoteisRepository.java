package br.com.hoteis.melhores.hotelbomdemais.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.hoteis.melhores.hotelbomdemais.model.TipoHotel;

public interface TiposDeHoteisRepository extends PagingAndSortingRepository<TipoHotel, Long> {

}
