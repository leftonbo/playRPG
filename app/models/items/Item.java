package models.items;

import models.Charactor;
import mt.Sfmt;

public class Item implements Comparable<Item> {
	
	public int num = 1;
	public double freq = 1.0;

	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 0;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "謎";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "謎";
	}
	
	/**
	 * せつめい2
	 * @return
	 */
	public String getDespAfterUse(Used used) {
		return "このアイテムは使えない。";
	}
	
	/**
	 * アイテムタイプ
	 * @return
	 */
	public Type getType() {
		return Type.UNUSE;
	}
	
	/**
	 * アイテムの価値
	 * @return
	 */
	public Long getPrice() {
		return 0L;
	}
	
	/**
	 * レアリティ
	 */
	public Rarity getRarity() {
		return Rarity.COMMON;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public Used onUse(Charactor c) {
		return Used.NOUSE;
	}
	
	// ===============================================
	
	/**
	 * 所持数をセット
	 * @param n
	 * @return
	 */
	public Item setNum(int n) {
		num = n;
		return this;
	}
	
	/**
	 * 出現率セット（1/1000000単位まで）
	 * @param p
	 * @return
	 */
	public Item setFreq(double p) {
		freq = p;
		return this;
	}
	
	/**
	 * ルートチェック
	 * @param mt
	 * @return
	 */
	public boolean IsChanceHitFreq(Sfmt mt, double freqadd) {
		double a = mt.NextUnif();
		if (a < freq * (1+freqadd)) return true;
		return false;
	}
	
	// ===============================================
	
	public String getTypeString() {
		switch (getType()) {
		case CONSUME:
			return "消費";
		case WEAPON:
			return "武器";
		case ARMOR:
			return "防具";
		case SHIELD:
			return "盾";
		case RING:
			return "腕輪";
		case AMULET:
			return "首飾り";
		default:
			return "その他";
		}
	}
	
	public String getRarityString() {
		switch (getRarity()) {
		case JUNK:
			return "ジャンク";
		case COMMON:
			return "一般的";
		case UNCOMMON:
			return "やや希少";
		case RARE:
			return "珍しい";
		case EPIC:
			return "壮大";
		case LEGENDARY:
			return "伝説的";
		default:
			return "その他";
		}
	}
	
	public String getRarityHTML() {
		switch (getRarity()) {
		case JUNK:
			return "<span class=\"text-muted\" rel=\"tooltip\" data-toggle=\"tooltip\" data-placement=\"top\" "
					+ "title=\"ジャンク\">"
					+ "<span class=\"glyphicon glyphicon-star-empty\"></span>"
					+ "</span>";
		case COMMON:
			return "<span class=\"text-primary\" rel=\"tooltip\" data-toggle=\"tooltip\" data-placement=\"top\" "
			+ "title=\"一般的\">"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "</span>";
		case UNCOMMON:
			return "<span class=\"text-info\" rel=\"tooltip\" data-toggle=\"tooltip\" data-placement=\"top\" "
			+ "title=\"やや希少\">"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "</span>";
		case RARE:
			return "<span class=\"text-success\" rel=\"tooltip\" data-toggle=\"tooltip\" data-placement=\"top\" "
			+ "title=\"珍しい\">"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "</span>";
		case EPIC:
			return "<span class=\"text-warning\" rel=\"tooltip\" data-toggle=\"tooltip\" data-placement=\"top\" "
			+ "title=\"壮大\">"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "</span>";
		case LEGENDARY:
			return "<span class=\"text-danger\" rel=\"tooltip\" data-toggle=\"tooltip\" data-placement=\"top\" "
			+ "title=\"伝説的\">"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "<span class=\"glyphicon glyphicon-star\"></span>"
			+ "</span>";
		default:
			return "その他";
		}
	}
	
	// ===============================================
	
	public String getDespForTooltip() {
		return getDesp().replace( "\n", "&lt;br&gt;");
	}
	
	// ===============================================

	@Override
	public int compareTo(Item o) {
		if (this.getType() != o.getType()) {
			return getType().compareTo(o.getType());
		}
		return this.getId() - o.getId();
	}
	
	// ===============================================
	
	public static enum Type {
		UNUSE,
		CONSUME,
		WEAPON,
		ARMOR,
		SHIELD,
		RING,
		AMULET
	}
	public static enum Used {
		OK,
		EQUIP,
		NOUSE,
		NONECESSARY,
		NOITEM,
	}
	public static enum Rarity {
		JUNK,
		COMMON,
		UNCOMMON,
		RARE,
		EPIC,
		LEGENDARY
	}
	
	public static Item createByInt(int i, int n) {
		Item res = null;
		
		switch (i) {
		case 1:
			res = new ItemPotion();	break;
		case 2:
			res = new ItemSwordCopper();	break;
		case 3:
			res = new ItemRodWood();	break;
		case 4:
			res = new ItemBowShort();	break;
		case 5:
			res = new ItemAxeBattle();	break;
		}
		
		if (res == null) res = new Item();
		res.num = n;
		return res;
	}
	public static Item createByInt(int i) {
		return createByInt(i,1);
	}
}
