package br.com.ritcher.sistema.seguranca.controller;

import br.com.ritcher.sistema.seguranca.gen.data.User;
import br.com.ritcher.sistema.seguranca.gen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/user")
public class UserAdd {
    @Autowired
    private UserService userService;

    @GetMapping("/thiago")
    public Flux<User> getAllUsersThiago(){
            return userService
                    .findAll()
                    .filter(u -> u.getUsername().contains("thiago"));
    }
}
