package org.joget.marketplace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.joget.apps.app.model.AppDefinition;
import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.app.service.AppUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListColumnFormatDefault;
import org.joget.commons.util.LogUtil;

public class StatusColorDatalistFormatter extends DataListColumnFormatDefault{

   private final static String MESSAGE_PATH = "messages/StatusColorDatalistFormatter";

    public String getName() {
        return "Status Color Datalist Formatter";
    }

    public String getVersion() {
        return "7.0.0";
    }

    public String getDescription() {
        //support i18n
		//=======================================================================================================
        return AppPluginUtil.getMessage("org.joget.marketplace.StatusColorDatalistFormatter.pluginDesc", getClassName(), MESSAGE_PATH);
    }

    @Override
    public String format(DataList dataList, DataListColumn column, Object row, Object value) {
        AppDefinition appDef = AppUtil.getCurrentAppDefinition();
        String result = (String) value;
 
        if (result != null && !result.isEmpty()) {
            try {
		boolean isCaseSensitive = false;
                if(getPropertyString("statusCaseSensitivity") != null){
                    isCaseSensitive = Boolean.parseBoolean(getPropertyString("statusCaseSensitivity"));                    
                }	
				// set options
                Object[] options = (Object[]) getProperty("options");
		Collection<Map> optionMap = new ArrayList<>();
				
		for (Object o : options) {
		Map mapping = (HashMap) o;
                                        					
					//case must match
		if (isCaseSensitive){
                    if(((String)value).equals((String)mapping.get("value"))){
			result = "<p style=\"color: white; background-color: " + (String)mapping.get("backgroundColor") + 
                                 "; white-space: nowrap; border-radius: 8px; padding:6px; text-align: center; margin: 0px; \">" +
                                 (String)mapping.get("label") + "</p>";
                                    }
		}
				
					//no case required
		else{
                    if(((String)value).equalsIgnoreCase((String)mapping.get("value"))){
			result = "<p style=\"color: white; background-color: " + (String)mapping.get("backgroundColor") + 
                                  "; white-space: nowrap; border-radius: 8px; padding:6px; text-align: center; margin: 0px; \">" +
                                   (String)mapping.get("label") + "</p>";
                                    }
		}
						
		optionMap.add(mapping);
		}
				
		Map model = new HashMap();
		model.put("options", optionMap);
		model.put("columnName", column.getName());
		model.put("element", this);				

            } catch (Exception e) {
                LogUtil.error(getClassName(), e, "");
            }
    }
    return result;
    }

    public String getLabel() {
        //support i18n
        return AppPluginUtil.getMessage("org.joget.marketplace.StatusColorDatalistFormatter.pluginLabel", getClassName(), MESSAGE_PATH);
    }

    public String getClassName() {
        return getClass().getName();
    }

    public String getPropertyOptions() {
        return AppUtil.readPluginResource(getClassName(), "/properties/StatusColorDatalistFormatter.json", null, true, MESSAGE_PATH);
    }
}
