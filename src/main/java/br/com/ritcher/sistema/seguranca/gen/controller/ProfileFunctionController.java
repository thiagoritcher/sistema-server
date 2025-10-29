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
import br.com.ritcher.sistema.seguranca.gen.data.ProfileFunction;
import br.com.ritcher.sistema.seguranca.gen.service.ProfileFunctionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/profile_function")
public class ProfileFunctionController {

	@Autowired
	private ProfileFunctionService profileFunctionService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<ProfileFunction> create(@RequestBody ProfileFunction profileFunction) {
		return profileFunctionService.save(profileFunction);
	}

	@GetMapping
	public Flux<ProfileFunction> getAllProfileFunctions(){
		return profileFunctionService.findAll();
	}

	@PostMapping("/query")
	public Flux<ProfileFunction> getAllQuery(@RequestBody PostQuery query){
		return profileFunctionService.findAllQuery(query.getQuery(), PageRequest.of(0, 50));
	}

	@GetMapping("/{profileFunctionId}")
	 public Mono<ResponseEntity<ProfileFunction>> getById(@PathVariable Long profileFunctionId){
        Mono<ProfileFunction> profileFunction = profileFunctionService.findById(profileFunctionId);
        return profileFunction.map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	
    @PutMapping("/{profileFunctionId}")
    public Mono<ResponseEntity<ProfileFunction>> updateById(@PathVariable Long profileFunctionId, @RequestBody ProfileFunction profileFunction){
        return profileFunctionService.update(profileFunctionId,profileFunction)
                .map(updatedProfileFunction -> ResponseEntity.ok(updatedProfileFunction))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    
    
    @DeleteMapping("/{profileFunctionId}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Long profileFunctionId){
        return profileFunctionService.delete(profileFunctionId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}