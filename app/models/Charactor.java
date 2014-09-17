package models;

import java.util.*;

import play.db.ebean.*;

import javax.persistence.*;

import com.avaje.ebean.annotation.CreatedTimestamp;

import models.charbox.*;
import models.items.Item;
import mt.Sfmt;
import play.data.validation.Constraints;
import play.data.validation.Constraints.*;

@Entity
public class Charactor extends Model {

	/**
	 * SUIDかミ？
	 */
	private static final long serialVersionUID = -6538901145401091790L;

	/**
	 * ただのID
	 */
	@Id
	public Long id;
 
	/** Timestamp */
    @CreatedTimestamp
    public Date createDate;
    @Version
    public Date updateDate;
	
	/** 味方かな */
	public int side = 1;
	
	/** 勇者の名前　*/
    @Constraints.Required
    @MaxLength(16)
    public String name;
    
    /** 復活の呪文 */
    public String password;

    /** レベル　*/
    @Min(1)
    public int level;
    /** けいけんち　*/
    public long exp;
    /** おかね　*/
    public long money;
    
    /** せいめいりょく　*/
    public int mhp;
    /** 現在HP　*/
    public int hp;
    
    /** マナ　*/
    public int mmp;
    /** 現在MP　*/
    public int mp;
    
    /** ちから　*/
    public int str;
    /** きよう　*/
    public int agi;
    /** ちかく　*/
    public int sen;
    /** こころ　*/
    public int wil;

    /** 現在地点 */
    public int place = 0;
    /** シーン */
    public int scene = 0;
    /** 行こうとしてる地点 */
    public int nextplace = 0;
    /** リスポン地点 */
    public int respawn = 0;
    
    /** アイテム */
    @Column(columnDefinition = "varchar(2048)")
    public String itemstr = "";
    /** スキル */
    @Column(columnDefinition = "varchar(1024)")
    public String skillstr = "";
    /** フラグ */
    @Column(columnDefinition = "varchar(1024)")
    public String flagstr = "";
    
    /** 装備中武器 */
    public int equipWeapon;
    /** 装備中盾 */
    public int equipShield;
    /** 装備中鎧 */
    public int equipArmor;
    /** 装備中腕輪1 */
    public int equipRing;
    /** 装備中腕輪2 */
    public int equipAmulet;
    
    // 一時変数ども

    @Transient
    protected CharBoxItem items;
    @Transient
    public int attackType = 0;
    @Transient
    public int attackHit = 0;
    @Transient
    public int attackDNum = 1;
    @Transient
    public int attackDice = 6;
    @Transient
    public int attackVal = -3;

    @Transient
    public int armor = 0;
    @Transient
    public int defMelee = 0;
    @Transient
    public int defRanged = 0;
    @Transient
    public int defMagic = 0;

    /**
     * デフォルトパラメータ
     */
    public Charactor() {
    	level = 1;	exp = 0;
    	mhp = hp = 20;
    	mmp = mp = 20;
    	str = 3;	agi = 2;
    	sen = 2;	wil = 3;
    }
    
    /**
     * やられたかな？
     * @return
     */
    public boolean isDefeated() {
    	if (hp <= 0) return true;
    	return false;
    }
    
    /**
     * 次のレベルへの経験値
     * @return
     */
    public long getNextExp() {
    	if (level <= 1) return 2*2*2;
    	return (level+1) * (level+1) * (level+1);
    }
    
    /**
     * 現在のレベルの経験値(表示用)
     * @return
     */
    public long getNowExp() {
    	if (level <= 1) return exp;
    	return exp - level * level * level;
    }
    
    /**
     * 現在のレベルから見た次の経験値(表示用)
     * @return
     */
    public long getNowNextExp() {
    	if (level <= 1) return (level+1) * (level+1) * (level+1);
    	return (level+1) * (level+1) * (level+1) - level * level * level;
    }
    
    /* ************************************************************** */
    
    public CharBoxItem getItemBox() {
    	if (items != null) return items;
    	items = new CharBoxItem(itemstr);
    	return items;
    }
    
    public Charactor addItem(Item i) {
    	getItemBox().addItem(i);
		return this;
    }
    
    public List<Item> checkLoot(Sfmt mt) {
    	List<Item> get = new ArrayList<Item>();
    	for (Item i : items.items) {
    		if (i.IsChanceHitFreq(mt)) get.add(i);
    	}
    	return get;
    }

    /* ************************************************************** */

	public Charactor setAttackType(int attackType) {
		this.attackType = attackType;
		return this;
	}

	public Charactor setAttackHit(int attackHit) {
		this.attackHit = attackHit;
		return this;
	}
	
	public Charactor setAttacks(int x, int y, int hos) {
		attackDNum = x;	attackDice = y;	attackVal = hos;
		return this;
	}
	
	// ======================================================
	
	public Charactor setDefences(int ar, int me, int ra, int ma) {
		armor = ar;	defMelee = me;	defRanged = ra;	defMagic = ma;
		return this;
	}
	
	// ======================================================
	
	@Override
	public void save(){		
		this.itemstr = (items != null) ? this.items.makeSave() : this.itemstr;
	
		super.save();
	}

	@Override
	public void update() {
		this.itemstr = (items != null) ? this.items.makeSave() : this.itemstr;
		
		super.update();
	}
    
    /* ************************************************************** */
    
