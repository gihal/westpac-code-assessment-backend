package com.westpac.account.exception;

public class SavingsAccountException extends RuntimeException {

    public static final String ERROR_ACCOUNT_LIMIT_EXCEEDED =
            "ACCOUNT_LIMIT_EXCEEDED";

    public static final String ERROR_OFFENSIVE_NICKNAME =
            "OFFENSIVE_NICKNAME";

    public static final String ERROR_ACCOUNT_NOT_FOUND =
            "ACCOUNT_NOT_FOUND";

    private final String code;

    public SavingsAccountException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Gets the error code.
     * @return the error code
     */
    public String getCode() {
        return code;
    }
}
