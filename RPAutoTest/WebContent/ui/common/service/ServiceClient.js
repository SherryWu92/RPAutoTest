/**
 * 
 */

function ServiceClient()
{
 
    this.invoke = function(p_path, p_params, p_options)
    {
    	var options = $.extend({
			method: "post",
			dataType: "json"
    	}, p_options);
    	
    	options.url = getServiceUrl(p_path);
    	options.data = convertParams(p_params);
    	
    	var def = $.Deferred();
    	$.ajax(options).done(function(response)
		{
    		if (response.errCode == 0)
    		{
    			def.resolve(response.result);
    		}
    		else
    		{
    			def.reject(response.errCode, response.errDesc);
    		}
    	}).fail(function()
		{
    		def.reject(arguments);
    	});
    	return def;
    };
    
    function getServiceUrl(p_path)
    {	
    	var url = "/RPAutoTest/api/" + p_path;

    	return url;
    };

    function convertParams(p_params)
    {
    	if (p_params != null)
		{
    		return JSON.stringify(p_params);
		}
    	else
    	{
    		return null;
    	}
    };

};

//ui.common.service.ServiceClient = new ui.common.service.ServiceClient();
