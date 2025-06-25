package com.br.literalura.repository;

import com.br.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByNome(String s);
}
