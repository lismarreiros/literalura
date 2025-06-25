package com.br.literalura;

import com.br.literalura.principal.Principal;
import com.br.literalura.repository.AutorRepository;
import com.br.literalura.repository.LivroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private LivroRepository repositorioLivro;
	private AutorRepository repositorioAutor;

	public LiteraluraApplication(LivroRepository repositorioLivro, AutorRepository repositorioAutor) {
		this.repositorioLivro = repositorioLivro;
		this.repositorioAutor = repositorioAutor;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorioLivro, repositorioAutor);
		principal.exibeMenu();
	}
}
