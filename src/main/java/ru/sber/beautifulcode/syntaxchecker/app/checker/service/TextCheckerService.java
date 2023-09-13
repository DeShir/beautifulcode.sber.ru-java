package ru.sber.beautifulcode.syntaxchecker.app.checker.service;

import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckRequest;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckResponse;
import ru.sber.beautifulcode.syntaxchecker.common.types.Either;

/**
 *   Сервис, инкапсулирующий логику проверки текста
 */
public interface TextCheckerService {
    /**
     * Выполнит проверку текста и верент либо CheckResponse либо Exception если возникли ошибки
     *
     * @param request запрос на проверку
     * @return Either-контейнер с результатом
     */
    Either<Exception, CheckResponse> check(CheckRequest request);
}
