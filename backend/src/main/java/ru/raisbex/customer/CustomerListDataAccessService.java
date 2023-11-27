package ru.raisbex.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDataAccessService implements CustomerDao{

    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();

        Customer alex = new Customer(
                1L,
                "Alex",
                "alex@gmail.com",
                21);
        customers.add(alex);

        Customer jamila = new Customer(
                2L,
                "Jamila",
                "jamila@gmail.com",
                19);
        customers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        Long longId = id.longValue();
        return customers.stream()
                .filter(c -> c.getId().equals(longId))
                .findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        Long longId = id.longValue();
        return  customers.stream()
                .anyMatch(c -> c.getId().equals(longId));
    }

    @Override
    public void deleteCustomerByID(Integer customerId) {
        Long longId = customerId.longValue();
        customers.stream()
                .filter(c -> c.getId().equals(longId))
                .findFirst()
                .ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer update) {
        customers.add(update);
    }
}