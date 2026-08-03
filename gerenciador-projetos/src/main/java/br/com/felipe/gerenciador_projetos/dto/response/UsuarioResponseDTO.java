package br.com.felipe.gerenciador_projetos.dto.response;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        Long perfilId,
        String biografia,
        String urlFoto
) {
}
