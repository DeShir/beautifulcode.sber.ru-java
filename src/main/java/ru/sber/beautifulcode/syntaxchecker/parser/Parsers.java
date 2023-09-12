package ru.sber.beautifulcode.syntaxchecker.parser;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;
import ru.sber.beautifulcode.syntaxchecker.parser.impl.*;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Вспомогательный класс для комбинироования парсеров
 */
@UtilityClass
public class Parsers {

    @SafeVarargs
    public <S> Predicate<S> is(@NotNull S... symbols) {
        return (x -> Set.of(symbols).contains(x));
    }

    @SafeVarargs
    public <S> Predicate<S> isnt(@NotNull S... symbols) {
        return is(symbols).negate();
    }

    public <S> Parser<S, SymbolKind> satisfy(Predicate<S> predicate) {
        return map(new Satisfy<>(predicate), x -> SymbolKind.TERMINAL);
    }

    public <S, R> Lazy<S, R> lazy() {
        return new Lazy<>();
    }

    public <S, R1, R2> Parser<S, R2> map(Parser<S, R1> p, Function<R1, R2> mapper) {
        return new Transform<>(p, mapper);
    }

    public <S, R1, R2> Parser<S, Pair<R1, R2>> seq(Parser<S, R1> p1, Parser<S, R2> p2) {
        return new Seq<>(p1, p2);
    }

    public <S, R1, R2> Parser<S, R1> seql(Parser<S, R1> p1, Parser<S, R2> p2) {
        return map(seq(p1, p2), Pair::fst);
    }

    public <S, R1, R2> Parser<S, R2> seqr(Parser<S, R1> p1, Parser<S, R2> p2) {
        return map(seq(p1, p2), Pair::snd);
    }

    public <S, R1, R2, R3> Parser<S, R2> seq(Parser<S, R1> p1, Parser<S, R2> p2, Parser<S, R3> p3) {
        return seql(seqr(p1, p2), p3);
    }

    @SafeVarargs
    public <S, R> Parser<S, R> or(Parser<S, R> p1, Parser<S, R> p2, Parser<S, R>... ps) {
        return Arrays.stream(ps).reduce(new Or<>(p1, p2), Or::new);
    }

    public <S, R> Just<S, R> just(Parser<S, R> p) {
        return new Just<>(p);
    }
}
