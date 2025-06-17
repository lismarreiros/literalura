package com.br.literalura.principal;

import com.br.literalura.model.DadosLivro;
import com.br.literalura.model.Livro;
import com.br.literalura.repository.LivroRepository;
import com.br.literalura.service.ConsumoApi;
import com.br.literalura.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books?search";

    @Autowired
    private LivroRepository repositorio;
    private List<Livro> livros = new ArrayList<>();

    public Principal(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    Escolha o número de sua opção:
                    1 - buscar o livro pelo título
                    2 - listar livros registrados
                    3 - listar autores registrados
                    4 - listar autores vivos em um determinado ano
                    5 - listar livros em um determinado idioma
                    0 - sair
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

   private DadosLivro getDadosLivro() {
       System.out.println("Digite o nome do livro para busca: ");
       var nomeLivro = leitura.nextLine();
       var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "+"));
       DadosLivro dados = conversor.obterDados(json, DadosLivro.class);
       return dados;
   }

   private void buscarLivroWeb() {
        DadosLivro dados = getDadosLivro();
        Livro livro = new Livro(dados);
        repositorio.save(livro);
        System.out.println(dados);
   }
}
