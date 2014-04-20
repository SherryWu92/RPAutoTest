includeJS("ui/common/view/Header.js");
includeJS("ui/testLog/view/TestLogContent.js");

var testLogheader = null;
var testLogContent = null;

function TestLogView() {		
	testLogHeader = new Header("Run Log");
	testLogContent = new TestLogContent();	
}

TestLogView.prototype.hide = function() {
	testLogHeader.detach();
	testLogContent.detach();
};

TestLogView.prototype.show = function(p_homeView) {
	var body = $("body");
	body.append(testLogHeader);
	body.append(testLogContent);
	
	var that = this;
	$("#home").click(function() {
		that.hide();
		p_homeView.show();
	});
	
};