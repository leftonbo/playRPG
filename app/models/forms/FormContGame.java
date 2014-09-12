package models.forms;

import models.Charactor;
import play.data.validation.*;

public class FormContGame {
	@Constraints.Required(message="なまえがない！")
	public String name;
	@Constraints.Required(message="復活の呪文がない！")
	public String password;

    public String validate() {
		if (Charactor.find.where().eq("name",name).eq("password",password).findList().size() != 1) {
			return "おきのどくですが なまえ か 復活の呪文 が間違っているようです";
		}
		return null;
    }
}
