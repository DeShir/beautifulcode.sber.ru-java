package ru.sber.beautifulcode.syntaxchecker.common.parser.impl;

import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;
import ru.sber.beautifulcode.syntaxchecker.common.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контейнер для парсера, возвращающий терминальные результаты
 */
public class Just<S, R> implements Parser<S, R> {

    private final Parser<S, R> parser;

    public Just(Parser<S, R> parser) {
        this.parser = parser;
    }

    @Override
    public @NotNull List<Pair<List<S>, R>> parse(@NotNull List<S> symbols) {
        return parser.parse(symbols).stream().filter(x -> x.fst().isEmpty()).collect(Collectors.toList());
    }
}
