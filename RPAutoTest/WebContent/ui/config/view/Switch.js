includeCSS("ui/config/res/Device.css");

function Switch(p_id, p_left, p_top) {
	var $switch = $("<div id=" + p_id + " class='device' style='position:absolute;left:" +
			p_left + "px;top:" + p_top + "px'><span>" + p_id + "<span></div>");
	var $device = $("<img src='ui/config/res/images/switch.png'/>");	
	$switch.append($device);
	
	
	$switch.dblclick(function() {
		var body = $('body');
		var $settingDialog = new SettingDialog();
		body.append($settingDialog);
	});
	return $switch;
}