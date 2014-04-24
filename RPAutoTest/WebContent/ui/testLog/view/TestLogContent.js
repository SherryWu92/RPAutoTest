includeCSS("ui/testLog/res/TestLogContent.css");

includeJS("ui/common/view/FileHandler.js");

function TestLogContent() {
	var $contentDiv = $("<div id='testLogContent' class='runLogContent'></div>");
	var $mainView = null;
	var $logsView = null;
	var testLog = {};
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
			for(var i in testLog) {
				var id = i;
				var fileContent = testLog[id];
				var fileName = "Test_" + id +".log";
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
		testLog = JSON.parse(localStorage.getItem("TestLog"));
		for(var i in testLog) {
			var id = i;	
			var $navLi = $("<li><img src='ui/testLog/res/images/file.png'><a href='#' class='' id=" + id + ">Test_" + id + ".log</a></li>");
			$navUl.append($navLi);			
			
			$navLi.click(function() {
				if(selId != null) {
					$("#" + selId).removeClass("sel");
				}
				selId = $(this).find("a").attr("id");
				$("#" + selId).addClass("sel");			
				var logStr = testLog[selId].replace(/\/r\/n/g,"<br>");
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
