package application.repository;

import application.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeDepartmentGeneratorRepository extends JpaRepository<Employee, UUID> {
}
