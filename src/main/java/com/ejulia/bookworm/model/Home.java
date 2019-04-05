package com.ejulia.bookworm.model;

public class Home {
    private final String welcomeTemplate = "Welcome to BookWorm %s!";

    public String getWelcomeMessage(String userName) {
        return String.format(welcomeTemplate, userName);
    }
}
