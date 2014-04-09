/**
 * 
 */
includeJS("ui/common/view/Header.js");
includeJS("ui/config/view/Content.js");

window.onload = function () {
	jsPlumb.ready(function() {
		includeJS("ui/config/view/Connector.js");
		init();
	});
};

function init() {
	var header = new Header("Router Protocal Configuration");
	var content = new Content();
	var body = $("body");
	body.append(header);
	body.append(content);
}

