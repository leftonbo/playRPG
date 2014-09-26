package models.places;

import java.util.*;

import models.items.*;

public class PlaceTownUrest extends GamePlace {

	/**
	 * 場所の設定
	 */
	public PlaceTownUrest() {
		place = 6;
		name = "ウレストの村";
	}

	/**
	 * 場所の説明
	 * @return
	 */
	public String getDespriction() {
		return "森の中に位置する小さな村。";
	}

	/**
	 * 移動可能エリアの設定
	 */
	public void makeNextList() {
		nexts = new LinkedHashMap<String,GamePlace>();
		nexts.put("南", new PlaceDeminaForestDeep());
		nexts.put("東", new PlaceSalamDesert());
	}
	
	/**
	 * 探索の設定
	 */
	public void makeExploreList() {
		explores = new LinkedHashMap<String,Integer>();
		explores.put("役所に行く", 3);
		explores.put("道具屋に行く", 1100);
	}

	/**
	 * イベントテキストの定義
	 * @param scene シーン(1000~1999)
	 */
	public void makeEventText(int scene) {
		choose = new LinkedHashMap<Integer,String>();
		switch (scene) {
		case 3:
			eventName = "村長";
			eventText = 
				"おお、君は{{name}}じゃな。\n" +
				"次のレベルアップまで{{levrem}}の経験値が必要じゃ！\n" +
				"";
			choose.put(0,"わかりました");
			break;
		case -2:
			eventName = "村長";
			eventText = 
				"おお勇者{{name}}よ。しんでしまうとはなさけないのう。\n" +
				"そなたにもういちどチャンスを与えるぞ。\n" +
				"";
			choose.put(0,"ふっかつ！");
			break;
		case 1100:
			eventName = "道具屋";
			eventText = 
				"いらっしゃい！\n" +
				"好きなだけ見ていってくれ。\n" +
				"\n" +
				"あなたの所持金: " + getNowManey() + "\n" +
				"\n" +
				"<ul>";
			choose.put(0,"立ち去る");
			makeSoldItemSelect(210, new ItemPotion(), 1.0, 1);
			makeSoldItemSelect(211, new ItemPotionMedium(), 1.0, 1);
			makeSoldItemSelect(212, new ItemSwordIron(), 1.0, 1);
			makeSoldItemSelect(213, new ItemAxeBattle(), 1.0, 1);
			makeSoldItemSelect(216, new ItemSpearShort(), 1.0, 1);
			makeSoldItemSelect(214, new ItemBowShort(), 1.2, 1);
			makeSoldItemSelect(215, new ItemRodWood(), 1.2, 1);
			makeSoldItemSelect(217, new ItemSpearLong(), 1.5, 1);
			eventText += "</ul>";
			break;
		default:
			eventText = "謎の空間";
			choose.put(0,"次へ");
		}
	}
	
	/**
	 * ランダムイベント設定
	 * @param scene 200~299 ランダムリスト
	 * @return シーン移動
	 */
	public int onRandomEvent(int scene) {
		switch (scene) {
		case 210:
			processSoldItemSelect(new ItemPotion(), 1.0, 1);
			return 1100;
		case 211:
			processSoldItemSelect(new ItemPotionMedium(), 1.0, 1);
			return 1100;
		case 212:
			processSoldItemSelect(new ItemSwordIron(), 1.0, 1);
			return 1100;
		case 213:
			processSoldItemSelect(new ItemAxeBattle(), 1.0, 1);
			return 1100;
		case 214:
			processSoldItemSelect(new ItemBowShort(), 1.0, 1);
			return 1100;
		case 215:
			processSoldItemSelect(new ItemRodWood(), 1.2, 1);
			return 1100;
		case 216:
			processSoldItemSelect(new ItemSpearShort(), 1.2, 1);
			return 1100;
		case 217:
			processSoldItemSelect(new ItemSpearLong(), 1.5, 1);
			return 1100;
		}
		return 0;
	}
	
}
