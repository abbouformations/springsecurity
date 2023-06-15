package ma.formations.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Emp {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Double salary;
	private String fonction;

	public Emp(String name, Double salary, String fonction) {
		super();
		this.name = name;
		this.salary = salary;
		this.fonction = fonction;
	}
}