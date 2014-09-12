package models;

import java.util.*;

import models.places.*;

public class GamePlace {	
	public int place;
	public String name;
	
	// 場所移動用
	public Map<String,GamePlace> nexts;
	
	// イベント用変数
	public String eventName;
	public String eventText;
	public Map<Integer,String> choose;
	
	/**
	 * 場所の設定
	 */
	public GamePlace() {
		place = 0;
		name = "未定義の場所";
	}
	
	/**
	 * 場所の説明
	 * @return
	 */
	public String getDespriction() {
		return "";
	}
	
	/**
	 * 移動可能エリアの設定
	 */
	public void makeNextList() {
		nexts = new LinkedHashMap<String,GamePlace>();
		nexts.put("初期地点", new PlaceFazmari());
	}
	
	/**
	 * この地域に入ってきたときの処理
	 * @return　次のシーン
	 */
	public int onEnterPlace(GamePlace from) {
		return 0;
	}
	
	/**
	 * この地域から出たの処理
	 * @return　次のシーン、0なら次のMAPへ
	 */
	public int onLeavePlace(GamePlace to) {
		return 0;
	}
	
	/**
	 * イベントテキストの定義
	 * @param scene シーン(1000~1999)
	 */
	public void makeEventText(int scene) {
	}
	
	
	/// ====
	
	static public GamePlace createByPlace(int p) {
		switch(p) {
		case 2:
			return new PlaceFazmari();
		case 3:
			return new PlacePrimaGreen();
		}
		return new GamePlace();
	}
}
