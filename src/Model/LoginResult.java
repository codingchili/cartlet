package Model;

/**
 * Created by Robin on 2015-09-28.
 *
 * Carries the result of a login operation.
 */

public class LoginResult implements Bean {
    private Account account;
    private boolean erroneous;

    public LoginResult () {}

   protected void setAccount(Account account) {
       this.account = account;
   }

    public Account getAccount() {
        return account;
    }

    protected LoginResult setErroneous(boolean erroneous) {
        this.erroneous = erroneous;
        return this;
    }

    public boolean isErroneous() {
        return this.erroneous;
    }
}
