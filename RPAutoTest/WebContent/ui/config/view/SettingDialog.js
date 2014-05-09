includeCSS("ui/config/res/SettingDialog.css");

function SettingDialog(p_connInfo) {
	
	var connInfo = p_connInfo;	
//	if(connInfo == null) {
//		connInfo = {
//			id: "R1",
//			protocol: "OSPF",
//			connections: [{
//				port: "s0/0/0",
//				ipAddress: "192.168.0.1",
//				network: "192.168.0.0",
//				submask: "255.255.0.0",
//				area: "0",
//				target: "SW1"
//			},
//			{
//				port: "s0/0/0",
//				ipAddress: "",
//				submask: "255.255.0.0",
//				network: "192.168.0.0",
//				area: "0",
//				target: "SW1"
//			}]
//		};
//	};
	
	var $settingDialog = $("<div class='setting'></div>");
	var $ripTable = null;
	var $ospfTable = null;
	var $controlInt = null; 
//	var $maskingDiv = null;
	init();
	
	function init() {
//		createMask();
//		createTitle();
		createSelect();
		createOSPFTable();
		createRIPTable();
		
		var protocol = localStorage.getItem("Protocol");
		if(protocol != null && protocol.toLowerCase() == "ospf") {
			$settingDialog.append($ospfTable);
		}
		else {
			localStorage.setItem("Protocol", "rip");
			$settingDialog.append($ripTable);
		}

		createControlInt();
		createOperations();
	}
	
	function createTitle() {
		var $title = $("<span class='title'>" + connInfo.id + " Setting</span>");
		$settingDialog.append($title);
	}
	
	function createSelect() {
		var $selectDiv = $("<div class='select'></div>");
		
		var $select = $("<select id='protocolType'></select>");
		var $ripOption = $("<option value ='rip'>RIP</option>");
		var $ospfOption = $("<option value ='ospf'>OSPF</option>");
		$select.append($ripOption);
		$select.append($ospfOption);
		
		$selectDiv.append($select);
		$settingDialog.append($selectDiv);
		
//		if(connInfo != null && connInfo.protocol != null) {
//			$select.val(connInfo.protocol.toLowerCase());
//		}
		
		if(localStorage.getItem("Protocol") != null) {
			$select.val(localStorage.getItem("Protocol"));
		}
		
		$select.change(function() {
			var protocol = $(this).val(); 
			if(protocol.toLowerCase() == "ospf") {				
				$ripTable.detach();
				$controlInt.detach();
				$settingDialog.append($ospfTable);
				$settingDialog.append($controlInt);
			}
			else if(protocol.toLowerCase() == "rip") {
				$ospfTable.detach();
				$controlInt.detach();
				$settingDialog.append($ripTable);
				$settingDialog.append($controlInt);
			}
		});
	}
	
	function createRIPTable() {
		var $table = $('<table  class="rip" border="1px"></table>');
		var $title = $('<tr><th>port</th><th>ip-address</th><th>submask</th><th>network</th></tr>');
		$table.append($title);
		if(connInfo != null && connInfo.connections != null) {			 
			for(var i in connInfo.connections) {
				var conn = connInfo.connections[i];
				var $row = $('<tr></tr>');
				
				var $port = $('<td><input type="text" class="port" placeholder="s0/0/0" value="' + conn.port + '"/></td>');
				var $ipAddress = $('<td><input type="text" class="ip_address" placeholder="192.168.2.3" value="' + conn.ipAddress + '"/></td>');
				var $submask = $('<td><input type="text" class="submask" placeholder="255.255.255.0" value="' + conn.submask + '"/></td>');
				var $network = $('<td><input type="text" class="network" placeholder="192.168.2.0" value="' + conn.network + '"/></td>');
				
				$row.append($port);
				$row.append($ipAddress);
				$row.append($submask);
				$row.append($network);
				
				$table.append($row);
			}
		}

		$ripTable = $table;
	}
	
	function createOSPFTable() {
		var $table = $('<table class="ospf" border="1px"></table>');
		var $title = $('<tr><th>port</th><th>ip-address</th><th>submask</th><th>network</th><th>area</th><th>target</th></tr>');
		$table.append($title);
		if(connInfo != null && connInfo.connections != null) {
			for(var i in connInfo.connections) {
				var conn = connInfo.connections[i];
				var $row = $('<tr></tr>');
				
				var $port = $('<td><input type="text" class="port" placeholder="s0/0/0" value="' + conn.port + '"/></td>');
				var $ipAddress = $('<td><input type="text" class="ip_address" placeholder="192.168.2.3" value="' + conn.ipAddress + '"/></td>');
				var $submask = $('<td><input type="text" class="submask" placeholder="255.255.255.0" value="' + conn.submask + '"/></td>');
				var $network = $('<td><input type="text" class="network" placeholder="192.168.2.0" value="' + conn.network + '"/></td>');
				var $area = $('<td><input type="text" class="area" placeholder="0" value="' + conn.area + '"/></td>');
				var $target = $('<td>' + conn.target + '</td>');
							
				$row.append($port);
				$row.append($ipAddress);
				$row.append($submask);
				$row.append($network);
				$row.append($area);
				$row.append($target);
				
				$table.append($row);
			}
		}

		$ospfTable = $table;
	}
	
	function createControlInt() {
		$controlInt = $('<div class="controlInt"></div>');
		
		var $physicalIpLabel = $('<label>Physical-Ip</label>');
		var $physicalIpInput = $('<input type="text" id="physicalIp" class="physicalIp" placeholder="192.168.1.1" value="' + connInfo.physicalIp + '"/>');
		
		var $passwordLabel = $('<label>Password</label>');
		var $passwordInput = $('<input type="password" id="password" class="password"  value="' + connInfo.password + '"/>');
		
		$controlInt.append($physicalIpLabel);
		$controlInt.append($physicalIpInput);
		$controlInt.append($passwordLabel);
		$controlInt.append($passwordInput);
		
		$settingDialog.append($controlInt);
	}
	
	function createOperations() {
		var $operations = $('<div class="operation"></div>');
		
		var $ok = $('<a href="#">OK</a>');
		var $cancel = $('<a href="#">Cancel</a>');
		var $delete = $('<a href="#">Delete</a>');		
		$operations.append($ok);
		$operations.append($cancel);
		$operations.append($delete);
		
		$settingDialog.append($operations);
		
		$ok.click(function(){
			var protocol = $("#protocolType").val(); 
			localStorage.setItem("Protocol", protocol);
			if(protocol.toLowerCase() == "ospf") {
//				connInfo.protocol = protocol;
				var i = 0;
				$(".ospf tr:gt(0)").each(function() {	
					var conn = connInfo.connections[i];
					conn.port = $(this).find("td:eq(0) input").val();
					conn.ipAddress = $(this).find("td:eq(1) input").val();
					conn.submask = $(this).find("td:eq(2) input").val();
					conn.network = $(this).find("td:eq(3) input").val();
					conn.area = $(this).find("td:eq(4) input").val();					
					i++;
				}); 
			}
			else if(protocol.toLowerCase() == "rip") {
//				connInfo.protocol = protocol;
				var i = 0;
				$(".rip tr:gt(0)").each(function() {	
					var conn = connInfo.connections[i];
					conn.port = $(this).find("td:eq(0) input").val();
					conn.ipAddress = $(this).find("td:eq(1) input").val();
					conn.submask = $(this).find("td:eq(2) input").val();
					conn.network = $(this).find("td:eq(3) input").val();
					i++;
				}); 
			}
			connInfo.physicalIp = $("#physicalIp").val();
			connInfo.password = $("#password").val();
			localStorage.setItem(connInfo.id, JSON.stringify(connInfo));
			
			$settingDialog.remove();
			$("#masking").remove();	
			$("#titleTabs").remove();
		});
		
		$cancel.click(function(){
			$settingDialog.remove();
			$("#masking").remove();
			$("#titleTabs").remove();
		});
		
		$delete.click(function(){
			var id = connInfo.id;
			localStorage.removeItem(id);
			jsPlumb.detachAllConnections(id);
			jsPlumb.removeAllEndpoints(id);
			$("#" + id).remove();
			$settingDialog.remove();
			$("#masking").remove();	
			$("#titleTabs").remove();
		});
	}
	
//	function createMask() {
//        $maskingDiv = $("<div></div>");
//        $maskingDiv.css({
//            "position" : "absolute",
//            "left" : 0,
//            "top" : 0,
//            "width" : "100%",
//            "height" : "100%", 
//            "background" : "rgba(0, 0, 0, 0.7)",
//            "z-index" : 1
//        });
//        $('body').append($maskingDiv);
//	}
	
	return $settingDialog;
}