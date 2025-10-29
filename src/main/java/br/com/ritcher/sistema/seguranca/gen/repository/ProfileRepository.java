package br.com.ritcher.sistema.seguranca.gen.repository;

import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import br.com.ritcher.sistema.seguranca.gen.data.Profile;

public interface ProfileRepository 
					extends ReactiveCrudRepository<Profile, Long>, ReactiveSortingRepository<Profile, Long>{
    Flux<Profile> findAllBy(Pageable pages);
    Flux<Profile> findByNameContainsAllIgnoringCaseOrderByName(String query, Pageable pages);

}
