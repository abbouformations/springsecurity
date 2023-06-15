package ma.formations.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.formations.domaine.EmpPaginatorDTO;
import ma.formations.domaine.EmpVo;
import ma.formations.service.IEmpService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
public class EmpController {

	private final IEmpService service;

	@GetMapping(value = "/employees", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<EmpVo> getAll() {
		return service.getEmployees();
	}

	/**
	 * Pour chercher un employé par son id
	 */
	@GetMapping(value = "/employees/{id}")
	public ResponseEntity<Object> getEmpById(@PathVariable(value = "id") Long empVoId) {
		EmpVo empVoFound = service.getEmpById(empVoId);
		if (empVoFound == null)
			return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
		return new ResponseEntity<>(empVoFound, HttpStatus.OK);
	}

	/**
	 * Pour créer un nouveau employé
	 */
	@PostMapping(value = "/admin/create")
	public ResponseEntity<Object> createEmp(@Valid @RequestBody EmpVo empVo) {
		service.save(empVo);
		return new ResponseEntity<>("employee is created successfully", HttpStatus.CREATED);
	}

	/**
	 * Pour modifier un produit par son id
	 */
	@PutMapping(value = "/admin/update/{id}")
	public ResponseEntity<Object> updateEmp(@PathVariable(name = "id") Long empVoId, @Valid @RequestBody EmpVo empVo) {
		EmpVo empVoFound = service.getEmpById(empVoId);
		if (empVoFound == null)
			return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
		empVo.setId(empVoId);
		service.save(empVo);
		return new ResponseEntity<>("Employee is updated successsfully", HttpStatus.OK);
	}

	/**
	 * Pour supprimer un employé par son id
	 */
	@DeleteMapping(value = "/admin/delete/{id}")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteEmp(@PathVariable(name = "id") Long empVoId) {
		EmpVo empVoFound = service.getEmpById(empVoId);
		if (empVoFound == null)
			return new ResponseEntity<>("employee doen't exist", HttpStatus.OK);
		service.delete(empVoId);
		return new ResponseEntity<>("Employee is deleted successsfully", HttpStatus.OK);
	}

	/**
	 * Pour chercher tous les emplyés
	 */
	@GetMapping(value = "/employees/sort/{fieldName}", produces = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public List<EmpVo> sortBy(@PathVariable String fieldName) {
		return service.sortBy(fieldName);
	}

	/**
	 * Afficher la liste des employés en utilisant la pagination
	 */
	@GetMapping("/employees/pagination/{pageid}/{size}")
	public List<EmpVo> pagination(@PathVariable int pageid, @PathVariable int size, Model m) {
		return service.findAll(pageid, size);
	}

	@GetMapping("/employees/paginator")
	public EmpPaginatorDTO pagination(@RequestParam Map<String, String> params) {
		EmpPaginatorDTO dto = new EmpPaginatorDTO();
		dto.setEmployees(service.findAll(Integer.parseInt(params.get("page")), Integer.parseInt(params.get("size"))));
		dto.setSize(dto.getEmployees().size());
		return dto;
	}
}
