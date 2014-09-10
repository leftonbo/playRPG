package controllers;

import static play.data.Form.form;

import java.util.*;

import models.*;
import mt.Sfmt;
import play.data.*;
import play.mvc.*;
import views.html.*;

public class GameMain extends Controller {
	
    public static Result GO_HOME = redirect(
        routes.GameMain.index()
    );
	
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
    	// TODO:ここにシーン遷移
        return ok(gameMenu.render(""));
    }
}

