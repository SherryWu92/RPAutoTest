includeCSS("ui/config/res/Device.css");

function Computer(p_id, p_left, p_top) {
	var $computer = $("<div id=" + p_id + " class='device' style='position:absolute;left:" +
			p_left + "px;top:" + p_top + "px'><span>" + p_id + "<span></div>");
	var $device = $("<img src='ui/config/res/images/computer.png'/>");	
	$computer.append($device);
	return $computer;
}