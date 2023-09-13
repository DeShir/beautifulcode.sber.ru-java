package ru.sber.beautifulcode.syntaxchecker.app.checker;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckRequest;
import ru.sber.beautifulcode.syntaxchecker.app.checker.handler.impl.TextWithBracketsHandlerImpl;
import ru.sber.beautifulcode.syntaxchecker.app.checker.service.impl.TextWithBracketsCheckerImplServiceImpl;
import ru.sber.beautifulcode.syntaxchecker.common.grammar.GrammarService;
import ru.sber.beautifulcode.syntaxchecker.common.grammar.impl.GrammarServiceImpl;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static ru.sber.beautifulcode.syntaxchecker.common.parser.Grammars.*;

@Configuration
public class CheckerConfiguration {

    public final static String CHECK_BRACKETS_ENDPOINT = "/api/checkBrackets";

    @Bean
    GrammarService textWithBracketsGrammar() {
        return new GrammarServiceImpl(textWithBrackets());
    }

    @Bean
    TextWithBracketsCheckerImplServiceImpl textWithBracketsCheckerImplServiceImpl(@Qualifier("textWithBracketsGrammar") GrammarService service) {
        return new TextWithBracketsCheckerImplServiceImpl(service);
    }

    @Bean
    TextWithBracketsHandlerImpl textWithBracketsHandlerImpl(TextWithBracketsCheckerImplServiceImpl service) {
        return new TextWithBracketsHandlerImpl(service);
    }

    @RouterOperations(
            @RouterOperation(
                    path = CHECK_BRACKETS_ENDPOINT,
                    operation = @Operation(
                            operationId = "opCheckBrackets",
                            requestBody = @RequestBody(
                                    required = true,
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = CheckRequest.class)
                                    )
                            )
                    )
            )
    )
    @Bean
    RouterFunction<ServerResponse> checkBracketsEndpoint(TextWithBracketsHandlerImpl handler) {
        return route()
                .POST(
                        CHECK_BRACKETS_ENDPOINT,
                        accept(APPLICATION_JSON),
                        handler
                )
                .build();
    }
}
