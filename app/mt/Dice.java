package mt;

import java.util.*;

public class Dice {
	public int num;
	public int dice;
	
	public int sum = 0;
	public int critical = 0;
	public List<Integer> results;
	
	public Sfmt mt;
	
	public Dice(Sfmt m, int x, int y) {
		mt = m;
		num = x;	dice = y;
		rollDice();
	}
	
	public void rollDice() {
		int fnvchk = 0, critchk = 0;
		results = new ArrayList<Integer>();
		for (int i = 0; i < num; i++) {
			int r = mt.NextIntEx(dice) + 1;
			sum += r;
			results.add(r);
			if (r == 1) fnvchk ++;
			if (r == dice) critchk ++;
		}
		if (num >= 2) {
			if (fnvchk == num) {
				critical = -1;
			} else if (critchk >= 2) {
				critical = 1;
			}
		}
	}
}
