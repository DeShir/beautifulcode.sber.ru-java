package ru.sber.beautifulcode.syntaxchecker.common.grammar.impl;

import lombok.experimental.ExtensionMethod;
import ru.sber.beautifulcode.syntaxchecker.common.extensions.StringExtension;
import ru.sber.beautifulcode.syntaxchecker.common.grammar.GrammarService;
import ru.sber.beautifulcode.syntaxchecker.common.parser.Parser;
import ru.sber.beautifulcode.syntaxchecker.common.parser.SymbolKind;

@ExtensionMethod(StringExtension.class)
public class GrammarServiceImpl implements GrammarService {

    private final Parser<Character, SymbolKind> parser;

    public GrammarServiceImpl(Parser<Character, SymbolKind> parser) {
        this.parser = parser;
    }

    @Override
    public boolean match(String text) {
        return parser.parse(text.toCharList()).stream().anyMatch(r -> r.fst().isEmpty());
    }
}
