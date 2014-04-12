includeCSS("ui/config/res/Content.css");

includeJS("ui/common/service/ServiceClient.js");
includeJS("ui/config/view/Router.js");
includeJS("ui/config/view/Switch.js");
includeJS("ui/config/view/Computer.js");

function Content() {
	var $contentDiv = $("<div class='content'></div>");
	var $mainView = null;
	var $canvas = null;
	var routerCount = 0;
	var switchCount = 0;
	var computerCount = 0;
	init();
	
	function init() {
		createMenu();
		createMainView();
		createMachinesView();
		createCanvas();
		createTabs();
	}
	
	function createMenu() {
		var $menuUl = $("<ul id='menu'></ul>");
		
		var $undoLi = $("<li><a href='#' id='menu-undo' class=''>Undo</a></li>");
		var $redoLi = $("<li><a href='#' id='menu-redo' class=''>Redo</a></li>");
		var $importLi = $("<li><a href='#' id='menu-import' class=''>Import</a></li>");
		var $okLi = $("<li><a href='#' id='menu-ok' class=''>OK</a></li>");
		var $clearLi = $("<li><a href='#' id='menu-clear' class=''>Clear</a></li>");
		
		$menuUl.append($clearLi);
		$menuUl.append($okLi);
		$menuUl.append($importLi);
		$menuUl.append($redoLi);
		$menuUl.append($undoLi);

		$contentDiv.append($menuUl);
		
		$okLi.click(function() {
			var protocalInfo = {};
			protocalInfo.type = "rip";
			protocalInfo.routers = [];
			protocalInfo.switches = [];
			if(localStorage.getItem("Protocal") != null) {
				protocalInfo.protocal = localStorage.getItem("Protocal");
			}
			$canvas.find(".device").each(function(){
				var id = $(this).attr("id");
				if(id.indexOf("R") != -1) {
					var router = localStorage.getItem(id);
					if(router == null) {
						alert("Please complete the " + id + " router's info!");
						return;
					}
					protocalInfo.routers.push(router);
				}
				else if(id.indexOf("SW") != -1) {
					var _switch = localStorage.getItem(id);
					if(_switch == null) {
						alert("Please complete the " + id + " switch's info!");
						return;
					}
					protocalInfo.switches.push(_switch);
				}
			});
			
			console.debug(protocalInfo);
			ServiceClient.invoke("configure/protocal", protocalInfo).done(function(p_results){
				console.debug(p_results);
			});
			
		});
		
		$clearLi.click(function() {
			clearCanvas();
		});
	}

	function clearCanvas() {
		$canvas.empty();
		localStorage.clear();
	}
	
	function createMainView() {
		$mainView = $("<div class='mainView'></div>");
		$contentDiv.append($mainView);
	}
	
	function createMachinesView() {
		var $machinesDiv = $("<div id='machines'></div>");
		
		var $routerDiv = $("<div id='router'></div>");
		var $routerImg = $("<img src='ui/config/res/images/router.png'>");
		$routerDiv.append($routerImg);
		$routerImg.draggable({zIndex:100, revert: "invalid", helper: "clone", cursor: "move" });
		
		var $switchDiv = $("<div id='switch'></div>");
		var $switchImg = $("<img src='ui/config/res/images/switch.png'>");
		$switchDiv.append($switchImg);
		$switchImg.draggable({zIndex:100, revert: "invalid", helper: "clone", cursor: "move" });

		var $computerDiv = $("<div id='computer'></div>");
		var $computerImg = $("<img src='ui/config/res/images/computer.png'>");
		$computerDiv.append($computerImg);
		$computerImg.draggable({zIndex:100, revert: "invalid", helper: "clone", cursor: "move" });
		
		$machinesDiv.append($routerDiv);
		$machinesDiv.append($switchDiv);
		$machinesDiv.append($computerDiv);
		
		$mainView.append($machinesDiv);
	}
	
	function createCanvas() {
		$canvas = $("<div id='canvas'></div>");
		$mainView.append($canvas);
		
		$canvas.droppable({
			activeClass: "ui-state-hover",
			hoverClass: "ui-state-active",
			accept: "#machines div img",
			drop: function( event, ui ) {
				var src = ui.draggable.context.src;
				var left = ui.offset.left - $("#canvas").offset().left;
				var top = ui.offset.top - $("#canvas").offset().top;
				if(src.indexOf("router") != -1) {
					routerCount++;
					id = "R" + routerCount;
					$device = new Router(id, left, top);
				}
				else if(src.indexOf("switch") != -1) {
					switchCount++;
					id = "SW" + switchCount;
					$device = new Switch(id, left, top);
				}
				else if(src.indexOf("computer") != -1) {
					computerCount++;
					id = "C" + computerCount;
					$device = new Computer(id, left, top);
				}

				$(this).append($device);
				addEndPoints(id);
		    }
		});    

	}
	
	function createTabs() {
		var $tabsDiv = $("<div class='tabs' id='tabs-module'></div>");
		
		var $imgTab = $("<a id='tab-Img' href='#' class='sel'>Img</a>");
		var $xmlTab = $("<a id='tab-xml' href='#' class=''>Xml</a>");
		
		$tabsDiv.append($imgTab);
		$tabsDiv.append($xmlTab);
		
		$contentDiv.append($tabsDiv);
	}
	$contentDiv.height(window.outerHeight - 170);
	return $contentDiv;
};
