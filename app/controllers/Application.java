package controllers;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	public static final String appTitle = "playRPG";
	public static final String appVersion = "0.01";

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result newGame() {
        return TODO;
    }
    
    public static Result newGameApply() {
        return TODO;
    }

}
