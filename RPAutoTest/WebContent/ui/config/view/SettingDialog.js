includeCSS("ui/config/res/SettingDialog.css");

function SettingDialog(p_connInfo) {
	
	var connInfo = p_connInfo;	
//	if(connInfo == null) {
//		connInfo = {
//			id: "R1",
//			protocal: "OSPF",
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
	var $maskingDiv = null;
	init();
	
	function init() {
		createMask();
		createTitle();
		createSelect();
		createOSPFTable();
		createRIPTable();
		
		var protocal = localStorage.getItem("Protocal");
		if(protocal != null && protocal.toLowerCase() == "ospf") {
			$settingDialog.append($ospfTable);
		}
		else {
			localStorage.setItem("Protocal", "rip");
			$settingDialog.append($ripTable);
		}

		createOperations();
	}
	
	function createTitle() {
		var $title = $("<span class='title'>" + connInfo.id + " Setting</span>");
		$settingDialog.append($title);
	}
	
	function createSelect() {
		var $selectDiv = $("<div class='select'></div>");
		
		var $select = $("<select id='protocalType'></select>");
		var $ripOption = $("<option value ='rip'>RIP</option>");
		var $ospfOption = $("<option value ='ospf'>OSPF</option>");
		$select.append($ripOption);
		$select.append($ospfOption);
		
		$selectDiv.append($select);
		$settingDialog.append($selectDiv);
		
//		if(connInfo != null && connInfo.protocal != null) {
//			$select.val(connInfo.protocal.toLowerCase());
//		}
		
		if(localStorage.getItem("Protocal") != null) {
			$select.val(localStorage.getItem("Protocal"));
		}
		
		$select.change(function() {
			var protocal = $(this).val(); 
			if(protocal.toLowerCase() == "ospf") {
				$ripTable.detach();
				$settingDialog.append($ospfTable);				
			}
			else if(protocal.toLowerCase() == "rip") {
				$ospfTable.detach();
				$settingDialog.append($ripTable);
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
			var protocal = $("#protocalType").val(); 
			localStorage.setItem("Protocal", protocal);
			if(protocal.toLowerCase() == "ospf") {
//				connInfo.protocal = protocal;
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
			else if(protocal.toLowerCase() == "rip") {
//				connInfo.protocal = protocal;
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
			localStorage.setItem(connInfo.id, JSON.stringify(connInfo));
			
			$settingDialog.remove();
			$maskingDiv.remove();
		});
		
		$cancel.click(function(){
			$settingDialog.remove();
			$maskingDiv.remove();
		});
		
		$delete.click(function(){
			var id = connInfo.id;
			localStorage.removeItem(id);
			jsPlumb.detachAllConnections(id);
			jsPlumb.removeAllEndpoints(id);
			$("#" + id).remove();
			$settingDialog.remove();
			$maskingDiv.remove();			
		});
	}
	
	function createMask() {
        $maskingDiv = $("<div></div>");
        $maskingDiv.css({
            "position" : "absolute",
            "left" : 0,
            "top" : 0,
            "width" : "100%",
            "height" : "100%", 
            "background" : "rgba(0, 0, 0, 0.7)",
            "z-index" : 1
        });
        $('body').append($maskingDiv);
	}
	
	return $settingDialog;
}