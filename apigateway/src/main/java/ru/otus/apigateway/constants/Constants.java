package ru.otus.apigateway.constants;

public class Constants {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
    public static final String SIGNING_KEY = "devglan123r";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    //todo refactor
    public static volatile int THRESHOLD = -100;
}
