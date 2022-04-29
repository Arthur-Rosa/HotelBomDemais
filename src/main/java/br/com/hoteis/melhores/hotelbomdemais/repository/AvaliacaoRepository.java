package br.com.hoteis.melhores.hotelbomdemais.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.hoteis.melhores.hotelbomdemais.model.Avaliacao;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long> {

}
