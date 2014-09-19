package models.charbox;

import java.util.HashMap;
import java.util.Map;

public class CharBoxFlag extends CharBox {
	
	public Map<String,Integer> flags;
	
	public CharBoxFlag(String s) {
		createBox(s);
	}

	public void createBox(String s) {
		flags = new HashMap<String,Integer>();
		if (s == "") return;
		String[] strlist = s.split(",");
		for (String ss : strlist) {
			String[] stritm = ss.split(":");
			if (stritm.length >= 2)
				flags.put(stritm[0], SaveToVal(stritm[1]));
		}
	}
	
	public String makeSave() {
		String res = "";
		for (Map.Entry<String, Integer> e : flags.entrySet()) {
			if (res != "") res += ",";
			res += e.getKey() + ":" + ValToSave(e.getValue());
		}
		return res;
	}
	
	public void setFlag(String flg, int val) {
		flags.put(flg, val);
	}
	
	public int getFlag(String flg) {
		if (!flags.containsKey(flg)) return 0;
		return flags.get(flg);
	}

}
