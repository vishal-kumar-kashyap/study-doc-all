package com.adjecti.pis.workflow;

import com.adjecti.pis.model.RegistrationRequest;
import com.adjecti.pis.service.RegistrationRequestLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.BaseWorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = WorkflowHandler.class)
public class RegistrationRequestWorkflowHandler extends BaseWorkflowHandler<RegistrationRequest>{

	 @Override
	    public String getClassName() {
	        return RegistrationRequest.class.getName();
	    }

	    @Override
	    public String getType(Locale locale) {
	        return _resourceActions.getModelResource(locale, getClassName());
	    }


	    @Override
	    public RegistrationRequest updateStatus(
	            int status, Map<String, Serializable> workflowContext)
	        throws PortalException {

	    	System.out.println("**********RegistrationRequestWorkflowHandler.updateStatus********");
	    	
	        long userId = GetterUtil.getLong(
	            (String)workflowContext.get(WorkflowConstants.CONTEXT_USER_ID));
	        long resourcePrimKey = GetterUtil.getLong(
	            (String)workflowContext.get(
	                WorkflowConstants.CONTEXT_ENTRY_CLASS_PK));

	        ServiceContext serviceContext = (ServiceContext)workflowContext.get(
	            "serviceContext");
	        return registrationRequestLocalService.updateStatus(
	            userId, resourcePrimKey, status, serviceContext);
	    }
	    
	    @Reference(unbind = "-")
	    protected void setResourceActions(ResourceActions resourceActions) {

	        _resourceActions = resourceActions;
	    }

	    private ResourceActions _resourceActions;

		
		@Reference
		private RegistrationRequestLocalService registrationRequestLocalService;
}
