package models.items;

import models.Charactor;
import models.items.Item;

public class ItemArmorIron extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 13;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "鉄の重鎧";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "粗い鉄の鎧。重たい。\n\n種類:重い鎧\n防御力:4\n近接防御:+2\n射撃回避:-1";
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
		c.armor += 4;
		c.defMelee += 2;
		c.defRanged -= 1;
		return Item.Used.OK;
	}
}
