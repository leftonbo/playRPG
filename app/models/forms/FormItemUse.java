package models.forms;

import play.data.validation.*;

public class FormItemUse {
	
	@Constraints.Required(message="君は一体何を使おうとしたのかね")
	public int use;

}
