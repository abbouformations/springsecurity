package ma.formations.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.formations.service.model.Emp;

public interface EmpRepository extends JpaRepository<Emp, Long> {
	List<Emp> findBySalary(Double salary);

	List<Emp> findByFonction(String designation);

	List<Emp> findBySalaryAndFonction(Double salary, String fonction);

	@Query(" SELECT e from Emp e where e.salary=(select MAX(salary) as salary FROM Emp)")
	Emp getEmpHavaingMaxSalary();
}
