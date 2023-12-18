package ru.raisbex.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.raisbex.exception.DuplicateResourceException;
import ru.raisbex.exception.RequestValidationException;
import ru.raisbex.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    private final CustomerDTOMapper  customerDTOMapper;
    private final PasswordEncoder passwordEncoder;



    public CustomerService(@Qualifier("jpa") CustomerDao customerDao, CustomerDTOMapper customerDTOMapper, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerDao.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }


    public CustomerDTO getCustomer(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerId)
        ));
    }


    public void addCustomer(CustomerRegistrationRequest request) {
       String email = request.email();
       if (customerDao.existsPersonWithEmail(email)){
           throw new DuplicateResourceException(
                   "email already taken"
           );
       }

       Customer customer = new Customer(
              request.name(),
               request.email(),
               passwordEncoder.encode(request.password()),
               request.age(),
               request.gender());

       customerDao.insertCustomer(customer);
    }


    public void deleteCustomerById(Integer customerId) {
        if (!customerDao.existsPersonWithId(customerId)){
            throw  new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }

        customerDao.deleteCustomerByID(customerId);
    }


    public void updateCustomer(Integer customerID, CustomerUpdateRequest updateRequest) {
        Customer customer = customerDao.selectCustomerById(customerID)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(customerID)
                ));

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())){
             customer.setName(updateRequest.name());
             changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())){
            if (customerDao.existsPersonWithEmail(updateRequest.email())){
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes){
            throw new RequestValidationException(
                  "no data changes found"
            );
        }

        customerDao.updateCustomer(customer);
    }
}
