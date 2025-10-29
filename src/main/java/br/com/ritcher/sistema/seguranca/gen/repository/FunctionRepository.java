package br.com.ritcher.sistema.seguranca.gen.repository;

import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import br.com.ritcher.sistema.seguranca.gen.data.Function;

public interface FunctionRepository 
					extends ReactiveCrudRepository<Function, Long>, ReactiveSortingRepository<Function, Long>{
    Flux<Function> findAllBy(Pageable pages);
    Flux<Function> findByNameContainsAllIgnoringCaseOrderByName(String query, Pageable pages);

}
