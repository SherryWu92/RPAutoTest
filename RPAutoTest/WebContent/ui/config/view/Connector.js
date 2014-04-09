 
    jsPlumb.importDefaults({
        DragOptions : { cursor: 'pointer', zIndex:2000 },
        EndpointStyle : { width:20, height:16, strokeStyle:'#666' },
        Endpoint : "Rectangle"
    });

    var color1 = "#225588";
    var exampleEndpoint1 = {
        endpoint:["Dot", { radius:3 }],
        paintStyle:{ fillStyle:color1 },
        isSource:true,
        scope:"green dot",
        connectorStyle:{ strokeStyle:color1, lineWidth:2 },
        connector: ["Straight", { curviness:63 } ],
        maxConnections:1,
        isTarget:true
    };    

    function addEndPoints(toId, anchors) {
    	if(anchors == null) {
    	    anchors = [[0.7, 0, -1, 0], [1, 0.5, 0, 1], [0.7, 1, -1, 0], [ 0.4, 0.5, 0, 1 ] ];//["TopCenter", "RightMiddle", "BottomCenter", "LeftMiddle"];
    	}
        for (var i = 0; i < anchors.length; i++) {
            jsPlumb.addEndpoint(toId, { anchor:anchors[i]}, exampleEndpoint1);                       
        }
        jsPlumb.draggable(jsPlumb.getSelector(".device"), {containment: "#canvas"});   
    }
 