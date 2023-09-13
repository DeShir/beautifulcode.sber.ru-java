package ru.sber.beautifulcode.syntaxchecker.app.checker.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckRequest;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckResponse;
import ru.sber.beautifulcode.syntaxchecker.app.checker.service.TextCheckerService;
import ru.sber.beautifulcode.syntaxchecker.common.grammar.GrammarService;
import ru.sber.beautifulcode.syntaxchecker.common.types.Either;

import static org.mockito.Mockito.*;

class TextWithBracketsCheckerImplServiceTest {

    private final GrammarService grammarService = mock(GrammarService.class);

    private final TextCheckerService textCheckerService = new TextWithBracketsCheckerImplServiceImpl(grammarService);

    private final CheckRequest request = mock(CheckRequest.class);

    @BeforeEach
    void beforeEach() {
        reset(grammarService, request);
    }

    @Test
    void checkOkAndTrue() {
        when(request.text()).thenReturn("test");
        when(grammarService.match(any())).thenReturn(true);
        Assertions.assertEquals(Either.right(new CheckResponse(true)), textCheckerService.check(request));
    }

    @Test
    void checkOkAndFalse() {
        when(request.text()).thenReturn("test");
        when(grammarService.match(any())).thenReturn(false);
        Assertions.assertEquals(Either.right(new CheckResponse(false)), textCheckerService.check(request));
    }

    @Test
    void checkEmptyText() {
        when(request.text()).thenReturn("");
        Assertions.assertEquals(Either.right(new CheckResponse(false)), textCheckerService.check(request));
    }

    @Test
    void checkNullText() {
        when(request.text()).thenReturn(null);
        Assertions.assertEquals(Either.left(new IllegalArgumentException("Field text should not be null.")).toString(), textCheckerService.check(request).toString());
    }
}