package ru.sber.beautifulcode.syntaxchecker.common.parser;

import lombok.experimental.UtilityClass;

import static ru.sber.beautifulcode.syntaxchecker.common.parser.Parsers.*;

/**
 * Утилитный класс для стандратных парсеров
 */
@UtilityClass
public class Grammars {
    /**
     * Вернер парсер, распознающий грамматику(текст со скобками):
     * T -> s.T
     * T -> (.T.).T
     * T -> (.T.)
     * T -> s
     * где s - любой символ, кроме '(', ')'
     *     s, '(', ')' - терминальные символы
     *
     * @return парсер, распознающий текст со скобками
     */
    public Parser<Character, SymbolKind> textWithBrackets() {

        var s = until(satisfy(isnt('(', ')')));
        var o = satisfy(is('('));
        var c = satisfy(is(')'));

        var t_ = Parsers.<Character, SymbolKind>lazy();

        var t = or(
                seqr(s, t_)
                , seqr(o, t_, c, t_)
                , seq(o, t_, c)
                , s
        );

        t_.set(t);

        return just(t);
    }
}
