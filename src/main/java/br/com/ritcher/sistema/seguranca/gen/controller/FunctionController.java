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
import br.com.ritcher.sistema.seguranca.gen.data.Function;
import br.com.ritcher.sistema.seguranca.gen.service.FunctionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/function")
public class FunctionController {

	@Autowired
	private FunctionService functionService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Function> create(@RequestBody Function function) {
		return functionService.save(function);
	}

	@GetMapping
	public Flux<Function> getAllFunctions(){
		return functionService.findAll();
	}

	@PostMapping("/query")
	public Flux<Function> getAllQuery(@RequestBody PostQuery query){
		return functionService.findAllQuery(query.getQuery(), PageRequest.of(0, 50));
	}

	@GetMapping("/{functionId}")
	 public Mono<ResponseEntity<Function>> getById(@PathVariable Long functionId){
        Mono<Function> function = functionService.findById(functionId);
        return function.map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	
    @PutMapping("/{functionId}")
    public Mono<ResponseEntity<Function>> updateById(@PathVariable Long functionId, @RequestBody Function function){
        return functionService.update(functionId,function)
                .map(updatedFunction -> ResponseEntity.ok(updatedFunction))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    
    
    @DeleteMapping("/{functionId}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable Long functionId){
        return functionService.delete(functionId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}