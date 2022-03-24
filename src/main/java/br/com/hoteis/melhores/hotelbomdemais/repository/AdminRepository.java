package br.com.hoteis.melhores.hotelbomdemais.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.hoteis.melhores.hotelbomdemais.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long> {

}
