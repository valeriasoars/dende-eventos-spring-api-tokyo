package tokyo_spring_api.dende_eventos.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tokyo_spring_api.dende_eventos.exceptions.EmailJaCadastradoException;
import tokyo_spring_api.dende_eventos.exceptions.UsuarioNaoEncontradoException;
import tokyo_spring_api.dende_eventos.mappers.UsuarioComumMapper;
import tokyo_spring_api.dende_eventos.model.Usuario;
import tokyo_spring_api.dende_eventos.model.UsuarioComum;
import tokyo_spring_api.dende_eventos.model.dto.AlterarPerfilComumDTO;
import tokyo_spring_api.dende_eventos.model.dto.ReativarUsuarioDTO;
import tokyo_spring_api.dende_eventos.model.dto.request.CadastrarUsuarioComumRequestDto;
import tokyo_spring_api.dende_eventos.model.dto.response.PerfilComumResponseDTO;
import tokyo_spring_api.dende_eventos.repositories.UsuarioRepository;

@Service
public class UsuarioComumService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioComumService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String cadastrar(CadastrarUsuarioComumRequestDto dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException(dto.email());
        }
        UsuarioComum usuario = UsuarioComumMapper.toModel(dto);
        usuarioRepository.save(usuario);
        return "Usuario " + usuario.getEmail() + " cadastrado com sucesso!";
    }

    public PerfilComumResponseDTO buscarPerfil(String email) {
        UsuarioComum usuario = buscarUsuarioComum(email);
        return UsuarioComumMapper.toResponse(usuario);
    }

    @Transactional
    public String alterar(String email, AlterarPerfilComumDTO dto) {
        UsuarioComum usuario = buscarUsuarioComum(email);
        usuario.alterarPerfil(dto);
        usuarioRepository.save(usuario);
        return "Perfil de " + email + " atualizado com sucesso.";
    }

    @Transactional
    public String desativar(String email) {
        UsuarioComum usuario = buscarUsuarioComum(email);
        usuario.desativarUsuario();
        usuarioRepository.save(usuario);
        return "Usuario desativado com sucesso.";
    }

    @Transactional
    public String reativar(String email, ReativarUsuarioDTO dto) {
        UsuarioComum usuario = buscarUsuarioComum(email);
        String senha = (dto != null) ? dto.senha() : null;
        usuario.reativarUsuario(email, senha);
        usuarioRepository.save(usuario);
        return "Usuario reativado com sucesso.";
    }

    private UsuarioComum buscarUsuarioComum(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(email));
        if (!(usuario instanceof UsuarioComum usuarioComum)) {
            throw new UsuarioNaoEncontradoException(email);
        }
        return usuarioComum;
    }
}
