package controllers;

import static play.data.Form.form;
import models.FormContGame;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class AuthHero extends Controller {

    public static Result index() {
        return TODO;
    }
    
    public static Result loginProcess() {
    	Form<FormContGame> fm = form(FormContGame.class).bindFromRequest();
    	if (fm.hasErrors()) {
    		return badRequest(continueGameForm.render(fm));
    	}
        return TODO;
    }
    
    
}
