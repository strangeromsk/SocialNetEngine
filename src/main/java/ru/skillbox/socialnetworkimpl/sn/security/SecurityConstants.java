package ru.skillbox.socialnetworkimpl.sn.security;

public class SecurityConstants {

    public static final String API_LOGIN_URL = "/auth/login";
    public static final String API_LOGOUT_URL = "/auth/logout";
    public static final String HEADER = "Authorization";
    public static final long EXPIRATION_TIME_IN_MILLIS = 3_600_000;
    public static final String SECRET = "supersecret";
    public static final String PREFIX = "Bearer ";
    public static final String STORAGE_URL = "/storage";
    public static final String LOGOUT_URL = "/auth/logout";
    public static final String ACCOUNT_REGISTER_URL = "/account/register";
    public static final String ACCOUNT_PASSWORD_RECOVERY_URL = "/account/password/recovery";
    public static final String ACCOUNT_PASSWORD_SET_URL = "/account/password/set";
    public static final String PLATFORM_LANGS_URL = "/platform/languages";
    public static final String PLATFORM_COUNTRIES_URL = "/platform/countries";
    public static final String PLATFORM_CITIES_URL = "/platform/cities";
}