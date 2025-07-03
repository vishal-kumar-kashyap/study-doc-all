package hdfc.whatsnew.module.constants;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author ronak.parekh
 */
public class HdfcWhatsNewPortletKeys {

	public static final String HdfcWhatsNew = "hdfcwhatsnew";
	public static final String 	WHATS_NEW_WEB_SERVICE_URL=GetterUtil.getString(PropsUtil.get("whats.new.web.service.url"));
	public static final int 	WHATS_NEW_WEB_SERVICE_CONNECTION_TIMEOUT=GetterUtil.getInteger(PropsUtil.get("whats.new.web.service.connection.timeout"),6000);
	public static final String HDFC_PROXY_URL = GetterUtil.getString(PropsUtil.get("hdfc.proxy.url"),"hdfcproxy2"); 
	public static final int HDFC_PROXY_PORT = GetterUtil.getInteger(PropsUtil.get("hdfc.proxy.port"),8080);
	
	public static final String STRUCTURE_WHATS_NEW = "What's New"; 
	public static final String INTRANET_COMPANY_WEB_ID = "Intranet";
	
	
	public static final String STRUCTURE_FIELD_LINK_URL = "LinkURL";
	public static final String STRUCTURE_FIELD_LINK_TITLE = "LinkTitle";
	public static final String WHATSNEWARTICLE_SYNC_SCHEDULER_CRON_EXPRESSION="0 5 22 * * ?";

}