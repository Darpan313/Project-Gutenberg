<%--
  Created by IntelliJ IDEA.
  User: darpan
  Date: 2020-03-11
  Time: 12:15 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Project Gutenberg</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/table.css">
    <script src="scripts/jquery.min.js"></script>
    <script src="scripts/jquery-ui.min.js"></script>
    <script src="scripts/datatable.js"></script>
</head>
<body>
<script>
    $(function () {
        $("#submit").click(function () {
            $("#noteText").empty();
            $("#resultNote").empty();
            $("#noteresult").css("display", "none");
            var today = new Date();
            $.ajax({
                url: 'http://ec2-35-173-247-4.compute-1.amazonaws.com:8081/saveLog',
                type: 'POST',
                data: JSON.stringify({
                    'time': today.getHours() + ":" + today.getMinutes(),
                    'keyword': $("#search").val()
                }),
                contentType: 'application/json',
                success: function (data) {
                    alert(data);
                }
            });
            $.ajax({
                url: 'http://ec2-35-173-247-4.compute-1.amazonaws.com:8082/searchResult',
                type: 'GET',
                data: {'keyword': $("#search").val()},
                contentType: 'application/json; charset=utf-8',
                success: function (data) {
                    if (data.length > 0) {
                        $("#note").css("display", "inline-block");
                    } else {
                        $("#note").css("display", "none");
                    }
                    var d = new Array();
                    for (var i = 0; i < data.length; i++) {
                        var din = new Array();
                        din.push(data[i].author)
                        din.push(data[i].title);
                        d.push(din);
                    }
                    $('#result').DataTable({
                        destroy: true,
                        data: d,
                        columns: [
                            {title: "Author"},
                            {title: "Title"},
                        ]
                    });
                }
            });
        });
        $("#addNote").click(function () {
            $("#noteText").empty();
            $("#resultNote").empty();
            $.ajax({
                url: 'http://ec2-35-173-247-4.compute-1.amazonaws.com:8083/addNote?key=' + $("#search").val(),
                type: 'POST',
                data: JSON.stringify({'note': $("#noteText").val()}),
                contentType: 'application/json',
                success: function (data) {
                    if (data == "Successful") {
                        alert("Note added!")
                        $("#noteText").empty();
                    } else {
                        alert("Something went wrong!");
                    }

                }
            });
        });

        $("#searchNote").click(function () {
            $("#noteText").empty();
            $("#resultNote").empty();
            $.ajax({
                url: 'http://ec2-35-173-247-4.compute-1.amazonaws.com:8083/findNote',
                type: 'GET',
                data: {'key': $("#search").val()},
                contentType: 'application/json',
                success: function (data) {
                    if (data.length > 0) {
                        $("#noteresult").css("display", "inline-block");
                    } else {
                        $("#noteresult").css("display", "inline-block");
                        $("#resultNote").append("No notes added yet!!");
                    }
                    for (var i = 0; i < data.length; i++) {
                        $("#resultNote").append("<" + (i + 1) + ">-" + data[i] + "\n_____________________________________________________________________________\n");
                    }
                }
            });
        });
    });
</script>
<h1 align="center">Project Gutenberg</h1>
<div class="wrap">
    <div class="search">
        <input id="search" type="text" class="searchTerm" placeholder="Type Author name or Book Title">
        <input id="submit" type="submit" class="searchButton">
        <input id="searchNote" type="submit" class="searchNote" style="display: block" value="Search notes">
    </div>
</div>
<br>
<br>
<table id="result" class="display" width="100%"></table>
<div style="display: inline">
    <div id="note" style="display: none;margin-left: 5px">
        <p style="margin-right: 34%"><b>Comments:</b></p>
        <textarea name="message" rows="8" cols="80" placeholder="Please add note here..." id="noteText"></textarea>
        <div>
            <input id="addNote" type="submit" style="margin-top: 5px">
        </div>
    </div>
    <div id="noteresult" style="float:right; display: none;margin-right: 5px">
        <p style="margin-right: 34%"><b>Notes:</b></p>
        <textarea name="message" rows="8" cols="80" placeholder="Result appears here..." id="resultNote"
                  style="overflow-y: scroll" wrap="hard" readonly></textarea>
    </div>
</div>
<div style="display: none">
    <ol id="textFromTextArea">
    </ol>
</div>
</body>
</html>
