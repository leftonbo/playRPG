package controllers;

import static play.data.Form.form;

import models.*;
import models.forms.*;
import play.data.Form;
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
    	loginName = request().username();
    	login = Charactor.getByName(loginName);
    	int scene = login.scene;
    	GamePlace place = GamePlace.createByPlace(login.place);
    	
    	Html render = null;
    	if (scene >= 1000 && scene < 2000) {
        	// イベントシーンへ
    		place.makeEventText(scene);
    		String text = place.eventText
    				.replace("{{name}}", loginName)
    				.replace("\n", "<br>");
    		render = gameEvent.render( place.name, place.eventName, text, place.choose);
    	} else {
    		String desp = place.getDespriction()
    				.replace("\n", "<br>");
    		render = gameMenu.render( place.name, desp, login);
    	}
    	
    	// 選択メニュー
        return ok(render);
    }

    /**
     * 次のシーンに移動
     * @return
     */
    @Security.Authenticated(MyAuthenticator.class)
    public static Result eventProcess() {
    	loginName = request().username();
    	login = Charactor.getByName(loginName);
    	
    	// シーンを変更
    	Form<FormEventChoose> fc = form(FormEventChoose.class).bindFromRequest();
    	int next = fc.get().choose;
    	login.scene = next;
    	// キャラクタ更新…？
    	login.update();
    	
    	return GO_HOME;
    }
}

