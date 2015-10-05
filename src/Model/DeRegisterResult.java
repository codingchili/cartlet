package Model;

/**
 * Created by Robin on 2015-09-29.
 *
 * Holds the result of the Account DeRegister operation.
 */

public class DeRegisterResult implements Bean {
    private boolean erroneous;
    private boolean accountNotFound;

    protected DeRegisterResult() {}

    public void setErroneous(boolean erroneous) {
        this.erroneous = erroneous;
    }

    public boolean isErroneous() {
        return erroneous;
    }

    public void setAccountNotFound(boolean accountNotFound) {
        this.accountNotFound = accountNotFound;
    }

    public boolean isAccountNotFound() {
        return accountNotFound;
    }
}
