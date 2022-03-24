package br.com.hoteis.melhores.hotelbomdemais.controller;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hoteis.melhores.hotelbomdemais.model.Administrador;
import br.com.hoteis.melhores.hotelbomdemais.repository.AdminRepository;

@Controller
public class IndexController {

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

		try {
			// salva no db a entity
			repAdmin.save(admin);
			// mensagem de sucesso
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastro com Sucesso!! ID: " + admin.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Ocorreu um erro ao cadastrar / Error : " + e.getMessage());
		}
		return "redirect:cadastroAdmin";
	}
}
