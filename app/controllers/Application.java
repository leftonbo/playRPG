package controllers;

import static play.data.Form.form;

import java.util.*;

import models.*;
import mt.Sfmt;
import play.data.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	public static final String appTitle = "playRPG";
	public static final String appVersion = "0.01";

    public static Result index() {
    	Form<FormNewGame> fm = form(FormNewGame.class);
        return ok(index.render(fm));
    }
    
    public static Result newGame() {
    	Form<FormNewGame> fm = form(FormNewGame.class).bindFromRequest();
    	if (fm.hasErrors()) {
    		return badRequest(newGameForm.render(fm));
    	}
        return TODO;
    }
    
    public static Result newGameApply() {
        return TODO;
    }
    
    public static Result testBattle() {
    	Sfmt mt = new Sfmt();
    	
    	List<Charactor> a = new ArrayList();
    	a.add(new Charactor().DebugRandomCreate(mt, 1, 40));
    	a.add(new Charactor().DebugRandomCreate(mt, 1, 35));
    	a.add(new Charactor().DebugRandomCreate(mt, 1, 35));
    	a.add(new Charactor().DebugRandomCreate(mt, 1, 30));

    	List<Charactor> b = new ArrayList();
    	switch (mt.NextInt(4)) {
    	case 0:
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 10));
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 10));
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 10));
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 10));
	    	break;
    	case 1:
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 60));
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 15));
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 15));
	    	break;
    	case 2:
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 25));
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 25));
	    	break;
    	case 3:
	    	b.add(new Charactor().DebugRandomCreate(mt, 0, 100));
	    	break;
    	}
    	
    	Battle btl = new Battle(a,b);
    	List<BattleOccur> bo = btl.processBattle();
    	
    	return ok(testBattleResult.render(bo));
    }

}
