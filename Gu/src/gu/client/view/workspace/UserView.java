package gu.client.view.workspace;

import gu.client.model.User;
import gu.client.view.DatabaseEditorView;
import gu.client.view.dialogs.UserDialog;


public class UserView extends ObjectView {


	private final User user;

	public UserView( User user1, DatabaseEditorView view ){	
		super( "<img src='item-user.png' hspace='3'>"+user1.getName(), view );
		this.user = user1;

		addField( "id", user.getId() );
		addField( "name", user.getName() );
		addField( "email", user.getEmail() );
	}

	protected void onDelete() {
		getView().getObjectFactory().getUserDAO().delete(user);
	}
	protected void onEdit() {
		new UserDialog( user, getView() );
	}
}
