package models.places;

import java.util.*;

import models.GamePlace;

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
	}
	
	/**
	 * 探索の設定
	 */
	public void makeExploreList() {
		explores = new LinkedHashMap<String,Integer>();
		explores.put("役所に行く", 3);
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
		default:
			eventText = "謎の空間";
			choose.put(0,"次へ");
		}
	}
	
}
