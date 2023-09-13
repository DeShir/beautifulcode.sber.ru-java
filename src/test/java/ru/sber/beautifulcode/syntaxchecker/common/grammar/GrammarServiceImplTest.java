package ru.sber.beautifulcode.syntaxchecker.common.grammar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sber.beautifulcode.syntaxchecker.common.grammar.impl.GrammarServiceImpl;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;
import ru.sber.beautifulcode.syntaxchecker.common.parser.Parser;
import ru.sber.beautifulcode.syntaxchecker.common.parser.SymbolKind;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class GrammarServiceImplTest {
    @SuppressWarnings("unchecked")
    private final Parser<Character, SymbolKind> parser = mock(Parser.class);
    private final GrammarServiceImpl grammarChecker = new GrammarServiceImpl(parser);

    @BeforeEach
    void beforeEach() {
        //noinspection unchecked
        reset(parser);
    }

    @Test
    void isCorrectReturnFalseIfResultIsEmtpy() {
        when(parser.parse(anyList())).thenReturn(List.of());
        Assertions.assertFalse(grammarChecker.match(""));
    }

    @Test
    void isCorrectReturnFalseIfResultIsNotTerminal() {
        when(parser.parse(anyList())).thenReturn(List.of(Pair.of(List.of('a'), SymbolKind.TERMINAL)));
        Assertions.assertFalse(grammarChecker.match(""));
    }

    @Test
    void isCorrectReturnTrue() {
        when(parser.parse(anyList())).thenReturn(List.of(Pair.of(List.of(), SymbolKind.TERMINAL)));
        Assertions.assertTrue(grammarChecker.match(""));
    }
}