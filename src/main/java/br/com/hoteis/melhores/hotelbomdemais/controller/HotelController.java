package br.com.hoteis.melhores.hotelbomdemais.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.hoteis.melhores.hotelbomdemais.model.Administrador;
import br.com.hoteis.melhores.hotelbomdemais.model.Hotel;
import br.com.hoteis.melhores.hotelbomdemais.model.TipoHotel;
import br.com.hoteis.melhores.hotelbomdemais.repository.HotelRepository;
import br.com.hoteis.melhores.hotelbomdemais.repository.TiposDeHoteisRepository;
import br.com.hoteis.melhores.hotelbomdemais.repository.TiposDeQuartosRepository;
import br.com.hoteis.melhores.hotelbomdemais.util.FirebaseUtil;

@Controller
public class HotelController {

	@Autowired
	private TiposDeHoteisRepository repTipoHotel;

	@Autowired
	private TiposDeQuartosRepository repTipoQuarto;

	@Autowired
	private HotelRepository repHotel;

	@Autowired
	private FirebaseUtil fireUtil;

	@RequestMapping("cadastroHotel")
	public String form(Model model) {
		model.addAttribute("tipoHotel", repTipoHotel.findAllByOrderByNomeAsc());
		model.addAttribute("tipoQuarto", repTipoQuarto.findAllByOrderByNomeAsc());
		return "hotel/hotelCadastro";
	}

	@RequestMapping("salvarHotel")
	public String salvar(Hotel hotel, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		// String para armazernar as URLs
		String fotos = hotel.getFotos();
		// percorre cada arquivo no vetor
		for (MultipartFile arquivo : fileFotos) {
			// verifica se o arquivo existe
			if (arquivo.getOriginalFilename().isEmpty()) {
				// vai para o proximo arquivo
				continue;
			}
			try {
				// faz o upload e Guarda a url na String fotos
				fotos += fireUtil.upload(arquivo) + ";";
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		// guarda na variavel hotel as fotos
		hotel.setFotos(fotos);
		// salva no DB
		repHotel.save(hotel);
		return "redirect:listarHotel/1";
	}

	@RequestMapping("deletarHotel")
	public String excluirHotel(Long id) {
		Hotel h = repHotel.findById(id).get();
		if(h.getFotos().length() > 0) {
			for (String foto : h.verFotos()) {
				fireUtil.deletar(foto);
			}
		}
		repHotel.delete(h);
		
		return "redirect:listarHotel/1";
	}
	
	@RequestMapping("listarHotel/{page}")
	public String listarHotel(Model model, @PathVariable("page") int page) {

		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));

		Page<Hotel> pagina = repHotel.findAll(pageable);
		int totalPages = pagina.getTotalPages();
		model.addAttribute("hoteis", pagina.getContent());
		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}
		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);

		return "hotel/listarHotel";
	}

	@RequestMapping("editarHotel")
	public String editar(Model model, Long id) {
		Hotel h = repHotel.findById(id).get();
		model.addAttribute("h", h);
		model.addAttribute("tipoHotel", repTipoHotel.findAllByOrderByNomeAsc());
		model.addAttribute("tipoQuarto", repTipoQuarto.findAllByOrderByNomeAsc());
		return "forward:cadastroHotel";
	}

	@RequestMapping("buscarOsHoteis/{page}")
	public String listarBusca(@PathVariable("page") int page, Model model, String t) {
		PageRequest pageable = PageRequest.of(page - 1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Hotel> pagina = repHotel.buscarPorTudo(t, pageable);
		int totalPages = pagina.getTotalPages();

		model.addAttribute("hoteis", pagina.getContent());

		List<Integer> numPaginas = new ArrayList<Integer>();

		for (int i = 1; i <= totalPages; i++) {
			numPaginas.add(i);
		}

		model.addAttribute("numPaginas", numPaginas);
		model.addAttribute("totalPags", totalPages);
		model.addAttribute("pagAtual", page);
		model.addAttribute("busca", t);
		return "hotel/listarHotel";
	}

	@RequestMapping("excluirFotos")
	public String excluirFotos(Long idHotel, int numFoto, Model model) {
		// busca hotel no banco
		Hotel h = repHotel.findById(idHotel).get();
		// busca a URL da foto
		String urlFoto = h.verFotos()[numFoto];
		// deleta a foto
		fireUtil.deletar(urlFoto);
		// remove a url do atributo fotos
		h.setFotos(h.getFotos().replace(urlFoto+";", ""));
		// salva no BD
		repHotel.save(h);
		// coloca o h para o formulario
		model.addAttribute("h", h);
		return "forward:cadastroHotel";
	}
}
