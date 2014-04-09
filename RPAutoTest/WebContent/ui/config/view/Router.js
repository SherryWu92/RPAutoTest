includeCSS("ui/config/res/Device.css");

function Router(p_id, p_left, p_top) {
	var $router = $("<div id=" + p_id + " class='device' style='position:absolute;left:" +
			p_left + "px;top:" + p_top + "px'><span>" + p_id + "<span></div>");
	var $device = $("<img src='ui/config/res/images/router.png'/>");	
	$router.append($device);
	return $router;
}