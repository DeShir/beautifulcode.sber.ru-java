package ru.sber.beautifulcode.syntaxchecker.grammarchecker.impl;

import lombok.experimental.ExtensionMethod;
import ru.sber.beautifulcode.syntaxchecker.common.extensions.StringExtension;
import ru.sber.beautifulcode.syntaxchecker.grammarchecker.GrammarCheckService;
import ru.sber.beautifulcode.syntaxchecker.parser.Parser;
import ru.sber.beautifulcode.syntaxchecker.parser.SymbolKind;

@ExtensionMethod(StringExtension.class)
public class GrammarCheckServiceImpl implements GrammarCheckService {

    private final Parser<Character, SymbolKind> parser;

    public GrammarCheckServiceImpl(Parser<Character, SymbolKind> parser) {
        this.parser = parser;
    }

    @Override
    public boolean isCorrect(String text) {
        return parser.parse(text.toCharList()).stream().anyMatch(r -> r.fst().isEmpty());
    }
}
