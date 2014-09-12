package models;

import java.util.*;

import models.places.*;

public class GamePlace {	
	public int place;
	public String name;
	
	// 場所移動用
	public List<GamePlace> nexts;
	
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
		}
		return null;
	}
}
