package ma.formations.domaine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo implements UserDetails {
	private Long id;
	@NotEmpty
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	@NotEmpty
	private String password;
	private List<RoleVo> roles = new ArrayList<RoleVo>();

	// les roles de Spring Security
	// c'est un champ dont la valeur sera récupré à partir du champ roles
	private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

	public UserVo(String username, String password, List<RoleVo> roles, boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.accountNonExpired=accountNonExpired;
		this.accountNonLocked=accountNonLocked;
		this.credentialsNonExpired=credentialsNonExpired;
		this.enabled=enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
