package com.br.literalura.model;

import com.br.literalura.dto.DadosAutor;
import jakarta.persistence.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}
    public Autor(DadosAutor dadosAutor) {
        this.nome = String.valueOf(dadosAutor.nome());
        this.anoNascimento = Integer.valueOf(dadosAutor.anoNascimento());
        this.anoFalecimento = Integer.valueOf(dadosAutor.anoFalecimento());
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

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<String> getLivros() {
        return livros.stream().map(l->l.getTitulo()).collect(Collectors.toList());
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
        return MessageFormat.format(
                "\nAutor: {0}" +
                        "\nAno de nascimento: {1}" +
                        "\nAno de falecimento: {2} " +
                        "\nLivros: {3}", getNome(), getAnoNascimento().toString(), getAnoFalecimento().toString(), getLivros());
    }
}
