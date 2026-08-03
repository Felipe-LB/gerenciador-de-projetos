package br.com.felipe.gerenciador_projetos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ProjetoRequestDTO(
        @NotBlank(message = "O nome do projeto é obrigatório")
        @Size(
                min = 3,
                max = 100,
                message = "O nome deve ter entre 3 e 100 caracteres"
        )
        String nome,

        @NotBlank(message = "A descrição é obrigatória")
        @Size(
                max = 500,
                message = "A descrição deve ter no máximo 500 caracteres"
        )
        String descricao,

        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio,

        LocalDate dataFim,

        @NotNull(message = "O proprietário é obrigatório")
        Long proprietarioId

) {
}
