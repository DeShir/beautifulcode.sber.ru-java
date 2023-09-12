package ru.sber.beautifulcode.syntaxchecker.parsers;

import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sber.beautifulcode.syntaxchecker.common.Pair;
import ru.sber.beautifulcode.syntaxchecker.common.StringExtension;

import java.util.List;

import static ru.sber.beautifulcode.syntaxchecker.parser.SymbolKind.TERMINAL;
import static ru.sber.beautifulcode.syntaxchecker.parsers.PresetParsers.parensWithText;

@ExtensionMethod(StringExtension.class)
class PresetParsersTest {

    @Test
    @DisplayName("Parens With Text Test")
    void parensWithTextTest() {
        var parser = parensWithText();

        // Negative
        Assertions.assertEquals(List.of(), parser.parse("".toCharList()));
        Assertions.assertEquals(List.of(), parser.parse("(()())".toCharList()));
        Assertions.assertEquals(List.of(), parser.parse("0((0)(0)".toCharList()));

        // Positive
        Assertions.assertEquals(List.of(Pair.of(List.of(), TERMINAL)), parser.parse("0".toCharList()));
        Assertions.assertEquals(List.of(Pair.of(List.of(), TERMINAL)), parser.parse("00".toCharList()));
        Assertions.assertEquals(List.of(Pair.of(List.of(), TERMINAL)), parser.parse("((0)(0))".toCharList()));
        Assertions.assertEquals(List.of(Pair.of(List.of(), TERMINAL)), parser.parse("0((0)(0))".toCharList()));
        Assertions.assertEquals(List.of(Pair.of(List.of(), TERMINAL)), parser.parse("((0)(0))0".toCharList()));
        Assertions.assertEquals(List.of(Pair.of(List.of(), TERMINAL)), parser.parse("0(0(0)0)00".toCharList()));

    }

}