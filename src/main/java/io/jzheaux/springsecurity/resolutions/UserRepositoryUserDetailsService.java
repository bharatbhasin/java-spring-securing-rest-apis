package io.jzheaux.springsecurity.resolutions;

import java.util.Collection;
import java.util.HashSet;

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
		return this.userRepository.findByUsername(username).map(this::map)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with name: " + username));
	}

	private BridgeUser map(User user) {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (UserAuthority userAuthority : user.getUserAuthorities()) {
			if (userAuthority.getAuthority().equals("ROLE_ADMIN")) {
				authorities.add(new SimpleGrantedAuthority("resolution:read"));
				authorities.add(new SimpleGrantedAuthority("resolution:write"));
			}
			authorities.add(new SimpleGrantedAuthority(userAuthority.getAuthority()));
		}
		return new BridgeUser(user, authorities);
	}

	private static class BridgeUser extends User implements UserDetails {
		private Collection<GrantedAuthority> authorities;

		BridgeUser(User user, Collection<GrantedAuthority> authorities) {
			super(user);
			this.authorities = authorities;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return this.authorities;
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
