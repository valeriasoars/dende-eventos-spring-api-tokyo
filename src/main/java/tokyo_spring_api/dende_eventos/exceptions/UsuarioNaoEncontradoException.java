package tokyo_spring_api.dende_eventos.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {

    public UsuarioNaoEncontradoException(String email) {
        super("Usuário não encontrado com o e-mail: " + email);
    }
}
