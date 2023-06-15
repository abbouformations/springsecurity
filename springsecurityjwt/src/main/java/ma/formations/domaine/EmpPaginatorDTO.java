package ma.formations.domaine;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpPaginatorDTO {
	private List<EmpVo> employees;
	private Integer size;

}
