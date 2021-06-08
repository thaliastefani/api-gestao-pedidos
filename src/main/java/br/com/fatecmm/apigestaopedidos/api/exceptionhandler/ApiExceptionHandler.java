package br.com.fatecmm.apigestaopedidos.api.exceptionhandler;

import br.com.fatecmm.apigestaopedidos.domain.exception.BusinessException;
import br.com.fatecmm.apigestaopedidos.domain.exception.DiscountExceedsLimitValue;
import br.com.fatecmm.apigestaopedidos.domain.exception.EntityInUseException;
import br.com.fatecmm.apigestaopedidos.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }
        Problem problem = createErrorBuilder(ErrorUtils.TITLE_SYNTAX_ERROR, status, "Invalid send format, " +
                "check syntax of sent data").build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        String detail = String.format("The resource %s, which you tried to access, is non-existent.",
                ex.getRequestURL());
        Problem problem = createErrorBuilder(ErrorUtils.TITLE_RESOURCE_NOT_FOUND, status, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }
        return super.handleTypeMismatch(ex, headers, status, request);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String detail = "One or more fields are invalid. Make the right fill and try again.";
        BindingResult bindingResult = ex.getBindingResult();
        List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> Problem.Field.builder()
                        .name(fieldError.getField())
                        .userMessage(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        Problem problem = createErrorBuilder(ErrorUtils.TITLE_BAD_DATA, status, detail)
                .detail(detail)
                .fields(problemFields)
                .build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .status(Long.valueOf(status.value()))
                    .title(status.getReasonPhrase())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .status(Long.valueOf(status.value()))
                    .title((String) body)
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        String detail = "An unexpected internal error occurred in the system. Try again and if the problem persists," +
                " contact the system administrator.";

        log.error(ex.getMessage(), ex);

        Problem problem = createErrorBuilder(ErrorUtils.TITLE_SYSTEM_ERROR, status, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerNotFound(EntityNotFoundException ex,
                                             WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Problem problem = createErrorBuilder(ErrorUtils.TITLE_RESOURCE_NOT_FOUND, status, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handlerConflict(EntityInUseException ex,
                                             WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        Problem problem = createErrorBuilder(ErrorUtils.TITLE_RESOURCE_IN_USE, status, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handlerBadRequest(BusinessException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    @ExceptionHandler(DiscountExceedsLimitValue.class)
    public ResponseEntity<?> handlerDiscountExceedsLimitValue(DiscountExceedsLimitValue ex,
                                                              WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
                                                       HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());
        String detail = String.format("The property '%s' received the value '%s', "
                        + "of an invalid type. Correct and report a value compatible with type %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());
        Problem problem = createErrorBuilder(ErrorUtils.TITLE_SYNTAX_ERROR, status, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());
        String detail = String.format("The property '%s' doesn't exist."
                + "Fix or remove this property and try again.", path);
        Problem problem = createErrorBuilder(ErrorUtils.TITLE_INCOMPREHENSIBLE, HttpStatus.BAD_REQUEST, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String detail = String.format("The URL parameter '%s' has received the value '%s', which is an invalid type." +
                        "Correct and enter a value compatible with the type '%s'.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        Problem problem = createErrorBuilder(ErrorUtils.TITLE_INVALID_PARAMETER, status, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    private Problem.ProblemBuilder createErrorBuilder(String title, HttpStatus status, String detail) {
        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .status(Long.valueOf(status.value()))
                .title(title)
                .detail(detail);
    }
    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }
}
