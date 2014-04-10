includeCSS("ui/config/res/SettingDialog.css");

function SettingDialog(p_connInfo) {
	
	var connInfo = p_connInfo;
	if(connInfo == null) {
		connInfo = {
			protocal: "OSPF",
			connections: [{
				source: "R1",
				port: "s0/0/0",
				ipAddress: "192.168.0.1",
				network: "192.168.0.0",
				area: "0",
				target: "SW1"
			},
			{
				source: "R1",
				port: "s0/0/0",
				ipAddress: "",
				network: "192.168.0.0",
				area: "0",
				target: "SW1"
			}]
		};
	};
	
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
		if(connInfo.protocal.toLowerCase() == "ospf") {
			$settingDialog.append($ospfTable);
		}
		else if(connInfo.protocal.toLowerCase() == "rip") {
			$settingDialog.append($ripTable);
		}
		createOperations();
	}
	
	function createTitle() {
		var $title = $("<span class='title'>Setting</span>");
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
		
		if(connInfo != null && connInfo.protocal != null) {
			$select.val(connInfo.protocal.toLowerCase());
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
		var $title = $('<tr><th>source</th><th>ip-address</th><th>network</th></tr>');
		$table.append($title);
		for(var i in connInfo.connections) {
			var conn = connInfo.connections[i];
			var $row = $('<tr></tr>');
			
			var $source = $('<td>' + conn.source + '</td>');
			var $ipAddress = $('<td><input type="text" class="ip_address" placeholder="192.168.2.3" value="' + conn.ipAddress + '"/></td>');
			var $network = $('<td><input type="text" class="network" placeholder="192.168.2.0" value="' + conn.network + '"/></td>');
			
			$row.append($source);
			$row.append($ipAddress);
			$row.append($network);
			
			$table.append($row);
		}
		$ripTable = $table;
	}
	
	function createOSPFTable() {
		var $table = $('<table class="ospf" border="1px"></table>');
		var $title = $('<tr><th>source</th><th>port</th><th>ip-address</th><th>network</th><th>area</th><th>target</th></tr>');
		$table.append($title);
		for(var i in connInfo.connections) {
			var conn = connInfo.connections[i];
			var $row = $('<tr></tr>');
			
			var $source = $('<td>' + conn.source + '</td>');
			var $port = $('<td><input type="text" class="port" placeholder="s0/0/0" value="' + conn.port + '"/></td>');
			var $ipAddress = $('<td><input type="text" class="ip_address" placeholder="192.168.2.3" value="' + conn.ipAddress + '"/></td>');
			var $network = $('<td><input type="text" class="network" placeholder="192.168.2.0" value="' + conn.network + '"/></td>');
			var $area = $('<td><input type="text" class="area" placeholder="0" value="' + conn.area + '"/></td>');
			var $target = $('<td>' + conn.target + '</td>');
			
			$row.append($source);
			$row.append($port);
			$row.append($ipAddress);
			$row.append($network);
			$row.append($area);
			$row.append($target);
			
			$table.append($row);
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
			console.debug("ok");
			var protocal = $("#protocalType").val(); 
			if(protocal.toLowerCase() == "ospf") {
				connInfo.protocal = protocal;
				connInfo.connections = [];
				$(".ospf tr:gt(0)").each(function() {	
					var conn = {};
					conn.source = $(this).find("td:eq(0)").text();
					conn.port = $(this).find("td:eq(1) input").val();
					conn.ipAddress = $(this).find("td:eq(2) input").val();
					conn.network = $(this).find("td:eq(3) input").val();
					conn.area = $(this).find("td:eq(4) input").val();
					conn.target = $(this).find("td:eq(5)").text();
					connInfo.connections.push(conn);
				}); 
			}
			else if(protocal.toLowerCase() == "rip") {
				connInfo.protocal = protocal;
				connInfo.connections = [];
				$(".rip tr:gt(0)").each(function() {	
					var conn = {};
					conn.source = $(this).find("td:eq(0)").text();
					conn.ipAddress = $(this).find("td:eq(1) input").val();
					conn.network = $(this).find("td:eq(2) input").val();
					connInfo.connections.push(conn);
				}); 
			}
			console.debug(connInfo);
			localStorage.setItem(connInfo.id, JSON.stringify(connInfo));
			console.debug(JSON.parse(localStorage.getItem(connInfo.id)));
			$settingDialog.remove();
			$maskingDiv.remove();
		});
		
		$cancel.click(function(){
			console.debug("cancel");
			$settingDialog.remove();
			$maskingDiv.remove();
		});
		
		$delete.click(function(){
			console.debug("delete");
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