package ru.sber.beautifulcode.syntaxchecker.app.checker.handler.impl;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckRequest;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckResponse;
import ru.sber.beautifulcode.syntaxchecker.app.checker.service.TextCheckerService;

import static java.util.Objects.requireNonNullElse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

/**
 * Обрааботчик запрос на проверку текста
 */
public class TextWithBracketsHandlerImpl implements HandlerFunction<ServerResponse> {

    private final TextCheckerService service;

    public TextWithBracketsHandlerImpl(TextCheckerService service) {
        this.service = service;
    }

    /**
     * Обработка запроса
     *  - если service вернул IllegalArgumentException, то возвращаем HTTP 400
     *  - если непонятная ошибка, то возвращаем HTTP 500
     *
     * @param request запрос с текстом для обработки
     * @return Mono-контейнер с отвтеом от сервера
     */
    @NotNull
    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return request.bodyToMono(CheckRequest.class).flatMap(it -> service.check(it).fold(
                (error) -> {
                    if(error instanceof IllegalArgumentException exp) {
                        return badRequest().bodyValue(requireNonNullElse(exp.getMessage(), "Bad Request"));
                    }
                    return status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(requireNonNullElse(error.getMessage(), "Internal Server Error"));
                },
                (response) -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(Mono.just(response), CheckResponse.class)
        ));
    }
}
