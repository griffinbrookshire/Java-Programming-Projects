package edu.ncsu.csc216.pack_scheduler.user;
/**
 * Abstract class that is the superclass of Student
 * Contains the setters and getters of the fields
 * @author noaheggenschwiler
 *
 */
public abstract class User {

	/** first name of student */
	private String firstName;
	/** last name of student */
	private String lastName;
	/** id of student */
	private String id;
	/** email of student */
	private String email;
	/** password of student */
	private String password;
	
	/**
	 * Constructor for the user class
	 * @param firstName First name of user
	 * @param lastName last Name of user
	 * @param id id of user
	 * @param email of user
	 * @param pw password of user
	 */
	public User(String firstName, String lastName, String id, String email, String pw) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setId(id);
		this.setEmail(email);
		this.setPassword(pw);
	}

	/**
	 * Returns first name of student
	 * @return firstName 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns last name of student
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns id of student
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns email of student
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the student's email
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		if(email == null || email.equals("") || !email.contains("@") || 
		!email.contains(".") || email.lastIndexOf('.') < email.indexOf('@')) {
	throw new IllegalArgumentException ("Invalid email");
		}
		this.email = email;
	}

	/**
	 * Returns the password of the student
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the student's password
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if (password == null || password.equals("")) {
	throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * Sets the first name of the student
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if the parameter is null or empty
	 */
	public void setFirstName(String firstName) {
		if(firstName == null || firstName.equals("")) {
	throw new IllegalArgumentException ("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the last name of the student
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if the parameter is null or empty
	 */
	public void setLastName(String lastName) {
		if(lastName == null || lastName.equals("")) {
	throw new IllegalArgumentException ("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Sets the student's id
	 * @param id the id to set
	 */
	protected void setId(String id) {
		if(id == null || id.equals("")) {
	throw new IllegalArgumentException ("Invalid id");
		}
		this.id = id;
	}

	/**
	 * Generates hashCode for the User object
	 * @return hashCode of object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	/**
	 * Determines if a User is equal to another user
	 * @return true if objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	

}