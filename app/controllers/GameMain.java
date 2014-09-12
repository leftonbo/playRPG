package controllers;

import static play.data.Form.form;

import models.*;
import models.forms.*;
import play.data.Form;
import play.mvc.*;
import play.twirl.api.Html;
import views.html.*;

/*
 * シーンについて
 * 0：メニュー
 * 1:地域に入る
 * 2：地域から出る
 * 100~199：敵と遭遇
 * 1000~1999：イベント
 * 
 * -1：死亡時の処理
 * -2：「おおゆうしゃよ、しんでしまうとはなさけない」
 */

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
    		place.makeNextList();
    		render = gameMenu.render( place.name, desp, place.nexts, login);
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
    
    
    /**
     * 場所移動の処理
     * @return
     */
    @Security.Authenticated(MyAuthenticator.class)
    public static Result moveProcess() {
    	loginName = request().username();
    	login = Charactor.getByName(loginName);
    	FormMovePlace f = form(FormMovePlace.class).bindFromRequest().get();
    	int nse = 0;
    	
    	GamePlace bef = GamePlace.createByPlace(login.place);
    	GamePlace aft = GamePlace.createByPlace(f.next);
    	
    	// 場所離れる処理
    	nse = bef.onLeavePlace(aft);
    	if (nse != 0) {
    		// シーン割り込みで移動失敗
        	login.scene = nse;
        	login.update();
        	return GO_HOME;
    	}
    	
    	// 場所を更新
    	login.place = f.next;
    	nse = aft.onEnterPlace(bef);
    	login.scene = nse;
    	login.update();
    	
    	return GO_HOME;
    }
}

