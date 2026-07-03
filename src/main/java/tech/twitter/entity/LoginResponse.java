package tech.twitter.entity;

public class LoginResponse {
    public Long userId;
    public String message;
    public boolean isLoggedIn;

    public LoginResponse() {
    }

    public LoginResponse(Long userId, String message, boolean isLoggedIn) {
        this.userId = userId;
        this.message = message;
        this.isLoggedIn = isLoggedIn;
    }
}
