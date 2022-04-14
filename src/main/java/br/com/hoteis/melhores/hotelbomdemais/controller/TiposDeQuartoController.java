package br.com.hoteis.melhores.hotelbomdemais.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hoteis.melhores.hotelbomdemais.model.TipoHotel;
import br.com.hoteis.melhores.hotelbomdemais.model.TipoQuarto;
import br.com.hoteis.melhores.hotelbomdemais.repository.TiposDeQuartosRepository;

@Controller
public class TiposDeQuartoController {

	@Autowired
	private TiposDeQuartosRepository repQuarto;

	@RequestMapping("listarTiposDeQuartos/{page}")
	public String listar(Model model, @PathVariable("page") int page, String t) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<TipoQuarto> pagina = repQuarto.findAll(pageable);
		int totalPages = pagina.getTotalPages();

		model.addAttribute("q", pagina.getContent());
		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		// adiciona os valores a model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		return "tiposDeQuarto/listaTiposDeQuarto";
	}

	@RequestMapping("cadastroTiposDeQuartos")
	public String cadas() {
		return "tiposDeQuarto/cadasTiposDeQuartos";
	}

	@RequestMapping(value = "salvarTiposDeQuartos", method = RequestMethod.POST)
	public String salvar(@Valid TipoQuarto t, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os Campos ...");
			return "redirect:cadastroTiposDeQuartos";
		}
		try {
			repQuarto.save(t);
			attr.addFlashAttribute("mensagemSucesso", "Quarto cadastro com Sucesso!! ID: " + t.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Ocorreu um erro ao cadastrar / Error : " + e.getMessage());
			return "redirect:cadastroTiposDeQuartos";
		}
		return "redirect:listarTiposDeQuartos/1";
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
		return "redirect:listarTiposDeQuartos/1";
	}
	
	@RequestMapping("buscarQuartos/{page}")
	public String listarBusca(@PathVariable("page") int page, Model model,String t) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<TipoQuarto> pagina = repQuarto.buscarPorTudo(t, pageable);
		int totalPages = pagina.getTotalPages();
		
		model.addAttribute("q", pagina.getContent());
		
		List<Integer> numPaginas = new ArrayList<Integer>();
		
		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		model.addAttribute("busca", t);
		return "tiposDeQuarto/listaTiposDeQuarto";
	}
	
}
