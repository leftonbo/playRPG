package models.items;

import models.Charactor;
import models.items.Item;

public class ItemSwordCopper extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 2;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "銅のつるぎ";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "銅でできた、粗悪な剣。\n\n種類:近接 - ちから攻撃\nダメージ:[命中度]+1D6-1";
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
		return 3000L;
	}
	
	/**
	 * レアリティ
	 */
	public Rarity getRarity() {
		return Rarity.JUNK;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public Item.Used onUse(Charactor c) {
		c.attackHit = 0;
		c.attackDNum = 1;
		c.attackDice = 6;
		c.attackVal = -1;
		return Item.Used.OK;
	}
}
