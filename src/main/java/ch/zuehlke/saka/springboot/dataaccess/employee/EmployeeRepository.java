package ch.zuehlke.saka.springboot.dataaccess.employee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EmployeeRepository {
	private static AtomicLong EMPLOYEE_ID = new AtomicLong();

	private List<Employee> employees;

	public EmployeeRepository() {
		employees = new ArrayList<>();
		employees.add(createEmployee("Peter", "Parker"));
		employees.add(createEmployee("Tony", "Stark"));
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public Optional<Employee> findById(Long id) {
		return employees.stream()
				 .filter(employee -> employee.getId().equals(id))
				 .findFirst();
	}

	public Employee save(Employee employeeToSave) {
		if (employeeToSave.getId() == null) {
			employeeToSave.setId(EMPLOYEE_ID.incrementAndGet());
			employees.add(employeeToSave);
		} else {
			update(employeeToSave);
		}

		return employeeToSave;
	}

	private void update(Employee employeeToSave) {
		Optional<Employee> employeeToUpdate = findById(employeeToSave.getId());
		if (employeeToUpdate.isPresent()) {
			employeeToUpdate.get().setFirstName(employeeToSave.getFirstName());
			employeeToUpdate.get().setLastName(employeeToSave.getLastName());
		} else {
			employees.add(employeeToSave);
		}
	}

	private static Employee createEmployee(String firstName, String lastName) {
		Employee employee = new Employee();
		employee.setId(EMPLOYEE_ID.incrementAndGet());
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		return employee;
	}
}
