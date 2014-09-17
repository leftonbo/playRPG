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
	public boolean IsChanceHitFreq(Sfmt mt) {
		double a = mt.NextUnif();
		if (a < freq) return true;
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
		NOUSE,
		NONECESSARY
	}
	
	public static Item createByInt(int i, int n) {
		Item res = null;
		
		switch (i) {
		case 1:
			res = new ItemPotion();	break;
		}
		
		if (res == null) res = new Item();
		res.num = n;
		return res;
	}
	public static Item createByInt(int i) {
		return createByInt(i,1);
	}
}
