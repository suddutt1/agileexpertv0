package com.ibm.cognitive.app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.watson.developer_cloud.dialog.v1.DialogService;
import com.ibm.watson.developer_cloud.dialog.v1.model.Conversation;
import com.ibm.watson.developer_cloud.dialog.v1.model.Dialog;
import static com.ibm.cognitive.app.ApplicationConstants.*;

@ApplicationPath("expert")
@Path("service")
public class AgileExpertService extends Application {

	private static final StorageService _storageService = new DummyDataStorage();

	@GET
	@Path("context")
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContextParamertes() {
		Response response = null;
		Dialog dialog = DialogHelper.getDialogId(DIALOG_NAME);
		if (dialog != null) {
			response = Response.ok(new ServiceResponse(true, "Dialog found", dialog)).build();
		} else {
			response = Response.ok(new ServiceResponse(false, "Dialog not found", null)).build();
		}
		return response;
	}

	@POST
	@Path("converse")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response converse(@FormParam("dialogId") String dialogId, @FormParam("clientId") int clientId,
			@FormParam("conversionId") int convid, @FormParam("question") String question) {

		DialogService service = new DialogService();
		service.setUsernameAndPassword(USER_ID, PASSWORD);
		Conversation conversation = new Conversation();
		if (clientId > 0) {

			conversation.setClientId(clientId);
		}
		if (convid > 0) {
			conversation.setId(convid);
		}
		conversation.setDialogId(dialogId);
		Conversation reply = service.converse(conversation, question.toLowerCase()).execute();
		if (reply.getConfidence() <= CONF_THREASHOLD) {
			// Store the question for further training
			if(reply.getInput()!=null && reply.getInput().trim().length()>0)
			_storageService.storeObjects(DIALOG_NAME, reply.getInput());
		}

		return Response.ok(new ServiceResponse(true, "Reply received", reply)).build();
	}

	@GET
	@Path("lowConfidenceAnswers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLowerConfidenceQuestios() {
		return Response
				.ok(new ServiceResponse(true, "Low confidence answers", _storageService.retrieveObjects(DIALOG_NAME)))
				.build();
	}

}
