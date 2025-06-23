package com.br.literalura.principal;

import com.br.literalura.model.DadosLivro;
import com.br.literalura.model.ResultadoGutendex;
import com.br.literalura.model.Livro;
import com.br.literalura.repository.LivroRepository;
import com.br.literalura.service.ConsumoApi;
import com.br.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books?search=";

    @Autowired
    private LivroRepository repositorio;
    private List<Livro> livros = new ArrayList<>();

    public Principal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    Escolha o número de sua opção:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para busca: ");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        var data = conversor.obterDados(json, ResultadoGutendex.class);
        Optional<DadosLivro> livroEncontrado = data.results()
                .stream()
                .filter(b -> b.titulo().toLowerCase().contains(nomeLivro.toLowerCase()))
                .findFirst();
        if (livroEncontrado.isPresent()) {
            Livro livro = new Livro(livroEncontrado.get());
            repositorio.save(livro);
        } else {
            System.out.println("Livro não encontrado!");
        }
    }

}
