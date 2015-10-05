package Model;

/**
 * Created by Robin on 2015-09-28.
 *
 * Carries the result of a Registration operation.
 */

public class RegisterResult implements Bean {
    private boolean passwordMismatch;
    private boolean isZipSet = true;
    private boolean isStreetSet = true;
    private boolean accountExists;
    private boolean passwordTooShort;
    private boolean accountNameTooShort;
    private boolean serverError;
    private Account account;

    public RegisterResult() {
    }

    public Account getAccount() {
        return account;
    }

    protected void setAccount(Account account) {
        this.account = account;
    }

    public boolean isAccountExists() {
        return accountExists;
    }

    protected RegisterResult setAccountExists(boolean accountExists) {
        this.accountExists = accountExists;
        return this;
    }

    public boolean isErroneous() {
        return (serverError || passwordMismatch || !isZipSet || !isStreetSet
                || accountExists || passwordTooShort || accountNameTooShort);
    }

    public boolean isPasswordMismatch() {
        return passwordMismatch;
    }

    public RegisterResult setPasswordMismatch(boolean isMismatching) {
        passwordMismatch = isMismatching;
        return this;
    }

    public boolean isZipSet() {
        return isZipSet;
    }

    public RegisterResult setZipSet(boolean isZipSet) {
        this.isZipSet = isZipSet;
        return this;
    }

    public boolean isStreetSet() {
        return isStreetSet;
    }

    public RegisterResult setStreetSet(boolean isStreetSet) {
        this.isStreetSet = isStreetSet;
        return this;
    }

    protected RegisterResult setPasswordLowEntropy(boolean passwordLowEntropy) {
        this.passwordTooShort = passwordLowEntropy;
        return this;
    }

    public boolean isPasswordTooShort() {
        return passwordTooShort;
    }

    public boolean isServerError() {
        return serverError;
    }

    public void setServerError(boolean serverError) {
        this.serverError = serverError;
    }

    protected RegisterResult setAccountNameTooShort(boolean accountNameTooShort) {
        this.accountNameTooShort = accountNameTooShort;
        return this;
    }

    public boolean isAccountNameTooShort() {
        return accountNameTooShort;
    }
}
