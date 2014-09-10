package models;

import java.util.*;

import mt.*;

public class Battle {
	public List<Charactor> allies;
	public List<Charactor> enemies;
	
	public List<BattleOccur> result;
	
	private boolean processed = false;
	private Sfmt mt;
	private int turn = 0;
	private int lastcrit = 0;
	
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
		
		result = new ArrayList<BattleOccur>();
		
		// 開始処理
		result.add( new BattleOccur(-1, BattleOccur.Occur.START) );
		for (Charactor cc : allies) {
			result.add( new BattleOccur(1, BattleOccur.Occur.APPEAR, cc) );
		}
		for (Charactor cc : enemies) {
			result.add( new BattleOccur(0, BattleOccur.Occur.APPEAR, cc) );
		}
		
		// どっちか全滅するまでループ
		int chk = 0;
		boolean endflag = false;
		turn = 1;
		while (true) {
			result.add( new BattleOccur(0, BattleOccur.Occur.TURN, null, turn) );
			// inisia
			List<Charactor> cturn = getInitiativeList();
			for (Charactor cc : cturn) {
				// HP0なら行動しない
				if (cc.hp <= 0) continue;
				/** 敵か味方か **/
				boolean ally = isAlly(cc);
				int allyInt = ally ? 1 : 0;
				// とりあえず攻撃
				result.add( new BattleOccur(allyInt, BattleOccur.Occur.ATTACK, cc) );
				// テキトーに攻撃
				Charactor target = randomTarget(!ally);
				int damage = meleeAttack( cc, target, 0);
				if (lastcrit ==  1) 
					result.add( new BattleOccur(ally ? 0 : 1, BattleOccur.Occur.CRITICALHIT, target) );
				if (damage >= 0) {
					result.add( new BattleOccur(allyInt, BattleOccur.Occur.DAMAGE, target, damage) );
					target.hp -= damage;
					if (target.hp <= 0) {
						result.add( new BattleOccur(allyInt, BattleOccur.Occur.DIE, target) );
					}
				} else {
					if (lastcrit == -2) 
						result.add( new BattleOccur(ally ? 0 : 1, BattleOccur.Occur.FUNBLEHIT, target) );
					else if (lastcrit == -1) 
						result.add( new BattleOccur(allyInt, BattleOccur.Occur.CRITICALBLOCK, target) );
					else 
						result.add( new BattleOccur(allyInt, BattleOccur.Occur.MISS, target) );
				}
				// 戦闘終了チェック
				if ((chk = checkTeamDefeated()) != 0) {
					endflag = true;	break;
				}
			}
			// 戦闘ループ終了
			if (endflag) break;
			// ターンカウント
			turn ++;
		}
		
		if (chk == -1) {
			// 負け
			result.add( new BattleOccur(0, BattleOccur.Occur.END) );
		} else {
			// 勝ち
			result.add( new BattleOccur(1, BattleOccur.Occur.END) );
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
		List<Charactor> targets = new ArrayList();
		if (ally) {
			for (Charactor c : allies) {
				if (c.hp > 0) targets.add(c);
			}
		} else {
			for (Charactor c : enemies) {
				if (c.hp > 0) targets.add(c);
			}
		}
		return targets.get(mt.NextIntEx(targets.size()));
	}
	
	public int meleeAttack(Charactor atk, Charactor def, int type) {
		int res = -1;
		int judge;
		Dice atkDice = new Dice(mt, 2, 6);
		Dice defDice = new Dice(mt, 2, 6);
		int atkp = 0, defp = 0;
		
		// 攻撃タイプ
		// +2補正はそのうち外すかも
		if (type == 0) {
			atkp = atk.str + 2;
			defp = def.str;
		} else if (type == 1) {
			atkp = atk.agi + 2;
			defp = def.agi;
		} else if (type == 2) {
			atkp = atk.sen + 2;
			defp = def.sen;
		} else if (type == 3) {
			atkp = atk.wil + 2;
			defp = def.wil;
		}
		
		lastcrit = 0;
		if (atkDice.critical == 1) {
			// 攻撃クリッツ
			judge = (atkp + atkDice.sum) - (defp);
			lastcrit = 1;
		} else if (atkDice.critical == -1) {
			// ふぁんぶる
			judge = -6;
			lastcrit = -2;
		} else if (defDice.critical == 1) {
			// 防御クリッツ
			judge = -6;
			lastcrit = -1;
		} else {
			// 通常
			judge = (atkp + atkDice.sum) - (defp + defDice.sum);
			if (judge >= 10) {
				// 10以上クリッツ
				judge = (atkp + atkDice.sum) - (defp);
				lastcrit = 1;
			}
		}
		
		if (judge >= 0) {
			// 0以上なら命中
			res = Math.max( judge , 0) + xDy(1+atk.level/10,6);
		}
		// ダメージ値を返す
		return res;
	}
	
	/* ********/
	
	private int xDy (int x, int y) {
		int res = 0;
		for (int i = 0; i < x; i++) res += mt.NextInt(y) + 1;
		return res;
	}
}
