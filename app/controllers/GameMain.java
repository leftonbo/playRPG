package controllers;

import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;

import models.*;
import models.forms.*;
import models.items.Item;
import models.items.Item.Used;
import mt.Sfmt;
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

    	if (scene == -1) {
    		// 死亡シーンへ
    		return moveDeathProcess();
    	}
    	if (scene == 0 && login.nextplace != 0) {
    		// シーンなし＋次場所予約済みなら移動する
    		return moveProcessTo(login.nextplace);
    	}
    	
    	GamePlace place = GamePlace.createByPlace(login.place);
    	
    	boolean dataNeedSave = false;
    	if (scene >= 200 && scene < 300) {
    		// シーンの自動遷移(ランダムイベント用)
    		login.scene = place.onRandomEvent(login.scene);
    		// ランダムイベント用のセーブフラグ
    		dataNeedSave = true;
    	}
    	
    	Html render = null;
    	if (login.exp >= login.getNextExp()) {
    		render = gameLevelUp.render( login);
    	} else if (scene >= 100 && scene < 200) {
    		// 戦闘に突入
    		// ランダムイベント用のセーブフラグを消す(戦闘後にupdateされるため)
    		dataNeedSave = false;
    		// 敵チーム、次シーン
    		int nextscene = place.setEnemies(scene);
    		// 味方チーム
    		List<Charactor> a = new ArrayList<Charactor>();
    		a.add(login);  login.applyEquips();
    		// 戦闘処理へ
        	Battle btl = new Battle(a,place.enemies);
        	List<BattleOccur> bo = btl.processBattle();
        	// 結果
        	if (btl.win == -1) {
        		// 全滅なら次シーンはリスポン
        		login.scene = -1;
        	} else {
        		// そうでないなら次へ
        		login.scene = nextscene;
        		//　ついでに報酬ももらう
        		Sfmt mt = new Sfmt();
        		List<Item> getitems = new ArrayList<Item>();
        		int re = 0, rm = 0;
        		for (Charactor cc : place.enemies) {
        			if (cc.isDefeated()) {
        				re += cc.exp;
        				rm += cc.money;
        				getitems.addAll(cc.checkLoot(mt,login));
        			}
        		}
        		login.exp += re;
        		login.money += rm;
        		// アイテム追加処理
        		for (Item i : getitems) {
        			login.addItem(i);
        		}
        		// TODO : ここに報酬表示処理とレベルアップ処理
        	}
        	login.update();
    		render = gameBattle.render( place.name, bo);
    	} else if (scene == -2 || scene >= 1000 && scene < 2000) {
        	// イベントシーンへ
    		place.makeEventText(scene);
    		String text = place.eventText
    				.replace("{{name}}", loginName)
    				.replace("\n", "<br>");
    		if (text.matches(".*\\Q{{levrem}}\\E.*")) {
    			play.Logger.debug("fassa");
    			text = text.replace("{{levrem}}", String.format("%,d",login.getNextExp()-login.exp));
    		}
    		render = gameEvent.render( place.name, place.eventName, text, place.choose);
    	} else {
    		// メニュー画面
    		String desp = place.getDespriction()
    				.replace("\n", "<br>");
    		place.makeNextList();
    		place.makeExploreList();
    		login.applyEquips();
    		render = gameMenu.render( place.name, desp, place.nexts, place.explores, login);
    	}
    	
		// ランダムイベント用のセーブフラグ
		if (dataNeedSave) {
			login.update();
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

		// シーンの自動遷移(ランダムイベント用)
    	if (login.scene >= 200 && login.scene < 300) {
    		GamePlace place = GamePlace.createByPlace(login.place);
    		login.scene = place.onRandomEvent(login.scene);
    	}
    	
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
        	login.nextplace = f.next;
        	login.scene = nse;
        	login.update();
        	return GO_HOME;
    	}
    	
    	// 場所を更新
    	login.place = f.next;
    	login.nextplace = 0;
    	nse = aft.onEnterPlace(bef);
    	login.scene = nse;
    	login.update();
    	
    	return GO_HOME;
    }
    
    
    /**
     * 場所移動の処理 その2
     * @return
     */
    @Security.Authenticated(MyAuthenticator.class)
    public static Result moveProcessTo(int np) {
    	//loginName = request().username();
    	//login = Charactor.getByName(loginName);
    	int nse = 0;
    	
    	GamePlace bef = GamePlace.createByPlace(login.place);
    	GamePlace aft = GamePlace.createByPlace(np);
    	
	    // 場所離れる処理は終わっているとする
    	
    	// 場所を更新
    	login.place = np;
    	login.nextplace = 0;
    	nse = aft.onEnterPlace(bef);
    	login.scene = nse;
    	login.update();
    	
    	return GO_HOME;
    }
    
    
    
    /**
     * リスポン処理
     * @return
     */
    @Security.Authenticated(MyAuthenticator.class)
    public static Result moveDeathProcess() {
    	//loginName = request().username();
    	//login = Charactor.getByName(loginName);
    	
    	// HPとMPを回復させておく
    	login.hp = login.mhp;
    	login.mp = login.mmp;
    	
    	// 場所を更新
    	login.place = login.respawn;
    	login.nextplace = 0;
    	login.scene = -2;
    	login.update();
    	
    	return GO_HOME;
    }
    
    /**
     * アイテムを使う
     * @return
     */
    @Security.Authenticated(MyAuthenticator.class)
    public static Result useItemProcess() {
    	loginName = request().username();
    	login = Charactor.getByName(loginName);
    	FormItemUse f = form(FormItemUse.class).bindFromRequest().get();
    	
    	Used result = login.useItemByID(f.use);
    	Item youuse = Item.createByInt(f.use);
    	boolean writeflag = false;
    	switch(result){
    	case NOITEM:
    		Application.flash("warning", "存在しないアイテムです。");
    		break;
    	default:
    		writeflag = true;
    		Application.flash("success", youuse.getDespAfterUse(result));
    	}
    	if (writeflag) login.update();

    	return GO_HOME;
    }
    
    /**
     * レベルアップ！
     * @return
     */
    @Security.Authenticated(MyAuthenticator.class)
    public static Result levelUpProcess() {
    	loginName = request().username();
    	login = Charactor.getByName(loginName);
    	// 本当はあまりよくないフォームの使い回し
    	Form<FormEventChoose> fc = form(FormEventChoose.class).bindFromRequest();

    	// レベル増加
    	login.levels += 1;
    	
    	// スキルポイント増加
    	login.skillPoint += 1;
    	if (login.levels % 10 == 0) login.skillPoint += 1;
    	
    	// 選択した能力値の増加
    	switch (fc.get().choose) {
    	case 0:
    		login.str += 1;	login.mhp += 5;
    		break;
    	case 1:
    		login.agi += 1;	login.mhp += 5;
    		break;
    	case 2:
    		login.sen += 1;	login.mmp += 5;
    		break;
    	case 3:
    		login.wil += 1;	login.mmp += 5;
    		break;
    	}
    	
    	// HPMPが回復
    	login.hp = login.mhp;
    	login.mp = login.mmp;
    	
    	//セーブ
    	login.update();
    	
    	return GO_HOME;
    }
}

