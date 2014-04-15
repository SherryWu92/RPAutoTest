/**
 * 
 */
includeJS("ui/common/view/Header.js");
includeJS("ui/config/view/Content.js");

var header = null;
var content = null;

function ConfigureView() {
	
	jsPlumb.ready(function() {
		includeJS("ui/config/view/Connector.js");
		localStorage.clear();
		header = new Header("Router Protocal Configuration");
		content = new Content();
	});
}


ConfigureView.prototype.hide = function() {
	header.detach();
	content.detach();
};

ConfigureView.prototype.show = function() {
	var body = $("body");
	body.append(header);
	body.append(content);
};