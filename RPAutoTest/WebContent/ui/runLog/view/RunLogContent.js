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
		
		var $settingLi = $("<li><a href='#' id='menu-setting' class=''>Setting</a></li>");
		var $saveLi = $("<li><a href='#' id='menu-save' class=''>Save</a></li>");		

		$menuUl.append($settingLi);
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
		
		var runLog_w = [];
		runLog_w.push({id:"R1", log: "#Router1"});
		runLog_w.push({id:"R2", log: "#Router2"});
		runLog_w.push({id:"R3", log: "#Router3"});
		localStorage.setItem("RunLog", JSON.stringify(runLog_w));
		
		var $navUl = $("<ul></ul>");
		var runLog = JSON.parse(localStorage.getItem("RunLog"));
		for(var i in runLog) {
			var deviceLog = runLog[i];
			var id = deviceLog.id;
			var $navLi = $("<li><img src='ui/runLog/res/images/file.png'><a href='#' class='' id=" + runLog[i].id + ">Device_" + runLog[i].id + ".log</a></li>");
			$navUl.append($navLi);
			runLogArr[id] = deviceLog.log;
			
			$navLi.click(function() {
				if(selId != null) {
					$("#" + selId).removeClass("sel");
				}
				selId = $(this).find("a").attr("id");
				$("#" + selId).addClass("sel");				
				$logsView.text(runLogArr[selId]);
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
