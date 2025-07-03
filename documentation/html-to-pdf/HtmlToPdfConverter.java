package com.adjecti.noting.web.portlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
/**
 * @author adj
 */
public class HtmlToPdfConverter {

	private static final Log log = LogFactoryUtil.getLog(HtmlToPdfConverter.class);
	
	public static Boolean convert(String wkhtmlHome,Boolean landscapeOrientationRequired, String sourcePath, String destinationPath, String pageSize,Boolean isFooterRequired) throws Exception {
			List<String> commandList=new ArrayList();
			commandList.add(wkhtmlHome);
			commandList.add("--disable-smart-shrinking");
			String reportFooterFile=null;
			try {
				if (landscapeOrientationRequired) {
					commandList.add("-O");
					commandList.add("landscape");
				}
				if(pageSize!=null){
					if(pageSize.contains("X")){
						try{
							String[] heightWidth=pageSize.split("X");
							String width=heightWidth[0].trim();
							String height=heightWidth[1].trim();
							Double mmWidth=Double.parseDouble(width)*10;
							Double mmHeight=Double.parseDouble(height)*10;
							commandList.add("--page-width");
							commandList.add(mmWidth.toString());
							commandList.add("--page-height");
							commandList.add(mmHeight.toString());
						}catch(Exception e){
							
						}
					}else{
						commandList.add("-s");
						commandList.add(pageSize);
					}
				}else{
					commandList.add("-s");
					commandList.add("A4");
				}
				
				if(isFooterRequired){
					commandList.add("--margin-bottom");
					commandList.add("10mm");
					commandList.add("--footer-html");
					reportFooterFile = "";
					commandList.add(reportFooterFile);
				}
				commandList.add("--margin-left");
				commandList.add("25.4mm");
				commandList.add("--margin-right");
				commandList.add("25.4mm");
				commandList.add("--margin-top");
				commandList.add("25.4mm");
				commandList.add("--margin-bottom");
				commandList.add("28mm");
				
				commandList.add(sourcePath);
				commandList.add(destinationPath);
				ProcessBuilder pb = new ProcessBuilder(commandList);
				log.info("**********WkhtmlToPdf Command Generated during conversion**********"+pb.command());
				pb.redirectErrorStream(true);
				Process process = pb.start();
				//process.waitFor();
				/*Thread t=new Thread();
				t.sleep(2000L);*/
				BufferedInputStream inStream = new BufferedInputStream(process.getInputStream());
				
				byte line[] = new byte[512];

				while (inStream.read(line) != -1) {
					
				}
				File destFile = new File(destinationPath);
				String path = destFile.getPath();
						log.info("path "+ path);
//				log.info("99999");
				if (!destFile.exists()) {
					log.info("10101010101");
					return false;
				}
				log.info("The conversion was done by wkhtml.");
			} catch (Exception e) {
				log.info("Unable to convert with wkhtml." + e.getMessage());
				return false;
			}finally{
				if(null!=reportFooterFile){
					File footerFile = new File(reportFooterFile);
					if(null!=footerFile && footerFile.exists())
						footerFile.delete();
				}
			}
			return true;
	}
	public static InputStream convertToInputStream(String wkhtmlHome,Boolean landscapeOrientationRequired, String sourcePath, String destinationPath, String pageSize,Boolean isFooterRequired) throws Exception {
		List commandList=new ArrayList();
		commandList.add(wkhtmlHome);
		commandList.add("--disable-smart-shrinking");
		String reportFooterFile=null;
		try {
			if (landscapeOrientationRequired) {
				commandList.add("-O");
				commandList.add("landscape");
			}
			if(pageSize!=null){
				if(pageSize.contains("X")){
					try{
						String[] heightWidth=pageSize.split("X");
						String width=heightWidth[0].trim();
						String height=heightWidth[1].trim();
						Double mmWidth=Double.parseDouble(width)*10;
						Double mmHeight=Double.parseDouble(height)*10;
						commandList.add("--page-width");
						commandList.add(mmWidth.toString());
						commandList.add("--page-height");
						commandList.add(mmHeight.toString());
					}catch(Exception e){
						
					}
				}else{
					commandList.add("-s");
					commandList.add(pageSize);
				}
			}else{
				commandList.add("-s");
				commandList.add("A4");
			}
			
			if(isFooterRequired){
				commandList.add("--margin-bottom");
				commandList.add("10mm");
				commandList.add("--footer-html");
				reportFooterFile = "";
				commandList.add(reportFooterFile);
			}
			commandList.add("--margin-left");
			commandList.add("25.4mm");
			commandList.add("--margin-right");
			commandList.add("25.4mm");
			commandList.add("--margin-top");
			commandList.add("25.4mm");
			commandList.add("--margin-bottom");
			commandList.add("25.4mm");
			
			commandList.add(sourcePath);
			commandList.add(destinationPath);
			ProcessBuilder pb = new ProcessBuilder(commandList);
			log.info("**********WkhtmlToPdf Command Generated during conversion**********"+pb.command());
			pb.redirectErrorStream(true);
			Process process = pb.start();
			process.waitFor();
			/*Thread t=new Thread();
			t.sleep(2000L);*/
			
			return process.getInputStream();
		} catch (Exception e) {
			log.error("Unable to convert with wkhtml." + e.getMessage());
		}finally{
			if(null!=reportFooterFile){
				File footerFile = new File(reportFooterFile);
				if(null!=footerFile && footerFile.exists())
					footerFile.delete();
			}
		}
		return null;
}

	/*private String prepareHtml(String content){
		StringBuffer htmlContent=new StringBuffer();
		htmlContent.append("<HTML><body>");
		htmlContent.append(content);
		htmlContent.append("</body></HTML>");
		
		String filePath=null;
		try {
			filePath = writeContentToFile(htmlContent.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return filePath;
	}

	public String writeContentToFile(String content) throws IOException {

		File file = File.createTempFile("content", ".html");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getPath()))) {
			bw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file.getPath();
	}*/
}