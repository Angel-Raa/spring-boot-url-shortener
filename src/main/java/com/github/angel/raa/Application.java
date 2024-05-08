package com.github.angel.raa;

import com.github.angel.raa.persistence.entity.Authorities;
import com.github.angel.raa.persistence.entity.RoleEntity;
import com.github.angel.raa.persistence.entity.UserEntity;
import com.github.angel.raa.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		UserEntity userEntity = new UserEntity();
		RoleEntity role = new RoleEntity();
		role.setName("angel");
		role.setAuthorities(Authorities.ROLE_USER);
		userEntity.setEmail("angel");
		userEntity.setUsername("angel");
		userEntity.setRole(role);
		userRepository.save(userEntity);

	}
}
