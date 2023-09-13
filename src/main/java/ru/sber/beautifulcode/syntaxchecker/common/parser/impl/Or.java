package ru.sber.beautifulcode.syntaxchecker.common.parser.impl;

import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;
import ru.sber.beautifulcode.syntaxchecker.common.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *  Комбинатор для реализации операции ИЛИ над паарсерами
 */
public class Or<S, R> implements Parser<S, R> {
    private final Parser<S, R> parser1;
    private final Parser<S, R> parser2;

    public Or(Parser<S, R> parser1, Parser<S, R> parser2) {
        this.parser1 = parser1;
        this.parser2 = parser2;
    }

    /**
     * Применит к входящему списку символов parser1 и parser2 и объеденит их результаты
     *
     * @param symbols входящий список символов
     * @return список из результатов работы parser1 и parser2
     */
    @Override
    public @NotNull List<Pair<List<S>, R>> parse(@NotNull List<S> symbols) {
        return Stream.of(parser1, parser2).flatMap(parser -> parser.parse(symbols).stream())
                .collect(Collectors.toList());
    }
}
