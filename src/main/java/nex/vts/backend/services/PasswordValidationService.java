package nex.vts.backend.services;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidationService {

    public String isValidPassword(String password) {
        // Check password length
        if (password.length() < 8 || password.length() > 20) {
            return "Password contains at least 8 characters and at most 20 characters";
        }

        // Check for at least one digit
        if (!password.matches(".*\\d.*")) {
            return "Need at least one digit";
        }

        // Check for at least one upper case alphabet
        if (!password.matches(".*[A-Z].*")) {
            return "Need at least one upper case alphabet";
        }

        // Check for at least one lower case alphabet
        if (!password.matches(".*[a-z].*")) {
            return "Need at least one lower case alphabet";
        }

        // Check for at least one special character
        if (!password.matches(".*[!@#$%&*()\\-+=^].*")) {
            return "Need at least one special character";
        }

        // Check for white spaces
        if (password.contains(" ")) {
            return "Can't put white spaces";
        }

        return null;
    }
}

