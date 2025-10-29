package br.com.ritcher.sistema.seguranca.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.ritcher.sistema.seguranca.gen.data.UserProfile;
import br.com.ritcher.sistema.seguranca.gen.repository.UserProfileRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserProfileService {

	@Autowired
	UserProfileRepository user_profileRepository;

	public <S extends UserProfile> Mono<S> save(S entity) {
		return user_profileRepository.save(entity);
	}

	public Mono<UserProfile> findById(Long id) {
		return user_profileRepository.findById(id);
	}

	public Flux<UserProfile> findAll() {
		return user_profileRepository.findAll();
	}

	public Mono<UserProfile> delete(Long user_profileId){
        return user_profileRepository.findById(user_profileId)
                .flatMap(existingUserProfile -> user_profileRepository.delete(existingUserProfile)
                .then(Mono.just(existingUserProfile)));
    }
	
    public Mono<UserProfile> update(Long user_profileId,  UserProfile user_profile){
        return user_profileRepository.findById(user_profileId)
                .flatMap(dbUserProfile -> {
                    dbUserProfile.setUser(user_profile.getUser());
                    dbUserProfile.setProfile(user_profile.getProfile());
                    return user_profileRepository.save(dbUserProfile);
                });
    }	
    
    public Flux<UserProfile> findAllQuery(String query,  PageRequest page){
        return user_profileRepository.findAllBy(page);
    }
}
