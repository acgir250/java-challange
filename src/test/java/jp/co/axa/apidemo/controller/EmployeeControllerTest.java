package jp.co.axa.apidemo.controller;

import jp.co.axa.apidemo.controllers.employee.EmployeeController;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.impl.AppUserAuthenticationServiceImpl;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    EmployeeController employeeController;

    @Mock
    EmployeeService employeeService;

    @Mock
    AppUserAuthenticationServiceImpl appUserAuthenticationServiceImpl;


    @Test
    public void should_save_employee_when_PostMethod() {
        //given
        Employee employee = Employee.builder().department("test_department").salary(10000).name("test").build();
        given(appUserAuthenticationServiceImpl.validateUserRole(any())).willReturn(true);
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer");

        //when
        employeeController.saveEmployee(employee, Mockito.mock(OAuth2Authentication.class), header);
        //then
        verify(employeeService, times(1)).saveEmployee(employee);
    }

    @Test
    public void should_update_employee_when_PutMethodCalled() {
        //given
        Employee employee = Employee.builder().department("test_department").salary(10000).name("test").build();
        given(appUserAuthenticationServiceImpl.validateAdminUser(any())).willReturn(true);
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer");

        //when
        employeeController.updateEmployee(employee, Long.valueOf(1), Mockito.mock(OAuth2Authentication.class), header);
        //then
        verify(employeeService, times(1)).updateEmployee(employee, Long.valueOf(1));
    }

    @Test
    public void should_delete_employee_when_deleteMethodCalled() {
        //given
        given(appUserAuthenticationServiceImpl.validateAdminUser(any())).willReturn(true);
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer");

        //when
        employeeController.deleteEmployee(Long.valueOf(1), Mockito.mock(OAuth2Authentication.class), header);
        //then
        verify(employeeService, times(1)).deleteEmployee(Long.valueOf(1));
    }

    @Test
    public void should_return_employee_when_getEmployeeMethodCalled() {
        //given
        Employee employee = Employee.builder().department("test_department").salary(10000).name("test").build();
        given(appUserAuthenticationServiceImpl.validateUserRole(any())).willReturn(true);
        given(employeeService.getEmployee(Long.valueOf(1))).willReturn(employee);
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer");

        //when
        Employee result = employeeController.getEmployee(Long.valueOf(1), Mockito.mock(OAuth2Authentication.class), header);
        //then
        verify(employeeService, times(1)).getEmployee(Long.valueOf(1));
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(employee);
    }

    @Test
    public void should_return_allEmployee_when_getEmployeesMethodCalled() {
        //given
        Employee employee = Employee.builder().department("test_department").salary(10000).name("test").build();
        given(appUserAuthenticationServiceImpl.validateAdminUser(any())).willReturn(true);
        given(employeeService.retrieveEmployees()).willReturn(Collections.singletonList(employee));
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer");

        //when
        List<Employee> result = employeeController.getEmployees(Mockito.mock(OAuth2Authentication.class), header);
        //then
        verify(employeeService, times(1)).retrieveEmployees();
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0)).isEqualTo(employee);
    }

    @Test
    public void should_throw_Exception_when_getEmployeesMethodCalled() {
        //given
        Employee employee = Employee.builder().department("test_department").salary(10000).name("test").build();
        given(appUserAuthenticationServiceImpl.validateAdminUser(any())).willReturn(false);
        given(employeeService.retrieveEmployees()).willReturn(Collections.singletonList(employee));
        Map<String, String> header = new HashMap<>();
        header.put("authorization", "Bearer");

        //when

        //then
        assertThatThrownBy(() -> employeeController.getEmployees(Mockito.mock(OAuth2Authentication.class), header)).isInstanceOf(OAuth2Exception.class);
        verifyNoMoreInteractions(employeeService);
    }

}
