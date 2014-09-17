package models.items;

import models.Charactor;

public class Item implements Comparable<Item> {
	
	public int num;

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

	@Override
	public int compareTo(Item o) {
		// TODO Auto-generated method stub
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
