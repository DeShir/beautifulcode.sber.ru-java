package ru.sber.beautifulcode.syntaxchecker.common.parser;

import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sber.beautifulcode.syntaxchecker.common.extensions.StringExtension;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;

import java.util.List;

import static ru.sber.beautifulcode.syntaxchecker.common.parser.Grammars.textWithBrackets;
import static ru.sber.beautifulcode.syntaxchecker.common.parser.SymbolKind.TERMINAL;

@ExtensionMethod(StringExtension.class)
class GrammarsTest {

    private final Parser<Character, SymbolKind> parser = textWithBrackets();

    @Test
    @DisplayName("Parens With Text Test")
    void parensWithTextTest() {

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

    @Test
    @DisplayName("Parens With Big Text Test")
    void parensWithBigTextTest() {
        var text = "Вчера я отправился в поход в лес (это мое любимое место для отдыха) вместе с друзьями. Мы выбрали маршрут, который проходил через горные потоки и поля (для разнообразия). В начале пути погода была отличной, солнце светило ярко, и птицы радостно пели. Однако, когда мы подошли ближе к вершине горы, небо стало покрываться облаками, (как будто природа готовила нам небольшой сюрприз). Несмотря на это, виды были захватывающими, особенно когда мы достигли высшей точки и увидели прекрасный вид на долину (я почувствовал, что все усилия стоили того).";

        Assertions.assertEquals(List.of(Pair.of(List.of(), TERMINAL)), parser.parse(text.toCharList()));
    }


}