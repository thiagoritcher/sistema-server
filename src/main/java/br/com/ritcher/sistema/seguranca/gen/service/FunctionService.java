package br.com.ritcher.sistema.seguranca.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.ritcher.sistema.seguranca.gen.data.Function;
import br.com.ritcher.sistema.seguranca.gen.repository.FunctionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class FunctionService {

	@Autowired
	FunctionRepository functionRepository;

	public <S extends Function> Mono<S> save(S entity) {
		return functionRepository.save(entity);
	}

	public Mono<Function> findById(Long id) {
		return functionRepository.findById(id);
	}

	public Flux<Function> findAll() {
		return functionRepository.findAll();
	}

	public Mono<Function> delete(Long functionId){
        return functionRepository.findById(functionId)
                .flatMap(existingFunction -> functionRepository.delete(existingFunction)
                .then(Mono.just(existingFunction)));
    }
	
    public Mono<Function> update(Long functionId,  Function function){
        return functionRepository.findById(functionId)
                .flatMap(dbFunction -> {
                    dbFunction.setName(function.getName());
                    dbFunction.setDescription(function.getDescription());
                    return functionRepository.save(dbFunction);
                });
    }	
    
    public Flux<Function> findAllQuery(String query,  PageRequest page){
        return functionRepository.findByNameContainsAllIgnoringCaseOrderByName(query, page);
    }
}
