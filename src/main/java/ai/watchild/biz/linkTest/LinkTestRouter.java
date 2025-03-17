package ai.watchild.biz.linkTest;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.*;

// basic link test controller using webflux

@RestController
public class LinkTestRouter {

    @Bean
    public RouterFunction<ServerResponse> linkTestRoute(LinkTestHandler linkTestHandler) {
        return route()
                .GET("/v1/linktest", linkTestHandler::test)
                .build();
    }

}
