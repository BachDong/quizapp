var answerDelete = [];


$(document).ready(function (e) {
    displaySubject();
    displayLevel();
    displaySubjectTab();
    fillData("/user/question/getAll", 5, 0, "DESC", "");


    $(document).on("keyup", "#txtSearchQuestionText", function () {
        var value = $(this).val().toLowerCase();
        $("#tbodyQuestion tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });


});
$(document).on("click", ".btnViewQuestion", function (e) {
    e.preventDefault();
    $("#modalView").modal('show');
    var trFisrt = $(this).closest("tr");
    var question_id = trFisrt.find("td:first");
    $.ajax({
        url: "/user/view-question",
        method: "Get",
        data: {"question_id": question_id.text()},
        statusCode: {
            200: function (result) {
                console.log(result)
                var viewAQuestion = '<div id="nameQuestion" style="margin-left: 45px;"><h2 style="font-style: italic"><i class="fa fa-paper-plane-o" aria-hidden="true"></i>&nbsp;<span style="color: #13a9c0">Question:&nbsp;&nbsp;</span> ' + result.questionText + '<i class="fa fa-question" aria-hidden="true"></i></h2>' +
                    '</div>'
                $("#detail-question").html(viewAQuestion);
                $.each(result.answers, function (index) {
                    var answer = '<h4 style=margin-left: 15px;">' + result.answers[index].sequence + '.&nbsp;' + result.answers[index].answerText + '</h3>';
                    if (result.answers[index].correctAnswer) {
                        answer = '<h4 style="color: #e8477e;"><i class="fa fa-hand-o-right" aria-hidden="true"></i>&nbsp;' + result.answers[index].sequence + '. &nbsp;' + result.answers[index].answerText + '</h3>';
                    }
                    $("#nameQuestion").append(answer);
                })

            },
            500: function () {

            }
        }
    })
})
$(document).on("click", "#createNewQuestion", function () {
    $("#exampleModalLabel").text("Create new question");
    $('#questionModal').modal('show');
    $("#updateQuestionNow").attr('id', 'btnAddQuestion');
    $("#btnAddQuestion").text("Create Question");
    $("#btnAddQuestion").attr('class', 'btn btn-primary');
    $("#questionText").val("");
    $('input[name="courseId"]').prop('checked', false);
    $('#selectLevel option:first').prop('selected', true);
     $("#tbl-add-question").load('' + location + ' #tbodyAnswerAdd')
});
$(document).on("click", ".btnDeleteQuestion", function (e) {
    e.preventDefault();
    var choise = confirm("Delete this now !");
    if (choise) {
        var trFisrt = $(this).closest("tr");
        var question_id = trFisrt.find("td:first").text();
        $.ajax({
            url: "/admin/question-management/deleteQuestion",
            type: "get",
            dataType: "text",
            data: {
                question_id: question_id,
            },
            statusCode: {
                200: function () {
                    trFisrt.remove();
                    fillData("/user/question/getAll", 5, 0, "DESC", "");
                    $("#message").html(alert("You  Delete  Question completely !"));
                },
            },
            error: function () {
                $("#message").html(alert("Sorry Can't  Delete Question !"));
            },
        });
    }
});

$(document).on("click", ".btnEditQuestion", function (e) {
    e.preventDefault();
    $("#tbl-add-question").load('' + location + ' #tbodyAnswerAdd')
    $("#exampleModalLabel").text("Update Question");
    $('#questionModal').modal('show');
    $("#btnAddQuestion").attr('id', 'updateQuestionNow');
    $("#updateQuestionNow").text("Update Question ")
    $("#updateQuestionNow").attr('class', 'btn btn-success');

    var trFisrt = $(this).closest("tr");
    var question_id = trFisrt.find("td:first");
    var questionText = question_id.next();
    var answerCorrectText = questionText.next();
    var subjectText = answerCorrectText.next();
    var courseText = subjectText.next();
    var levelText = courseText.next();
    var button = levelText.next();
    var courseId = button.next();
    /*Get Data from table fill form Update*/
    var subjecName = $('.nav-link.active.show').text();
    $("#question_id").text(question_id.text());
    $("#questionText").val(question_id.next().text());
    $('.nav-link').removeClass('active');
    $('a[aria-controls="' + subjectText.text().trim() + '"]').addClass('active show');

    $('.tab-pane').removeClass('active show');
    $('div[id="' + subjectText.text().trim() + '"]').addClass('active show');
    $('#course-' + courseId.text().trim()).attr('checked', true);
    $('#selectLevel option').each(function () {
        if ($(this).text() == levelText.text())
            $(this).attr('selected', 'selected');
    });
    /*End*/
    $.ajax({
        url: "/user/question/getListAnswerByQuestionId",
        method: "Get",
        data:
            {
                question_id: question_id.text(),
            },
        success: function (result) {
            alpha2 = 'A';

            $.each(result, function (index, value) {
                $('#answer' + alpha2 + '').val(value.answerText);
                $('#correctAnswer' + alpha2 + '').val(value.answerId);
                $('#correctAnswer' + alpha2 + '').attr('checked', value.correctAnswer);
                alpha2 = String.fromCharCode(alpha2.charCodeAt() + 1);
            })
        },
        error: function () {
            alert(" Sorry ! Has A Problem ! ");
        }
    })
});
$(document).on("click", "#updateQuestionNow", function (e) {
    e.preventDefault();
    var _csrf = $('meta[name="_csrf"]').attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var questionText = $("#questionText").val();
    var level = {
        "levelId": $("#selectLevel").val(),
        "levelName": $("#selectLevel option:selected").text()
    }
    var input_checked = $("input[name='courseId']:checked");
    var course = {
        "courseId": input_checked.val(),
        "courseName": input_checked.next().text()
    }
    var isCorrectA = false;
    var isCorrectB = false;
    var isCorrectC = false;
    var isCorrectD = false;
    if($('#correctAnswerA').prop('checked')==false && $('#correctAnswerB').prop('checked')==false&&
        $('#correctAnswerC').prop('checked')==false &&$('#correctAnswerD').prop('checked')==false)
    {
        alert("You must one answer is correct for question ?")
        return false;
    }
    if ($('#correctAnswerA').prop('checked')) {
        isCorrectA = true;
    }
    if ($('#correctAnswerB').prop('checked')) {
        isCorrectB = true;
    }
    if ($('#correctAnswerC').prop('checked')) {
        isCorrectC = true;
    }
    if ($('#correctAnswerD').prop('checked')) {
        isCorrectD = true;
    }
    var answerA = {
        "answerId": $("#correctAnswerA").val(),
        "answerText": $("#answerA").val(),
        "sequence": "A",
        "correctAnswer": isCorrectA
    }
    var answerB = {
        "answerId": $("#correctAnswerB").val(),
        "answerText": $("#answerB").val(),
        "sequence": "B",
        "correctAnswer": isCorrectB
    }
    var answerC = {
        "answerId": $("#correctAnswerC").val(),
        "answerText": $("#answerC").val(),
        "sequence": "C",
        "correctAnswer": isCorrectC
    }
    var answerD = {
        "answerId": $("#correctAnswerD").val(),
        "answerText": $("#answerD").val(),
        "sequence": "D",
        "correctAnswer": isCorrectD
    }
    var answersInsert = [answerA, answerB, answerC, answerD];

    var object = {
        "questionId": $("#question_id").text().trim(),
        "questionText": questionText,
        "levelDto": level,
        "courseDto": course,
        "answersDto": answersInsert,
        "answersDelete": answerDelete
    };
    if (questionText === "") {
        alert("Question can't empty ?");
        return false;
    }
    if (!$("input[name='courseId']:checked").val()) {
        alert("Course can't empty ?");
        return false;
    }
    if ($("#selectLevel").val() === "Choose Level...") {
        alert("Level can't empty ?");
        return false;
    }
    if ($("#answerA").val() === "" || $("#answerB").val() === "" || $("#answerC").val() === "" || $("#answerD").val() === "") {
        alert("Answer can't empty ?");
        return false;
    }

    $.ajax({
        url: "/admin/question-management/createQuestion",
        method: "POST",
        contentType: 'application/json;',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, _csrf);
        },
        data:
            JSON.stringify(object),
        statusCode: {
            200: function (result) {
                console.log("Object Updated Return:");
                console.log(result);
                $('#questionModal').modal('hide');
                fillData("/user/question/getAll", 5, 0, "DESC", "");
                $("#message").html(alert("You updated question !"));
            },
            500: function () {
                $("#message").html(alert("Sorry Can't update question !"));
            },
            error: function () {
                $("#message").html(alert("Sorry Can't updated question !"));
            },
        }
    });
    answerDelete = [];
});
$(document).on("click", "#btnAddQuestion", function (e) {
    e.preventDefault();

    var _csrf = $('meta[name="_csrf"]').attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    var questionText = $("#questionText").val();
    var level = {
        "levelId": $("#selectLevel").val(),
        "levelName": $("#selectLevel option:selected").text()
    }
    var input_checked = $("input[name='courseId']:checked");
    var course = {
        "courseId": input_checked.val(),
        "courseName": input_checked.next().text()
    }

    var isCorrectA = false;
    var isCorrectB = false;
    var isCorrectC = false;
    var isCorrectD = false;
    if($('#correctAnswerA').prop('checked')==false && $('#correctAnswerB').prop('checked')==false&&
        $('#correctAnswerC').prop('checked')==false &&$('#correctAnswerD').prop('checked')==false)
    {
        alert("You must one answer is correct for question ?")
        return false;
    }
    if ($('#correctAnswerA').prop('checked')) {
        isCorrectA = true;
    }
    if ($('#correctAnswerB').prop('checked')) {
        isCorrectB = true;
    }
    if ($('#correctAnswerC').prop('checked')) {
        isCorrectC = true;
    }
    if ($('#correctAnswerD').prop('checked')) {
        isCorrectD = true;
    }
    var answerA = {
        "answerText": $("#answerA").val(),
        "sequence": "A",
        "correctAnswer": isCorrectA
    }
    var answerB = {
        "answerText": $("#answerB").val(),
        "sequence": "B",
        "correctAnswer": isCorrectB
    }
    var answerC = {
        "answerText": $("#answerC").val(),
        "sequence": "C",
        "correctAnswer": isCorrectC
    }
    var answerD = {
        "answerText": $("#answerD").val(),
        "sequence": "D",
        "correctAnswer": isCorrectD
    }
    var answers = [answerA, answerB, answerC, answerD];

    var object = {
        "questionText": questionText,
        "levelDto": level,
        "courseDto": course,
        "answersDto": answers,
    };
    if (questionText === "") {
        alert("Question can't empty ?");
        return false;
    }

    if (!$("input[name='courseId']:checked").val()) {
        alert("Course can't empty ?");
        return false;
    }
    if ($("#selectLevel").val() === "Choose Level...") {
        alert("Level can't empty ?");
        return false;
    }
    if ($("#answerA").val() === "" || $("#answerB").val() === "" || $("#answerC").val() === "" || $("#answerD").val() === "") {
        alert("Answer can't empty ?");
        return false;
    }
    $.ajax({
        url: "/admin/question-management/createQuestion",
        method: "POST",
        contentType: 'application/json;',
        dataType: 'json',
        beforeSend: function (xhr) {
            // here it is
            xhr.setRequestHeader(header, _csrf);
        },
        data:
            JSON.stringify(object),

        statusCode: {
            200: function (result) {
                console.log("Object Saved  Return:");
                console.log(result);
                $('#questionModal').modal('hide');
                fillData("/user/question/getAll", 5, 0, "DESC", "");

                $("#message").html(alert("You created question !"));
            },
            500: function () {
                $("#message").html(alert("Sorry Can't create question !"));
            },
            error: function () {
                $("#message").html(alert("Sorry Can't create question !"));
            },
        }
    });
    answerDelete = [];
});

function displayLevel() {
    $.ajax({
        url: "http://localhost:8080/level/getAllLevel",
        method: "GET",
        success: function (result) {
            $.each(result, function (index, value1) {
                var data = '<option  value=' + value1.levelId + '>' + value1.levelName + '</option>';
                $('#selectLevel').append(data);
                $('#selectLevelSearch').append(data);
            })
        }
    })
}

/*JavaScript*/
var alpha = 'A';
var countClickedAdd = 0;

/**
 *
 */
function addAnswer() {
    var table = document.getElementById("tbl-add-question").getElementsByTagName("tbody")[0];
    if (table.rows.length == 5) {
        alert("Oh No! Max Answer is 4.");
    } else {
        countClickedAdd++;
        alpha = String.fromCharCode(alpha.charCodeAt() + 1);
        var row = table.insertRow(table.rows.length - 1);

        var prevRow = row.previousElementSibling;
        if (countClickedAdd > 1) {
            var elem = document.getElementById("btn-remove-answer");

            if (prevRow.getElementsByTagName('td')[1].contains(elem)) {
                prevRow.getElementsByTagName('td')[1].removeChild(elem);
            }
        }

        var td1 = document.createElement("td");
        td1.innerHTML = "<input type ='checkbox' id = 'correctAnswer" + alpha + "' value = '0' + name = 'correctAnswer'> ";
        row.appendChild(td1);
        var td2 = document.createElement("td");
        td2.className = "td-question";
        td2.innerHTML = "<input type = 'text' name='answer" + alpha + "' id = 'answer"
            + alpha + "'"
            + " class='answer rounded mx-4 '/> <button type ='button' id ='btn-remove-answer' class='btn-remove-answer'"
            + " onclick='removeQuestion(this)'><i class='fa fa-times' style='color: #e8477e'></i></button> ";
        row.appendChild(td2);
    }
}
function removeRowB() {
    var answerB = {
        "answerId": $("#correctAnswerB").val()
    }
    alpha = 'A';
    answerDelete.push(answerB);
    $("#rowB").remove();
    console.log(alpha);

}

function removeRowC() {
    var answerC = {
        "answerId": $("#correctAnswerC").val()
    }
    alpha = 'B';
    answerDelete.push(answerC);
    $("#rowC").remove();
    console.log(alpha);
}


function removeRowD() {

    var answerD = {
        "answerId": $("#correctAnswerD").val()
    }
    alpha = 'C';
    answerDelete.push(answerD);
    $("#rowD").remove();
    console.log(alpha);
}

/**
 *
 * @param node
 */
function removeQuestion(node) {
    var p = node.parentNode.parentNode;
    alpha = String.fromCharCode(alpha.charCodeAt() - 1);

    if (countClickedAdd > 1) {
        var html = "<button type = 'button' id ='btn-remove-answer'" + "onclick = 'removeQuestion(this)'><i class='fa fa-times' aria-hidden='true' style='color: #e8477e'></i></button> ";
        p.previousElementSibling.getElementsByTagName('td')[1].insertAdjacentHTML("beforeend", html);
    }

    p.parentNode.removeChild(p);
    countClickedAdd--;
}

/*Pagging*/
//Customed
/**
 *
 */
$(document).on("click", '#btnSearchQuestion', function (e) {
    e.preventDefault();
    var questionText = $("#txtSearchQuestionText").val();
    var levelId = $("#selectLevelSearch").val();
    var subjectId = $("#slcSubject").val();

    searchQuestion(subjectId, questionText, levelId);
});
/*Start*/
//Customed
/**
 *
 * @param subjectId
 * @param questionText
 * @param levelId
 * @return {boolean}
 */
function searchQuestion(subjectId, questionText, levelId) {
    if (questionText == "" && subjectId == 0 && levelId == 0) {
        return false;
    }
    try {
        fillData("/user/question/searchByCondition", 5, 0, "DESC", "&subjectId=" + subjectId + "&questionText=" + questionText + "&levelId=" + levelId);
    } catch (e) {
        alert("No Find Result");
        console.log(e)
    }
}

/**
 *
 * @param url
 * @param size
 * @param page
 * @param sort
 * @param query
 * @return table Question
 */
function fillData(url, size, page, sort, query) {
    $('#questionTable').find("tbodyQuestion").html('<img src="/images/ajax-loader.gif">');
    $.ajax({
        url: url + "?size=" + size + "&page=" + page + "&sort=" + sort + query,
        method: "GET",
        success: function (result) {
            var html = "";
            var allRecord = result.totalElements;
            $("#tbodyQuestion").html('');
            try {
                $.each(result.content, function (index, value) {
                    var subjectName = "";
                    if (value.course.subject.subjectName == null) {
                        subjectName = value.course.subject;
                    } else {
                        subjectName = value.course.subject.subjectName;
                    }
                    var levelName;
                    if (value.level.levelName == null) {
                        levelName = value.level;
                    } else {
                        levelName = value.level.levelName;
                    }
                    var answerTextIsCorrect = "";
                    $.each(value.answers, function (index1, value1) {
                        if (value1.correctAnswer) {
                            answerTextIsCorrect = value1.answerText;
                        }
                    });

                    html += bulidRowTableQuestion(value.questionId, value.questionText, answerTextIsCorrect, subjectName, value.course.courseName, levelName, value.course.courseId, numberRecord);
                    var numberRecord = '<tr><th scope="col" colspan="6"> <b style="color: #e8477e">Record : ' + allRecord + '</b></th></tr>';

                    $("#tbodyQuestion").html(html);
                    $("#tbodyQuestion").append(numberRecord);
                    $('#paginationQuestion').html(buildPaginationQuestion(result.totalPages, page));
                });
            } catch (e) {
                console.log("Khong The Do Du Lieu")
            }

        }
    });
}

//Customed
/**
 *
 * @param totalPage
 * @param currentPage
 * @return {string}
 */
function buildPaginationQuestion(totalPage, currentPage) {
    var str = "";

    for (i = 0; i < totalPage; i++) {
        var active = "";
        if (currentPage == i) {
            active = "active";
        }
        str += ' <li class="page-item ' + active + '"><a href=""  class="page-link page-question" data-pageQuestion="' + i + '">' + (i + 1) + '</a></li>';

    }

    return str;
}
$(document).on("click", ".page-question", function (e) {
    e.preventDefault();
    var page = $(this).attr("data-pageQuestion");
    var questionText = $("#txtSearchQuestionText").val();
    var levelId = $("#selectLevelSearch").val();
    var subjectId = $("#slcSubject").find(":selected").val();

    if (subjectId != 0 || questionText != "" || levelId != 0) {
        fillData("/user/question/searchByCondition", 5, page, "DESC", "&subjectId=" + subjectId + "&questionText=" + questionText + "&levelId=" + levelId);
    } else {
        fillData("/user/question/getAll", 5, page, "DESC", "");
    }

});

//Function for filling data to a Row of table
/**
 *
 * @param questionId
 * @param questionText
 * @param answerTextIsCorrect
 * @param subjectName
 * @param courseName
 * @param levelName
 * @param courseId
 * @param numberRecord
 * @return A Row Question
 */
function bulidRowTableQuestion(questionId, questionText, answerTextIsCorrect, subjectName, courseName, levelName, courseId, numberRecord) {
    var rowFill = '<tr><td class="questionId" hidden="hidden" >' + questionId + '</td>' +
        '<td >' + questionText + ' </td>' +
        '<td><span ><i style="color:#2db457;" class="fa fa-check" aria-hidden="true">' + answerTextIsCorrect + '</i></span></td> ' +
        '<td >' + subjectName + '</td>' +
        '<td >' + courseName + '</td>' +
        '<td >' + levelName + '</td>' +
        '<td><a href="" class="btnViewQuestion"><i class="fa fa-eye"></i> </a> &nbsp;' +
        '<a href="" class="btnEditQuestion"><i class="fa fa-edit"></i></a>&nbsp;' +
        '<a href="" class="btnDeleteQuestion"><i class="fa fa-trash"></i></a>&nbsp; </td> ' +
        '<td hidden="hidden" >' + courseId + '</td>' +
        '</tr>';
    return rowFill;
}

/*End */

/*GET SUBJECT AND COURSE TO SHOW*/
function displaySubject() {
    $.ajax({
        url: "/subjects/getAllSubject",
        method: "GET",
        success: function (result) {
            var slc = $('#slcSubject');
            slc.html("");
            slc.append('<option value="0" selected>All...</option>');
            $.each(result, function (index, value) {
                slc.append($('<option>',
                    {
                        value: value.subjectId,
                        text: value.subjectName
                    }));
            })
        }

    })
}


$(document).on("click", "#clearSearchQuestion", function (e) {
    e.preventDefault();
    fillData("/user/question/getAll", 5, 0, "DESC", "");
});

function displaySubjectTab() {
    $.ajax({
        url: "/subjects/getAllSubject",
        method: "GET",
        success: function (result) {
            console.log(result)
            $.each(result, function (index, value) {
                var active = "";
                if (index === 0) {
                    active = "active";
                }
                $('#tabSubject').find("ul").append(buildTab(value.subjectName, active));
                $('#tabSubject').find(".tab-content").append(buildTabContent(value.subjectName, active));
                $.each(value.courses, function (index1, value1) {
                    buildRadioCourse(value.subjectName, value1.courseId, value1.courseName);
                })
            })
        }

    })
}

function buildTab(name, active) {
    return '<li role="presentation" class="nav-item">\n' +
        '<a class="nav-link ' + active + '" data-toggle="tab" href="#' + name + '" role="tab" aria-controls="' + name + '">' + name + '</a>\n' +
        '</li>';
}

function buildTabContent(name, active) {
    return '<div class="tab-pane ' + active + '" id="' + name + '" role="tabpanel" ></div>'

}

function buildRadioCourse(id, courseId, courseName) {
    $('#' + id).append('<div class="form-check form-check-inline">' +
        '  <input class="form-check-input" type="radio" name="courseId" id="course-' + courseId + '" value="' + courseId + '">\n' +
        '  <label class="form-check-label" for="course-' + courseId + '">' + courseName + '</label>\n' +
        '</div>');

}

/*END GET SUBJECT AND COURSE TO SHOW*/