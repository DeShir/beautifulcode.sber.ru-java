package ru.sber.beautifulcode.syntaxchecker.common.extensions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 *  Расширение для списков
 */
public class ListExtension {

    /**
     * Вернет "голову" списка
     *
     * @param list исходный список
     * @return первый элемент в списке
     * @throws IndexOutOfBoundsException если список пустой
     */
    @Nullable
    public static <T> T head(@NotNull List<T> list) {
        return list.get(0);
    }

    /**
     * Вернет "хвост" списка
     *
     * @param list исходный список
     * @return "хвост" списка, если длина списка меньше 1, то вернут пустой список
     */
    @NotNull
    public static <T> List<T> tail(@NotNull List<T> list) {
        if(list.size() < 1) {
            return List.of();
        }
        return list.subList(1, list.size());
    }
}
