package models;

import java.util.*;

import models.places.*;

public class GamePlace {	
	public int place;
	public String name;
	
	public String eventName;
	public String eventText;
	public Map<Integer,String> choose;
	
	public GamePlace() {
		place = 0;
		name = "未定義の場所";
	}
	
	public String getDespriction() {
		return "";
	}
	
	public void makeEventText(int scene) {
	}
	
	
	/// ====
	
	static public GamePlace createByPlace(int p) {
		switch(p) {
		case 2:
			return new PlaceFazmari();
		}
		return null;
	}
}
