package org.xhliu.springwebflux;

import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.xhliu.springwebflux.entity.Customer;
import org.xhliu.springwebflux.repository.CustomerRepository;

import java.time.Duration;
import java.util.Arrays;

@EnableR2dbcRepositories
@SpringBootApplication
public class SpringWebfluxApplication {

    private final static Logger log = LoggerFactory.getLogger(SpringWebfluxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringWebfluxApplication.class, args);
    }

//    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory factory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(factory);
//        initializer.setDatabasePopulator(new ResourceDatabasePopulator(
//                new ClassPathResource("schema.sql")
//        ));

        return initializer;
    }

    @Bean
    public CommandLineRunner runner(CustomerRepository repository) {
        return (args) -> {
            repository.saveAll(Arrays.asList(new Customer("Jack", "Bauer"),
                            new Customer("Chloe", "O'Brian"),
                            new Customer("Kim", "Bauer"),
                            new Customer("David", "Palmer"),
                            new Customer("Michelle", "Dessler")))
                    .blockLast(Duration.ofSeconds(10));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            repository.findAll().doOnNext(customer -> {
                log.info(customer.toString());
            }).blockLast(Duration.ofSeconds(10));
            log.info("");

            // fetch an individual customer by ID
            repository.findById(1L).doOnNext(customer -> {
                log.info("Customer found with findById(1L):");
                log.info("--------------------------------");
                log.info(customer.toString());
                log.info("");
            }).block(Duration.ofSeconds(10));


            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").doOnNext(bauer -> {
                log.info(bauer.toString());
            }).blockLast(Duration.ofSeconds(10));;
            log.info("");
        };
    }
}
