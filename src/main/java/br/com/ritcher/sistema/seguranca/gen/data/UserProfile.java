package br.com.ritcher.sistema.seguranca.gen.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("seg_user_profile")
public class UserProfile {
	@Id
	@Column("id")
	private Long id;
	@Column("user_id")
	private Long user;
	@Column("profile_id")
	private Long profile;
}
