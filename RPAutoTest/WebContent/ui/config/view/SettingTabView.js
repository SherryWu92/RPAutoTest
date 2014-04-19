includeCSS("ui/config/res/SettingTabView.css");

function SettingTabView(p_configView, p_testView) {
	var $SettingTabView = $('<div></div>');
	var $maskingDiv = null;
	init();
	
	function init() {
		createMask();
		createTabs();
		addConfigView();
	}
	
	function createTabs() {
		var $tabs = $('<div id="titleTabs"></div>');
		
		var $configTab = $('<a href="#" id="tab-config" class="sel">Config</a>');
		var $testTab = $('<a href="#"  id="tab-test"  class="">Test</a>');		
		$tabs.append($configTab);
		$tabs.append($testTab);
		
		$SettingTabView.append($tabs);
		
		$configTab.click(function() {
			$SettingTabView.append(p_configView);
			p_testView.detach();
			$(this).addClass("sel");
			$("#tab-test").removeClass("sel");
		});
		
		$testTab.click(function() {
			$SettingTabView.append(p_testView);
			p_configView.detach();
			$(this).addClass("sel");
			$("#tab-config").removeClass("sel");
		});
	}
	
	function addConfigView() {
		$SettingTabView.append(p_configView);
	}
	
	function createMask() {
        $maskingDiv = $("<div id='masking'></div>");
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
	
	return $SettingTabView;
}