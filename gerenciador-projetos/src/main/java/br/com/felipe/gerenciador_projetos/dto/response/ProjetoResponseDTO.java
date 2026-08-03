package br.com.felipe.gerenciador_projetos.dto.response;

import java.time.LocalDate;
import java.util.Set;

public record ProjetoResponseDTO(
        Long id,
        String nome,
        String descricao,
        LocalDate dataInicio,
        LocalDate dataFim,
        Long proprietarioId,
        String proprietarioNome,
        Set<UsuarioResumoDTO> colaboradores
) {
}
