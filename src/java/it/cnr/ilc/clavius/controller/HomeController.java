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
        return "homeView";
    }

    public String userAction() {
        return "userView";
    }

    public String glossaryAction() {
        return "glossaryView";
    }

    public String translationAction() {
        return "translationView";
    }
}
