
//ServiceClient实现对AJAX请求的封装

function ServiceClient()
{
	//发出AJAX请求时调用此方法
    this.invoke = function(p_path, p_params, p_options)
    {
    	var options = $.extend({
			method: "post",
			dataType: "json",
			contentType: "application/json"
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
    //得到完整的服务地址
    function getServiceUrl(p_path)
    {	
    	var url = "/RPAutoTest/api/" + p_path;

    	return url;
    };
    //转换请求参数
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

var ServiceClient = new ServiceClient();
