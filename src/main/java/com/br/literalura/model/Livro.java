package com.br.literalura.model;

import jakarta.persistence.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private List<String> idiomas;
    private Integer downloads;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Autor autor;

    public Livro () {}
    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idiomas = dadosLivro.idiomas();
        this.downloads = dadosLivro.downloads();
        this.autor = new Autor(dadosLivro.autores().get(0));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Livro=id={0}, titulo=''{1}'', idiomas={2}, downloads={3}, autor={4}'}'", id, titulo, idiomas, downloads, autor);
    }
}
