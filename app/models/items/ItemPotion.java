package models.items;

import models.Charactor;
import models.items.Item.*;

public class ItemPotion {

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "小さな回復薬";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "HPを20回復する。";
	}
	
	/**
	 * せつめい2
	 * @return
	 */
	public String getDespAfterUse(Used used) {
		if (used == Used.OK) return "HPが20回復した。";
		return "HPは既に満タンだ。";
	}
	
	/**
	 * アイテムタイプ
	 * @return 0:Unusable 1:Consumable 2:Weapon 3:Armor 4:Shield 5:Ring 6:Amulet
	 */
	public Type getType() {
		return Type.CONSUME;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public Used onUse(Charactor c) {
		if (c.hp >= c.mhp) return Used.NONECESSARY;
		c.hp += 20;
		if (c.hp > c.mhp) c.hp = c.mhp;
		return Used.OK;
	}
}
