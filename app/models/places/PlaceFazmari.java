package models.places;

import java.util.*;

import models.GamePlace;

public class PlaceFazmari extends GamePlace {
	
	public PlaceFazmari() {
		place = 2;
		name = "ファズマリの街";
	}
	
	public String getDespriction() {
		return "街の説明TODO";
	}

	public void makeEventText(int scene) {
		choose = new LinkedHashMap<Integer,String>();
		switch (scene) {
		case 1000:
			eventName = "王様";
			eventText = 
				"おお、勇者{{name}}よ。ようやく現れおったな。\n" +
				//"早速だが、私からの頼みがある。\n" +
				"";
			choose.put(0,"はい");
			break;
		default:
			eventText = "謎の空間";
			choose.put(0,"次へ");
		}
	}
	
}
