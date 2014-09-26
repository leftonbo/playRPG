package models.places;

import java.util.*;

import models.Charactor;
import models.items.*;
import mt.Sfmt;

public class PlaceSalamDesert extends GamePlace {

	/**
	 * 場所の設定
	 */
	public PlaceSalamDesert() {
		place = 7;
		name = "サラム砂漠";
	}

	/**
	 * 場所の説明
	 * @return
	 */
	public String getDespriction() {
		return "砂漠。";
	}

	/**
	 * 移動可能エリアの設定
	 */
	public void makeNextList() {
		nexts = new LinkedHashMap<String,GamePlace>();
		nexts.put("西", new PlaceTownUrest());
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
			return enemyEncounter(0.9);
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
			switch (mt.NextIntEx(3)) {
			case 0:
				return 100;
			case 1:
				return 101;
			case 2:
				return 102;
			}
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
			.setName("ゴブリンA").setparams(6, 20, 20, 2, 3, 2, 2).setRewards(8, 1500)
			.setAttackType(2)
			.setAttacks(1, 6, 2)
			.addItem(new ItemPotion().setFreq(0.05))
			);
			enemies.add(new Charactor()
			.setName("ゴブリンB").setparams(6, 20, 20, 2, 3, 0, 4).setRewards(8, 1500)
			.setAttackType(3)
			.setAttacks(1, 6, 2)
			.addItem(new ItemPotion().setFreq(0.05))
			);
			break;
		case 102:
			enemies.add(new Charactor()
				.setName("オーク").setparams(9, 50, 30, 5, 1, 0, 1).setRewards(28, 5000)
				.setAttacks(2, 8, 1)
				.addItem(new ItemPotionMedium().setFreq(0.08))
				.addItem(new ItemHammerWar().setFreq(0.08))
				);
			break;
		default:
			enemies.add(new Charactor()
				.setName("サソリ").setparams(7, 25, 5, 2, 5, 0, 2).setRewards(16, 2000)
				.setAttackType(1)
				.setAttacks(1, 6, 2)
				.addItem(new ItemPotion().setFreq(0.05))
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
		default:
			eventText = "謎の空間";
			choose.put(0,"次へ");
		}
	}
	
}
