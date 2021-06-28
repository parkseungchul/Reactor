package com.psc.sample.reactor.conttoller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class BaseController {

    /**
     * 가능하지만 맞지 않음
     * @param model
     * @return
     */
    @GetMapping
    Mono<String> base1(Model model){
        model.addAttribute("key", "value");
        return Mono.just("base");
    }

    /**
     * webflux 는 면빨 뽑기 대회임.. 이렇게 면빨 끊지 않고 뽑으면 됨
     * @return
     */
    @GetMapping("/2")
    Mono<Rendering> home() {
        return Mono.just(Rendering.view("base.html")
                .modelAttribute("key",
                        "value")
                .build());
    }
}
