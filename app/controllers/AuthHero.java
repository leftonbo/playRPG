package controllers;

import static play.data.Form.form;

import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import models.Charactor;
import models.forms.FormContGame;
import models.forms.FormNewGame;
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
    	// ログイン処理
    	FormContGame fn = fm.get();
    	// トークン作成
    	final String userToken = UUID.randomUUID().toString(); // ランダムトークン作成
    	MyAuthenticator.registerLoginSession(ctx(), userToken, fn.name);
    	return GameMain.GO_HOME;
    }

    
    public static Result newGame() {
    	Form<FormNewGame> fm = form(FormNewGame.class).bindFromRequest();
    	if (fm.hasErrors()) {
    		return badRequest(newGameForm.render(fm));
    	}
    	// 登録処理
    	FormNewGame fn = fm.get();
    	Charactor newchar = new Charactor();
    	newchar.name = fn.name;
    	newchar.password = BCrypt.hashpw( fn.password, BCrypt.gensalt());
    	newchar.place = 2;		// 初期地点(ファズマリ)
    	newchar.respawn = 2;
    	newchar.scene = 1000;	// 初期メッセージ画面？
    	newchar.save();
    	// トークン作成
    	final String userToken = UUID.randomUUID().toString(); // ランダムトークン作成
    	MyAuthenticator.registerLoginSession(ctx(), userToken, fn.name);
    	return GameMain.GO_HOME;
    }

    public static Result logoutProcess() {
    	MyAuthenticator.unregisterLoginSession(ctx());
    	return Application.GO_HOME;
    }
}
