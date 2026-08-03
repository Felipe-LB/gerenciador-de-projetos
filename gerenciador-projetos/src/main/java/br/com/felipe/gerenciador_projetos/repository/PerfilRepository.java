package br.com.felipe.gerenciador_projetos.repository;

import br.com.felipe.gerenciador_projetos.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
