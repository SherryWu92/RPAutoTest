includeCSS("ui/config/res/LogDialog.css");

function LogDialog(p_title, p_logContent) {
	var $logDialog = $('<div class="log"></div>');
	var $maskingDiv = null;
	init();
	
	function init() {
		createTitle();
		createLogView();
		createOperations();
		createMask();
	}
	
	function createTitle() {
		var $title = $("<span class='title'>" + p_title + "</span>");
		$logDialog.append($title);
	}
	
	function createLogView() {
		var $logView = $('<div id="logView"></div>');
		$logView.html(p_logContent ? p_logContent : "No run log");
		$logDialog.append($logView);
	}
	
	function createOperations() {
		var $operations = $('<div class="operation"></div>');
		
		var $close = $('<a href="#">Close</a>');	
		$operations.append($close);
		
		$logDialog.append($operations);		
		
		$close.click(function(){
			$logDialog.remove();
			$maskingDiv.remove();
		});
		
	}
	
	function createMask() {
        $maskingDiv = $("<div></div>");
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
	
	return $logDialog;
}
