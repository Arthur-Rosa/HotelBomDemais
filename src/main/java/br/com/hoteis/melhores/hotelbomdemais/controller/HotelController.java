package br.com.hoteis.melhores.hotelbomdemais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.hoteis.melhores.hotelbomdemais.repository.TiposDeHoteisRepository;
import br.com.hoteis.melhores.hotelbomdemais.repository.TiposDeQuartosRepository;

@Controller
public class HotelController {
	
	@Autowired
	private TiposDeHoteisRepository repTipoHotel;
	
	@Autowired
	private TiposDeQuartosRepository repTipoQuarto;
	
	@RequestMapping("cadastroHotel")
	public String form(Model model) {
		model.addAttribute("tipoHotel", repTipoHotel.findAllByOrderByNomeAsc());
		model.addAttribute("tipoQuarto", repTipoQuarto.findAllByOrderByNomeAsc());
		return "hotel/form";
	}
}
