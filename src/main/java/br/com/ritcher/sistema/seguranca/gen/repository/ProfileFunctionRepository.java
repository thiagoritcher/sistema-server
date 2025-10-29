package br.com.ritcher.sistema.seguranca.gen.repository;

import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import br.com.ritcher.sistema.seguranca.gen.data.ProfileFunction;

public interface ProfileFunctionRepository 
					extends ReactiveCrudRepository<ProfileFunction, Long>, ReactiveSortingRepository<ProfileFunction, Long>{
    Flux<ProfileFunction> findAllBy(Pageable pages);

}
