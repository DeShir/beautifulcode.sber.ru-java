package ru.sber.beautifulcode.syntaxchecker.grammarchecker;

/**
 * Сервис для проверки на соответсиве текста определенным правилам
 */
public interface GrammarCheckService {
    boolean isCorrect(String text);
}
