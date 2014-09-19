package models.items;

import models.Charactor;
import models.items.Item;

public class ItemPotionMedium extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 6;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "回復薬";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "HPを50回復する。";
	}
	
	/**
	 * せつめい2
	 * @return
	 */
	public String getDespAfterUse(Item.Used used) {
		if (used == Item.Used.OK) return "HPが50回復した。";
		return "HPは既に満タンだ。";
	}
	
	/**
	 * アイテムタイプ
	 * @return 0:Unusable 1:Consumable 2:Weapon 3:Armor 4:Shield 5:Ring 6:Amulet
	 */
	public Item.Type getType() {
		return Type.CONSUME;
	}
	
	/**
	 * アイテムの価値
	 * @return
	 */
	public Long getPrice() {
		return 6000L;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public Item.Used onUse(Charactor c) {
		if (c.hp >= c.mhp) return Item.Used.NONECESSARY;
		c.hp += 50;
		if (c.hp > c.mhp) c.hp = c.mhp;
		return Item.Used.OK;
	}
}
