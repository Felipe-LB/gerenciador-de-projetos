package br.com.felipe.gerenciador_projetos.service;

import br.com.felipe.gerenciador_projetos.dto.request.UsuarioRequestDTO;
import br.com.felipe.gerenciador_projetos.dto.response.UsuarioResponseDTO;
import br.com.felipe.gerenciador_projetos.entity.Perfil;
import br.com.felipe.gerenciador_projetos.entity.Usuario;
import br.com.felipe.gerenciador_projetos.exception.RecursoNaoEncontradoException;
import br.com.felipe.gerenciador_projetos.exception.RegraDeNegocioException;
import br.com.felipe.gerenciador_projetos.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Transactional
    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new RegraDeNegocioException(
                    "Já existe um usuário cadastrado com esse email"
            );
        }
        Perfil perfil = new Perfil();
        perfil.setBiografia(request.biografia());
        perfil.setUrlFoto(request.urlFoto());

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setPerfil(perfil);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return converterParaResponse(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository
                .findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = buscarEntidadePorId(id);

        return converterParaResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO atualizar(
            Long id,
            UsuarioRequestDTO request
    ) {
        Usuario usuario = buscarEntidadePorId(id);

        boolean emailPertenceAOutroUsuario =
                usuarioRepository.existsByEmail(request.email())
                        && !usuario.getEmail().equalsIgnoreCase(request.email());

        if (emailPertenceAOutroUsuario) {
            throw new RegraDeNegocioException(
                    "Já existe outro usuário com esse e-mail"
            );
        }

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());

        Perfil perfil = usuario.getPerfil();

        if (perfil == null) {
            perfil = new Perfil();
            usuario.setPerfil(perfil);
        }

        perfil.setBiografia(request.biografia());
        perfil.setUrlFoto(request.urlFoto());

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return converterParaResponse(usuarioAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        Usuario usuario = buscarEntidadePorId(id);

        usuarioRepository.delete(usuario);
    }
    @Transactional(readOnly = true)
    public Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository
                .findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário de ID " + id + " não encontrado"
                        )
                );
    }

    private UsuarioResponseDTO converterParaResponse(
            Usuario usuario
    ) {
        Perfil perfil = usuario.getPerfil();

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                perfil != null ? perfil.getId() : null,
                perfil != null ? perfil.getBiografia() : null,
                perfil != null ? perfil.getUrlFoto() : null
        );
    }
}