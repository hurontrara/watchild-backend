package ai.watchild.biz.linkTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LinkTestHandler {

    public Mono<ServerResponse> test(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("Hello World123");
    }

}
