package ru.sber.beautifulcode.syntaxchecker.parser.impl;

import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.Pair;
import ru.sber.beautifulcode.syntaxchecker.parser.Parser;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  Комбинатор для реализации операции СЛЕДОВАНИЯ над паарсерами
 */
public class Seq<S, R1, R2> implements Parser<S, Pair<R1, R2>> {

    private final Parser<S, R1> parser1;
    private final Parser<S, R2> parser2;

    public Seq(Parser<S, R1> parser1, Parser<S, R2> parser2) {
        this.parser1 = parser1;
        this.parser2 = parser2;
    }

    /**
     * Применит к входящему списку символов parser1, а затем, к резултату применит parser2
     *
     * @param symbols входящий список символов
     * @return список из результатов последвоательной работы parser1, parser2
     */
    @Override
    public @NotNull List<Pair<List<S>, Pair<R1, R2>>> parse(@NotNull List<S> symbols) {
        return parser1.parse(symbols).stream().flatMap( x1 -> {
            var x2 = parser2.parse(x1.fst());
            return x2.stream().map(it -> Pair.of(it.fst(), Pair.of(x1.snd(), it.snd())));
        }).collect(Collectors.toList());
    }
}
