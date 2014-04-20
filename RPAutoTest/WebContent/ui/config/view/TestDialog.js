includeCSS("ui/config/res/TestDialog.css");

function TestDialog(p_connInfo) {
	var $testDialog = $('<div class="test"></div>');
	var $cmdsDiv = $('<div class="cmds"></div>');
	
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
		var $resultView = $('<div class="resultView">Show ip int</div>');
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
			
			p_connInfo.testCmds = [];
			p_connInfo.testCmds.push(testCmds);
			localStorage.setItem(p_connInfo.id, JSON.stringify(p_connInfo));
//			$testDialog.remove();
//			$("#masking").remove();	
//			$("#titleTabs").remove();
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