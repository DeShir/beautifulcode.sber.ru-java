package ru.sber.beautifulcode.syntaxchecker.common.parser.impl;

import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.parser.Parser;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Парсер для разбора цепочки повторяющихся символов.
 * Будет применять parser, пока он не вернет пустой список
 */
public class Until<S, R> implements Parser<S, R> {

    private final Parser<S, R> parser;

    public Until(Parser<S, R> parser) {
        this.parser = parser;
    }

    private List<Pair<List<S>, R>> parse1(List<Pair<List<S>, R>> pairs) {
        return pairs.stream().flatMap(pair -> parser.parse(pair.fst()).stream()).collect(Collectors.toList());
    }

    /**
     * @param symbols список символов для разбора
     * @return возвращает результат, удовлетворяющий предикату
     */
    @Override
    public @NotNull List<Pair<List<S>, R>> parse(@NotNull List<S> symbols) {
        var i  = parser.parse(symbols);
        var r = i;

        for(; !i.isEmpty(); i = parse1(i)) {
            r = i;
        }

        return r;
    }
}
