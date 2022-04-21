package br.com.hoteis.melhores.hotelbomdemais.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.hoteis.melhores.hotelbomdemais.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// variavel para obter a URL
		String uri = request.getRequestURI();
		// váriavel para a sessão
		HttpSession session = request.getSession();
		if (uri.startsWith("/api")) {
			return true;
		} else {

			// se for pagina de erro libera
			if (uri.startsWith("/error")) {
				return true;
			}
			// Verificar se handler é um HandlerMethod
			// o que indica que ele está chamado um método
			// em algum controller
			if (handler instanceof HandlerMethod) {
				// casting de Object para HandlerMethod
				HandlerMethod metodo = (HandlerMethod) handler;
				// verifica se este metodo é público
				if (metodo.getMethodAnnotation(Publico.class) != null) {
					return true;
				}
				// verifica se existe um usuario logado
				if (session.getAttribute("usuarioLogado") != null) {
					return true;
				}
				// redireciona para a página inicial
				response.sendRedirect("/");
				return false;
			}

		}
		return true;
	}
}
