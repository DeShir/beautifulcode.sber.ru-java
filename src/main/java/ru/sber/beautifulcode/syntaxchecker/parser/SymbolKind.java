package ru.sber.beautifulcode.syntaxchecker.parser;

/**
 * Экземпляр терминального состояния прасера.
 * Используется в спомогательных целях
 */
public enum SymbolKind {
    TERMINAL {
    @Override
    public String toString() {
        return "Terminal";
    }
}}
