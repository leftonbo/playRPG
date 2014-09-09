package models;

import play.db.ebean.*;

import javax.persistence.*;

import play.db.ebean.*;
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
    public int Mp;
    
    /** ちから　*/
    public int str;
    /** きよう　*/
    public int agi;
    /** ちかく　*/
    public int sen;
    /** こころ　*/
    public int wil;

}
