package ru.sber.beautifulcode.syntaxchecker.app.checker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckRequest;
import ru.sber.beautifulcode.syntaxchecker.app.checker.dto.CheckResponse;

import static ru.sber.beautifulcode.syntaxchecker.app.checker.CheckerConfiguration.CHECK_BRACKETS_ENDPOINT;

@SuppressWarnings("FieldCanBeLocal")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EndpointsTest {

    private final String correctText = "Вчера я отправился в поход в лес (это мое любимое место для отдыха(раньше было)) вместе с друзьями. Мы выбрали маршрут, который проходил через горные потоки и поля (для разнообразия). В начале пути погода была отличной, солнце светило ярко, и птицы радостно пели. Однако, когда мы подошли ближе к вершине горы, небо стало покрываться облаками, (как будто природа готовила нам небольшой сюрприз). Несмотря на это, виды были захватывающими, особенно когда мы достигли высшей точки и увидели прекрасный вид на долину (я почувствовал, что все усилия стоили того).";
    private final String incorrectText = "Вчера я отправился в() поход в лес (это мое любимое место для отдыха) вместе с друзьями. Мы выбрали маршрут, который проходил через горные потоки и поля (для разнообразия). В начале пути погода была отличной, солнце светило ярко, и птицы радостно пели. Однако, когда мы подошли ближе к вершине горы, небо стало покрываться облаками, (как будто природа готовила нам небольшой сюрприз). Несмотря на это, виды были захватывающими, особенно когда мы достигли высшей точки и увидели прекрасный вид на долину (я почувствовал, что все усилия стоили того).";

    @Autowired
    private WebTestClient webClient;

    @Test
    void checkBracketsEndpoint() {
        webClient.post().uri(CHECK_BRACKETS_ENDPOINT).bodyValue(new CheckRequest("")).exchange()
                .expectStatus().isOk()
                .expectBody(CheckResponse.class).isEqualTo(new CheckResponse(false));

        webClient.post().uri(CHECK_BRACKETS_ENDPOINT).bodyValue(new CheckRequest(null)).exchange()
                .expectStatus().isBadRequest();

        webClient.post().uri(CHECK_BRACKETS_ENDPOINT).bodyValue(new CheckRequest(correctText)).exchange()
                .expectStatus().isOk()
                .expectBody(CheckResponse.class).isEqualTo(new CheckResponse(true));

        webClient.post().uri(CHECK_BRACKETS_ENDPOINT).bodyValue(new CheckRequest(incorrectText)).exchange()
                .expectStatus().isOk()
                .expectBody(CheckResponse.class).isEqualTo(new CheckResponse(false));
    }
}