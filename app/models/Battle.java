package models;

import java.util.*;

import mt.Sfmt;

public class Battle {
	public List<Charactor> allies;
	public List<Charactor> enemies;
	
	public Battle(List<Charactor> a, List<Charactor> e) {
		allies = a;		enemies = e;
	}
	
	/**
	 * 戦闘処理
	 * 
	 * @return 行動の一連
	 */
	public List<BattleOccur> processBattle() {
		List<BattleOccur> result = new ArrayList<BattleOccur>();
		Sfmt mt = new Sfmt();
		
		// 開始処理
		result.add( new BattleOccur(BattleOccur.Occur.START) );
		
		// どっちか全滅するまでループ
		int chk;
		while ((chk = checkTeamDefeated()) != 0) {
			
		}
		
		return result;
	}
	
	/**
	 * 全滅判定
	 * 
	 * @return 1 = WIN, -1 = LOSE, 0 = no
	 */
	public int checkTeamDefeated() {
		boolean flag;
		
		// 味方
		flag = false;
		for (Charactor c : allies) {
			if (c.hp > 0) { flag = true; break; }
		}
		if (!flag) return -1;
		
		// 敵
		flag = false;
		for (Charactor c : enemies) {
			if (c.hp > 0) { flag = true; break; }
		}
		if (!flag) return  1;
		
		return 0;
	}
}
