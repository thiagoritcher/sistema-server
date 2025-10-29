package br.com.ritcher.sistema.seguranca.gen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.ritcher.sistema.seguranca.gen.data.User;
import br.com.ritcher.sistema.seguranca.gen.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepository;

	public <S extends User> Mono<S> save(S entity) {
		return userRepository.save(entity);
	}

	public Mono<User> findById(Long id) {
		return userRepository.findById(id);
	}

	public Flux<User> findAll() {
		return userRepository.findAll();
	}

	public Mono<User> delete(Long userId){
        return userRepository.findById(userId)
                .flatMap(existingUser -> userRepository.delete(existingUser)
                .then(Mono.just(existingUser)));
    }
	
    public Mono<User> update(Long userId,  User user){
        return userRepository.findById(userId)
                .flatMap(dbUser -> {
                    dbUser.setUsername(user.getUsername());
                    dbUser.setPassword(user.getPassword());
                    dbUser.setDescription(user.getDescription());
                    return userRepository.save(dbUser);
                });
    }	
    
    public Flux<User> findAllQuery(String query,  PageRequest page){
        return userRepository.findByUsernameContainsAllIgnoringCaseOrderByUsername(query, page);
    }
}
