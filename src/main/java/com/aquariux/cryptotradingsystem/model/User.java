package com.aquariux.cryptotradingsystem.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "Users")
public class User {
	@Id
	@Column(name = "UserID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "UserName")
	private String userName;
	
	@Column(name = "Lock")
	private Boolean lock;
	
	@Column(name = "LockedDate")
	private Date lockedDate;
	
	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
	private Set<Wallet> wallets;

	public User() {
		
	}
	
	public User(String firstName, String lastName, String userName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.lock = false;
		this.lockedDate = null;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}
	
	public Set<Wallet> getWallets() {
		return wallets;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", userName="
				+ userName + ", lock=" + lock + ", lockedDate=" + lockedDate + ", wallets=" + wallets + "]";
	}

	
}
