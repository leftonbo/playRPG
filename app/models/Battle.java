package models;

import java.util.*;

import mt.Sfmt;

public class Battle {
	public List<Charactor> allies;
	public List<Charactor> enemies;
	
	private boolean processed = false;
	private Sfmt mt;
	private int turn = 0;
	
	public Battle(List<Charactor> a, List<Charactor> e) {
		allies = a;		enemies = e;
		mt = new Sfmt();
	}
	
	/**
	 * 戦闘処理
	 * 
	 * @return 行動の一連
	 */
	public List<BattleOccur> processBattle() {
		if (processed) return new ArrayList<BattleOccur>();
		
		List<BattleOccur> result = new ArrayList<BattleOccur>();
		
		// 開始処理
		result.add( new BattleOccur(BattleOccur.Occur.START) );
		
		// どっちか全滅するまでループ
		int chk;
		turn = 1;
		while ((chk = checkTeamDefeated()) == 0) {
			play.Logger.debug("!");
			// inisia
			List<Charactor> cturn = getInitiativeList();
			for (Charactor cc : cturn) {
				boolean ally = isAlly(cc);
				// とりあえず攻撃
				result.add( new BattleOccur(BattleOccur.Occur.ATTACK, cc) );
				// テキトーに攻撃
				Charactor target = randomTarget(!ally);
				int damage = meleeAttack( cc, target, 0);
				if (damage >= 0) {
					result.add( new BattleOccur(BattleOccur.Occur.DAMAGE, target, damage) );
					target.hp -= damage;
				} else {
					result.add( new BattleOccur(BattleOccur.Occur.MISS, target) );
				}
			}
			// ターンカウント
			turn ++;
		}
		
		if (chk == -1) {
			result.add( new BattleOccur(BattleOccur.Occur.LOSE) );
		} else {
			result.add( new BattleOccur(BattleOccur.Occur.WIN) );
		}
		
		processed = true;
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
	
	/**
	 * イニシアティブ
	 * @return
	 */
	private List<Charactor> getInitiativeList() {
		Map<Integer,Charactor> sort = new TreeMap<Integer,Charactor>();

		// 味方
		for (Charactor c : allies) {
			int ini = (Math.max(c.agi, c.sen) + xDy(1,6)) << 1 + 1;
			sort.put(-ini,c);
		}
		// 敵
		for (Charactor c : enemies) {
			int ini = (Math.max(c.agi, c.sen) + xDy(1,6)) << 1;
			sort.put(-ini,c);
		}

		List<Charactor> result = new ArrayList<Charactor>();
        Iterator<Integer> it = sort.keySet().iterator();
        while (it.hasNext()) {
        	Integer key = it.next();
        	result.add(sort.get(key));
        }
		
		return result;
	}
	
	/**
	 * そいつ仲間かミ？
	 * @param c
	 * @return
	 */
	public boolean isAlly(Charactor c) {
		return (allies.indexOf(c) == -1) ? false : true;
	}
	
	/**
	 * ランダムターゲット
	 * @param ally 味方側を狙う
	 * @return
	 */
	private Charactor randomTarget(boolean ally) {
		if (ally) {
			return allies.get(mt.NextIntEx(allies.size()));
		} else {
			return enemies.get(mt.NextIntEx(enemies.size()));
		}
	}
	
	public int meleeAttack(Charactor atk, Charactor def, int type) {
		int res = -1;
		int judge;
		int atkDice = xDy(2,6);
		int defDice = xDy(2,6);
		if (turn >= 10) {
			atkDice = xDy(1+turn/5,6);
		}
		if (atkDice >= 12) {
			// 攻撃クリッツ
			defDice = 0;
		} else if (atkDice <= 2 || defDice >= 12) {
			// ふぁんぶる
			return -2;
		}
		// +2補正はそのうち外すかも
		judge = (atk.str + atkDice + 2) - (def.str - defDice);
		if (judge >= 0) {
			// 0以上なら命中
			res = Math.max( judge + xDy(1,6)-3, 0);
		}
		return res;
	}
	
	/* ********/
	
	private int xDy (int x, int y) {
		int res = 0;
		for (int i = 0; i < x; i++) res += mt.NextIntEx(y);
		return res;
	}
}
