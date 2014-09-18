package models.forms;

import models.Charactor;
import play.data.validation.*;

import org.mindrot.jbcrypt.BCrypt;

public class FormContGame {
	@Constraints.Required(message="なまえがない！")
	public String name;
	@Constraints.Required(message="復活の呪文がない！")
	public String password;

    public String validate() {
    	Charactor login = Charactor.find.where().eq("name",name).findUnique();
    	if (login == null) return "おきのどくですが なまえ か 復活の呪文 が間違っているようです";
		if (!BCrypt.checkpw(password, login.password)) {
			return "おきのどくですが なまえ か 復活の呪文 が間違っているようです";
		}
		return null;
    }
}
