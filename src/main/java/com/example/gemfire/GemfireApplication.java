package com.example.gemfire;

import com.example.gemfire.hello.Person;
import com.example.gemfire.repository.PersonRepository;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.stream.StreamSupport.stream;



@SpringBootApplication(scanBasePackages = {"com.example.gemfire.repository"})
@EnableAutoConfiguration

@ClientCacheApplication(name = "AccessingDataGemFireApplication", logLevel = "error")
@EnableEntityDefinedRegions(basePackageClasses = Person.class,
		clientRegionShortcut = ClientRegionShortcut.LOCAL)
//@EnableJpaRepositories(basePackages={"com"})


@EnableGemfireRepositories
public class GemfireApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(GemfireApplication.class, args);
	}





	@Bean
	ApplicationRunner run(PersonRepository personRepository) {

		return args -> {

			Person alice = new Person("Adult Alice", 40);
			Person bob = new Person("Baby Bob", 1);
			Person carol = new Person("Teen Carol", 13);

			System.out.println("Before accessing data in Pivotal GemFire...");

			asList(alice, bob, carol).forEach(person -> System.out.println("\t" + person));

			System.out.println("Saving Alice, Bob and Carol to Pivotal GemFire...");

			personRepository.save(alice);
			personRepository.save(bob);
			personRepository.save(carol);

			System.out.println("Lookup each person by name...");

			asList(alice.getName(), bob.getName(), carol.getName())
					.forEach(name -> System.out.println("\t" + personRepository.findByName(name)));

			System.out.println("Query adults (over 18):");

			stream(personRepository.findByAgeGreaterThan(18).spliterator(), false)
					.forEach(person -> System.out.println("\t" + person));

			System.out.println("Query babies (less than 5):");

			stream(personRepository.findByAgeLessThan(5).spliterator(), false)
					.forEach(person -> System.out.println("\t" + person));

			System.out.println("Query teens (between 12 and 20):");

			stream(personRepository.findByAgeGreaterThanAndAgeLessThan(12, 20).spliterator(), false)
					.forEach(person -> System.out.println("\t" + person));
		};
	}
}
