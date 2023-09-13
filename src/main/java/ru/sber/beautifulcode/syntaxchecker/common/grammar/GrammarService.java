package ru.sber.beautifulcode.syntaxchecker.common.grammar;

/**
 * Сервис для проверки на соответсиве текста определенным правилам
 */
public interface GrammarService {
    boolean match(String text);
}
