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
import br.com.ritcher.sistema.seguranca.gen.data.UserProfile;
import br.com.ritcher.sistema.seguranca.gen.service.UserProfileService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user_profile")
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<UserProfile> create(@RequestBody UserProfile userProfile) {
		return userProfileService.save(userProfile);
	}

	@GetMapping
	public Flux<UserProfile> getAllUserProfiles(){
		return userProfileService.findAll();
	}

	@PostMapping("/query")
	public Flux<UserProfile> getAllQuery(@RequestBody PostQuery query){
		return userProfileService.findAllQuery(query.getQuery(), PageRequest.of(0, 50));
	}

	@GetMapping("/{userProfileId}")
	 public Mono<ResponseEntity<UserProfile>> getById(@PathVariable Long userProfileId){
        Mono<UserProfile> userProfile = userProfileService.findById(userProfileId);
        return userProfile.map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	
    @PutMapping("/{userProfileId}")
    public Mono<ResponseEntity<UserProfile>> updateById(@PathVariable Long userProfileId, @RequestBody UserProfile userProfile){
        return userProfileService.update(userProfileId,userProfile)
                .map(updatedUserProfile -> ResponseEntity.ok(updatedUserProfile))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    
    
    @DeleteMapping("/{userProfileId}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Long userProfileId){
        return userProfileService.delete(userProfileId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}