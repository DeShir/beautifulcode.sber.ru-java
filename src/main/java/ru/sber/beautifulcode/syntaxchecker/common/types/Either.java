package ru.sber.beautifulcode.syntaxchecker.common.types;

import lombok.Data;

import java.util.function.Function;

/**
 * Either контейнер
 */
@Data
public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public static <L, R> Either<L, R> left(L val) {
        return new Either<>(val, null);
    }

    public static <L, R> Either<L, R> right(R val) {
        return new Either<>(null, val);
    }

    /**
     * Выполнить маппинг в соответствие с состоянием Either
     *
     * @param lmap преобразование над левым значением
     * @param rmap преобразование над правым значением
     * @return результат выполнение lmap или rmap в зависимости от состояния
     */
    public <U> U fold(Function<L, U> lmap, Function<R, U> rmap) {
        if(right == null) {
            return lmap.apply(left);
        }

        return rmap.apply(right);
    }
}
