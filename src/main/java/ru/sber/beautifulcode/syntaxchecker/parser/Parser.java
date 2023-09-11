package ru.sber.beautifulcode.syntaxchecker.parser;

import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.Pair;

import java.util.List;

/**
 * Парсер
 *
 * @param <S> тип символа
 * @param <R> тип результата
 */
public interface Parser<S, R> {
    /**
     * Применит парсер к входящему списку символов и вернет результат разбора
     *  - если парсер не может быть применен, то результат будет пустым
     *  - если существует несколько вариантов применения парсера, то результат будет содержать их все
     *
     * @param symbols входящий список символов
     * @return резульаты разбра, где List<S> - оставшийся, неразобранный, список символов, а R - результат
     */
    @NotNull
    List<Pair<List<S>, R>> parse(@NotNull List<S> symbols);
}
