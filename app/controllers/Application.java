package controllers;

import java.util.*;

import models.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	public static final String appTitle = "playRPG";
	public static final String appVersion = "0.01";

    public static Result index() {
        return ok(index.render(""));
    }
    
    public static Result newGame() {
        return TODO;
    }
    
    public static Result newGameApply() {
        return TODO;
    }
    
    public static Result testBattle() {
    	List<Charactor> a = new ArrayList();
    	a.add(new Charactor());
    	List<Charactor> b = new ArrayList();
    	b.add(new Charactor());
    	
    	Battle btl = new Battle(a,b);
    	List<BattleOccur> bo = btl.processBattle();
    	
    	return ok(testBattleResult.render(bo));
    }

}
