package ru.sber.beautifulcode.syntaxchecker.common.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EitherTest {

    @Test
    void foldTest() {
        Assertions.assertTrue(Either.left(0).fold(l -> true, r -> false).booleanValue());
        Assertions.assertTrue(Either.right(0).fold(l -> false, r -> true).booleanValue());
    }
}