package ch.zuehlke.saka.springboot.service.employee;

import ch.zuehlke.saka.springboot.dataaccess.employee.Employee;
import ch.zuehlke.saka.springboot.dataaccess.employee.EmployeeRepository;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/employees", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EmployeeController {
	private final EmployeeRepository repository;
	private final EmployeeResourceAssembler resourceAssembler;

	public EmployeeController(EmployeeRepository repository, EmployeeResourceAssembler resourceAssembler) {
		this.repository = repository;
		this.resourceAssembler = resourceAssembler;
	}

	@GetMapping
	public Resources<Resource<Employee>> getAll() {
		List<Resource<Employee>> employeeResources = repository.getEmployees().stream()
				.map(resourceAssembler::toResource)
				.collect(Collectors.toList());
		return new Resources<>(employeeResources);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resource<Employee>> findById(@PathVariable Long id) {
		return repository.findById(id)
				.map(resourceAssembler::toResource)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping (consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource<Employee>> createEmployee(Employee employee) {
		if (employee.getId() != null) {
			return ResponseEntity.badRequest().build();
		}

		Employee createdEmployee = repository.save(employee);
		Resource<Employee> createdResource = resourceAssembler.toResource(createdEmployee);

		return ResponseEntity
				.created(URI.create(createdResource.getId().getHref()))
				.body(createdResource);
	}
}
