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

import br.com.hoteis.melhores.hotelbomdemais.model.Administrador;
import br.com.hoteis.melhores.hotelbomdemais.repository.AdminRepository;
import br.com.hoteis.melhores.hotelbomdemais.util.HashUtil;

@Controller
public class AdminController {

	// variavel para persistencia dos dados
	@Autowired
	private AdminRepository repAdmin;

	@RequestMapping("/")
	public void handleRequest() {
		throw new RuntimeException("exception");
	}

	// request do formulario do tipo GET
	@RequestMapping("cadastroAdmin")
	public String cadastroAdmin() {
		return "administrador/cadastroAdministrador";
	}

	// Request mapping para salvar o administrador
	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		// verifica se houveram erros na validação
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os Campos ...");
			// volta para o request mapping do cadastroAdmin
			return "redirect:cadastroAdmin";
		}
		// variavel para descobrir alteração ou inserção
		boolean alteracao = admin.getId() != null ? true : false;
		// verificar se a senha esta vazia
		if (admin.getSenha().equals(HashUtil.hash(""))) {
			if (!alteracao) {
				// retirada a parte antes do arroba
				String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
				// setar a parte na senha do admin
				admin.setSenha(parte);
			} else {
				// buscar senha atual no banco
				String hash = repAdmin.findById(admin.getId()).get().getSenha();
				// "setar" o hash na senha
				admin.setSenhaComHash(hash);
			}
		}
		try {
			// salva no db a entity
			repAdmin.save(admin);
			// mensagem de sucesso
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastro com Sucesso!! ID: " + admin.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Ocorreu um erro ao cadastrar / Error : " + e.getMessage());
			return "redirect:cadastroAdmin";
		}
		return "redirect:listaAdmin/1";
	}

	@RequestMapping("listaAdmin/{page}")
	public String listaAdmin(Model model, @PathVariable("page") int page) {
		// cria um pageable informando os parametros da pagina
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		// cria um page de administrador atraves dos parametros passados pelo repository
		Page<Administrador> pagina = repAdmin.findAll(pageable);
		// variavel para total de paginas
		int totalPages = pagina.getTotalPages();
		// cria um list de inteiro para armazenar os ns das paginas
		model.addAttribute("admins", pagina.getContent());
		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		// adiciona os valores a model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		// retora para o html
		return "administrador/lista";
	}

	@RequestMapping("editarAdm")
	public String editarAdm(Long id, Model model) {
		Administrador adm = repAdmin.findById(id).get();
		model.addAttribute("valueAdm", adm);
		return "forward:cadastroAdmin";
	}

	@RequestMapping("deletarAdm")
	public String deletaAdm(Long id) {
		repAdmin.deleteById(id);
		return "redirect:listaAdmin/1";
	}

	@RequestMapping("buscarAdms/{page}")
	public String listarBusca(@PathVariable("page") int page, Model model, String t) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Administrador> pagina = repAdmin.buscarPorTudo(t, pageable);
		int totalPages = pagina.getTotalPages();
		List<Integer> numPaginas = new ArrayList<Integer>();

		model.addAttribute("admins", pagina.getContent());
		
		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		// adiciona os valores a model
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		model.addAttribute("busca", t);
		return "administrador/lista";
	}

}
