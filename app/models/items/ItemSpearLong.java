package models.items;

import models.Charactor;
import models.items.Item;

public class ItemSpearLong extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 11;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "ロングスピア";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "長柄の槍。器用さで戦う。\n\n種類:近接 - きようさ攻撃\nダメージ:[命中度]+2D6+3";
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
		return 18000L;
	}
	
	/**
	 * レアリティ
	 */
	public Item.Rarity getRarity() {
		return Item.Rarity.UNCOMMON;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public Item.Used onUse(Charactor c) {
		c.attackType = 1;
		c.attackHit = 0;
		c.attackDNum = 2;
		c.attackDice = 6;
		c.attackVal = 3;
		return Item.Used.OK;
	}
}
