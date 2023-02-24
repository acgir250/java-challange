package jp.co.axa.apidemo.repositories;

import jp.co.axa.apidemo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  Interface is for the employee to perform the database operation and manipulations.
 *  Ideally called the database operation save,update,delete etc.....
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
