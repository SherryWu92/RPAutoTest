includeCSS("ui/config/res/TestDialog.css");

includeJS("ui/common/service/ServiceClient.js");

function TestDialog(p_connInfo) {
	var $testDialog = $('<div class="test"></div>');
	var $cmdsDiv = $('<div class="cmds"></div>');
	var $resultView = null;
	
	init();
	
	function init() {
		createCmdLabel();
		createCmds();
		createResultLabel();
		createResultView();
		createOperations();
	}
	
	function createCmdLabel() {
		var $cmdLabel = $('<label class="cmdLabel">Cmds</label>');
		$testDialog.append($cmdLabel);
	}
	
	function createCmds() {
		var testCmds = p_connInfo.testCmds;
		for(var i = 0; i < testCmds.length -1; i++) {
			createACmd(testCmds[i]);
		}
		var cmd = "";
		if(testCmds.length >= 1) {
			cmd = testCmds[testCmds.length -1];
		}		
//		var $cmdsDiv = $('<div class="cmds"></div>');

		var $addImg = $('<img class="add" src="ui/config/res/images/add.png">');
		var $addHoverImg = $('<img class="addHover" src="ui/config/res/images/addHover.png">');

		var $cmdDiv2 = $('<div class="cmd"></div>');
		var $input = $('<input class="cmdInput" type="text"/>');	
		$input.val(cmd);
		$cmdDiv2.append($input);
		$cmdDiv2.append($addImg);			
		
		$cmdsDiv.append($cmdDiv2);
		$testDialog.append($cmdsDiv);
		
		$addHoverImg.click(function() {
			var $deleteImg = $('<img class="delete" src="ui/config/res/images/delete.png">');
			var $deleteHoverImg = $('<img class="deleteHover" src="ui/config/res/images/deleteHover.png">');
			$(this).parent().append($deleteImg);
			$(this).detach();
			
			var _$cmdDiv = $('<div class="cmd"></div>');
			var _$input = $('<input class="cmdInput" type="text"/>');		
			_$cmdDiv.append(_$input);
			_$cmdDiv.append($addImg);			
			$cmdsDiv.append(_$cmdDiv);
			
			$testDialog.height($testDialog.height() + _$cmdDiv.height());
			
			$deleteImg.mouseover(function() {
				$(this).parent().append($deleteHoverImg);
				$(this).detach();
			});
			
			$deleteHoverImg.mouseout(function() {
				$(this).parent().append($deleteImg);
				$(this).detach();
			});
			
			$deleteHoverImg.click(function() {
				$testDialog.height($testDialog.height() - $(this).parent().height());
				$(this).parent().remove();				
			});
		});
					
		$addImg.mouseover(function() {
			$(this).parent().append($addHoverImg);
			$(this).detach();
		});
		
		$addHoverImg.mouseout(function() {
			$(this).parent().append($addImg);
			$(this).detach();
		});
	}
	
	function createACmd(cmd) {
		var $cmdDiv = $('<div class="cmd"></div>');
		var $deleteImg = $('<img class="delete" src="ui/config/res/images/delete.png">');
		var $deleteHoverImg = $('<img class="deleteHover" src="ui/config/res/images/deleteHover.png">');
		
		var $input = $('<input class="cmdInput" type="text" />');	
		$input.val(cmd);
		$cmdDiv.append($input);
		$cmdDiv.append($deleteImg);			
		$cmdsDiv.append($cmdDiv);
 		
		$testDialog.height($testDialog.height() + $cmdDiv.height());
		
		$deleteImg.mouseover(function() {
			$(this).parent().append($deleteHoverImg);
			$(this).detach();
		});
		
		$deleteHoverImg.mouseout(function() {
			$(this).parent().append($deleteImg);
			$(this).detach();
		});
		
		$deleteHoverImg.click(function() {
			$testDialog.height($testDialog.height() - $(this).parent().height());
			$(this).parent().remove();				
		});
	}
	
	function createResultLabel() {
		var $resultsLabel = $('<label class="resultsLabel">Results</label>');
		$testDialog.append($resultsLabel);
	}
	
	function createResultView() {
		$resultView = $('<div class="resultView"></div>');
		$testDialog.append($resultView);
	}
	
	function createOperations() {
		var $operations = $('<div class="operation"></div>');
		
		var $run = $('<a href="#">Run</a>');
		var $close = $('<a href="#">Close</a>');
		var $delete = $('<a href="#">Delete</a>');		
		$operations.append($run);
		$operations.append($close);
		$operations.append($delete);
		
		$testDialog.append($operations);
		
		$run.click(function(){
			var testCmds = [];
			$(".cmdInput").each(function() {
				var cmd = $(this).val();
				if(cmd != null) {
					testCmds.push(cmd);
				}
			}); 
			
			p_connInfo.testCmds = testCmds;
			localStorage.setItem(p_connInfo.id, JSON.stringify(p_connInfo));
			
			var testInfo = {};
			testInfo.id = p_connInfo.id;
			testInfo.testCmds = testCmds;
			
			ServiceClient.invoke("test/testResults", testInfo).done(function(p_results){
				var a_log = p_results;
				var id = a_log.id;
				var logStr = a_log.log;
				logStr = logStr.replace(/\/r\/n/g,"<br>");
				console.debug(logStr);
				var testLog = JSON.parse(localStorage.getItem("TestLog"));
				if(testLog == null) {
					testLog = {};
				}
				testLog[id] = logStr;	
				localStorage.setItem("TestLog", JSON.stringify(testLog));
				
				$resultView.html(logStr);
			});			

		});
		
		$close.click(function(){
			$testDialog.remove();
			$("#masking").remove();
			$("#titleTabs").remove();
		});
		
		$delete.click(function(){
			var id = connInfo.id;
			localStorage.removeItem(id);
			jsPlumb.detachAllConnections(id);
			jsPlumb.removeAllEndpoints(id);
			$("#" + id).remove();
			$testDialog.remove();
			$("#masking").remove();	
			$("#titleTabs").remove();
		});
	}
	
	return $testDialog;
}