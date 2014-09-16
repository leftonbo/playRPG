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
	 * 消耗品かミ？
	 * @return
	 */
	public boolean isConsumable() {
		return false;
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
