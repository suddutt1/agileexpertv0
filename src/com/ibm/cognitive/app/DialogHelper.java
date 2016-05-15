package com.ibm.cognitive.app;

import com.ibm.watson.developer_cloud.dialog.v1.DialogService;
import com.ibm.watson.developer_cloud.dialog.v1.model.Dialog;
import com.ibm.watson.developer_cloud.http.ServiceCall;

import static com.ibm.cognitive.app.ApplicationConstants.*;

import java.io.File;
import java.util.List;

public class DialogHelper {

	
	public static void main(String[] args)
	{
		if(args.length>0 )
		{
			updateDialog(DIALOG_NAME, args[0]);
		}
		else
		{
			System.out.println("Please provide the path to the dialog file");
		}
	}
	
	/**
	 * Search for a dialog with a given name
	 * @param name Dialog name to search 
	 * @return Dialog object matching the name. Null in case not found.
	 */
	public static Dialog getDialogId(String name)
	{
		Dialog result  = null;
		
		DialogService service = new DialogService();
		service.setUsernameAndPassword(USER_ID,PASSWORD);
		
		ServiceCall<List<Dialog>> serviceCall = service.getDialogs();
		List<Dialog> dialogs = serviceCall.execute();
		System.out.println("Going to check the registered dialogs");
		for(Dialog dialog:dialogs)
		{
			if(dialog.getName().equals(name))
			{
				result= dialog;
				System.out.println("Dialog named "+ name+ " is found. ");
				break;
			}
		}
		return result;
	}
	public static void updateDialog(String name,String pathToDialogFile)
	{
		boolean isUpdateMode  = false;
		String dialogId  = null;
		DialogService service = new DialogService();
		service.setUsernameAndPassword(USER_ID,PASSWORD);
		
		ServiceCall<List<Dialog>> serviceCall = service.getDialogs();
		List<Dialog> dialogs = serviceCall.execute();
		System.out.println("Going to check the registered dialogs");
		for(Dialog dialog:dialogs)
		{
			if(dialog.getName().equals(name))
			{
				isUpdateMode = true;
				dialogId = dialog.getId();
				System.out.println("Dialog named "+ name+ " is found . Proceeding to update the dialog");
				break;
			}
		}
		if(!isUpdateMode)
		{
			//Create the dialog
			ServiceCall<Dialog> createServiceCall = service.createDialog(name, new File( pathToDialogFile));
			Dialog updateDialog = createServiceCall.execute();
			System.out.println("Dialog create status :"+ updateDialog.toString());
		}
		else
		{
			ServiceCall<Void> updateServiceCall = service.updateDialog(dialogId, new File(pathToDialogFile));
			updateServiceCall.execute();
			System.out.println("Dialog service updated ");
		}
		
		
		
	}
}
