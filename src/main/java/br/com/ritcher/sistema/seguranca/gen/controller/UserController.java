package br.com.ritcher.sistema.seguranca.gen.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ritcher.sistema.lib.PostQuery;
import br.com.ritcher.sistema.seguranca.gen.data.User;
import br.com.ritcher.sistema.seguranca.gen.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<User> create(@RequestBody User user) {
		return userService.save(user);
	}

	@GetMapping
	public Flux<User> getAllUsers(){
		return userService.findAll();
	}

	@PostMapping("/query")
	public Flux<User> getAllQuery(@RequestBody PostQuery query){
		return userService.findAllQuery(query.getQuery(), PageRequest.of(0, 50));
	}

	@GetMapping("/{userId}")
	 public Mono<ResponseEntity<User>> getById(@PathVariable Long userId){
        Mono<User> user = userService.findById(userId);
        return user.map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	
    @PutMapping("/{userId}")
    public Mono<ResponseEntity<User>> updateById(@PathVariable Long userId, @RequestBody User user){
        return userService.update(userId,user)
                .map(updatedUser -> ResponseEntity.ok(updatedUser))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    
    
    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Long userId){
        return userService.delete(userId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}