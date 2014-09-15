package it.cnr.ilc.clavius.controller;

import it.cnr.ilc.clavius.domain.Account;
import it.cnr.ilc.clavius.manager.LoginManager;
import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author oakgen
 */
@SessionScoped
@Named
public class LoginController extends BaseController implements Serializable {

    private static final long FAIL_AUTHENTICATION_SLEEP = 1000;

    @Inject
    private transient LoginManager loginManager;

    private String username;
    private String password;

    private Account account;

    public Account getAccount() {
        return account;
    }

    public void reset() {
        account = null;
        username = null;
        password = null;
    }

    public boolean isLoggedIn() {
        return account != null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String enterAction() {
        if (username == null || username.equals("")) {
            warn("Autenticazione", "Nome utente mancante");
            return null;
        } else {
            account = loginManager.authenticate(username, password);
            if (account == null) {
                warn("Autenticazione", "Accesso negato.");
                try {
                    Thread.sleep(FAIL_AUTHENTICATION_SLEEP);
                } catch (InterruptedException ex) {
                }
                return null;
            } else {
                return "homeView";
            }
        }
    }

}
