package models.places;

import java.util.*;

import models.Charactor;
import models.GamePlace;
import models.items.ItemPotion;
import models.items.ItemRodWood;
import models.items.ItemSwordCopper;
import mt.Sfmt;

public class PlaceDeminaForest extends GamePlace {

	/**
	 * 場所の設定
	 */
	public PlaceDeminaForest() {
		place = 4;
		name = "デミナの森";
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
		nexts.put("南", new PlacePrimaGreen());
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
		return enemyEncounter(0.9);
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
				.setName("スライム").setparams(0, 10, 0, 1, 0, 1, 0).setRewards(2, 300)
				.addItem(new ItemPotion().setFreq(0.2))
				.addItem(new ItemSwordCopper().setFreq(0.05))
				);
			enemies.add(new Charactor()
				.setName("スライム").setparams(0, 10, 0, 1, 0, 1, 0).setRewards(2, 300)
				.addItem(new ItemPotion().setFreq(0.2))
				.addItem(new ItemSwordCopper().setFreq(0.05))
					);
			break;
		case 102:
			enemies.add(new Charactor()
				.setName("オオカミ").setparams(2, 18, 0, 2, 1, 0, 1).setRewards(4, 500)
				.setAttacks(1, 6, 0)
				.addItem(new ItemPotion().setFreq(0.2))
				.addItem(new ItemRodWood().setFreq(0.05))
				);
			break;
		default:
			enemies.add(new Charactor()
				.setName("スライム").setparams(1, 12, 0, 1, 1, 1, 0).setRewards(2, 400)
				.addItem(new ItemPotion().setFreq(0.2))
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
