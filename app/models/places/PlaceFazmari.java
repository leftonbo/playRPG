package models.places;

import java.util.*;

import models.items.*;

public class PlaceFazmari extends GamePlace {

	/**
	 * 場所の設定
	 */
	public PlaceFazmari() {
		place = 2;
		name = "ファスルの街";
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
	 * 探索の設定
	 */
	public void makeExploreList() {
		explores = new LinkedHashMap<String,Integer>();
		explores.put("王様に会う", 3);
		explores.put("道具屋へ行く", 1100);
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
				"早速だが、私からの頼みがある。\n" +
				"\n" +
				"この地ミドラムはかつての光の勇者によってもたらされた『栄光の印』によって" +
				"平和が保たれていた。\n" +
				"しかしある日悪しき王が現れ、その印を盗んでしまったのだ！\n" +
				"このままでは印が悪用されて、世界は闇に包まれてしまうだろう。\n" +
				"\n" +
				"そこで君にお願いしたい。\n" +
				"君は悪しき王を倒し、『栄光の印』を取り返すのだ！\n" +
				"成功の暁には、この城を君に譲ろう！\n" +
				"";
			choose.put(1001,"わかりました");
			break;
		case 1001:
			eventName = "王様";
			eventText = 
				"おお、私の願いを受け入れてくれるか。\n" +
				"君の持ち物にわずかながら贈り物を入れておいた。\n" +
				"さあ行くのじゃ、勇者{{name}}よ！\n" +
				"";
			choose.put(0,"いってまいります");
			break;
		case 3:
			eventName = "王様";
			eventText = 
				"勇者{{name}}よ！\n" +
				"次のレベルアップまで{{levrem}}の経験値が必要じゃ！\n" +
				"";
			choose.put(0,"わかりました");
			break;
		case -2:
			eventName = "王様";
			eventText = 
				"おお勇者{{name}}よ。しんでしまうとはなさけない。\n" +
				"そなたにもういちど機会をやろう。\n" +
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
			makeSoldItemSelect(211, new ItemSwordCopper(), 1.0, 1);
			makeSoldItemSelect(212, new ItemSwordIron(), 1.0, 1);
			makeSoldItemSelect(214, new ItemSpearShort(), 1.5, 1);
			makeSoldItemSelect(213, new ItemArmorHide(), 1.0, 1);
			makeSoldItemSelect(215, new ItemArmorChain(), 1.0, 1);
			makeSoldItemSelect(216, new ItemArmorIron(), 1.0, 1);
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
			processSoldItemSelect(new ItemSwordCopper(), 1.0, 1);
			return 1100;
		case 212:
			processSoldItemSelect(new ItemSwordIron(), 1.0, 1);
			return 1100;
		case 213:
			processSoldItemSelect(new ItemArmorHide(), 1.0, 1);
			return 1100;
		case 214:
			processSoldItemSelect(new ItemSpearShort(), 1.5, 1);
			return 1100;
		case 215:
			processSoldItemSelect(new ItemArmorChain(), 1.0, 1);
			return 1100;
		case 216:
			processSoldItemSelect(new ItemArmorIron(), 1.0, 1);
			return 1100;
		}
		return 0;
	}
	
}
