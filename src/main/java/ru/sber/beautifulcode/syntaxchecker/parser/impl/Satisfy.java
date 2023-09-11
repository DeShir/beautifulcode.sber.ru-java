package ru.sber.beautifulcode.syntaxchecker.parser.impl;

import lombok.experimental.ExtensionMethod;
import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.ListExtension;
import ru.sber.beautifulcode.syntaxchecker.common.Pair;
import ru.sber.beautifulcode.syntaxchecker.parser.Parser;

import java.util.List;
import java.util.function.Predicate;

/**
 * Проверяет текущий символ на соответствие предикату
 */
@ExtensionMethod(ListExtension.class)
public class Satisfy<S> implements Parser<S, S> {

    private final Predicate<S> predicate;

    public Satisfy(@NotNull Predicate<S> predicate) {
        this.predicate = predicate;
    }

    /**
     * @param symbols список символов для разбора
     * @return возвращает результат, удовлетворяющий предикату
     */
    @Override
    public @NotNull List<Pair<List<S>, S>> parse(@NotNull List<S> symbols) {
        if(symbols.isEmpty() || Boolean.FALSE.equals(predicate.test(symbols.head()))) {
            return List.of();
        }
        return List.of(Pair.of(symbols.tail(), symbols.head()));
    }
}
