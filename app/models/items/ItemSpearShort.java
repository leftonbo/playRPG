package models.items;

import models.Charactor;
import models.items.Item;

public class ItemSpearShort extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 10;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "ショートスピア";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "短い槍。器用さで戦う。\n\n種類:近接 - きようさ攻撃\nダメージ:[命中度]+1D6+5\n命中:-1";
	}
	
	/**
	 * せつめい2
	 * @return
	 */
	public String getDespAfterUse(Item.Used used) {
		return getName() + " を装備した。";
	}
	
	/**
	 * アイテムタイプ
	 * @return 0:Unusable 1:Consumable 2:Weapon 3:Armor 4:Shield 5:Ring 6:Amulet
	 */
	public Item.Type getType() {
		return Type.WEAPON;
	}
	
	/**
	 * アイテムの価値
	 * @return
	 */
	public Long getPrice() {
		return 9000L;
	}
	
	/**
	 * レアリティ
	 */
	public Item.Rarity getRarity() {
		return Item.Rarity.COMMON;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public Item.Used onUse(Charactor c) {
		c.attackType = 1;
		c.attackHit = -1;
		c.attackDNum = 1;
		c.attackDice = 6;
		c.attackVal = 5;
		return Item.Used.OK;
	}
}
