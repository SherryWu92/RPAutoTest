includeCSS("ui/runLog/res/RunLogContent.css");

includeJS("ui/common/view/FileHandler.js");

function RunLogContent() {
	var $contentDiv = $("<div id='runLogContent' class='runLogContent'></div>");
	var $mainView = null;
	var $logsView = null;
	var runLogArr = [];
	var selId = null;
	init();
	
	function init() {
		createMenu();
		createMainView();
		createNavView();
		createLogsView();
	}
	
	function createMenu() {
		var $menuUl = $("<ul id='menu'></ul>");
		
		var $saveLi = $("<li><a href='#' id='menu-save' class=''>Save</a></li>");		
		$menuUl.append($saveLi);

		$contentDiv.append($menuUl);	
		
		$saveLi.click(function() {
			for(var i in runLogArr) {
				var id = i;
				var fileContent = runLogArr[id];
				var fileName = "Device_" + id +".log";
				saveFile(fileContent, "text/log", fileName); 
			}
		});
	}		  	
	
	function createMainView() {
		$mainView = $("<div class='mainView'></div>");
		$contentDiv.append($mainView);				
	}	
	
	function createNavView() {
		$navView = $("<div id='navView'></div>");
				
		var $navUl = $("<ul></ul>");
		var runLog = JSON.parse(localStorage.getItem("RunLog"));
		for(var i in runLog) {
			var deviceLog = runLog[i];
			var id = deviceLog.id;
			var $navLi = $("<li><img src='ui/runLog/res/images/file.png'><a href='#' class='' id=" + id + ">Device_" + id + ".log</a></li>");
			$navUl.append($navLi);
			runLogArr[id] = deviceLog.log;
			
			$navLi.click(function() {
				if(selId != null) {
					$("#" + selId).removeClass("sel");
				}
				selId = $(this).find("a").attr("id");
				$("#" + selId).addClass("sel");			
				var logStr = runLogArr[selId].replace(/\r\n/g,"<br>");
				$logsView.html(logStr);
			});
		}		
		$navView.append($navUl);
		
		$mainView.append($navView);	
	}
	
	function createLogsView() {
		$logsView = $("<div id='logsView'></div>");
		$mainView.append($logsView);
	}	
	
	$contentDiv.height(window.outerHeight - 170);
	return $contentDiv;
};
