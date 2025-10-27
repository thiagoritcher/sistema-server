package br.com.ritcher.sistema.seguranca.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("seg_usuario")
public class Usuario {
	@Id
	@Column("id")
	private Long id;
	@Column("username")
	private String username;
	@Column("password")
	private String password;
}
