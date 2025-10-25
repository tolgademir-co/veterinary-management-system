package com.tolgademir.veterinarymanagementsystem.service;

import com.tolgademir.veterinarymanagementsystem.dto.CustomerDto;
import com.tolgademir.veterinarymanagementsystem.model.Customer;
import com.tolgademir.veterinarymanagementsystem.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public List<CustomerDto> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    public CustomerDto create(CustomerDto dto) {
        Customer customer = modelMapper.map(dto, Customer.class);
        return modelMapper.map(customerRepository.save(customer), CustomerDto.class);
    }

    public void delete(Long id) {
        customerRepository.deleteById(id);
    }
}