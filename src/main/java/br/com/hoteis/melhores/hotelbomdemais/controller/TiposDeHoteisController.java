package br.com.hoteis.melhores.hotelbomdemais.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.hoteis.melhores.hotelbomdemais.model.TipoHotel;
import br.com.hoteis.melhores.hotelbomdemais.repository.TiposDeHoteisRepository;

@Controller
public class TiposDeHoteisController {

	@Autowired
	private TiposDeHoteisRepository repTip;

	@RequestMapping("listarTiposDeHoteis/{page}")
	public String listarTipos(Model model, @PathVariable("page") int page) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<TipoHotel> pagina = repTip.findAll(pageable);
		int totalPages = pagina.getTotalPages();
		
		model.addAttribute("tipos", pagina.getContent());
		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		// adiciona os valores a model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		return "tiposDeHoteis/listaTiposDeHoteis";
	}

	@RequestMapping("cadastroTiposDeHoteis")
	public String cadastrarTiposFront() {
		return "tiposDeHoteis/cadasTiposDeHoteis";
	}

	@RequestMapping(value = "salvarTiposDeHoteis", method = RequestMethod.POST)
	public String cadastrarTipo(TipoHotel h) {
		repTip.save(h);

		return "redirect:listarTiposDeHoteis/1";
	}

	@RequestMapping("editarTipo")
	public String editar(Long id, Model model) {
		TipoHotel t = repTip.findById(id).get();
		model.addAttribute("t", t);
		return "forward:cadastroTiposDeHoteis";
	}

	@RequestMapping("deletarTipo")
	public String deletarTipo(Long id) {
		repTip.deleteById(id);
		return "redirect:listarTiposDeHoteis";
	}
}
