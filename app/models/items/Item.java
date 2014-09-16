package models.items;

import models.Charactor;

public class Item {

	/**
	 * なまえ
	 * @return
	 */
	public String getName() {
		return "謎";
	}
	
	/**
	 * せつめい
	 * @return
	 */
	public String getDesp() {
		return "謎";
	}
	
	/**
	 * アイテムタイプ
	 * @return 0:Unusable 1:Consumable 2:Weapon 3:Armor 4:Shield 5:Ring 6:Amulet
	 */
	public int getType() {
		return 0;
	}
	
	/**
	 * 使用時の処理
	 * 武器装備時にどうなるかもここに書く
	 * @param c
	 */
	public void onUse(Charactor c) {
		
	}
	
	// ===============================================
	
	public static Item createByInt(int i) {
		switch (i) {
			
		}
		return new Item();
	}
}
