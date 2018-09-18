package ch.zuehlke.saka.springboot.service.employee;

import ch.zuehlke.saka.springboot.dataaccess.employee.Employee;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
class EmployeeResourceAssembler implements ResourceAssembler<Employee, Resource<Employee>> {

	@Override
	public Resource<Employee> toResource(Employee employee) {
		// Add conditional links for actions on the resource here (e.g. complete or cancel an order)
		// This allows an easy implementation on the client side, as the client does not need to know which actions are possible in this state.
		return new Resource<>(employee,
							  linkTo(methodOn(EmployeeController.class).findById(employee.getId())).withSelfRel(),
							  linkTo(methodOn(EmployeeController.class).getAll()).withRel("employees")
		);
	}
}