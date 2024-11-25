package edu.miu.cse.vsms.controller;

import edu.miu.cse.vsms.dto.request.EmployeeRequestDto;
import edu.miu.cse.vsms.dto.request.ServiceRequestDto;
import edu.miu.cse.vsms.dto.response.EmployeeResponseDto;
import edu.miu.cse.vsms.dto.response.VehicleServiceResponseDto;
import edu.miu.cse.vsms.model.VService;
import edu.miu.cse.vsms.repository.VehicleServiceRepository;
import edu.miu.cse.vsms.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;
    @Mock
    private VehicleServiceRepository vehicleServiceRepository;
    @InjectMocks
    private EmployeeController employeeController;


    @Test
    void addEmployee() {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(
                "Sue",
                "sue.mwemeke@gmail.com",
                "0772896767",
                LocalDate.of(2024,12,23)
        );
        List<VService> vServiceList = vehicleServiceRepository.findAll();

        List<VehicleServiceResponseDto> vehicleServiceResponseDtos =
                vServiceList.stream()
                        .map(vService ->
                                new VehicleServiceResponseDto(
                                        vService.getId(),
                                        vService.getServiceName(),
                                        vService.getCost(),
                                        vService.getVehicleType()
                                )).toList();

        EmployeeResponseDto employeeResponseDto =
                new EmployeeResponseDto(
                        1L,
                        "Sue",
                        "sue.mwemeke@gmail.com",
                        "0773816767",
                        LocalDate.of(2024,12,23),
                        vehicleServiceResponseDtos
                );

        Mockito.when(employeeService.addEmployee(employeeRequestDto))
                .thenReturn(employeeResponseDto);
        ResponseEntity<?> responseEntity1 =
                employeeController.addEmployee(employeeRequestDto);

        Assertions.assertEquals(HttpStatus.CREATED,
                responseEntity1.getStatusCode());
        Assertions.assertEquals(employeeResponseDto, responseEntity1.getBody());
    }
}