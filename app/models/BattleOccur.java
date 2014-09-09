package models;

public class BattleOccur {
	public enum Occur {
		START,
		ATTACK,
		CAST,
		DAMAGE,
		HEAL,
		MISS,
		RUN,
		DIE,
		WIN,
		LOSE,
	}

	public Occur occur;
	public int amount;
	public Charactor target;

	public BattleOccur(Occur o) {
		occur = o;
		amount = 0;
	}
	public BattleOccur(Occur o, Charactor t) {
		occur = o;
		target = t;
		amount = 0;
	}
	public BattleOccur(Occur o, Charactor t, int a) {
		occur = o;
		target = t;
		amount = a;
	}
}
