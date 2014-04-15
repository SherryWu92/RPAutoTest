includeCSS("ui/common/res/Home.css");

var $homeDiv = null;
function Home(p_configView) {
    $homeDiv = $('<div class="home"></div>');
	init();
	
	function init() {
		createHeader();
		createContent();
	}
	
	function createHeader() {
		var $HeaderDiv = $('<div class="header"><span class="title">Router Protocal Auto Configuration and Test</span></div>');
		$homeDiv.append($HeaderDiv);
	}
	
	function createContent() {
		var $contentDiv = $('<div class="content"></div>');
		
		var $configDiv = $('<div id="config">Configuration</div>');
		var $runLogDiv = $('<div id="runLog">Running Log</div>');
		var $testLogDiv = $('<div id="testLog">Test Log</div>');
		
		$contentDiv.append($configDiv);
		$contentDiv.append($runLogDiv);
		$contentDiv.append($testLogDiv);
		
		$homeDiv.append($contentDiv);
		
		$configDiv.click(function() {
			$homeDiv.detach();
			p_configView.show();
		});
		
		$runLogDiv.click(function() {
//			$homeDiv.detach();
			console.debug("Show Run Log View");
		});
		
		$testLogDiv.click(function() {
//			$homeDiv.detach();
			console.debug("Show Test Log View");
		});
	}
	
//	return $homeDiv;	
}

Home.prototype.hide = function() {
	$homeDiv.detach();
};

Home.prototype.show = function() {
	$('body').append($homeDiv);
};