package br.com.ritcher.sistema.seguranca.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ritcher.sistema.lib.PostQuery;
import br.com.ritcher.sistema.seguranca.data.Usuario;
import br.com.ritcher.sistema.seguranca.service.UsuarioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Usuario> create(@RequestBody Usuario usuario) {
		return usuarioService.save(usuario);
	}

	@GetMapping
	public Flux<Usuario> getAllUsuarios(){
		return usuarioService.findAll();
	}

	@PostMapping("/query")
	public Flux<Usuario> getAllUsuarioQuery(@RequestBody PostQuery query){
		return usuarioService.findAllQuery(query.getQuery(), PageRequest.of(0, 50));
	}

	@GetMapping("/{usuarioId}")
	 public Mono<ResponseEntity<Usuario>> getUsuarioById(@PathVariable Long usuarioId){
        Mono<Usuario> usuario = usuarioService.findById(usuarioId);
        return usuario.map(u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
	
    @PutMapping("/{usuarioId}")
    public Mono<ResponseEntity<Usuario>> updateUsuarioById(@PathVariable Long usuarioId, @RequestBody Usuario usuario){
        return usuarioService.updateUsuario(usuarioId,usuario)
                .map(updatedUsuario -> ResponseEntity.ok(updatedUsuario))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
    
    
    @DeleteMapping("/{usuarioId}")
    public Mono<ResponseEntity<Void>> deleteUsuarioById(@PathVariable Long usuarioId){
        return usuarioService.deleteUsuario(usuarioId)
                .map( r -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}