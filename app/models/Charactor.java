package models;

import java.util.List;

import play.db.ebean.*;

import javax.persistence.*;

import play.db.ebean.Model.Finder;
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
	
	/** 勇者の名前　*/
    @Constraints.Required
    @MaxLength(16)
    public String name;

    /** レベル　*/
    @Min(1)
    public int level;
    /** けいけんち　*/
    public int exp;
    
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
    
    /* ************************************************************ */
    
	/**
	 * The Finder
	 */
    public static final Finder<Long,Charactor> find =
    	new Finder<Long,Charactor>(Long.class, Charactor.class);

	public static final List<Charactor> all() {
		return find.all();
	}
}