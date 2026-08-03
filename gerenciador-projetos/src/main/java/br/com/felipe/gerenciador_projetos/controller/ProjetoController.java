package br.com.felipe.gerenciador_projetos.controller;

import br.com.felipe.gerenciador_projetos.dto.request.ProjetoRequestDTO;
import br.com.felipe.gerenciador_projetos.dto.response.ProjetoResponseDTO;
import br.com.felipe.gerenciador_projetos.service.ProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
@RequiredArgsConstructor
public class ProjetoController {

    private final ProjetoService projetoService;

    @PostMapping
    public ResponseEntity<ProjetoResponseDTO> cadastrar(
            @Valid @RequestBody ProjetoRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(projetoService.cadastrarProjeto(request));
    }

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                projetoService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                projetoService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProjetoRequestDTO request
    ) {
        return ResponseEntity.ok(
                projetoService.atualizar(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id
    ) {
        projetoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{projetoId}/colaboradores/{usuarioId}")
    public ResponseEntity<ProjetoResponseDTO> adicionarColaborador(
            @PathVariable Long projetoId,
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                projetoService.adicionarColaborador(
                        projetoId,
                        usuarioId
                )
        );
    }

    @DeleteMapping("/{projetoId}/colaboradores/{usuarioId}")
    public ResponseEntity<ProjetoResponseDTO> removerColaborador(
            @PathVariable Long projetoId,
            @PathVariable Long usuarioId
    ) {
        return ResponseEntity.ok(
                projetoService.removerColaborador(
                        projetoId,
                        usuarioId
                )
        );
    }
}