package ru.sber.beautifulcode.syntaxchecker.common.extensions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 *  Расширение для строк
 */
public class StringExtension {
    /**
     * Вернет представление строки в виде списка символов
     * @param str исходная строка
     * @return список символов
     */
    @NotNull
    public static List<Character> toCharList(@NotNull String str) {
        return str.chars().mapToObj(it -> (char) it).collect(Collectors.toList());
    }
}
