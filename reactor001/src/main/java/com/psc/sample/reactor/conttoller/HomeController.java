package com.psc.sample.reactor.conttoller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
@AllArgsConstructor
public class HomeController {

    @GetMapping
    Mono<String> home(Model model){
        model.addAttribute("key", "value");
        return Mono.just("home");
    }
}
