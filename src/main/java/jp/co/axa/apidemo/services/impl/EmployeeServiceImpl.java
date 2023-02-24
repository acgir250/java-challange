/**
 * This class is used for the employee management service
 * to get the employee detail, save employee etc...
 */
package jp.co.axa.apidemo.services.impl;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.errors.exceptionhandler.EntityNotFoundException;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "Employees")
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final CacheManager cacheManager;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CacheManager cacheManager) {
        this.employeeRepository = employeeRepository;
        this.cacheManager = cacheManager;
    }


    public List<Employee> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees;
    }

    @Cacheable(value = "employeeCache", key = "#employeeId", unless = "#result == null")
    public Employee getEmployee(Long employeeId) {
        Optional<Employee> result = employeeRepository.findById(employeeId);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new javax.persistence.EntityNotFoundException("Employee does not exists for id :::"+employeeId);
        }
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = "employeeCache", key = "#employeeId")
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(@NotNull Employee employee, @NotNull Long employeeId) {
        Cache cache = cacheManager.getCache("employeeCache");
        boolean result = employeeRepository.existsById(employeeId);
        if (result) {
            employee.setEmployeeID(employeeId);
            employeeRepository.save(employee);
            cache.put(employeeId, employee);
        } else {
            throw new javax.persistence.EntityNotFoundException("Employee does not exists for id :::"+employeeId);
        }
    }

    @Override
    public boolean existsByEmployeeId(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}