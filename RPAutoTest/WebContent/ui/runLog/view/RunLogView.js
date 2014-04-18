includeJS("ui/common/view/Header.js");
includeJS("ui/runLog/view/RunLogContent.js");

var runLogheader = null;
var runLogContent = null;

function RunLogView() {		
	runLogheader = new Header("Run Log");
	runLogContent = new RunLogContent();	
}

RunLogView.prototype.hide = function() {
	runLogheader.detach();
	runLogContent.detach();
};

RunLogView.prototype.show = function(p_homeView) {
	var body = $("body");
	body.append(runLogheader);
	body.append(runLogContent);
	
	var that = this;
	$("#home").click(function() {
		that.hide();
		p_homeView.show();
	});
	
};