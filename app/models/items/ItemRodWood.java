package models.items;

import models.Charactor;
import models.items.Item;

public class ItemRodWood extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 3;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "木のつえ";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "室の悪い木で作った杖。\n\n種類:魔法 - こころ攻撃\nダメージ:1D6+3";
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
		return 8000L;
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
		c.attackType = 3;
		c.attackHit = 0;
		c.attackDNum = 1;
		c.attackDice = 6;
		c.attackVal = 3;
		return Item.Used.OK;
	}
}
