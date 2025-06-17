package com.br.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    private String author;
    private String summary;
    private List<String> idiomas;
    private Integer downloads;

    public Livro(DadosLivro dadosLivro) {
        this.name = dadosLivro.titulo();
        this.author = dadosLivro.autor();
        this.summary = dadosLivro.resumo();
        this.idiomas = dadosLivro.idioma();
        this.downloads = dadosLivro.downloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
        return "Livro{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", idiomas=" + idiomas +
                ", downloads=" + downloads +
                '}';
    }
}
