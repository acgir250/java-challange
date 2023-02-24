package jp.co.axa.apidemo.service;

import com.sun.tools.javac.util.List;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

    private final Employee STUB_EMPLOYEE = Employee.builder().department("testDepartment").salary(10000).name("test").build();
    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    CacheManager cacheManager;
    @InjectMocks
    EmployeeServiceImpl employeeService;
    @Mock
    Cache cache;

    @Test
    public void should_save_employeeEntity() {
        //given
        given(employeeRepository.save(any())).willReturn(STUB_EMPLOYEE);

        //when
        employeeService.saveEmployee(STUB_EMPLOYEE);

        //then
        then(employeeRepository).should(times(1)).save(STUB_EMPLOYEE);
    }

    @Test
    public void should_update_employeeEntity_byId() {
        //given
        Employee updatedEmployee = Employee.builder().employeeID(Long.valueOf(1)).name("updatedName").department("test_department").build();
        given(employeeRepository.save(any())).willReturn(updatedEmployee);
        given(employeeService.existsByEmployeeId(any())).willReturn(true);
        given(cacheManager.getCache(any())).willReturn(cache);
        //when
        employeeService.updateEmployee(updatedEmployee, Long.valueOf(1));

        //then
        then(employeeRepository).should(times(1)).save(updatedEmployee);
    }

    @Test
    public void should_delete_employeeEntity_byId() {
        //given
        Long employeeId = Long.valueOf(1);
        //when
        employeeService.deleteEmployee(employeeId);

        //then
        then(employeeRepository).should(times(1)).deleteById(employeeId);
    }

    @Test
    public void should_return_true_when_existsByEmployeeId() {
        //given
        Long employeeId = Long.valueOf(1);
        given(employeeRepository.existsById(employeeId)).willReturn(true);
        //when
        boolean result = employeeService.existsByEmployeeId(employeeId);

        //then
        then(employeeRepository).should(times(1)).existsById(employeeId);
        assertThat(result).isNotNull();
        assertThat(result).isTrue();
    }

    @Test
    public void should_return_Employee_when_getEmployeeById() {
        //given
        Long employeeId = Long.valueOf(1);
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(STUB_EMPLOYEE));
        //when
        Employee result = employeeService.getEmployee(employeeId);


        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("test");
        assertThat(result.getDepartment()).isEqualTo("testDepartment");
        assertThat(result.getSalary()).isEqualTo(10000);
    }

    @Test
    public void should_return_listOfEmployee_when_retrieveEmployees() {
        //given
        given(employeeRepository.findAll()).willReturn(List.of(STUB_EMPLOYEE));
        //when
        java.util.List<Employee> result = employeeService.retrieveEmployees();


        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(STUB_EMPLOYEE);
    }

}
