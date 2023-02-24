package jp.co.axa.apidemo.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 *  This class is the employee entity class to persist data in db for the fields
 *  employeeId,name,salary,department,salary.
 */
@Data
@Builder
@Entity
@Table(name = "EMPLOYEE")
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;
    @Column(name = "EMPLOYEE_NAME")
    @NotBlank(message = "Employee name should not be empty/blank")
    @Size(min = 2, message = "Employee name should have at least 2 characters")
    private String name;
    @Column(name = "EMPLOYEE_SALARY")
    @NotNull(message = "This should not be empty it accept positive numbers")
    @Min(value = 0L, message = "The value must be positive")
    private Integer salary;
    @Column(name = "EMPLOYEE_DEPARTMENT")
    @NotBlank(message = "Employee name should not be empty/blank")
    @Size(min = 2, message = "Employee name should have at least 2 characters")
    private String department;

}
