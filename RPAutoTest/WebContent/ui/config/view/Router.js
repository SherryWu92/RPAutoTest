includeCSS("ui/config/res/Device.css");
includeJS("ui/config/view/SettingDialog.js");

function Router(p_id, p_left, p_top) {
	var $router = null;
	var connInfo = {};
	init();
	
	function init() {		
		createRouter();
		initConnInfo();
	}
	
	function createRouter() {
		$router = $("<div id=" + p_id + " class='device' style='position:absolute;left:" +
				p_left + "px;top:" + p_top + "px'><span>" + p_id + "<span></div>");
		var $device = $("<img src='ui/config/res/images/router.png'/>");	
		$router.append($device);
		
		$router.dblclick(function() {
			var id = $(this).attr("id");
			initConnInfo(id);
			getAllConnections(id);
			var body = $('body');
			var $settingDialog = new SettingDialog(connInfo);
			body.append($settingDialog);
		});
	}		
		
	function initConnInfo(p_id) {
		connInfo = localStorage.getItem(p_id);
		console.debug(connInfo);
		if(connInfo == null) {
			connInfo = {};
			connInfo.id = p_id;
			connInfo.protocal = "RIP";
			connInfo.connections = [];
		}
		else {
			connInfo = JSON.parse(connInfo);
		}
	}
	
	function getAllConnections(p_id) {
		var id = p_id;
		var connections = jsPlumb.getConnections({scope:"blue dot", source: id});
		for(var i in connections) {			
			var newConn = {};
			newConn.source = id;
			newConn.port = "";
			newConn.ipAddress = "";
			newConn.network = "";
			newConn.area = "";
			newConn.target = connections[i].targetId;	
			connInfo.connections.push(newConn);
		}
				
		connections = jsPlumb.getConnections({scope:"blue dot", target: id});
		for(var i in connections) {
			var newConn = {};
			newConn.source = id;
			newConn.port = "";
			newConn.ipAddress = "";
			newConn.network = "";
			newConn.area = "";
			newConn.target = connections[i].sourceId;
			connInfo.connections.push(newConn);
		}
	}
	
	return $router;
}