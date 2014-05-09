/**
 * 
 */
includeJS("ui/common/view/Header.js");
includeJS("ui/config/view/ConfigContent.js");

var configHeader = null;
var configContent = null;

function ConfigureView() {
	
	jsPlumb.ready(function() {
		includeJS("ui/config/view/Connector.js");
		localStorage.clear();
		configHeader = new Header("Router Protocol Configuration");
		configContent = new ConfigContent();
	});
}


ConfigureView.prototype.hide = function() {
	configHeader.detach();
	configContent.detach();
};

ConfigureView.prototype.show = function(p_homeView) {
	var body = $("body");
	body.append(configHeader);
	body.append(configContent);
	
	var that = this;
	$("#home").click(function() {
		that.hide();
		p_homeView.show();
	});
	
};