package models.places;

import java.util.*;

import models.Charactor;
import models.GamePlace;
import models.items.ItemPotion;
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
				.addItem(new ItemPotion().setFreq(0.2)));
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
