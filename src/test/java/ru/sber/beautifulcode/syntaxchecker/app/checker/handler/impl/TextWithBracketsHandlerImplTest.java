package ru.sber.beautifulcode.syntaxchecker.app.checker.handler.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckRequest;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckResponse;
import ru.sber.beautifulcode.syntaxchecker.app.checker.service.TextCheckerService;
import ru.sber.beautifulcode.syntaxchecker.common.types.Either;

import static org.mockito.Mockito.*;

@SuppressWarnings("ConstantConditions")
class TextWithBracketsHandlerImplTest {

    private final ServerRequest request = mock(ServerRequest.class);

    private final TextCheckerService service = mock(TextCheckerService.class);

    private final HandlerFunction<ServerResponse> handler = new TextWithBracketsHandlerImpl(service);

    @BeforeEach
    void beforeEach() {
        reset(service, request);
    }

    @Test
    void handleOk() {
        when(service.check(any())).thenReturn(Either.right(new CheckResponse(true)));
        when(request.bodyToMono(CheckRequest.class)).thenReturn(Mono.just(new CheckRequest("")));

        Assertions.assertEquals(HttpStatus.OK, handler.handle(request).block().statusCode());
    }

    @Test
    void handleBadRequest() {
        when(service.check(any())).thenReturn(Either.left(new IllegalArgumentException("")));
        when(request.bodyToMono(CheckRequest.class)).thenReturn(Mono.just(new CheckRequest("")));

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, handler.handle(request).block().statusCode());
    }

    @Test
    void handleAnyError() {
        when(service.check(any())).thenReturn(Either.left(new Exception()));
        when(request.bodyToMono(CheckRequest.class)).thenReturn(Mono.just(new CheckRequest("")));

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, handler.handle(request).block().statusCode());
    }
}