package ru.raisbex.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.raisbex.exception.DuplicateResourceException;
import ru.raisbex.exception.RequestValidationException;
import ru.raisbex.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;


    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }


    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }


    public Customer getCustomer(Integer customerId) {
        return customerDao.selectCustomerById(customerId).
                orElseThrow(() -> new ResourceNotFoundException(
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
              request.age()
       );

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
        Customer customer = getCustomer(customerID);

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
