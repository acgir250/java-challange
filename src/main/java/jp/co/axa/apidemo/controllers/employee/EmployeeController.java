package jp.co.axa.apidemo.controllers.employee;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.impl.AppUserAuthenticationServiceImpl;
import jp.co.axa.apidemo.services.EmployeeService;
import jp.co.axa.apidemo.utils.HeadersUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static jp.co.axa.apidemo.constants.ApiConstant.BASE_URL;
import static jp.co.axa.apidemo.constants.ApiConstant.EMPLOYEE_ID_PARAM;

/**
 *
 *  This is the controller class where servlet request will be dispatch from the user and mapped with particular methods.
 *
 */
@RestController
@Slf4j
@RequestMapping(BASE_URL) // no need to specify on each method so this constant will work.
public class EmployeeController {


    private final EmployeeService employeeService;
    private final AppUserAuthenticationServiceImpl appUserAuthenticationServiceImpl;


    public EmployeeController(EmployeeService employeeService, AppUserAuthenticationServiceImpl appUserAuthenticationServiceImpl) {
        this.employeeService = employeeService;
        this.appUserAuthenticationServiceImpl = appUserAuthenticationServiceImpl;
    }

    /**
     *  This is the admin access endpoint for retrieve all employees
     *
     * @param authentication
     * @param headers
     * @return List<Employee>
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Employee> getEmployees(OAuth2Authentication authentication, @RequestHeader Map<String, String> headers) {
        if (appUserAuthenticationServiceImpl.validateAdminUser(authentication) && HeadersUtils.validateTokenHeader(headers)) {
            return employeeService.retrieveEmployees();
        } else {
            throw new OAuth2Exception("UnAuthenticated User");
        }
    }

    /**
     * This is the user endpoint to get the employee details which he has persisted in the database.
     * @param employeeId
     * @param authentication
     * @param headers
     * @return Employee
     */
    @GetMapping(EMPLOYEE_ID_PARAM)
    @PreAuthorize("hasAuthority('USER')")
    public Employee getEmployee(@PathVariable(name = "employeeId") Long employeeId, OAuth2Authentication authentication, @RequestHeader Map<String, String> headers) {
        if (appUserAuthenticationServiceImpl.validateUserRole(authentication) && HeadersUtils.validateTokenHeader(headers)) {
            return employeeService.getEmployee(employeeId);
        } else {
            throw new OAuth2Exception("UnAuthenticated User");
        }
    }

    /**
     *  This is the user endpoint to save the employee information in the database.
     * @param employee
     * @param authentication
     * @param headers
     * @return  Employee
     */
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public Employee saveEmployee(@Valid @RequestBody Employee employee, OAuth2Authentication authentication, @RequestHeader Map<String, String> headers) {  // need to add annotation of requestbody due to which request body is being captured.
        if (appUserAuthenticationServiceImpl.validateUserRole(authentication) && HeadersUtils.validateTokenHeader(headers)) {
            log.info("employee save operation started:::");
            return employeeService.saveEmployee(employee);
        } else {
            throw new OAuth2Exception("UnAuthenticated User");
        }
    }


    /**
     *  This is the admin endpoint where admin can perform the delete operation.
     * @param employeeId
     * @param authentication
     * @param headers
     * @return http status
     */
    @DeleteMapping(EMPLOYEE_ID_PARAM)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEmployee(@PathVariable(name = "employeeId") Long employeeId, OAuth2Authentication authentication, @RequestHeader Map<String, String> headers) {
        if (appUserAuthenticationServiceImpl.validateAdminUser(authentication) && HeadersUtils.validateTokenHeader(headers)) {
            log.info("employee delete operation started:::");
            employeeService.deleteEmployee(employeeId);
            log.info("employee deleted successfully");
        } else {
            throw new OAuth2Exception("UnAuthenticated User");
        }
    }

    /**
     * This endpoint is used by the admin for updating information of the employee.
     * @param employee
     * @param employeeId
     * @param authentication
     * @param headers
     */
    @PutMapping(EMPLOYEE_ID_PARAM)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateEmployee(@Valid @RequestBody Employee employee,
                               @PathVariable(name = "employeeId") Long employeeId, OAuth2Authentication authentication, @RequestHeader Map<String, String> headers) {
        if (appUserAuthenticationServiceImpl.validateAdminUser(authentication) && HeadersUtils.validateTokenHeader(headers)) {
            log.info("employee update operation started:::");
            employeeService.updateEmployee(employee, employeeId);
            log.info("employee updated successfully");
        } else {
            throw new OAuth2Exception("UnAuthenticated User");
        }
    }
}
