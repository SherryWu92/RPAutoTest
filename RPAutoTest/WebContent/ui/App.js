/**
 * 
 */
includeJS("ui/common/view/Home.js");
includeJS("ui/config/view/ConfigView.js");
//includeJS("ui/runLog/view/RunLogView.js");

window.onload = function () {
	var configView = new ConfigView();
//	var runLogView = new RunLogView();
	
	var homeView = new Home(configView);	
	homeView.show();	
};


//includeJS("ui/common/view/Header.js");
//includeJS("ui/config/view/Content.js");
//
//window.onload = function () {
//	jsPlumb.ready(function() {
//		includeJS("ui/config/view/Connector.js");
//		localStorage.clear();
//		init();
//	});
//};
//
//function init() {
//	var header = new Header("Router Protocol Configuration");
//	var content = new Content();
//	var body = $("body");
//	body.append(header);
//	body.append(content);	
//}


