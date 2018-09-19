package ch.zuehlke.saka.springboot.service.employee;

import ch.zuehlke.saka.springboot.dataaccess.employee.Employee;
import ch.zuehlke.saka.springboot.dataaccess.employee.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class EmployeeControllerTest {

	@Autowired
	private TestRestTemplate template;

	@MockBean
	private EmployeeRepository repository;

	@Test
	public void getAll_2employeesAvailable_listWith2Employees() {
		when(repository.getEmployees()).thenReturn(EmployeeFixture.getEmployees());

		ResponseEntity<Resources<Resource<Employee>>> result =
				template.exchange("/employees", HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<Employee>>>() {});

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(result.getBody().getContent()).hasSize(2);
	}

	@Test
	public void findById_employeeNotAvailable_notFound() {
		when(repository.findById(any())).thenReturn(Optional.empty());

		URI targetUri = URI.create("/employees/1");
		ResponseEntity<Resource<Employee>> result =
				template.exchange(targetUri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Employee>>() {});

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void findById_employeeAvailable_employeeWithLinkReturned() {
		Employee employee = EmployeeFixture.getEmployee();
		when(repository.findById(employee.getId())).thenReturn(Optional.of(employee));

		URI targetUri = URI.create("/employees/" + employee.getId());
		ResponseEntity<Resource<Employee>> result =
				template.exchange(targetUri, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Employee>>() {});

		assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(result.getBody().getId().getHref()).contains("/employees/" + employee.getId());
		// assertThat(result.getBody().getContent().getId()).isEqualTo(employee.getId());
		assertThat(result.getBody().getContent().getFirstName()).isEqualTo(employee.getFirstName());
		assertThat(result.getBody().getContent().getLastName()).isEqualTo(employee.getLastName());
	}
}