package br.com.felipe.gerenciador_projetos.dto.response;

import java.time.LocalDateTime;

public record ErroResponseDTO(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        String caminho
) {
}
