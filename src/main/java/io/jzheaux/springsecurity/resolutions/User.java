package io.jzheaux.springsecurity.resolutions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity(name = "users")
public class User implements Serializable {
	@Id
	UUID id;

	@Column
	String username;

	@Column
	String password;

	@Column
	Boolean enabled;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<UserAuthority> userAuthorities = new ArrayList<UserAuthority>();

	User() {

	}

	User(String username, String password) {
		this.id = UUID.randomUUID();
		this.username = username;
		this.password = password;
	}

	public User(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.userAuthorities = user.getUserAuthorities();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void grantAuthoriy(String authority) {
		UserAuthority userAuthority = new UserAuthority(authority, this);
		this.userAuthorities.add(userAuthority);
	}

}
