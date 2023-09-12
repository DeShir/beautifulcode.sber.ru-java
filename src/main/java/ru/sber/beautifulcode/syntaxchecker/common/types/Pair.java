package ru.sber.beautifulcode.syntaxchecker.common.types;

import org.jetbrains.annotations.NotNull;

/**
 * Кортеж из двух элеметнов
 *
 * @param fst
 * @param snd
 */
public record Pair<F, S>(F fst, S snd) {
    /**
     * Вспомогательнй метод для конструирования пары
     *
     * @param fst первый элемент
     * @param snd второй элемент
     * @return вернет пару
     */
    @NotNull
    public static <F, S> Pair<F, S> of(F fst, S snd) {
        return new Pair<>(fst, snd);
    }
}
