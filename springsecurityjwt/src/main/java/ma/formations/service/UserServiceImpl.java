package ma.formations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ma.formations.dao.EmpRepository;
import ma.formations.dao.RoleRepository;
import ma.formations.dao.UserRepository;
import ma.formations.domaine.RoleConverter;
import ma.formations.domaine.RoleVo;
import ma.formations.domaine.UserConverter;
import ma.formations.domaine.UserVo;
import ma.formations.service.exception.BusinessException;
import ma.formations.service.model.Role;
import ma.formations.service.model.User;

@Service
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private EmpRepository empRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return UserConverter.toVo(userRepository.findByUsername(username));
	}

	@Override
	public void save(UserVo userVo) {
		User user = UserConverter.toBo(userVo);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		List<Role> rolesPersist = new ArrayList<>();
		for (Role role : user.getRoles()) {
			Role userRole = roleRepository.findByRole(role.getRole()).get(0);
			rolesPersist.add(userRole);
		}
		user.setRoles(rolesPersist);
		userRepository.save(user);
	}

	@Override
	public void save(RoleVo roleVo) {
		roleRepository.save(RoleConverter.toBo(roleVo));
	}

	@Override
	public List<UserVo> getAllUsers() {
		return UserConverter.toVoList(userRepository.findAll());
	}

	@Override
	public List<RoleVo> getAllRoles() {
		return RoleConverter.toVoList(roleRepository.findAll());
	}

	@Override
	public RoleVo getRoleByName(String role) {
		return RoleConverter.toVo(roleRepository.findByRole(role).get(0));
	}

	@Override
	public void cleanDataBase() {
		userRepository.deleteAll();
		roleRepository.deleteAll();
		empRepository.deleteAll();
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public UserVo findByUsername(String username) {
		if (username == null || username.trim().equals(""))
			throw new BusinessException("login is empty !!");

		User bo = userRepository.findByUsername(username);

		if (bo == null)
			throw new BusinessException("No user with this login");

		UserVo vo = UserConverter.toVo(bo);
		return vo;
	}

}
