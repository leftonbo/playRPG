package models.charbox;

import java.util.Set;
import java.util.TreeSet;

import models.items.Item;

public class CharBoxItem extends CharBox {
	
	public Set<Item> items;
	
	public CharBoxItem(String s) {
		createBox(s);
	}

	public void createBox(String s) {
		items = new TreeSet<Item>();
		if (s == "") return;
		String[] strlist = s.split(",");
		for (String ss : strlist) {
			String[] stritm = ss.split(":");
			if (stritm.length >= 2)
				items.add(Item.createByInt(SaveToVal(stritm[0]), SaveToVal(stritm[1])));
		}
	}
	
	public String makeSave() {
		String res = "";
		for (Item i : items) {
			if (i.num <= 0) continue;	// ないよ！
			if (res != "") res += ",";
			res += ValToSave(i.getId()) + ":" + ValToSave(i.num);
		}
		return res;
	}
	
	public void addItem(Item add) {
		boolean flag = false;
		for (Item i : items) {
			if (i.getId() == add.getId()) {
				i.num += add.num;
				flag = true;
				break;
			}
		}
		if (!flag) {
			items.add(add);
		}
	}

}
