package br.com.ritcher.sistema.seguranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import br.com.ritcher.sistema.seguranca.data.Usuario;
import br.com.ritcher.sistema.seguranca.repository.UsuarioRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	public <S extends Usuario> Mono<S> save(S entity) {
		return usuarioRepository.save(entity);
	}

	public Mono<Usuario> findById(Long id) {
		return usuarioRepository.findById(id);
	}

	public Flux<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public Mono<Usuario> deleteUsuario(Long usuarioId){
        return usuarioRepository.findById(usuarioId)
                .flatMap(existingUsuario -> usuarioRepository.delete(existingUsuario)
                .then(Mono.just(existingUsuario)));
    }
	
    public Mono<Usuario> updateUsuario(Long usuarioId,  Usuario usuario){
        return usuarioRepository.findById(usuarioId)
                .flatMap(dbUsuario -> {
                    dbUsuario.setUsername(usuario.getUsername());
                    dbUsuario.setPassword(usuario.getPassword());
                    return usuarioRepository.save(dbUsuario);
                });
    }	
    
    public Flux<Usuario> findAllQuery(String query,  PageRequest page){
        return usuarioRepository.findByUsernameContainsAllIgnoringCaseOrderByUsername(query, page);
    }
}
