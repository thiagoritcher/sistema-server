package br.com.ritcher.sistema.seguranca.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.ritcher.sistema.seguranca.gen.data.Profile;
import br.com.ritcher.sistema.seguranca.gen.repository.ProfileRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class ProfileService {

	@Autowired
	ProfileRepository profileRepository;

	public <S extends Profile> Mono<S> save(S entity) {
		return profileRepository.save(entity);
	}

	public Mono<Profile> findById(Long id) {
		return profileRepository.findById(id);
	}

	public Flux<Profile> findAll() {
		return profileRepository.findAll();
	}

	public Mono<Profile> delete(Long profileId){
        return profileRepository.findById(profileId)
                .flatMap(existingProfile -> profileRepository.delete(existingProfile)
                .then(Mono.just(existingProfile)));
    }
	
    public Mono<Profile> update(Long profileId,  Profile profile){
        return profileRepository.findById(profileId)
                .flatMap(dbProfile -> {
                    dbProfile.setName(profile.getName());
                    dbProfile.setDescription(profile.getDescription());
                    return profileRepository.save(dbProfile);
                });
    }	
    
    public Flux<Profile> findAllQuery(String query,  PageRequest page){
        return profileRepository.findByNameContainsAllIgnoringCaseOrderByName(query, page);
    }
}
