<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:masterpage>
<jsp:attribute name="body">
    <script src="https://unpkg.com/gojs/release/go-debug.js"></script>

    <div class="jumbotron">
        <h1>The Food Web</h1>
    </div>

    <div id="foodWebDiagram" style="width: 100%; height: 700px"></div>
    <script>
        // Creates static food web diagram
        function init() {
            let $ = go.GraphObject.make;

            let foodWebDiagram =
                $(go.Diagram, "foodWebDiagram", // must be the ID or reference to div
                    {
                        initialAutoScale: go.Diagram.UniformToFill,
                        padding: 10,
                        contentAlignment: go.Spot.Center,
                        layout: $(go.ForceDirectedLayout, { defaultSpringLength: 20 })
                    });

            foodWebDiagram.nodeTemplate =
                $(go.Node, "Auto",
                    $(go.Shape,
                        {
                            figure: "Ellipse",
                            fill: "white",
                            minSize: new go.Size(60, 60)
                        },
                        new go.Binding("fill", "color"),
                        new go.Binding("desiredSize", "size")
                    ),

                    $(go.TextBlock,
                        {
                            margin: 5,
                            wrap: go.TextBlock.WrapFit,
                            textAlign: "center"
                        },
                        new go.Binding("text", "text"))
                );

            // create the model for the concept map
            let nodeDataArray = [
                {key: 1, text: "Grass", color: "green", size: new go.Size(60, 60) },
                {key: 2, text: "Rabbit", color: "turquoise", size: new go.Size(60, 60) },
                {key: 3, text: "Insect", color: "turquoise", size: new go.Size(60, 60) },
                {key: 4, text: "Snail", color: "turquoise", size: new go.Size(60, 60) },
                {key: 5, text: "Frog", color: "lightblue", size: new go.Size(65, 65) },
                {key: 6, text: "Vole", color: "lightsalmon", size: new go.Size(65, 65) },
                {key: 7, text: "Thrush", color: "salmon", size: new go.Size(70, 70) },
                {key: 8, text: "Hawk", color: "indianred", size: new go.Size(75, 75) },
                {key: 9, text: "Fox", color: "indianred", size: new go.Size(80, 80) },
            ];

            let linkDataArray = [
                {from: 1, to: 2},
                {from: 1, to: 3},
                {from: 1, to: 4},
                {from: 2, to: 9},
                {from: 3, to: 5},
                {from: 3, to: 6},
                {from: 3, to: 7},
                {from: 4, to: 7},
                {from: 5, to: 8},
                {from: 5, to: 9},
                {from: 6, to: 8},
                {from: 6, to: 9},
                {from: 7, to: 8},
                {from: 7, to: 9}
            ];

            foodWebDiagram.model = new go.GraphLinksModel(nodeDataArray, linkDataArray);
        }

        init();
    </script>
    
</jsp:attribute>
</my:masterpage>