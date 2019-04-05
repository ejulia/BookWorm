package com.ejulia.bookworm.model;

public class Home {
    private final String welcomeMessage;

    public Home(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }
}
