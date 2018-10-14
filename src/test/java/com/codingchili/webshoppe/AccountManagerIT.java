package com.codingchili.webshoppe;

import com.codingchili.webshoppe.model.DeRegisterResult;
import com.codingchili.webshoppe.model.LoginResult;
import com.codingchili.webshoppe.model.RegisterResult;
import com.codingchili.webshoppe.model.Account;
import com.codingchili.webshoppe.model.AccountManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Robin on 2015-09-29.
 * <p>
 * Tests for the authenticator used for the AccountStore.
 */

public class AccountManagerIT {
    private final static String ZIP = "137 32 SE";
    private final static String STREET = "Teststreet 1";
    private final static String USER_SHORT = ".";
    private final static String PASS_SHORT = ".";
    private final static String USER = "user_test_user";
    private final static String PASS = "my_password";
    public static final String MANAGER = "manager";
    public static final String ADMIN = "admin";

    @Test
    public void accountLifecycle() throws Exception {
        class AccountLifecycle {
            private Account account;

            private void login() throws Exception {
                LoginResult result = AccountManager.authenticate(USER, PASS);
                account = result.getAccount();
                if (result.isErroneous()) {
                    throw new Exception("Failed to authenticate valid account");
                }
            }

            private void register() throws Exception {
                RegisterResult result = AccountManager.register(getAccount());

                if (result.isErroneous()) {
                    throw new Exception("Account was not registered successfully.");
                }
            }

            private void overWriteProtected() throws Exception {
                RegisterResult result = AccountManager.register(getAccount());

                if (!result.isErroneous()) {
                    throw new Exception("Account registered when an account was already existing with same name.");
                }
            }

            private void deRegister() throws Exception {
                DeRegisterResult deRegister = AccountManager.deRegister(account);

                if (deRegister.isErroneous()) {
                    throw new Exception("Account was not successfully deRegistered.");
                }
            }

            public void loginWithWrongPassword() throws Exception {
                LoginResult result = AccountManager.authenticate(USER, PASS + "invalid");

                if (!result.isErroneous())
                    throw new Exception("Invalid passwords not detected.");
            }
        }

        AccountLifecycle lifecycle = new AccountLifecycle();
        lifecycle.register();
        lifecycle.overWriteProtected();
        lifecycle.loginWithWrongPassword();
        lifecycle.login();
        lifecycle.deRegister();
    }

    public static Account getAccount() {
        return new Account().setUsername(USER)
                .setPassword(PASS)
                .setPasswordRepeat(PASS)
                .setStreet(STREET)
                .setZip(ZIP);
    }

    @Test
    public void loginWithNonExistentUsername() throws Exception {
        LoginResult result = AccountManager.authenticate(USER + "non-existing", PASS);

        if (!result.isErroneous()) {
            throw new Exception("Invalid username not detected.");
        }
    }


    @Test
    public void registerWithShortUsername() throws Exception {
        RegisterResult result = AccountManager.register(getAccount().setUsername(USER_SHORT));

        if (!result.isAccountNameTooShort()) {
            AccountManager.deRegister(result.getAccount());
            throw new Exception("Too short account name not detected.");
        }
    }

    @Test
    public void registerWithShortPassword() throws Exception {
        RegisterResult result = AccountManager.register(getAccount()
                .setPassword(PASS_SHORT)
                .setPasswordRepeat(PASS_SHORT));

        if (!result.isPasswordTooShort()) {
            AccountManager.deRegister(result.getAccount());
            throw new Exception("Too short password not detected.");
        }
    }

    @Test
    public void registerWithPasswordMismatch() throws Exception {
        RegisterResult result = AccountManager.register(getAccount()
                .setPassword(PASS)
                .setPasswordRepeat(PASS_SHORT));

        if (!result.isPasswordMismatch()) {
            AccountManager.deRegister(result.getAccount());
            throw new Exception("Password mismatch not detected.");
        }
    }

    @Test
    public void registerWithoutZipShouldError() throws Exception {
        RegisterResult result = AccountManager.register(getAccount().setZip(""));

        if (result.isZipSet() || !result.isErroneous()) {
            AccountManager.deRegister(result.getAccount());
            throw new Exception("Account created with empty zip.");
        }
    }

    @Test
    public void registerWithoutStreetShouldError() throws Exception {
        RegisterResult result = AccountManager.register(getAccount().setStreet(""));

        if (result.isStreetSet() || !result.isErroneous()) {
            AccountManager.deRegister(result.getAccount());
            throw new Exception("Account created with empty street.");
        }
    }

    @Test
    public void shouldReturnAccountWithZip() throws Exception {
        RegisterResult result = AccountManager.register(getAccount());
        AccountManager.deRegister(result.getAccount());

        if (result.getAccount().getZip().length() == 0) {
            throw new Exception("Account does not contain ZIP information.");
        }
    }

    @Test
    public void shouldReturnAccountWithStreet() throws Exception {
        RegisterResult result = AccountManager.register(getAccount());
        AccountManager.deRegister(result.getAccount());

        if (result.getAccount().getStreet().length() == 0) {
            throw new Exception("Account does not contain street information.");
        }
    }

    @Test
    public void shouldReturnAccountWithRole() throws Exception {
        RegisterResult result = AccountManager.register(getAccount());
        AccountManager.deRegister(result.getAccount());

        if (result.getAccount().getRole().getId() != 1) {
            throw new Exception("Account does not contain role information.");
        }
    }

    @Test
    public void shouldCreateAdministrativeAccount() throws Exception {
        RegisterResult result = AccountManager.registerAdmin(getAccount().setUsername(ADMIN));
        AccountManager.deRegister(result.getAccount());

        if (result.isErroneous()) {
            throw new Exception("Administrative account failed to create.");
        }
    }

    @Test
    public void shouldCreateListAndRemoveManagers() throws Exception {
        RegisterResult register = AccountManager.registerManager(getAccount().setUsername(MANAGER));
        List<Account> managers = AccountManager.getManagers();
        DeRegisterResult deregister = AccountManager.deRegister(register.getAccount());

        if (register.isErroneous()) {
            throw new Exception("Failed to register manager.");
        }

        if (deregister.isErroneous()) {
            throw new Exception("Failed to deregister manager.");
        }

        if (managers.size() == 0) {
            throw new Exception("Failed to retrieve managers.");
        }
    }
}
