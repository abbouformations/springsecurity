package ma.formations.domaine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationAttribute;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ma.formations.service.model.Role;
import ma.formations.service.model.User;

public class UserConverter {
	
	// BO ==> VO
	public static UserVo toVo(User bo) {
		if (bo == null)
			return null;
		UserVo vo = new UserVo();
		
		vo.setId(bo.getId());
		vo.setUsername(bo.getUsername());
		vo.setPassword(bo.getPassword());
		vo.setRoles(RoleConverter.toVoList(bo.getRoles()));
		vo.setAccountNonExpired(bo.isAccountNonExpired());
		vo.setAccountNonLocked(bo.isAccountNonLocked());
		vo.setCredentialsNonExpired(bo.isCredentialsNonExpired());
		vo.setEnabled(bo.isEnabled());
		
		vo.setAuthorities(getAuthorities(bo.getRoles()));
		
		return vo;
	}

	
	private static Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
		List<GrantedAuthority> springSecurityAuthorities = new ArrayList<>();
		roles.forEach(r -> springSecurityAuthorities.add(new SimpleGrantedAuthority(r.getRole())));
		return springSecurityAuthorities;
	}
	
	
	//VO ==> BO
	public static User toBo(UserVo vo) {
		if (vo == null)
			return null;
		User bo = new User();
		if (vo.getId() != null)
			bo.setId(vo.getId());
		bo.setUsername(vo.getUsername());
		bo.setPassword(vo.getPassword());
		bo.setRoles(RoleConverter.toBoList(vo.getRoles()));
		bo.setAccountNonExpired(vo.isAccountNonExpired());
		bo.setAccountNonLocked(vo.isAccountNonLocked());
		bo.setCredentialsNonExpired(vo.isCredentialsNonExpired());
		bo.setEnabled(vo.isEnabled());
		return bo;
	}

	public static List<UserVo> toVoList(List<User> boList) {
		if (boList == null || boList.isEmpty())
			return null;
		List<UserVo> voList = new ArrayList<>();
		for (User user : boList) {
			voList.add(toVo(user));
		}
		return voList;
	}

	public static List<User> toBoList(List<UserVo> voList) {
		if (voList == null || voList.isEmpty())
			return null;
		List<User> boList = new ArrayList<>();
		for (UserVo userVo : voList) {
			boList.add(toBo(userVo));
		}
		return boList;
	}
}
