package com.br.literalura.principal;

import com.br.literalura.dto.DadosAutor;
import com.br.literalura.model.Autor;
import com.br.literalura.dto.DadosLivro;
import com.br.literalura.dto.ResultadoGutendex;
import com.br.literalura.model.Livro;
import com.br.literalura.repository.AutorRepository;
import com.br.literalura.repository.LivroRepository;
import com.br.literalura.service.ConsumoApi;
import com.br.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

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
    private LivroRepository repositorioLivro;
    @Autowired
    private AutorRepository repositorioAutor;
    private List<Livro> livros = new ArrayList<>();

    public Principal(LivroRepository repositorioLivro, AutorRepository repositorioAutor) {
        this.repositorioLivro = repositorioLivro;
        this.repositorioAutor = repositorioAutor;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ------------
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
                    buscarLivroPorTitulo();
                    break;
                case 2:
                    listarLivros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        var idioma = leitura.nextLine();
        var lista = repositorioLivro.findByIdiomas(idioma);
        if (lista.isEmpty()) {
            System.out.println("Não existem livros nesse idioma no banco de dados.");
        } else {
            lista.stream().forEach(System.out::println);
        }
    }

    private void listarAutoresVivos() {
        System.out.println("Insira o ano que deseja pesquisar");
        var ano = leitura.nextInt();
        var lista = repositorioAutor.autoresVivos(ano);
        lista.stream().forEach(System.out::println);
    }

    private void listarAutores() {
        var lista = repositorioAutor.findAll();
        lista.stream().forEach(System.out::println);
    }

    private void listarLivros() {
        var lista = repositorioLivro.findAll();
        lista.stream().forEach(System.out::println);
    }

    public void buscarLivroPorTitulo() {
        DadosLivro dadosLivro = obterDadosLivro();

        // checar se o autor já foi salvo no banco de dados:
        if (dadosLivro != null) {
            DadosAutor dadosAutor = dadosLivro.autor().get(0);
            Livro livro;
            Autor autorExistente = repositorioAutor.findByNome(dadosAutor.nome());
            if (autorExistente != null) {
                livro = new Livro(dadosLivro, autorExistente);
            } else {
                Autor novoAutor = new Autor(dadosAutor);
                livro = new Livro(dadosLivro, novoAutor);
                repositorioAutor.save(novoAutor);
            }

            try {
                repositorioLivro.save(livro);
                System.out.println(livro);
            } catch (Exception e) {
                System.out.println("Livro já consta no banco de dados.");
            }
        }

    }

    private DadosLivro obterDadosLivro() {
        System.out.println("Digite o nome do livro para busca: ");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
        var data = conversor.obterDados(json, ResultadoGutendex.class);

        Optional<DadosLivro> livroEncontrado = data.results()
                .stream()
                .filter(l -> l.titulo().toLowerCase().contains(nomeLivro.toLowerCase()))
                .findFirst();

        if (livroEncontrado.isPresent()) {
            return livroEncontrado.get();
        } else {
            System.out.println("Livro não encontrado!");
            return null;
        }
    }

}
