package models.charbox;

public class CharBox {
	
	public void createBox(String s) {
		
	}
	
	public String makeSave() {
		return "";
	}
	
	static public String ValToSave(int i) {
		return Integer.toString(i, 36);
	}
	
	static public int SaveToVal(String s) {
		return Integer.parseInt(s, 36);
	}
	
}
