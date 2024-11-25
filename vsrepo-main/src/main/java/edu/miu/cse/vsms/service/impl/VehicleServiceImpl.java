package edu.miu.cse.vsms.service.impl;

import edu.miu.cse.vsms.dto.request.ServiceRequestDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.exception.ResourceNotFoundException;
import edu.miu.cse.vsms.model.Employee;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.EmployeeRepository;
import edu.miu.cse.vsms.repository.VehicleServiceRepository;
import edu.miu.cse.vsms.service.VehicleService;
import lombok.RequiredArgsConstructor;

import java.util.List;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleServiceRepository vehicleServiceRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public VehicleServiceResponseDto assignService(ServiceRequestDto request) {
        // Write your code here
        Employee employee = employeeRepository.findEmployeeById(request.employeeId());
        VService vService = new VService(
                request.serviceName(),
                request.cost(), request.vehicleType(),employee);
        VService savedVService = vehicleServiceRepository.save(vService);

       // Employee assigneEmployee = employeeRepository.save(savedVService);

          return new VehicleServiceResponseDto(
                  //savedVService.getEmployee().
                  savedVService.getId(),
                  savedVService.getServiceName(),
                  savedVService.getCost(),
                  savedVService.getVehicleType()
          );

    }
}
