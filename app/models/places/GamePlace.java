package models.places;

import java.util.*;

import models.Charactor;
import models.places.*;

public class GamePlace {	
	public int place;
	public String name;
	
	// 場所移動用
	public Map<String,GamePlace> nexts;
	// 探索用
	public Map<String,Integer> explores;
	
	// イベント用変数
	public String eventName;
	public String eventText;
	public Map<Integer,String> choose;
	
	// 戦闘用
	public List<Charactor> enemies;
	
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
	 * 探索の設定
	 */
	public void makeExploreList() {
		explores = new LinkedHashMap<String,Integer>();
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
	 * ランダムイベント設定
	 * @param scene 200~299 ランダムリスト
	 * @return シーン移動
	 */
	public int onRandomEvent(int scene) {
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

	/**
	 * 敵グループ設定
	 * @param scene 100~199 敵グループ
	 * @return 勝った時のシーン移動
	 */
	public int setEnemies(int scene) {
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
		case 4:
			return new PlaceDeminaForest();
		case 5:
			return new PlaceDeminaForestDeep();
		case 6:
			return new PlaceTownUrest();
		}
		return new GamePlace();
	}
}
