package models.places;

import java.util.*;

import models.GamePlace;

public class PlaceFazmari extends GamePlace {

	/**
	 * 場所の設定
	 */
	public PlaceFazmari() {
		place = 2;
		name = "ファズマリの街";
	}

	/**
	 * 場所の説明
	 * @return
	 */
	public String getDespriction() {
		return "街の説明TODO";
	}

	/**
	 * 移動可能エリアの設定
	 */
	public void makeNextList() {
		nexts = new LinkedHashMap<String,GamePlace>();
		nexts.put("東", new PlacePrimaGreen());
	}

	/**
	 * イベントテキストの定義
	 * @param scene シーン(1000~1999)
	 */
	public void makeEventText(int scene) {
		choose = new LinkedHashMap<Integer,String>();
		switch (scene) {
		case 1000:
			eventName = "王様";
			eventText = 
				"おお、勇者{{name}}よ。ようやく現れおったな。\n" +
				//"早速だが、私からの頼みがある。\n" +
				"";
			choose.put(0,"はい");
			break;
		default:
			eventText = "謎の空間";
			choose.put(0,"次へ");
		}
	}
	
}
