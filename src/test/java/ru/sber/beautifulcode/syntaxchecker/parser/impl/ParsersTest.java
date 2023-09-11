package ru.sber.beautifulcode.syntaxchecker.parser.impl;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sber.beautifulcode.syntaxchecker.common.Pair;
import ru.sber.beautifulcode.syntaxchecker.parser.Parser;

import java.util.List;

public class ParsersTest {

    @SuppressWarnings("ClassCanBeRecord")
    private static class MockParser implements Parser<Character, Integer> {

        private final List<Pair<List<Character>, Integer>> result;

        private MockParser(List<Pair<List<Character>, Integer>> result) {
            this.result = result;
        }

        @Override
        public @NotNull List<Pair<List<Character>, Integer>> parse(@NotNull List<Character> symbols) {
            return result;
        }
    }

    @Test
    @DisplayName("Test Just Parser")
    void testJust() {
        var mock = new MockParser(List.of(
                Pair.of(List.of(), 0),
                Pair.of(List.of(), 0),
                Pair.of(List.of('a'), 1),
                Pair.of(List.of('a', 'b', 'c'), 3)
        ));

        // должны вернуться результаты только с терминальными результатами
        var result = new Just<>(mock).parse(List.of());

        Assertions.assertEquals(result, List.of(Pair.of(List.of(), 0), Pair.of(List.of(), 0)));
    }

    @Test
    @DisplayName("Test Lazy Parser")
    void testLazy() {
        var mock = new MockParser(List.of(Pair.of(List.of(), 0)));

        var mock_ = new Lazy<Character, Integer>();

        // должно быть выбрашено исключение, так как метод Lazy::set не был вызван
        Assertions.assertThrows(IllegalStateException.class, () -> mock_.parse(List.of()));

        mock_.set(mock);

        // lazy парсер должен проксировать вызов в парсер mock
        var result = mock_.parse(List.of());
        Assertions.assertEquals(result, List.of(Pair.of(List.of(), 0)));
    }

    @Test
    @DisplayName("Test Or Parser")
    void testOr() {
        var mock1 = new MockParser(List.of(
                Pair.of(List.of('a'), 1)
        ));

        var mock2 = new MockParser(List.of(
                Pair.of(List.of('b'), 2)
        ));

        // or парсер должен вернуть список результатов mock1 и mock2
        var result = new Or<>(mock1, mock2).parse(List.of());
        Assertions.assertEquals(result, List.of(Pair.of(List.of('a'), 1), Pair.of(List.of('b'), 2)));
    }

    @Test
    @DisplayName("Test Seq Parser")
    void testSeq() {
        var mock1 = new MockParser(List.of(
                Pair.of(List.of('a'), 1)
        ));

        var mock2 = new MockParser(List.of(
                Pair.of(List.of('b'), 2)
        ));

        // or парсер должен вернуть список результатов последовательного применения mock1, mock2
        var result = new Seq<>(mock1, mock2).parse(List.of());
        Assertions.assertEquals(result, List.of(Pair.of(List.of('b'), Pair.of(1, 2))));
    }

    @Test
    @DisplayName("Test Transform Parser")
    void testTransform() {
        var mock = new MockParser(List.of(
                Pair.of(List.of('a'), 1)
        ));

        // Transform парсер должен вернуть список результатов, трансформируемый с применением x -> x + 1
        var result = new Transform<>(mock, x -> x + 1).parse(List.of());
        Assertions.assertEquals(result, List.of(Pair.of(List.of('a'), 2)));
    }

    @Test
    @DisplayName("Test Satisfy Parser")
    void testSatisfy() {
        var a = new Satisfy<>(c -> c.equals('a'));

        // Satisfy парсер должен распарсить первое вхождение для списка символов a,b
        var result1 = a.parse(List.of('a', 'b'));
        Assertions.assertEquals(result1, List.of(Pair.of(List.of('b'), 'a')));

        // Satisfy парсер не должен распарсить первое вхождение для списка символов b,a
        var result2 = a.parse(List.of('b', 'a'));
        Assertions.assertEquals(result2, List.of());
    }

}
