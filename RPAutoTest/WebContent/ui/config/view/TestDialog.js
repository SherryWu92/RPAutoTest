includeCSS("ui/config/res/TestDialog.css");

function TestDialog() {
	var $testDialog = $('<div class="test"></div>');
	
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
		var $cmdsDiv = $('<div class="cmds"></div>');

		var $addImg = $('<img src="ui/config/res/images/add.png">');
		var $addHoverImg = $('<img src="ui/config/res/images/addHover.png">');
		var $deleteImg = $('<img src="ui/config/res/images/delete.png">');
		var $deleteHoverImg = $('<img src="ui/config/res/images/deleteHover.png">');
		
		var $cmdDiv1 = $('<div class="cmd"></div>');
		var $input = $('<input type="text"/>');				
		$cmdDiv1.append($input);
		$cmdDiv1.append($deleteImg);
		
		var $cmdDiv2 = $('<div class="cmd"></div>');
		var $input = $('<input type="text"/>');				
		$cmdDiv2.append($input);
		$cmdDiv2.append($addImg);			
		
		$cmdsDiv.append($cmdDiv1);
		$cmdsDiv.append($cmdDiv2);
		$testDialog.append($cmdsDiv);
		
		$addImg.mouseover(function() {
			$addImg.detach();
			$cmdDiv2.append($addHoverImg);	
		});
		
		$addHoverImg.mouseout(function() {
			$addHoverImg.detach();
			$cmdDiv2.append($addImg);	
		});
		
		$deleteImg.mouseover(function() {
			$deleteImg.detach();
			$cmdDiv1.append($deleteHoverImg);	
		});
		
		$deleteHoverImg.mouseout(function() {
			$deleteHoverImg.detach();
			$cmdDiv1.append($deleteImg);	
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
		var $cancel = $('<a href="#">Cancel</a>');
		var $delete = $('<a href="#">Delete</a>');		
		$operations.append($run);
		$operations.append($cancel);
		$operations.append($delete);
		
		$testDialog.append($operations);
		
		$run.click(function(){
			 
			$testDialog.remove();
			$("#masking").remove();	
			$("#titleTabs").remove();
		});
		
		$cancel.click(function(){
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