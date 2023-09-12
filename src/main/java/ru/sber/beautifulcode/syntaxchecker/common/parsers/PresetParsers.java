package ru.sber.beautifulcode.syntaxchecker.common.parsers;

import lombok.experimental.UtilityClass;
import ru.sber.beautifulcode.syntaxchecker.parser.Parser;
import ru.sber.beautifulcode.syntaxchecker.parser.Parsers;
import ru.sber.beautifulcode.syntaxchecker.parser.SymbolKind;

import static ru.sber.beautifulcode.syntaxchecker.parser.Parsers.*;
import static ru.sber.beautifulcode.syntaxchecker.parser.Parsers.is;

/**
 * Утилитный класс для стандратных парсеров
 */
@UtilityClass
public class PresetParsers {

    /**
     * Вернер парсер, распознающий текст с грамматикой:
     * T -> W.T
     * T -> W
     * W -> (.T.)
     * W -> s
     * где s - любой символ, кроме '(', ')'
     *     s, '(', ')' - терминальные символы
     *
     * @return парсер, распознающий текст со скобками
     */
    public Parser<Character, SymbolKind> parensWithText() {

        var s = satisfy(isnt('(', ')'));
        var o = satisfy(is('('));
        var c = satisfy(is(')'));

        var w_ = Parsers.<Character, SymbolKind>lazy();
        var t_ = Parsers.<Character, SymbolKind>lazy();

        var w = or(
                seq(o, t_, c),
                s
        );

        var t = or(
                seqr(w, t_),
                w
        );

        w_.set(w);
        t_.set(t);

        return just(t);
    }
}
