package ru.raisbex;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.raisbex.customer.Customer;
import ru.raisbex.customer.CustomerRepository;
import ru.raisbex.customer.Gender;

import java.util.Random;

@SpringBootApplication
public class SpringBootFulstackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFulstackApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(
			CustomerRepository customerRepository) {
		return args -> {
			createRandomCustomer(customerRepository);
			// testBucketUploadAndDownload(s3Service, s3Buckets);
		};
	}

	private static void createRandomCustomer(CustomerRepository customerRepository) {
		var faker = new Faker();
		Random random = new Random();
		Name name = faker.name();
		String firstName = name.firstName();
		String lastName = name.lastName();
		int age = random.nextInt(16, 99);
		Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
		String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@raisbex.ru";
		Customer customer = new Customer(
				firstName +  " " + lastName,
				email,
				age,
				gender);
		customerRepository.save(customer);
	}
}
