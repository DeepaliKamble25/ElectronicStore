package com.electronic.store;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@ComponentScan
public class ElectronicStoreApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

   @Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public void run(String... args) throws Exception {

		System.out.println(passwordEncoder.encode("abcd"));

	}
}
