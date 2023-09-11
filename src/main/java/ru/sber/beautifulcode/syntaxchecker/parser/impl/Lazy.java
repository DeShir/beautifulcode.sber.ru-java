package ru.sber.beautifulcode.syntaxchecker.parser.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.sber.beautifulcode.syntaxchecker.common.Pair;
import ru.sber.beautifulcode.syntaxchecker.parser.Parser;

import java.util.List;

/**
 * Конетейнер для парсера.
 * Его можно использовать, когда в грамматике есть необходимость ссылаться на себя
 */
public class Lazy<S, R> implements Parser<S, R> {

    @Nullable
    private Parser<S, R>  parser;

    @Override
    public @NotNull List<Pair<List<S>, R>> parse(@NotNull List<S> symbols) {
        if(parser == null) {
            throw new IllegalStateException("Lazy parser should be initialized.");
        }
        return parser.parse(symbols);
    }

    public void set(@NotNull Parser<S, R> parser) {
        this.parser = parser;
    }
}
