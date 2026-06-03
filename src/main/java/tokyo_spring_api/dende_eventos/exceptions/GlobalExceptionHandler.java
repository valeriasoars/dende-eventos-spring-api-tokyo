package tokyo_spring_api.dende_eventos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(UsuarioNaoEncontradoException.class)
        public ResponseEntity<Map<String, Object>> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {
            return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        @ExceptionHandler(EventoNaoEncontradoException.class)
        public ResponseEntity<Map<String, Object>> handleEventoNaoEncontrado(EventoNaoEncontradoException ex) {
            return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }

        @ExceptionHandler(IngressoNaoEncontradoException.class)
        public ResponseEntity<Map<String, Object>> handleIngressoNaoEncontrado(IngressoNaoEncontradoException ex) {
            return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
        }


        @ExceptionHandler(EmailJaCadastradoException.class)
        public ResponseEntity<Map<String, Object>> handleEmailJaCadastrado(EmailJaCadastradoException ex) {
            return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
        }


        @ExceptionHandler(OperacaoNaoPermitidaException.class)
        public ResponseEntity<Map<String, Object>> handleOperacaoNaoPermitida(OperacaoNaoPermitidaException ex) {
            return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        @ExceptionHandler(CapacidadeExcedidaException.class)
        public ResponseEntity<Map<String, Object>> handleCapacidadeExcedida(CapacidadeExcedidaException ex) {
            return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        @ExceptionHandler(DadosInvalidosException.class)
        public ResponseEntity<Map<String, Object>> handleDadosInvalidos(DadosInvalidosException ex) {
            return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
            return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
            return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> handleGenerico(Exception ex) {
            return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno no servidor: ");
        }

        private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String mensagem) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now().toString());
            body.put("status", status.value());
            body.put("erro", status.getReasonPhrase());
            body.put("mensagem", mensagem);
            return ResponseEntity.status(status).body(body);
        }
    }

