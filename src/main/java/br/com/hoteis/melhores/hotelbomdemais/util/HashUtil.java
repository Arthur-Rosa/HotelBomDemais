package br.com.hoteis.melhores.hotelbomdemais.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	public static String hash(String palavra) {
		// "tempero" do hash
		String salt = "537d9f3c4a2f0172debd094f2b3c4c636cf2b75a71bfda331c185e7e7eb0304d";
		// adiciona o "tempero" à palavra
		palavra = salt + palavra;
		// gera o hash
		String hash = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		// retorna o hash
		return hash;
	}
}
