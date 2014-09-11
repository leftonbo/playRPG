package controllers;

import play.mvc.*;
import views.html.*;

public class GameMain extends Controller {
	
    public static Result GO_HOME = redirect(
        routes.GameMain.index()
    );
    
    public static String loginName;
	
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
    	// TODO:ここにシーン遷移
    	loginName = request().username();
        return ok(loginName);
    }
}

