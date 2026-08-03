package br.com.felipe.gerenciador_projetos.service;

import br.com.felipe.gerenciador_projetos.dto.request.ProjetoRequestDTO;
import br.com.felipe.gerenciador_projetos.dto.response.ProjetoResponseDTO;
import br.com.felipe.gerenciador_projetos.dto.response.UsuarioResumoDTO;
import br.com.felipe.gerenciador_projetos.entity.Projeto;
import br.com.felipe.gerenciador_projetos.entity.Usuario;
import br.com.felipe.gerenciador_projetos.exception.RecursoNaoEncontradoException;
import br.com.felipe.gerenciador_projetos.exception.RegraDeNegocioException;
import br.com.felipe.gerenciador_projetos.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public ProjetoResponseDTO cadastrarProjeto(
            ProjetoRequestDTO request
    ) {
        validarDatas(request);

        Usuario proprietario =
                usuarioService.buscarEntidadePorId(
                        request.proprietarioId()
                );

        Projeto projeto = new Projeto();
        projeto.setNome(request.nome());
        projeto.setDescricao(request.descricao());
        projeto.setDataInicio(request.dataInicio());
        projeto.setDataFim(request.dataFim());
        projeto.setProprietario(proprietario);

        Projeto projetoSalvo =
                projetoRepository.save(projeto);

        return converterParaResponse(projetoSalvo);
    }

    @Transactional(readOnly = true)
    public List<ProjetoResponseDTO> listarTodos() {
        return projetoRepository
                .findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProjetoResponseDTO buscarPorId(Long id) {
        return converterParaResponse(
                buscarEntidadePorId(id)
        );
    }

    @Transactional
    public ProjetoResponseDTO atualizar(
            Long id,
            ProjetoRequestDTO request
    ) {
        validarDatas(request);

        Projeto projeto = buscarEntidadePorId(id);

        Usuario proprietario =
                usuarioService.buscarEntidadePorId(
                        request.proprietarioId()
                );

        projeto.setNome(request.nome());
        projeto.setDescricao(request.descricao());
        projeto.setDataInicio(request.dataInicio());
        projeto.setDataFim(request.dataFim());
        projeto.setProprietario(proprietario);

        Projeto projetoAtualizado =
                projetoRepository.save(projeto);

        return converterParaResponse(projetoAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        Projeto projeto = buscarEntidadePorId(id);

        projetoRepository.delete(projeto);
    }

    @Transactional
    public ProjetoResponseDTO adicionarColaborador(
            Long projetoId,
            Long usuarioId
    ) {
        Projeto projeto =
                buscarEntidadePorId(projetoId);

        Usuario colaborador =
                usuarioService.buscarEntidadePorId(usuarioId);

        if (projeto.getProprietario()
                .getId()
                .equals(usuarioId)) {
            throw new RegraDeNegocioException(
                    "O proprietário não pode ser adicionado como colaborador"
            );
        }

        if (projeto.getColaboradores().contains(colaborador)) {
            throw new RegraDeNegocioException(
                    "O usuário já é colaborador deste projeto"
            );
        }

        projeto.addColaborador(colaborador);

        Projeto projetoAtualizado =
                projetoRepository.save(projeto);

        return converterParaResponse(projetoAtualizado);
    }

    @Transactional
    public ProjetoResponseDTO removerColaborador(
            Long projetoId,
            Long usuarioId
    ) {
        Projeto projeto =
                buscarEntidadePorId(projetoId);

        Usuario colaborador =
                usuarioService.buscarEntidadePorId(usuarioId);

        if (!projeto.getColaboradores().contains(colaborador)) {
            throw new RegraDeNegocioException(
                    "O usuário não é colaborador deste projeto"
            );
        }

        projeto.removeColaborador(colaborador);

        Projeto projetoAtualizado =
                projetoRepository.save(projeto);

        return converterParaResponse(projetoAtualizado);
    }

    @Transactional(readOnly = true)
    public Projeto buscarEntidadePorId(Long id) {
        return projetoRepository
                .findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Projeto de ID " + id + " não encontrado"
                        )
                );
    }

    private void validarDatas(ProjetoRequestDTO request) {
        if (
                request.dataFim() != null
                        && request.dataFim()
                        .isBefore(request.dataInicio())
        ) {
            throw new RegraDeNegocioException(
                    "A data final não pode ser anterior à data inicial"
            );
        }
    }

    private ProjetoResponseDTO converterParaResponse(
            Projeto projeto
    ) {
        Set<UsuarioResumoDTO> colaboradores =
                projeto.getColaboradores()
                        .stream()
                        .map(usuario ->
                                new UsuarioResumoDTO(
                                        usuario.getId(),
                                        usuario.getNome(),
                                        usuario.getEmail()
                                )
                        )
                        .collect(Collectors.toSet());

        return new ProjetoResponseDTO(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getProprietario().getId(),
                projeto.getProprietario().getNome(),
                colaboradores
        );
    }
}