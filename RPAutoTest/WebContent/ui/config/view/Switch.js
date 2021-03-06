includeCSS("ui/config/res/Device.css");

includeJS("ui/config/view/SettingDialog.js");
includeJS("ui/config/view/SettingTabView.js");
includeJS("ui/config/view/TestDialog.js");

function Switch(p_id, p_left, p_top) {
	var $switch = null;
	var connInfo = {};
	init();
	
	function init() {		
		createSwitch();
		initConnInfo();
		localStorage.removeItem(p_id); //clear the localStorage
	}
	
	function createSwitch() {
		$switch = $("<div id=" + p_id + " class='device' style='position:absolute;left:" +
				p_left + "px;top:" + p_top + "px'><span>" + p_id + "<span></div>");
		var $device = $("<img src='ui/config/res/images/switch.png'/>");	
		$switch.append($device);
		
		$switch.dblclick(function() {
			var id = $(this).attr("id");
			initConnInfo(id);
			getAllConnections(id);
			var body = $('body');
			var $settingDialog = new SettingDialog(connInfo);
			var $testDialog = new TestDialog(connInfo);
			var $settingTabView = new SettingTabView(id, $settingDialog, $testDialog);
			body.append($settingTabView);
		});
	}
	
	function initConnInfo(p_id) {
		connInfo = localStorage.getItem(p_id);
		if(connInfo == null) {
			connInfo = {};
			connInfo.id = p_id;
			connInfo.physicalIp = "";
			connInfo.password = "";
			connInfo.connections = [];
			connInfo.testCmds = [];
		}
		else {
			connInfo = JSON.parse(connInfo);
		}
	}
	
	function getAllConnections(p_id) {
		var id = p_id;
		var storeConnections = $.extend(true, [], connInfo.connections);
		connInfo.connections = [];
		var connections = jsPlumb.getConnections({source: id});		
		for(var i in connections) {			
			var newConn = {};
			newConn.port = "";
			newConn.ipAddress = "";
			newConn.submask = "";
			newConn.network = "";
			newConn.area = "";
			newConn.target = connections[i].targetId;	
			var conn = getCompleteConnection(storeConnections, newConn);
			connInfo.connections.push(conn);
		}
				
		connections = jsPlumb.getConnections({target: id});
		for(var i in connections) {
			var newConn = {};
			newConn.port = "";
			newConn.ipAddress = "";
			newConn.submask = "";
			newConn.network = "";
			newConn.area = "";
			newConn.target = connections[i].sourceId;
			var conn = getCompleteConnection(storeConnections, newConn);
			connInfo.connections.push(conn);
		}
	}
	
	function getCompleteConnection(p_connections, p_newConn) {
		for(var i in p_connections) {
			var conn = p_connections[i];
			if(conn.target == p_newConn.target) {
				return conn;
			}
		}
		return p_newConn;
	}
	
	return $switch;
}