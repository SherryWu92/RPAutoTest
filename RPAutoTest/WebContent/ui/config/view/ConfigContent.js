includeCSS("ui/config/res/ConfigContent.css");

includeJS("ui/common/service/ServiceClient.js");
includeJS("ui/common/view/FileHandler.js");
includeJS("ui/common/view/LogDialog.js");
includeJS("ui/config/view/Router.js");
includeJS("ui/config/view/Switch.js");

function ConfigContent() {
	var $contentDiv = $("<div id='configContent' class='configContent'></div>");
	var $imgView = null;
	var $canvas = null;
	var $xmlArea = null;
	var routerCount = 0;
	var switchCount = 0;
	init();
	
	function init() {
		createMenu();
		createMainView();
		createMachinesView();
		createCanvas();
		createXmlArea();
		createTabs();
	}
	
	function createMenu() {
		var $menuUl = $("<ul id='menu'></ul>");
		
		var $importLi = $("<li><a href='#' id='menu-import' class=''>Import</a></li>");
		var $saveLi = $("<li><a href='#' id='menu-save' class=''>Save</a></li>");
		var $configureLi = $("<li><a href='#' id='menu-configure' class=''>Configure</a></li>");
		var $testLi = $("<li><a href='#' id='menu-test' class=''>Test</a></li>");
		var $clearLi = $("<li><a href='#' id='menu-clear' class=''>Clear</a></li>");
		
		$menuUl.append($clearLi);
		$menuUl.append($testLi);
		$menuUl.append($configureLi);
		$menuUl.append($saveLi);
		$menuUl.append($importLi);

		$contentDiv.append($menuUl);				
		
		var $fileInput = $('<input type="file" style="display:none" id="file" name="file" accept="text/xml"/>');
		$menuUl.append($fileInput);
		$importLi.click(function() {
			$fileInput.trigger('click');			
		});
		
		$fileInput.change(function(evt) {
			handleFileSelect();
		});
		
		$saveLi.click(function() {
			var protocalInfo = getProtocalInfo();			
			console.debug(protocalInfo);
			ServiceClient.invoke("configure/saveXml", protocalInfo).done(function(p_results){
				console.debug(p_results);
				var fileContent = p_results.content;
				saveFile(fileContent, "text/xml", "protocal.xml"); 
			});
		});
		
		$configureLi.click(function() {
			var protocalInfo = getProtocalInfo();			
			console.debug(protocalInfo);
//			var runLog = [];
//			runLog.push({id:"R1", log: "#Router1"});
//			runLog.push({id:"R2", log: "#Router2"});
//			runLog.push({id:"R3", log: "#Router3"});
//			localStorage.setItem("RunLog", JSON.stringify(runLog));
			ServiceClient.invoke("configure/protocal", protocalInfo).done(function(p_results){
				console.debug(p_results);		
				var body = $('body');
				var $logDialog = new LogDialog("Run Log", p_results);
				body.append($logDialog);
			});			
		});
		
		$clearLi.click(function() {
			clearCanvas();
		});
		
	}
	
	function handleFileSelect() {  
	    var files = $("#file")[0].files;
	    if (!files.length) {
	      alert('Please select a file!');
	      return;
	    }

	    var file = files[0];
	    var start = 0;
	    var stop = file.size - 1;

	    var reader = new FileReader();
	    reader.onloadend = function(evt) {
	      if (evt.target.readyState == FileReader.DONE) { 
//	    	  showXmlView();
//	    	  $("#xmlArea").text(evt.target.result);	    	  
	    	  var xmlInfo = {};
	    	  xmlInfo.content = evt.target.result;
	    	  ServiceClient.invoke("configure/parseXML", xmlInfo).done(function(p_results){
	    		  console.debug(p_results);
	    		  mapToImgView(p_results);
	    	  });
	      }
	    };

	    var blob = null;
	    if (file.webkitSlice) {
	      blob = file.slice(start, stop + 1);
	    } else if (file.mozSlice) {
	      blob = file.mozSlice(start, stop + 1);
	    }
	    reader.readAsBinaryString(blob);
	}
	
	function mapToImgView(p_connInfo) {
//		var connInfo = {"routers":[{"connections":[{"area":"","ipAddress":"20.0.0.2","network":"20.0.0.1","port":"s0/1","submask":"255.0.0.0","target":"R2"}],"id":"R1","password":"","physicalIp":""},{"connections":[{"area":"","ipAddress":"20.0.0.2","network":"20.0.0.0","port":"s0/1","submask":"255.0.0.0","target":"R3"},{"area":"","ipAddress":"20.0.0.3","network":"20.0.0.0","port":"s0/2","submask":"255.0.0.0","target":"R1"}],"id":"R2","password":"1234","physicalIp":"192.168.2.1"},{"connections":[{"area":"","ipAddress":"20.0.0.4","network":"20.0.0.0","port":"s0/1","submask":"255.0.0.0","target":"R2"}],"id":"R3","password":"1234","physicalIp":"192.168.3.1"}],"switches":[],"type":"rip"};
		var connInfo = p_connInfo;
		//step 1.clear
		showImgView();
		clearCanvas();
		//step 2.draw step 3.localStorage
		var routers = connInfo.routers;
		for(var i = 0; i < routers.length; i++) {
			routerCount++;			
			var id = "R" + routerCount;
			var left = ($canvas.width() - 240) * Math.random() + 120;
			var top = ($canvas.height() - 150) * Math.random() + 75;			
			var $router = new Router(id, left, top);
			$canvas.append($router);
			addEndPoints(id);
			
			localStorage.setItem(id, JSON.stringify(routers[i]));
		}
		var switches = connInfo.switches;
		for(var i = 0; i < switches.length; i++) {
			switchCount++;			
			var id = "SW" + switchCount;
			var left = ($canvas.width() - 200) * Math.random() + 100;
			var top = ($canvas.height() - 120) * Math.random() + 60;			
			var $switch = new Switch(id, left, top);
			$canvas.append($switch);
			addEndPoints(id);
			
			localStorage.setItem(id, switches[i]);
		}
		
		//step 4.connect
		for(var i in routers) {
			var router = routers[i];
			var conns = router.connections;
			for(var j in conns) {
				var conn = conns[j];
				connectEndPoints(router.id, conn.target);
			}
		}
		
		for(var i in switches) {
			var _switch = switches[i];
			var conns = _switch.connections;
			for(var j in conns) {
				var conn = conns[j];
				connectEndPoints(_switch.id, conn.target);
			}
		}
	}
	
	function clearCanvas() {
		routerCount = 0;
		switchCount = 0;
		computerCount = 0;
		jsPlumb.reset();
		$canvas.empty();
		localStorage.clear();		
	}
	
	function getProtocalInfo() {
		var protocalInfo = {};
		protocalInfo.type = "rip";
		protocalInfo.routers = [];
		protocalInfo.switches = [];
		if(localStorage.getItem("Protocal") != null) {
			protocalInfo.type = localStorage.getItem("Protocal");
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
		
		return protocalInfo;
	}
	
	function createMainView() {
		$imgView = $("<div class='mainView'></div>");
		$contentDiv.append($imgView);
		
		$xmlView = $("<div class='mainView'></div>");		
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
		
		$machinesDiv.append($routerDiv);
		$machinesDiv.append($switchDiv);
		
		$imgView.append($machinesDiv);
	}
	
	function createCanvas() {
		$canvas = $("<div id='canvas'></div>");
		$imgView.append($canvas);
		
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

				$(this).append($device);
				addEndPoints(id);
		    }
		});    

	}
	
	function createXmlArea() {
		$xmlArea = $("<textArea id='xmlArea'></textArea>");
		$xmlView.append($xmlArea);
	}
	
	function createTabs() {
		var $tabsDiv = $("<div class='tabs' id='tabs-module'></div>");
		
		var $imgTab = $("<a id='tab-Img' href='#' class='sel'>Img</a>");
		var $xmlTab = $("<a id='tab-xml' href='#' class=''>Xml</a>");
		
		$tabsDiv.append($imgTab);
		$tabsDiv.append($xmlTab);
		
		$contentDiv.append($tabsDiv);
		
		$imgTab.click(function() {
			showImgView();
		});
		
		$xmlTab.click(function() {
			showXmlView();
		});
	}
	
	function showImgView() {
		$("#tab-Img").addClass("sel");
		$("#tab-xml").removeClass("sel");
		$xmlView.detach();
		$contentDiv.append($imgView);
	}
	
	function showXmlView() {
		$("#tab-xml").addClass("sel");
		$("#tab-Img").removeClass("sel");
		$imgView.detach();
		$contentDiv.append($xmlView);
	}
	
	$contentDiv.height(window.outerHeight - 170);
	return $contentDiv;
};
