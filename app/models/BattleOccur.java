package models;

public class BattleOccur {
	public enum Occur {
		START,
		APPEAR,
		TURN,
		ATTACK,
		CAST,
		DAMAGE,
		HEAL,
		MISS,
		RUN,
		DIE,
		CRITICALHIT,
		FUNBLEHIT,
		CRITICALBLOCK,
		END,
	}

	public int side;
	public Occur occur;
	public int amount;
	public Charactor target;

	public BattleOccur(int s, Occur o) {
		side = s;
		occur = o;
		amount = 0;
	}
	public BattleOccur(int s, Occur o, Charactor t) {
		side = s;
		occur = o;
		target = t;
		amount = 0;
	}
	public BattleOccur(int s, Occur o, Charactor t, int a) {
		side = s;
		occur = o;
		target = t;
		amount = a;
	}
}
