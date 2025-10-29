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
import br.com.ritcher.sistema.seguranca.gen.data.Profile;
import br.com.ritcher.sistema.seguranca.gen.service.ProfileService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Profile> create(@RequestBody Profile profile) {
		return profileService.save(profile);
	}

	@GetMapping
	public Flux<Profile> getAllProfiles(){
		return profileService.findAll();
	}

	@PostMapping("/query")
	public Flux<Profile> getAllQuery(@RequestBody PostQuery query){
		return profileService.findAllQuery(query.getQuery(), PageRequest.of(0, 50));
	}

	@GetMapping("/{profileId}")
	 public Mono<ResponseEntity<Profile>> getById(@PathVariable Long profileId){
        Mono<Profile> profile = profileService.findById(profileId);
        return profile.map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	
    @PutMapping("/{profileId}")
    public Mono<ResponseEntity<Profile>> updateById(@PathVariable Long profileId, @RequestBody Profile profile){
        return profileService.update(profileId,profile)
                .map(updatedProfile -> ResponseEntity.ok(updatedProfile))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    
    
    @DeleteMapping("/{profileId}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Long profileId){
        return profileService.delete(profileId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}