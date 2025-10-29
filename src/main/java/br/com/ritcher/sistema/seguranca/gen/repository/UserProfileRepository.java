package br.com.ritcher.sistema.seguranca.gen.repository;

import reactor.core.publisher.Flux;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import br.com.ritcher.sistema.seguranca.gen.data.UserProfile;

public interface UserProfileRepository 
					extends ReactiveCrudRepository<UserProfile, Long>, ReactiveSortingRepository<UserProfile, Long>{
    Flux<UserProfile> findAllBy(Pageable pages);

}
