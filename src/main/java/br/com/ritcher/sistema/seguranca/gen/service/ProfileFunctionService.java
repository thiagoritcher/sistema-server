package br.com.ritcher.sistema.seguranca.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.ritcher.sistema.seguranca.gen.data.ProfileFunction;
import br.com.ritcher.sistema.seguranca.gen.repository.ProfileFunctionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProfileFunctionService {

	@Autowired
	ProfileFunctionRepository profile_functionRepository;

	public <S extends ProfileFunction> Mono<S> save(S entity) {
		return profile_functionRepository.save(entity);
	}

	public Mono<ProfileFunction> findById(Long id) {
		return profile_functionRepository.findById(id);
	}

	public Flux<ProfileFunction> findAll() {
		return profile_functionRepository.findAll();
	}

	public Mono<ProfileFunction> delete(Long profile_functionId){
        return profile_functionRepository.findById(profile_functionId)
                .flatMap(existingProfileFunction -> profile_functionRepository.delete(existingProfileFunction)
                .then(Mono.just(existingProfileFunction)));
    }
	
    public Mono<ProfileFunction> update(Long profile_functionId,  ProfileFunction profile_function){
        return profile_functionRepository.findById(profile_functionId)
                .flatMap(dbProfileFunction -> {
                    dbProfileFunction.setProfile(profile_function.getProfile());
                    dbProfileFunction.setFunction(profile_function.getFunction());
                    return profile_functionRepository.save(dbProfileFunction);
                });
    }	
    
    public Flux<ProfileFunction> findAllQuery(String query,  PageRequest page){
        return profile_functionRepository.findAllBy(page);
    }
}
