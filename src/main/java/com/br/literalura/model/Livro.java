package com.br.literalura.model;

import com.br.literalura.dto.DadosLivro;
import jakarta.persistence.*;

import java.text.MessageFormat;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idiomas;
    private Double downloads;

    @ManyToOne
    private Autor autor;

    public Livro () {}
    public Livro(DadosLivro dadosLivro, Autor autor) {
        this.titulo = dadosLivro.titulo();
        this.idiomas = dadosLivro.idiomas().get(0);
        this.downloads = dadosLivro.downloads();
        this.autor = autor;
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

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
    "\n----- LIVRO -----" +
            "\nTítulo: {0}" +
            "\nAutor: {1}" +
            "\nIdioma: {2}" +
            "\nNúmero de downloads: {3}" +
            "\n-----------------\n" , titulo, autor.getNome(), idiomas, downloads.toString());
    }
}
