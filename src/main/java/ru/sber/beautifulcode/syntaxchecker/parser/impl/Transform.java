package ru.sber.beautifulcode.syntaxchecker.parser.impl;

import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;
import ru.sber.beautifulcode.syntaxchecker.parser.Parser;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Маппер для результатов работы парсеров
 */
public class Transform<S, R1, R2> implements Parser<S, R2> {
    private final Parser<S, R1> parser;
    private final Function<R1, R2> transformer;

    public Transform(Parser<S, R1> parser, Function<R1, R2> transformer) {
        this.parser = parser;
        this.transformer = transformer;
    }

    /**
     * Распакует результат работы parser и упакует его в соответствие transformer
     * @param symbols входящий список символов
     * @return преобразованный списко результатов
     */
    @Override
    public @NotNull List<Pair<List<S>, R2>> parse(@NotNull List<S> symbols) {
        return parser.parse(symbols).stream()
                .map(x -> Pair.of(x.fst(), transformer.apply(x.snd()))).collect(Collectors.toList());
    }
}
