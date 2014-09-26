package models.places;

import java.util.*;

import controllers.GameMain;
import models.Charactor;
import models.items.ItemAxeBattle;
import models.items.ItemBowShort;
import models.items.ItemPotion;
import models.items.ItemPotionMedium;
import models.items.ItemSwordSteel;
import mt.Sfmt;

public class PlaceDeminaForestDeep extends GamePlace {

	/**
	 * 場所の設定
	 */
	public PlaceDeminaForestDeep() {
		place = 5;
		name = "デミナの深い森";
	}

	/**
	 * 場所の説明
	 * @return
	 */
	public String getDespriction() {
		return "森TEST";
	}

	/**
	 * 移動可能エリアの設定
	 */
	public void makeNextList() {
		nexts = new LinkedHashMap<String,GamePlace>();
		nexts.put("南", new PlaceDeminaForest());
		if (GameMain.login.getFlag("deminaDclear")!=0)
			nexts.put("北", new PlaceTownUrest());
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
		return enemyEncounter(0.3);
	}
	
	/**
	 * この地域から出たの処理
	 * @return　次のシーン、0なら次のMAPへ
	 */
	public int onLeavePlace(GamePlace to) {
		return enemyEncounter(0.3);
	}

	/**
	 * ランダムイベント設定
	 * @param scene 200~299 ランダムリスト
	 * @return シーン移動
	 */
	public int onRandomEvent(int scene) {
		switch (scene) {
		case 200:
			// 探索
			Sfmt mt = new Sfmt();
			if (GameMain.login.getFlag("deminaDaxe")==0) {
				if (mt.NextUnif() < 0.05) {
					// アックス
					GameMain.login.setFlag("deminaDaxe", 1);
					GameMain.login.addItem(new ItemAxeBattle());
					return 1001;
				}
			}
			int schnum = GameMain.login.getFlag("deminaDSch");
			GameMain.login.setFlag("deminaDSch", schnum + 1);
			if (schnum >= 20 && GameMain.login.getFlag("deminaDclear")==0) {
				if (mt.NextUnif() < 0.1) {
					// ボス戦
					return 1000;
				}
			}
			return enemyEncounter(0.9);
		case 202:
			// ボス撃破
			GameMain.login.setFlag("deminaDclear", 1);
			return 1002;
		}
		return 0;
	}

	/**
	 * 場所移動設定
	 * @param scene 300~399 移動リスト
	 * @return 場所(0=移動なし)
	 */
	public int setPlaceMove(int scene) {
		return 0;
	}

	
	public int enemyEncounter(double per) {
		Sfmt mt = new Sfmt();
		if (mt.NextUnif() < per) {
			return 100 + mt.NextIntEx(5);
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
		case 101:
			enemies.add(new Charactor()
			.setName("オオカミA").setparams(2, 16, 0, 2, 1, 0, 1).setRewards(3, 500)
			.setAttacks(1, 6, 0)
			.addItem(new ItemPotion().setFreq(0.1))
			);
			enemies.add(new Charactor()
			.setName("オオカミB").setparams(2, 16, 0, 2, 1, 0, 1).setRewards(3, 500)
			.setAttacks(1, 6, 0)
			);
			break;
		case 102:
			enemies.add(new Charactor()
				.setName("オオカミ").setparams(3, 22, 0, 2, 2, 1, 1).setRewards(5, 500)
				.setAttacks(1, 6, 0)
				.addItem(new ItemPotion().setFreq(0.1))
				);
			enemies.add(new Charactor()
				.setName("スライムA").setparams(1, 12, 0, 1, 1, 1, 0).setRewards(2, 400)
				);
			break;
		case 103:
			enemies.add(new Charactor()
			.setName("オオカミ").setparams(3, 22, 0, 2, 1, 1, 2).setRewards(5, 500)
			.setAttacks(1, 6, 0)
			.addItem(new ItemPotionMedium().setFreq(0.1))
			.addItem(new ItemBowShort().setFreq(0.05))
			);
			enemies.add(new Charactor()
			.setName("オオグモ").setparams(3, 25, 0, 1, 2, 2, 1).setRewards(5, 300)
			.setAttackType(1)
			.setAttackHit(1)
			.setAttacks(1, 6, 0)
			.addItem(new ItemBowShort().setFreq(0.05))
			);
			break;
		case 104:
			enemies.add(new Charactor()
			.setName("森のクマ").setparams(5, 36, 10, 3, 3, 2, 2).setRewards(18, 2200)
			.setAttackType(0)
			.setAttacks(1, 6, 4)
			.setDefences(2, 1, -2, -1)
			.addItem(new ItemPotionMedium().setFreq(0.4))
			.addItem(new ItemBowShort().setFreq(0.09))
			);
			break;
		case 120:
			enemies.add(new Charactor()
			.setName("ジャイアント・スパイダー").setparams(7, 70, 10, 2, 4, 3, 1).setRewards(18, 5000)
			.setAttackType(1)
			.setAttacks(2, 6, 1)
			.setDefences(0, 1, -3, -1)
			.addItem(new ItemPotionMedium().setFreq(1.0))
			.addItem(new ItemPotionMedium().setFreq(1.0))
			.addItem(new ItemSwordSteel().setFreq(1.0))
			);
			return 202;
		default:
			enemies.add(new Charactor()
				.setName("スライムA").setparams(1, 12, 0, 1, 1, 1, 0).setRewards(2, 400)
				.addItem(new ItemPotion().setFreq(0.1))
				);
			enemies.add(new Charactor()
			.setName("スライムB").setparams(1, 12, 0, 1, 1, 1, 0).setRewards(2, 400)
			);
			enemies.add(new Charactor()
			.setName("スライムC").setparams(1, 12, 0, 1, 1, 1, 0).setRewards(2, 400)
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
		case 1000:
			eventName = "巨大なクモ";
			eventText = "あなたの目の前に大きなクモがたちはだかります。\n" +
			"周囲の魔物より強いでしょう。気をつけて！";
			choose.put(120,"戦う");
			choose.put(0,"一旦戻る");
			break;
		case 1001:
			eventName = "愚かもののオノ";
			eventText = "探索中、森のなかで倒れた人間を見つけます。\n" +
			"彼は残念ながらしんでしまっていますが、彼のもっていたオノは無事のようです。";
			choose.put(0,"バトルアックス を拾う");
			break;
		case 1002:
			eventName = "巨大なクモ 撃破";
			eventText = "脅威の巨大グモを倒し、あなたは安堵します。";
			choose.put(0,"やったぜ。");
			break;
		default:
			eventText = "謎の空間";
			choose.put(0,"次へ");
		}
	}
	
}
