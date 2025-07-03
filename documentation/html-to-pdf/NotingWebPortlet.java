package com.adjecti.noting.web.portlet;

import com.adjecti.noting.model.Noting;
import com.adjecti.noting.service.NotingLocalServiceUtil;
import com.adjecti.noting.web.constants.NotingWebPortletKeys;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author adj_2
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=NotingWeb",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + NotingWebPortletKeys.NOTINGWEB,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NotingWebPortlet extends MVCPortlet {
	
	@Override
		public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {
		 	ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
			List<Noting> notingList= NotingLocalServiceUtil.getNotings(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			List<NotingDTO> notingDtos = new ArrayList<NotingDTO>();
			for(Noting noting : notingList) {
				NotingDTO dto = new NotingDTO();
				dto.setNotingId(noting.getNoteId());
				dto.setTitle(noting.getTitle());
				dto.setViewUrl(getDownloadUrl(noting.getPdfId(), themeDisplay));
				notingDtos.add(dto);
			}
			renderRequest.setAttribute("notingList", notingDtos);
			super.doView(renderRequest, renderResponse);
		}
	
	 public void saveContent(ActionRequest actionRequest, ActionResponse actionResponse) 
	    throws IOException, PortletException {
		 ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
	     String content = ParamUtil.getString(actionRequest, "content");
	     String name = ParamUtil.getString(actionRequest, "title");
	     try {
	    	 Pattern word = Pattern.compile("src=\"");
	       	 Matcher match = word.matcher(content);
	       	 String append = "http://localhost:8080";

	       	 while (match.find()) {
	       		content = content.substring(0, match.end()) + append + content.substring(match.end());
	       	      System.out.println(content);
	       	 }

			  String htmlFilePath="C:\\Users\\adj_2\\OneDrive\\Desktop\\wkhtmltst.html";	
			  String destinationFile=	"C:\\Users\\adj_2\\OneDrive\\Desktop\\test\\"+name+".pdf";
		      File fileObj = new File(htmlFilePath);
		      if (fileObj.createNewFile()) {
		        System.out.println("File created: " + fileObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		      FileWriter textWriter = new FileWriter(htmlFilePath);
		      textWriter.write(content);
		      textWriter.close();

		      HtmlToPdfConverter.convert("C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe", false, htmlFilePath, destinationFile, "X", false);
		      long fileEntryId = DocumentMgtmt.fileUploadByApp(new File(destinationFile), themeDisplay, actionRequest);
		      
		      NotingLocalServiceUtil.addNoting(name,"",fileEntryId);
		      
		    } catch (IOException | PortalException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     
	    
	     System.out.println("content is:"+content);
	   }
	 
	 private String getDownloadUrl(long pdfId, ThemeDisplay themeDisplay) {
		 try {
			FileEntry fileEntry= DLAppLocalServiceUtil.getFileEntry(pdfId);
			FileVersion fileVersion = fileEntry.getFileVersion();
			System.out.println(DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, ""));
			return DLUtil.getDownloadURL(fileEntry, fileVersion, themeDisplay, "");
		} catch (PortalException e) {
			System.err.println("SomeThing Went Wrog!!!"+pdfId);
		}
		 return null;
	 }
	 

}