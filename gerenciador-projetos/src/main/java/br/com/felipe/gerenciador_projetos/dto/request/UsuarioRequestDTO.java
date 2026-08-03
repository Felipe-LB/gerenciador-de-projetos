package br.com.felipe.gerenciador_projetos.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(  @NotBlank(message = "O nome é obrigatório")
                                  @Size(
                                          min = 3,
                                          max = 100,
                                          message = "O nome deve ter entre 3 e 1000 caracteres"
                                  )
                                  String nome,

                                  @NotBlank(message = "O e-mail é obrigatório")
                                  @Email(message = "Informe um e-mail válido")
                                  @Size(
                                          max = 150,
                                          message = "O e-mail deve ter no máximo 150 caracteres"
                                  )
                                  String email,

                                  @NotBlank(message = "A biografia é obrigatória")
                                  @Size(
                                          max = 500,
                                          message = "A biografia deve ter no máximo 500 caracteres"
                                  )
                                  String biografia,

                                  @Size(
                                          max = 500,
                                          message = "A URL deve ter no máximo 500 caracteres"
                                  )
                                  String urlFoto) {
}
