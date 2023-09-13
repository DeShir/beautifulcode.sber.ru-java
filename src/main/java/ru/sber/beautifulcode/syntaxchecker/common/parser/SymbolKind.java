package ru.sber.beautifulcode.syntaxchecker.common.parser;

/**
 * Экземпляр состояния прасера.
 * Используется в спомогательных целях
 */
public enum SymbolKind {
    TERMINAL {
    @Override
    public String toString() {
        return "Terminal";
    }
}}
