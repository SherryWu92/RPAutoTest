includeCSS("ui/runLog/res/RunLogContent.css");

function RunLogContent() {
	var $contentDiv = $("<div id='runLogContent' class='runLogContent'></div>");
	var $mainView = null;
	var $logsView = null;
	init();
	
	function init() {
		createMenu();
		createMainView();
		createLogsView();
		createLogsTable();
	}
	
	function createMenu() {
		var $menuUl = $("<ul id='menu'></ul>");
		
		var $setLi = $("<li><a href='#' id='menu-set' class=''>Set</a></li>");		
		$menuUl.append($setLi);

		$contentDiv.append($menuUl);									
	}		  	
	
	function createMainView() {
		$mainView = $("<div class='mainView'></div>");
		$contentDiv.append($mainView);			
	}	
	
	function createLogsView() {
		$logsView = $("<div id='logsView'></div>");
		$mainView.append($logsView);
	}	
 
	function createLogsTable() {
		var $logsTable = $('<table border="1px"></table>');
		var $logsTh = $('<tr><th>Date</th><th>Protocal</th><th>Device</th></tr>');
		$logsTable.append($logsTh);
		
		$logsView.append($logsTable);
	}	
	
	$contentDiv.height(window.outerHeight - 170);
	return $contentDiv;
};
