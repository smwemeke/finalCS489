package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.repository.VehicleServiceRepository;
import edu.miu.cse.vsms.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final VehicleServiceRepository vehicleServiceRepository;

    @Override
    public EmployeeResponseDto addEmployee(EmployeeRequestDto request) {

        // Fetch serveices

        Employee employee = new Employee(
              request.name(),
              request.email(),
              request.phone(),
              request.hireDate());
      Employee savedEmployee = employeeRepository.save(employee);
      List<VehicleServiceResponseDto> vehicleServiceResponseDtos = savedEmployee.getVServices().stream()
              .map(vService -> new VehicleServiceResponseDto(
                      vService.getEmployee().getId(),
                      vService.getServiceName(),
                      vService.getCost(),
                      vService.getVehicleType()
              ))
              .toList();

              return new EmployeeResponseDto(
              savedEmployee.getId(),
              savedEmployee.getName(),
              savedEmployee.getEmail(),
              savedEmployee.getPhone(),
              savedEmployee.getHireDate(),
              vehicleServiceResponseDtos
      );
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
     List<Employee> employeeList =employeeRepository.findAll();
     List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();

     List<VService> vServiceList = vehicleServiceRepository.findAll();

     List<VehicleServiceResponseDto> vehicleServiceResponseDtos = vServiceList.stream()
             .map(vService -> new VehicleServiceResponseDto(
                     vService.getEmployee().getId(),
                     vService.getServiceName(),
                     vService.getCost(),
                     vService.getVehicleType()
             ))
             .toList();

     for(Employee e : employeeList){
         employeeResponseDtos.add(
             new EmployeeResponseDto(
                 e.getId(),
                 e.getName(),
                 e.getEmail(),
                 e.getPhone(),
                 e.getHireDate(),
                 vehicleServiceResponseDtos
             )
         );
     }
        // Write your code here

        return employeeResponseDtos;
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        // Write your code here
        Employee foundEmployee = employeeRepository.findEmployeeById(id);

//        EmployeeResponseDto employeeResponseDto =
        return new EmployeeResponseDto(foundEmployee.getId(), foundEmployee.getName(), foundEmployee.getEmail(), foundEmployee.getPhone(),
                foundEmployee.getHireDate(),foundEmployee.getVServices().stream()
                .map(vService -> new VehicleServiceResponseDto(
                        foundEmployee.getId(),
                         vService.getServiceName(), vService.getCost(), vService.getVehicleType())).toList()
        );
    }

    @Override
    public EmployeeResponseDto partiallyUpdateEmployee(Long id, Map<String, Object> updates) {
        // Fetch the employee by ID or throw an exception if not found
////        Employee employee = employeeRepository.findById(id)
////                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
////
////        // Apply each update based on the key
////        updates.forEach((key, value) -> {
////            switch (key) {
//                case "name":
//                    // Write your code here
//
//                    break;
//                case "email":
//                    // Write your code here
//
//                    break;
//                case "phone":
//                    // Write your code here
//
//                    break;
//                case "hireDate":
//                    // Write your code here
//
//                    break;
//                default:
//                    throw new IllegalArgumentException("Invalid field: " + key);
//            }
//        });
//        // Write your code here
//
       return null;
//    }
    }

    private EmployeeResponseDto mapToResponseDto(Employee employee) {
        List<VehicleServiceResponseDto> serviceDtos = employee.getVServices().stream()
                .map(service -> new VehicleServiceResponseDto(
                        service.getId(),
                        service.getServiceName(),
                        service.getCost(),
                        service.getVehicleType()
                )).toList();

        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getHireDate(),
                serviceDtos
        );
    }
}
