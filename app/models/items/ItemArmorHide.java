package models.items;

import models.Charactor;
import models.items.Item;

public class ItemArmorHide extends Item {
	
	/**
	 * 識別ID
	 * @return
	 */
	public int getId() {
		return 9;
	}

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "革のよろい";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "革製のそぼくな鎧。\n\n種類:軽い防具\n防御力:1\n近接防御:+1\n魔法抵抗:+1";
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
		return 6000L;
	}
	
	/**
	 * レアリティ
	 */
	public Rarity getRarity() {
		return Rarity.COMMON;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public Item.Used onUse(Charactor c) {
		c.armor += 1;
		c.defMelee += 1;
		c.defMagic += 1;
		return Item.Used.OK;
	}
}
