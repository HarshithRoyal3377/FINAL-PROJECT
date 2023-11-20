package shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void createValidUser_ShouldSucceed() {
	// Arrange
	String validUsername = "user123";
	String validPassword = "P@ssw0rd";

	// Act
	User user = new User(validUsername, validPassword);

	// Assert
	assertEquals(validUsername, user.getUsername(), "Username should be correctly assigned.");
	assertEquals("hashed_" + validPassword, user.getHashedPassword(),
		"Hashed password should be correctly assigned.");
	assertTrue(user.getRoles().isEmpty(), "Roles should be empty initially.");
    }

    @Test
    void setUsername_InvalidFormat_ShouldThrowException() {
	// Arrange
	String invalidUsername = "user@name"; // Invalid format

	// Act & Assert
	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	    new User(invalidUsername, "ValidPassword123");
	});

	assertEquals("Invalid username.", exception.getMessage(),
		"Exception message should indicate invalid username.");
    }

    @Test
    void setHashedPassword_InvalidFormat_ShouldThrowException() {
	// Arrange
	String invalidPassword = "weakpassword"; // Invalid format

	// Act & Assert
	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
	    new User("ValidUsername", invalidPassword);
	});

	assertEquals("Invalid password.", exception.getMessage(),
		"Exception message should indicate invalid password.");
    }

    @Test
    void addRole_ShouldAddRole() {
	// Arrange
	User user = new User("ValidUser", "ValidPassword");

	// Act
	user.addRole("ROLE_USER");

	// Assert
	Set<String> roles = user.getRoles();
	assertEquals(1, roles.size(), "Roles should contain one role.");
	assertTrue(roles.contains("ROLE_USER"), "Roles should contain the added role.");
    }

    @Test
    void getRoles_ShouldReturnCopy() {
	// Arrange
	User user = new User("ValidUser", "ValidPassword");
	user.addRole("ROLE_ADMIN");

	// Act
	Set<String> roles = user.getRoles();

	// Assert
	assertEquals(1, roles.size(), "Roles should contain one role.");

	// Modifying the original set should not affect the user's roles
	roles.add("ROLE_USER");
	assertFalse(user.getRoles().contains("ROLE_USER"),
		"Adding to the copied roles set should not affect the user's roles.");
    }
}
