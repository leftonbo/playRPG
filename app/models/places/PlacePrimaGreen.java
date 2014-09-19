package models.places;

import java.util.*;

import controllers.GameMain;
import models.Charactor;
import models.GamePlace;
import models.items.ItemArmorHide;
import models.items.ItemPotion;
import models.items.ItemSwordCopper;
import models.items.ItemSwordIron;
import mt.Sfmt;

public class PlacePrimaGreen extends GamePlace {

	/**
	 * 場所の設定
	 */
	public PlacePrimaGreen() {
		place = 3;
		name = "プランナ平原";
	}

	/**
	 * 場所の説明
	 * @return
	 */
	public String getDespriction() {
		return "西にファスルの街が見える。";
	}

	/**
	 * 移動可能エリアの設定
	 */
	public void makeNextList() {
		nexts = new LinkedHashMap<String,GamePlace>();
		nexts.put("西", new PlaceFazmari());
		nexts.put("北", new PlaceDeminaForest());
	}
	
	/**
	 * 探索の設定
	 */
	public void makeExploreList() {
		explores = new LinkedHashMap<String,Integer>();
		explores.put("探索する", 200);
	}
	
	/**
	 * この地域に入ってきたときの処理
	 * @return　次のシーン
	 */
	public int onEnterPlace(GamePlace from) {
		return enemyEncounter();
	}
	
	/**
	 * この地域から出たの処理
	 * @return　次のシーン、0なら次のMAPへ
	 */
	public int onLeavePlace(GamePlace to) {
		return enemyEncounter();
	}

	/**
	 * ランダムイベント設定
	 * @param scene 200~299 ランダムリスト
	 * @return シーン移動
	 */
	public int onRandomEvent(int scene) {
		switch (scene) {
		case 200:
			Sfmt mt = new Sfmt();
			if (GameMain.login.money >= 5000 && mt.NextUnif() < 0.09) {
				return 1111 + mt.NextIntEx(2);
			}
			if (mt.NextUnif() < 0.9) {
				return 100;
			}
			return 0;
		case 211:
			GameMain.login.money -= 5000;
			GameMain.login.addItem(new ItemSwordIron());
			return 0;
		case 212:
			GameMain.login.money -= 5000;
			GameMain.login.addItem(new ItemArmorHide());
			return 0;
		}
		return 0;
	}
	
	public int enemyEncounter() {
		Sfmt mt = new Sfmt();
		if (mt.NextUnif() < 0.3) {
			return 100;
		}
		return 0;
	}

	/**
	 * 敵グループ設定
	 * @param scene 100~199 敵グループ
	 * @return 勝った時のシーン移動
	 */
	public int setEnemies(int scene) {
		enemies = new ArrayList<Charactor>();
		switch (scene) {
		default:
			enemies.add(new Charactor()
				.setName("スライム").setparams(0, 10, 0, 1, 0, 1, 0).setRewards(2, 300)
				.addItem(new ItemPotion().setFreq(0.2))
				.addItem(new ItemSwordCopper().setFreq(0.05))
				);
		}
		return 0;
	}

	/**
	 * イベントテキストの定義
	 * @param scene シーン(1000~1999)
	 */
	public void makeEventText(int scene) {
		choose = new LinkedHashMap<Integer,String>();
		switch (scene) {
		case 1111:
			eventName = "武器商人";
			eventText = "わたしは武器商人。\n" + 
			"君はお金をもっていて、私は鉄のつるぎを持っている。\n" + 
			"どうだい、買ってみるかね。";
			choose.put(211,"鉄のつるぎを買う(5000)");
			choose.put(0,"いらない");
			break;
		case 1112:
			eventName = "防具商人";
			eventText = "わたしは防具商人。\n" + 
			"君はお金をもっていて、私は革のよろいを持っている。\n" + 
			"どうだい、買ってみるかね。";
			choose.put(212,"革のよろいを買う(5000)");
			choose.put(0,"いらない");
			break;
		default:
			eventText = "謎の空間";
			choose.put(0,"次へ");
		}
	}
	
}
