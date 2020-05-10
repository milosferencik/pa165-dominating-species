<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:masterpage>
<jsp:attribute name="body">
    <script src="https://unpkg.com/gojs/release/go-debug.js"></script>

    <div class="jumbotron" onload="init()">
        <h1>The Food Web!</h1>
    </div>

    <div id="foodWebDiagram" style="background-color: whitesmoke; border: solid 1px black; width: 100%; height: 700px"></div>
    <script>
        function init() {
            let $ = go.GraphObject.make;  // for conciseness in defining templates

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
                        { figure: "Circle",
                            fill: "white" },
                        new go.Binding("fill", "color"),
                        new go.Binding("desiredSize", "size", go.Size.parse)
                    ),

                    $(go.TextBlock,
                        { margin: 5 },
                        new go.Binding("text", "text"))  // binding to get TextBlock.text from nodedata.key
                );

            // create the model for the concept map
            let nodeDataArray = [
                {key: 1, text: "Grass", color: "green", size: "5 5" },
                {key: 2, text: "Rabbit", color: "lightblue", size: "7 7" },
                {key: 3, text: "Insect", color: "lightblue", size: "7 7" },
                {key: 4, text: "Snail", color: "lightblue", size: "7 7" },
                {key: 5, text: "Frog", color: "lightblue", size: "5 5" },
                {key: 6, text: "Vole", color: "lightblue", size: "9 9" },
                {key: 7, text: "Thrush", color: "orange", size: "9 9" },
                {key: 8, text: "Hawk", color: "red", size: "9 9" },
                {key: 9, text: "Fox", color: "red", size: "12 12" },
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

    </script>


</jsp:attribute>
</my:masterpage>