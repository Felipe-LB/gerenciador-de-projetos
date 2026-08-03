package br.com.felipe.gerenciador_projetos.repository;

import br.com.felipe.gerenciador_projetos.entity.Projeto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findByNomeContainingIgnoreCase(String nome);

    List<Projeto> findByProprietarioId(Long proprietarioId);

    Page<Projeto> findByNomeContainingIgnoreCase(
            String nome,
            Pageable pageable
    );
}