package models.items;

import models.Charactor;
import models.items.Item;

public class ItemArmorChain extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 12;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "チェインアーマー";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "鎖かたびらの鎧。動きやすく頑丈。\n\n種類:軽い防具\n防御力:3\n近接防御:+1\n射撃回避:+1\n魔法抵抗:+2";
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
		return Type.ARMOR;
	}
	
	/**
	 * アイテムの価値
	 * @return
	 */
	public Long getPrice() {
		return 15000L;
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
		c.armor += 3;
		c.defMelee += 1;
		c.defRanged += 1;
		c.defMagic += 2;
		return Item.Used.OK;
	}
}
