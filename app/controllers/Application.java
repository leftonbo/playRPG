package controllers;

import java.util.*;

import models.*;
import mt.Sfmt;
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
    	Sfmt mt = new Sfmt();
    	
    	List<Charactor> a = new ArrayList();
    	a.add(new Charactor().DebugRandomCreate(mt, 1,  40));
    	a.add(new Charactor().DebugRandomCreate(mt, 1,  35));
    	a.add(new Charactor().DebugRandomCreate(mt, 1,  35));
    	a.add(new Charactor().DebugRandomCreate(mt, 1,  30));
    	
    	List<Charactor> b = new ArrayList();
    	b.add(new Charactor().DebugRandomCreate(mt, 0, 100));
    	b.add(new Charactor().DebugRandomCreate(mt, 0,  25));
    	b.add(new Charactor().DebugRandomCreate(mt, 0,  25));
    	
    	Battle btl = new Battle(a,b);
    	List<BattleOccur> bo = btl.processBattle();
    	
    	return ok(testBattleResult.render(bo));
    }

}
