package com.br.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String anoNascimento;
    private String anoFalecimento;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}
    public Autor(DadosAutor dadosAutor) {
        this.nome = dadosAutor.nomeAutor();
        this.anoNascimento = dadosAutor.anoNascimento();
        this.anoFalecimento = dadosAutor.anoFalecimento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(String anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public String getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(String anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = new ArrayList<>();
        livros.forEach(l -> {
            l.setAutor(this);
            this.livros.add(l);
        });
    }

    @Override
    public String toString() {
        List<String> livros = this.getLivros().stream().map(Livro::getTitulo).toList();
        return "\n----------------------------" +
                "\nNome: " + nome +
                "\n Ano de Nascimento :'" + anoNascimento + '\'' +
                "\n Ano de Falecimento='" + anoFalecimento + '\'' +
                "\n Livros {" + livros + "}" +
                "\n---------------------------";
    }
}
