package ch.zuehlke.saka.springboot.service.employee;

import ch.zuehlke.saka.springboot.dataaccess.employee.Employee;

import java.util.Arrays;
import java.util.List;

public class EmployeeFixture {
	private static final String FIRSTNAME_1 = "Michael";
	private static final String LASTNAME_1 = "Jackson";
	private static final String FIRSTNAME_2 = "Kurt";
	private static final String LASTNAME_2 = "Cobain";

	static Employee getEmployee() {
		return getEmployee(FIRSTNAME_1, LASTNAME_1, 1L);
	}

	static Employee getEmployee(String firstname, String lastname, long id) {
		Employee employee = new Employee();
		employee.setFirstName(firstname);
		employee.setLastName(lastname);
		employee.setId(id);
		return employee;
	}

	static List<Employee> getEmployees() {
		return Arrays.asList(
				getEmployee(FIRSTNAME_1, LASTNAME_1, 1L),
				getEmployee(FIRSTNAME_2, LASTNAME_2, 2L)
		);
	}
}
