package br.com.hoteis.melhores.hotelbomdemais.controller;

import java.util.ArrayList;
import java.util.List;

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
import br.com.hoteis.melhores.hotelbomdemais.repository.TiposDeHoteisRepository;
import br.com.hoteis.melhores.hotelbomdemais.util.HashUtil;

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
	public String cadastrarTipo(@Valid TipoHotel h, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os Campos ...");
			return "redirect:cadastroTiposDeHoteis";
		}
		
		try {
			repTip.save(h);repTip.save(h);
			attr.addFlashAttribute("mensagemSucesso", "Hotel cadastro com Sucesso!! ID: " + h.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Ocorreu um erro ao cadastrar / Error : " + e.getMessage());
			return "redirect:cadastroTiposDeHoteis";
		}
		return "redirect:buscarHoteis/1";
	}

	@RequestMapping("editarTipo")
	public String editar(Long id, Model model) {
		TipoHotel t = repTip.findById(id).get();
		model.addAttribute("t", t);
		return "forward:cadastroTiposDeHoteis";
	}
	
	@RequestMapping("buscarHoteis/{page}")
	public String listarBusca(@PathVariable("page") int page, Model model, String t) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<TipoHotel> pagina = repTip.buscarPorTudo(t, pageable);
		int totalPages = pagina.getTotalPages();
		
		model.addAttribute("tipos", pagina.getContent());
		
		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		model.addAttribute("busca", t);
		return "tiposDeHoteis/listaTiposDeHoteis";
	}

	@RequestMapping("deletarTipo")
	public String deletarTipo(Long id) {
		repTip.deleteById(id);
		return "redirect:listarTiposDeHoteis/1";
	}
}
