package ru.sber.beautifulcode.syntaxchecker.common.parser;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import ru.sber.beautifulcode.syntaxchecker.common.parser.impl.*;
import ru.sber.beautifulcode.syntaxchecker.common.types.Pair;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Вспомогательный класс для комбинироования парсеров
 */
@SuppressWarnings("unused")
@UtilityClass
public class Parsers {

    /**
     * Вернет предикат, который разрешится в true, если символ-аргумент содердится в symbols
     * Используется как вспомогательный метод, для красоты
     * @param symbols набор символов для сравнения
     * @return  предикат
     */
    @SafeVarargs
    public <S> Predicate<S> is(@NotNull S... symbols) {
        return (x -> Set.of(symbols).contains(x));
    }

    /**
     * Вернет предикат, обратный к Parsers::is
     * Используется как вспомогательный метод, для красоты
     * @param symbols набор символов для сравнения
     * @return  предикат
     */
    @SafeVarargs
    public <S> Predicate<S> isnt(@NotNull S... symbols) {
        return is(symbols).negate();
    }

    /**
     * Сконструирует парсер, который будет разбираться символы в соответствие с predicate
     * См.: ru.sber.beautifulcode.syntaxchecker.common.parser.impl.Satisfy
     *
     * @param predicate предикат для проверки
     * @return парсер
     */
    public <S> Parser<S, SymbolKind> satisfy(Predicate<S> predicate) {
        return map(new Satisfy<>(predicate), x -> SymbolKind.TERMINAL);
    }

    /**
     * Сконструирует парсер для того что бы ссылатся на себя в рекурсивных правилах
     * См.: ru.sber.beautifulcode.syntaxchecker.common.parser.impl.Lazy
     *
     * @return парсер
     */
    public <S, R> Lazy<S, R> lazy() {
        return new Lazy<>();
    }

    /**
     * Сконструирует парсер для того что бы преобразовывать результат друго парсера p
     * См.: ru.sber.beautifulcode.syntaxchecker.common.parser.impl.Transform
     *
     * @param p парсер, результат которого нужно преобразовать
     * @param mapper функция-маппер
     * @return парсер
     */
    public <S, R1, R2> Parser<S, R2> map(Parser<S, R1> p, Function<R1, R2> mapper) {
        return new Transform<>(p, mapper);
    }

    /**
     * Сконструирует парсер для того что бы выполнять последовательно p1 и p2
     * См.: ru.sber.beautifulcode.syntaxchecker.common.parser.impl.Seq
     *
     * @param p1 первый парсер
     * @param p2 второй парсер
     * @return скомбинированный парсер
     */
    public <S, R1, R2> Parser<S, Pair<R1, R2>> seq(Parser<S, R1> p1, Parser<S, R2> p2) {
        return new Seq<>(p1, p2);
    }

    /**
     * Сконструирует парсер для того что бы выполнять последовательно p1 и p2 и вернуть результат первого
     * См.: ru.sber.beautifulcode.syntaxchecker.common.parser.impl.Seq
     *
     * @param p1 первый парсер
     * @param p2 второй парсер
     * @return скомбинированный парсер
     */
    public <S, R1, R2> Parser<S, R1> seql(Parser<S, R1> p1, Parser<S, R2> p2) {
        return map(seq(p1, p2), Pair::fst);
    }

    /**
     * Сконструирует парсер для того что бы выполнять последовательно p1 и p2 и вернуть результат второго
     *
     * @param p1 первый парсер
     * @param p2 второй парсер
     * @return скомбинированный парсер
     */
    public <S, R1, R2> Parser<S, R2> seqr(Parser<S, R1> p1, Parser<S, R2> p2) {
        return map(seq(p1, p2), Pair::snd);
    }

    /**
     * Сконструирует парсер для того что бы выполнять последовательно p1, p2, p3 и вернуть результат среднего
     *
     * @param p1 первый парсер
     * @param p2 второй парсер
     * @param p3 третий парсер
     * @return скомбинированный парсер
     */
    public <S, R1, R2, R3> Parser<S, R2> seq(Parser<S, R1> p1, Parser<S, R2> p2, Parser<S, R3> p3) {
        return seql(seqr(p1, p2), p3);
    }

    /**
     * Сконструирует парсер для того что бы выполнять последовательно p1, p2, p3, p4 и вернуть результат последнего
     *
     * @param p1 первый парсер
     * @param p2 второй парсер
     * @param p3 третий парсер
     * @param p4 четвертый парсер
     * @return скомбинированный парсер
     */
    public <S, R1, R2, R3, R4> Parser<S, R4> seqr(Parser<S, R1> p1, Parser<S, R2> p2, Parser<S, R3> p3, Parser<S, R4> p4) {
        return seqr(seqr(seqr(p1, p2), p3), p4);
    }

    /**
     * Сконструирует парсер для того что бы выполнять последовательно p1, p2, p3, p4 и вернуть результат первого
     *
     * @param p1 первый парсер
     * @param p2 второй парсер
     * @param p3 третий парсер
     * @param p4 четвертый парсер
     * @return скомбинированный парсер
     */
    public <S, R1, R2, R3, R4> Parser<S, R1> seql(Parser<S, R1> p1, Parser<S, R2> p2, Parser<S, R3> p3, Parser<S, R4> p4) {
        return seql(seql(seql(p1, p2), p3), p4);
    }

    /**
     * Сконструирует парсер, который будет пробовать применить все парсеры p1, p2 ... к текущему символу
     * См.: ru.sber.beautifulcode.syntaxchecker.common.parser.impl.Or
     *
     * @param p1 первый парсер
     * @param p2 второй парсер
     * @param ps массив оставшихся парсеров
     * @return вернет парсер p1 || p2 || p3 ...
     */
    @SafeVarargs
    public <S, R> Parser<S, R> or(Parser<S, R> p1, Parser<S, R> p2, Parser<S, R>... ps) {
        return Arrays.stream(ps).reduce(new Or<>(p1, p2), Or::new);
    }

    /**
     * Контейнер для парсера, возвращающий терминальные результаты
     * См.: ru.sber.beautifulcode.syntaxchecker.common.parser.impl.Just
     *
     * @param p парсер
     * @return парсер, возвращающий только разобранные результаты
     */
    public <S, R> Just<S, R> just(Parser<S, R> p) {
        return new Just<>(p);
    }

    /**
     * Вернет парсер, который последовательно будет выполнять парсер p, пока тот возвращает непустой результат
     * См.: ru.sber.beautifulcode.syntaxchecker.common.parser.impl.Until
     *
     * @param p парсер
     * @return парсер
     */
    public <S, R> Until<S, R> until(Parser<S, R> p) {
        return new Until<>(p);
    }
}
