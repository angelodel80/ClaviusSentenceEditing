package it.cnr.ilc.clavius.controller;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author oakgen
 */
@RequestScoped
@Named
public class HomeController extends BaseController {

    @Inject
    private LoginController loginController;

    public String exitAction() {
        loginController.reset();
        return "loginView";
    }

    public String homeAction() {
        return "http://claviusontheweb.it";
    }

    public String userAction() {
        return "userView";
    }

    public String glossaryAction() {
        return "proofReaderView";
    }

    public String transcriptionAction() {
        return "transcriptionView";
    }
}
