package ma.formations.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.formations.service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String userName);

	boolean existsByUsername(String username);
}