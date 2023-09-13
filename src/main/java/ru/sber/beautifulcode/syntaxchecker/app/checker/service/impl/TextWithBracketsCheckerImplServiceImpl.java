package ru.sber.beautifulcode.syntaxchecker.app.checker.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckRequest;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckResponse;
import ru.sber.beautifulcode.syntaxchecker.app.checker.service.TextCheckerService;
import ru.sber.beautifulcode.syntaxchecker.common.grammar.GrammarService;
import ru.sber.beautifulcode.syntaxchecker.common.types.Either;

@Slf4j
public class TextWithBracketsCheckerImplServiceImpl implements TextCheckerService {

    private final GrammarService grammar;

    public TextWithBracketsCheckerImplServiceImpl(GrammarService grammar) {
        this.grammar = grammar;
    }

    @Override
    public Either<Exception, CheckResponse> check(CheckRequest request) {
        if(request.text() == null) {
            log.warn("Field text should not be null.");
            return Either.left(new IllegalArgumentException("Field text should not be null."));
        }

        CheckResponse response;

        log.info("Checking text with size: %d".formatted(request.text().length()));
        if (request.text().isEmpty()) {
            response = new CheckResponse(false);
        } else {
            response = new CheckResponse(grammar.match(request.text()));
        }
        log.info("Checking result: %s".formatted(response));

        return Either.right(response);
    }
}
