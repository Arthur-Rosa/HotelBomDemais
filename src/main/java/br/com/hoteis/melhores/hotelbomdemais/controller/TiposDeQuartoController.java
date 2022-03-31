package br.com.hoteis.melhores.hotelbomdemais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.hoteis.melhores.hotelbomdemais.model.TipoQuarto;
import br.com.hoteis.melhores.hotelbomdemais.repository.TiposDeQuartosRepository;

@Controller
public class TiposDeQuartoController {
	
	@Autowired
	private TiposDeQuartosRepository repQuarto;
	
	@RequestMapping("listarTiposDeQuartos")
	public String listar(Model model) {
		model.addAttribute("q", repQuarto.findAll());
		
		return "tiposDeQuarto/listaTiposDeQuarto";
	}
	
	@RequestMapping("cadastroTiposDeQuartos")
	public String cadas() {
		return "tiposDeQuarto/cadasTiposDeQuartos";
	}
	
	@RequestMapping(value = "salvarTiposDeQuartos", method = RequestMethod.POST)
	public String salvar(TipoQuarto t) {
		repQuarto.save(t);
		return "redirect:listarTiposDeQuartos";
	}
	
	@RequestMapping("editarTipoQuarto")
	public String editar(Long id, Model model) {
		TipoQuarto q = repQuarto.findById(id).get();
		model.addAttribute("q", q);
		return "forward:cadastroTiposDeQuartos";
	}
	
	@RequestMapping("deletarTipoQuarto")
	public String deletar(Long id) {
		repQuarto.deleteById(id);
		return "redirect:listarTiposDeQuartos";
	}
}
