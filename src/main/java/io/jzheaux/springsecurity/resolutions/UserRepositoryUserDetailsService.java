package io.jzheaux.springsecurity.resolutions;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserRepositoryUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public UserRepositoryUserDetailsService(UserRepository users) {
		this.userRepository = users;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username).map(BridgeUser::new)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with name: " + username));
	}

	private static class BridgeUser extends User implements UserDetails {

		BridgeUser(User user) {
			super(user);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return getUserAuthorities().stream().map(UserAuthority::getAuthority).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
		}

		@Override
		public boolean isAccountNonExpired() {
			return this.getEnabled();
		}

		@Override
		public boolean isAccountNonLocked() {
			return this.getEnabled();
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return this.getEnabled();
		}

		@Override
		public boolean isEnabled() {
			return this.getEnabled();
		}

	}
}
