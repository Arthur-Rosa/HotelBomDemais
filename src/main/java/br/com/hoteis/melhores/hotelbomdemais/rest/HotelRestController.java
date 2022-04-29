package br.com.hoteis.melhores.hotelbomdemais.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.hoteis.melhores.hotelbomdemais.annotation.Publico;
import br.com.hoteis.melhores.hotelbomdemais.model.Hotel;
import br.com.hoteis.melhores.hotelbomdemais.repository.HotelRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/hotel")
public class HotelRestController {

	@Autowired
	private HotelRepository repHotel;

	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Hotel> getHoteis(){
		return repHotel.findAll();
	}
	
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Hotel> getHotel(@PathVariable("id") Long idHotel){
		// tenta buscar o hotel no repository
		Optional<Hotel> optional = repHotel.findById(idHotel);
		// se o hotel existir
		if(optional.isPresent()) {
			return ResponseEntity.ok(optional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// select * from hoteis where tipo = 2
	@Publico
	@RequestMapping(value = "/tipo/{id}", method = RequestMethod.GET)
	public Iterable<Hotel> getHoteisTipos(@PathVariable("id") Long idTipo){
		return repHotel.buscarPorTipoHotel(idTipo);
	}
}
