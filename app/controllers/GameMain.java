package controllers;

import models.*;
import play.mvc.*;
import play.twirl.api.Html;
import views.html.*;

public class GameMain extends Controller {
	
    public static Result GO_HOME = redirect(
        routes.GameMain.index()
    );
    
    public static String loginName;
    public static Charactor login;
	
    @Security.Authenticated(MyAuthenticator.class)
    public static Result index() {
    	// TODO:ここにシーン遷移
    	loginName = request().username();
    	login = Charactor.getByName(loginName);
    	int scene = login.scene;
    	
    	Html render = null;
    	// イベントシーンへ
    	if (scene >= 1000 && scene < 2000) {
    		
    	} else {
    		render = gameMenu.render(loginName);
    	}
    	
    	// 選択メニュー
        return ok(render);
    }
}

