package models.forms;

import models.Charactor;
import play.data.validation.*;

public class FormNewGame {
	@Constraints.Required(message="なまえがない！")
	public String name;
	@Constraints.Required(message="復活の呪文がない！")
	public String password;

    public String validate() {
		if (Charactor.find.where().eq("name",name).findList().size() >= 1) {
			return "そのなまえは 使われている！";
		}
		return null;
    }
}
