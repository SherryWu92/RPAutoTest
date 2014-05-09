includeCSS("ui/common/res/Home.css");

includeJS("ui/runLog/view/RunLogView.js");
includeJS("ui/testLog/view/TestLogView.js");

var $homeDiv = null;
function Home(p_configView) {
    $homeDiv = $('<div class="home"></div>');           
    var $contentDiv = null;
    var $navDiv = null;
    var $showViewDiv = null;
    var imgs = [];
	init();
	
	var that = this;
	
	function init() {
		createHeader();
		createContent();
		createNav();
		createShowView();
	}
	
	function createHeader() {
		var $HeaderDiv = $('<div class="header"><span class="title">Router Protocol Auto Configuration and Test</span></div>');
		$homeDiv.append($HeaderDiv);
	}
		
	function createContent() {
		$contentDiv = $('<div class="content"></div>');		
		$homeDiv.append($contentDiv);
	}
	
	function createNav() {
		$navDiv = $('<div class="nav"></div>');
		var $configDiv = $('<div id="config">Configuration</div>');
		var $runLogDiv = $('<div id="runLog">Running Log</div>');
		var $testLogDiv = $('<div id="testLog">Test Log</div>');
		$navDiv.append($configDiv);
		$navDiv.append($runLogDiv);
		$navDiv.append($testLogDiv);
		
		$contentDiv.append($navDiv);
		
		$configDiv.click(function() {
			that.hide();
			p_configView.show(that);
		});
		
		$runLogDiv.click(function() {
			that.hide();
			var runLogView = new RunLogView();
			runLogView.show(that);
		});
		
		$testLogDiv.click(function() {
			that.hide();
			var testLogView = new TestLogView();
			testLogView.show(that);
		});
		
		$configDiv.mouseover(function() {
			$("#showView").remove();
			$showViewDiv.append(imgs[0]);			
		});
		
		$runLogDiv.mouseover(function() {
			$("#showView").remove();
			$showViewDiv.append(imgs[1]);			
		});
		
		$testLogDiv.mouseover(function() {
			$("#showView").remove();
			$showViewDiv.append(imgs[2]);			
		});
	}
	
	function createShowView() {
		$showViewDiv = $('<div class="showView"></div>');
		
		var $img_topo = $("<img id='showView' src='ui/common/res/images/topology.png'>");
		var $img_run = $("<img id='showView' src='ui/common/res/images/topology1.png'>"); 
		var $img_test = $("<img id='showView' src='ui/common/res/images/topology.png'>");
		imgs.push($img_topo);
		imgs.push($img_run);
		imgs.push($img_test);
		
		$showViewDiv.append(imgs[0]);			
		$contentDiv.append($showViewDiv);
	}

}

Home.prototype.hide = function() {
	$homeDiv.detach();
};

Home.prototype.show = function() {
	$('body').append($homeDiv);
};