package br.com.hoteis.melhores.hotelbomdemais.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.hoteis.melhores.hotelbomdemais.model.TipoQuarto;

public interface TiposDeQuartosRepository extends PagingAndSortingRepository<TipoQuarto, Long> {

}
