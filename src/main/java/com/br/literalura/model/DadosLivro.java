package com.br.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") String autor,
        @JsonAlias("summaries") String resumo,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") int downloads)
{}
