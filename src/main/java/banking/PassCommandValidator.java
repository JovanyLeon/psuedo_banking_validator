package banking;

public class PassCommandValidator {

	public boolean validate(String command) {
		// Split the command into parts
		String[] parts = command.split("\\s+");

		// Validate the command structure
		if (parts.length != 2) {
			return false; // Invalid command format
		}

		// Validate the command keyword
		if (!parts[0].equalsIgnoreCase("pass")) {
			return false; // Command doesn't start with "pass"
		}

		// Validate the number of months
		try {
			int months = Integer.parseInt(parts[1]);
			return months >= 1 && months <= 60; // Only valid for 1 to 60 months
		} catch (NumberFormatException e) {
			return false; // The second part isn't a valid integer
		}
	}
}
