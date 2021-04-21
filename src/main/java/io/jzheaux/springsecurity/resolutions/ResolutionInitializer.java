package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
	private final ResolutionRepository resolutions;

	private final UserRepository userRepository;

	public ResolutionInitializer(ResolutionRepository resolutions, UserRepository userRepository) {
		this.resolutions = resolutions;
		this.userRepository = userRepository;
	}

	@Override
	public void afterSingletonsInstantiated() {
		this.resolutions.save(new Resolution("Read War and Peace", "user"));
		this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
		this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));
		// {bcrypt}$2a$10$zNFnSXwNAv3eL7VABvE0r.0An6yz39bc9m7Pq27A1089B5Hk5/FZC
		// User with both authorities read and write
		User user = new User("bharat", "{bcrypt}$2a$10$3njzOWhsz20aimcpMamJhOnX9Pb4Nk3toq8OO0swIy5EPZnb1YyGe");
		user.grantAuthoriy("resolution:read");
		user.grantAuthoriy("resolution:write");
		this.userRepository.save(user);

		// User with both authorities read and write
		User admin = new User("bharat", "{bcrypt}$2a$10$3njzOWhsz20aimcpMamJhOnX9Pb4Nk3toq8OO0swIy5EPZnb1YyGe");
		admin.grantAuthoriy("ROLE_ADMIN");
		this.userRepository.save(admin);

		// User with both authorities read and write
		User readUser = new User("bharatR", "{bcrypt}$2a$10$3njzOWhsz20aimcpMamJhOnX9Pb4Nk3toq8OO0swIy5EPZnb1YyGe");
		user.grantAuthoriy("resolution:read");
		this.userRepository.save(readUser);

		// User with both authorities read and write
		User writeUser = new User("bharatW", "{bcrypt}$2a$10$3njzOWhsz20aimcpMamJhOnX9Pb4Nk3toq8OO0swIy5EPZnb1YyGe");
		user.grantAuthoriy("resolution:write");
		this.userRepository.save(writeUser);
	}
}
