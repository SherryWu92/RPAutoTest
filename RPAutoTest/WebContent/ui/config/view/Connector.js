// 	jsPlumb.importDefaults({
//        DragOptions : { cursor: 'pointer', zIndex:2000 },
//        EndpointStyle : { width:20, height:16, strokeStyle:'#666' },
//        Endpoint : "Rectangle"
//    });

    var color = "#225588";
    var anchors = [[0.7, 0, -1, 0], [1, 0.5, 0, 1], [0.7, 1, -1, 0], [ 0.4, 0.5, 0, 1 ]];//["TopCenter", "RightMiddle", "BottomCenter", "LeftMiddle"];
    var exampleEndpoint = {
        endpoint:["Dot", { radius:4 }],
        paintStyle:{ fillStyle:color },
        isSource:true,
        connectorStyle:{ strokeStyle:color, lineWidth:2 },
        connector: ["Straight", { curviness:63 } ],
        maxConnections:1,
        isTarget:true
    };    

    function addEndPoints(p_toId, p_anchors) {
    	if(p_anchors != null) {
    	    anchors = p_anchors;
    	}
        for (var i = 0; i < anchors.length; i++) {
            jsPlumb.addEndpoint(p_toId, { anchor:anchors[i]}, exampleEndpoint);                       
        }
        jsPlumb.draggable(jsPlumb.getSelector(".device"), {containment: "#canvas"});         
    }
 
    function connectEndPoints(sourceId, targetId, sPointIndex, tPointIndex) {
    	var sConns1 = jsPlumb.getConnections({source: sourceId});
    	var sConns2 = jsPlumb.getConnections({target: sourceId});
    	
    	var tConns1 = jsPlumb.getConnections({source: targetId});
    	var tConns2 = jsPlumb.getConnections({target: targetId});
    	
		for(var i in tConns1) {			
			if(sourceId == tConns1[i].targetId) {
				return;
			}
		}		
				
		var sPointIndex = sConns1.length + sConns2.length;
		var tPointIndex = tConns1.length + tConns2.length;
    	jsPlumb.connect({
    		source: sourceId,
    		target: targetId,
    		anchors: [anchors[sPointIndex], anchors[tPointIndex]],
            endpoint:["Dot", { radius:4 }],
            paintStyle:{ strokeStyle:color, lineWidth:2  },
            connector: ["Straight", { curviness:63 } ]           
    	});
    }