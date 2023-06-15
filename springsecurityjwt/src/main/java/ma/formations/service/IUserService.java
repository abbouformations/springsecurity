package ma.formations.service;


import java.util.List;

import ma.formations.domaine.RoleVo;
import ma.formations.domaine.UserVo;

public interface IUserService{
	void save(UserVo user);
	void save(RoleVo role);
	List<UserVo> getAllUsers();
	List<RoleVo> getAllRoles();
	RoleVo getRoleByName(String role);
	void cleanDataBase();
	boolean existsByUsername(String username);
	UserVo findByUsername(String username);
}
