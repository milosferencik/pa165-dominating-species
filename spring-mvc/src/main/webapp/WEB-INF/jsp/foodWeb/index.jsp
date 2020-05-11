<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false"%>

<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<my:masterpage title="Food Web">
    <jsp:attribute name="body">
        <script src="https://unpkg.com/gojs/release/go-debug.js" ></script>

        <div class="jumbotron">
            <h1>Food Web of All Data</h1>
        </div>

        <div id="foodWebDiagram" style="width: 100%; height: 700px"></div>

        <script>
            // Sorry, first time writing some JavaScript
            function init() {
                let $ = go.GraphObject.make;  // prepare diagram

                // setup graph template, bind to html div element
                let foodWebDiagram =
                    $(go.Diagram, "foodWebDiagram", // must be the ID or reference to div
                        {
                            initialAutoScale: go.Diagram.UniformToFill,
                            padding: 10,
                            contentAlignment: go.Spot.Center,
                            layout: $(go.ForceDirectedLayout, { defaultSpringLength: 20 })
                        });

                // setup graph node template
                foodWebDiagram.nodeTemplate =
                    $(go.Node, "Auto",
                        $(go.Shape,
                            {
                                figure: "Ellipse",
                                fill: "white",
                                minSize: new go.Size(40, 40)
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
                            new go.Binding("text", "text")
                        )
                    );

                var foodWeb = getFoodWeb();
                var predatorLevelDict = {}; // predator level, eats vs. eaten

                var nodeDataArray = [];
                var linkDataArray = [];

                // setup node links and predator level
                foodWeb.forEach(predator => {
                    predator.preyIds.forEach(preyId => {
                        linkDataArray.push({ from: preyId, to: predator.id});
                        predatorLevelDict[predator.id] = (predatorLevelDict[predator.id] || 0) + 1;
                        predatorLevelDict[preyId] = (predatorLevelDict[preyId] || 0) - 1;
                    });
                });

                // setup nodes
                foodWeb.forEach(animal => {
                    var predatorLevel = predatorLevelDict[animal.id];

                    var color = "white";        // default color, color judged by predator level
                    if (predatorLevel > 3) {
                       color = "red";
                    } else if (predatorLevel > 0) {
                        color = "orange";
                    } else if (predatorLevel <= 0) {
                        color = "lightblue";
                    } else if (predatorLevel < -3) {
                        color = "green";
                    }

                    var size = 50 + 10 * animal.preyIds.length;    // default size = 50, judged by count of prey
                    if (size > 120) {
                        size = 120;
                    }
                    nodeDataArray.push({key: animal.id, text: animal.name, color: color, size: new go.Size(size, size)});
                });

                // build graf
                foodWebDiagram.model = new go.GraphLinksModel(nodeDataArray, linkDataArray);
            }

            // generates food web data representation in JS
            function getFoodWeb() {
                return [
                    <c:forEach items="${foodWeb.foodWeb}" var="animal">
                    {
                        id: ${animal.key.id},
                        name: "${animal.key.name}",
                        preyIds:  [
                            <c:forEach items="${animal.value}" var="prey">
                                ${prey.id},
                            </c:forEach>
                            ]
                    },
                </c:forEach>
                ];
            }

            init();
        </script>
    </jsp:attribute>
</my:masterpage>