    /**
     * デバッグ用ランダムクリエイト
     * @return
     */
    public Charactor DebugRandomCreate(Sfmt mt, int s, int Lv) {
    	level = Lv;
    	side = s;
    	if (side == 1) {
    		int rnd = mt.NextInt(8);
    		switch (rnd) {
    		case 0:	name="トロ";	break;
    		case 1:	name="ミン";	break;
    		case 2:	name="レア";	break;
    		case 3:	name="シグ";	break;
    		case 4:	name="HAS";	break;
    		case 5:	name="ああああ";	break;
    		case 6:	name="トロイヌ";	break;
    		case 7:	name="よう";	break;
    		}
    	} else {
    		int rnd = mt.NextInt(10);
    		switch (rnd) {
    		case 0:	name="スライム";	break;
    		case 1:	name="スライムB";	break;
    		case 2:	name="スライムC";	break;
    		case 3:	name="スライムD";	break;
    		case 4:	name="スライムE";	break;
    		case 5:	name="スライムF";	break;
    		case 6:	name="スライムG";	break;
    		case 7:	name="スライムH";	break;
    		case 8:	name="スライムI";	break;
    		case 9:	name="スライムJ";	break;
    		}
    	}
    	mhp = hp = 20 + Lv * 2;
    	mmp = mp = 20 + Lv * 2;
    	str = 1;	agi = 1;
    	sen = 1;	wil = 1;
    	for (int i = 0; i < 6+Lv/5; i++) {
    		int rnd = mt.NextIntEx(4);
    		switch (rnd) {
    		case 0:	str ++;	break;
    		case 1:	agi ++;	break;
    		case 2:	sen ++;	break;
    		case 3:	wil ++;	break;
    		}
    	}
    	// ATKDIPE
    	switch (mt.NextIntEx(9)) {
    	case 0:
    		setAttackType(mt.NextIntEx(2));
    		setAttackHit(2 + Lv / 20);
    		setAttacks(1, 6, 0 + Lv / 10);
    		if (Lv >= 20) attackDNum ++;
    		if (Lv >= 80) attackDNum ++;
    		break;
    	case 1:
    		setAttackType(0);
    		setAttackHit(Lv / 30);
    		setAttacks(1, 6, 3 + Lv / 8);
    		if (Lv >= 10) attackDNum ++;
    		if (Lv >= 50) attackDNum ++;
    		break;
    	case 2:
    		setAttackType(0);
    		setAttackHit(-1 + Lv / 40);
    		setAttacks(2, 8, 5 + Lv / 5);
    		if (Lv >= 10) attackDNum ++;
    		if (Lv >= 50) attackDNum ++;
    		break;
    	case 3:
    		setAttackType(1);
    		setAttackHit(Lv / 30);
    		setAttacks(1, 6, 3 + Lv / 8);
    		if (Lv >= 10) attackDNum ++;
    		if (Lv >= 50) attackDNum ++;
    		break;
    	case 4:
    		setAttackType(1);
    		setAttackHit(3 + Lv / 15);
    		setAttacks(1, 6, Lv / 20);
    		if (Lv >= 30) attackDNum ++;
    		if (Lv >= 90) attackDNum ++;
    		break;
    	case 5:
    		setAttackType(2);
    		setAttackHit(Lv / 20);
    		setAttacks(1, 6, 2 + Lv / 10);
    		if (Lv >= 10) attackDNum ++;
    		if (Lv >= 50) attackDNum ++;
    		break;
    	case 6:
    		setAttackType(2);
    		setAttackHit(2 + Lv / 20);
    		setAttacks(1, 6, -2 + Lv / 20);
    		if (Lv >= 20) attackDNum ++;
    		if (Lv >= 80) attackDNum ++;
    		break;
    	case 7:
    		setAttackType(3);
    		setAttackHit(Lv / 20);
    		setAttacks(1, 6, 2 + Lv / 10);
    		if (Lv >= 10) attackDNum ++;
    		if (Lv >= 50) attackDNum ++;
    		break;
    	case 8:
    		setAttackType(3);
    		setAttackHit(2 + Lv / 20);
    		setAttacks(1, 6, -2 + Lv / 15);
    		if (Lv >= 20) attackDNum ++;
    		if (Lv >= 80) attackDNum ++;
    		break;
    	}
    	// ARMOR
    	switch (mt.NextIntEx(3)) {
    	case 0:
    		setDefences( Lv/30, 0, 2+Lv/15, 2+Lv/15);
    		break;
    	case 1:
    		setDefences( 1+Lv/15, 1+Lv/20, 1+Lv/20, 1+Lv/20);
    		break;
    	case 2:
    		setDefences( 2+Lv/10, 2+Lv/15, -1, Lv/30);
    		break;
    	}
    	return this;
    }
    
    public Charactor setName(String s) {
    	name = s;
    	return this;
    }
    public Charactor setLevel(int v) {
    	level = v;
    	return this;
    }
    public Charactor setHP(int v) {
    	mhp = hp = v;
    	return this;
    }
    public Charactor setMP(int v) {
    	mmp = mp = v;
    	return this;
    }
    public Charactor setparams(int s, int a, int e, int w) {
    	str = s; agi = a; sen = e; wil = w;
    	return this;
    }
    public Charactor setparams(int h, int m, int s, int a, int e, int w) {
    	mhp = hp = h;
    	mmp = mp = m;
    	str = s; agi = a; sen = e; wil = w;
    	return this;
    }
    public Charactor setparams(int l, int h, int m, int s, int a, int e, int w) {
    	level = l;
    	mhp = hp = h;
    	mmp = mp = m;
    	str = s; agi = a; sen = e; wil = w;
    	return this;
    }
    public Charactor setRewards(int e, int m) {
    	exp = e;	money = m;
    	return this;
    }
    
    /* ************************************************************ */
    
	/**
	 * The Finder
	 */
    public static final Finder<Long,Charactor> find =
    	new Finder<Long,Charactor>(Long.class, Charactor.class);

	public static final List<Charactor> all() {
		return find.all();
	}
	
	public static final Charactor getByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
}