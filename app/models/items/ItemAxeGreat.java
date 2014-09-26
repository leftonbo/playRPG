package models.items;

import models.Charactor;
import models.items.Item;

public class ItemAxeGreat extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 15;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "ウォーハンマー";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "戦闘用の大きなハンマー。\n\n種類:近接 - ちから攻撃\nダメージ:[命中度]+2D10+2\n命中:-2";
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
		return 20000L;
	}
	
	/**
	 * レアリティ
	 */
	public Item.Rarity getRarity() {
		return Item.Rarity.RARE;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public Item.Used onUse(Charactor c) {
		c.attackType = 0;
		c.attackHit = -2;
		c.attackDNum = 2;
		c.attackDice = 10;
		c.attackVal = 2;
		return Item.Used.OK;
	}
}
