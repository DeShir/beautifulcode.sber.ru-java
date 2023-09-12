package ru.sber.beautifulcode.syntaxchecker.grammarchecker.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;
import ru.sber.beautifulcode.syntaxchecker.parser.Parser;
import ru.sber.beautifulcode.syntaxchecker.parser.SymbolKind;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class GrammarCheckServiceImplTest {
    @SuppressWarnings("unchecked")
    private final Parser<Character, SymbolKind> parser = mock(Parser.class);
    private final GrammarCheckServiceImpl grammarChecker = new GrammarCheckServiceImpl(parser);

    @BeforeEach
    void beforeEach() {
        //noinspection unchecked
        reset(parser);
    }

    @Test
    void isCorrectReturnFalseIfResultIsEmtpy() {
        when(parser.parse(anyList())).thenReturn(List.of());
        Assertions.assertFalse(grammarChecker.isCorrect(""));
    }

    @Test
    void isCorrectReturnFalseIfResultIsNotTerminal() {
        when(parser.parse(anyList())).thenReturn(List.of(Pair.of(List.of('a'), SymbolKind.TERMINAL)));
        Assertions.assertFalse(grammarChecker.isCorrect(""));
    }

    @Test
    void isCorrectReturnTrue() {
        when(parser.parse(anyList())).thenReturn(List.of(Pair.of(List.of(), SymbolKind.TERMINAL)));
        Assertions.assertTrue(grammarChecker.isCorrect(""));
    }
}