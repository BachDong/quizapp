var questionIdList = [];
var listTestIdAndQuestion = [];
var listQuestionIdLoaded = [];
$(document).ready(function () {
    loadTableQuiz();
    displaySubject();
    displayLevel();
    displaySubjectTab();

    $('#btnShowQuizManagement').click(function (e) {
        $('#adminContent').load("http://localhost:8080/admin/quiz-management");

    });
    fillData("/user/question/getAll?size=1000","");
});
//search from database
$(document).on("click", "#btnSearch", function (e) {
    e.preventDefault();

    var txtSearch=$("#txtSearch").val();
    $.ajax({
        url:"/admin/quiz-management/searchQuiz",
        data:{"txtSearch":txtSearch},
        method:"Get",
        statusCode: {
            200: function (result) {
                buildTableQuiz(result);
            },
            500: function () {
                console.log("Can't load table");
            }
        }
    })
});

$(document).on("click", "#clearSearchQuiz", function (e) {
    e.preventDefault();
    $("#txtSearch").val("");
    loadTableQuiz();
});
//Seacrh Online
$(document).on("keyup", "#txtSearch", function () {

    var value = $("#txtSearch").val().toLowerCase();
    $("#tbodyQuiz tr").filter(function () {
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });


});

$(document).on('change', "#selectLevelSearch", function () {

    if ($("#selectLevelSearch option:selected").text() == "Choose...") {
        loadTableQuiz();
    } else {
        var value = $("#selectLevelSearch option:selected").text().toLowerCase();
        $("#tbodyQuiz tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    }

});

$(document).on('change', "#slcSubject", function () {
    if ($("#slcSubject option:selected").text() == "All...") {
        loadTableQuiz();
    } else {
        var value = $("#slcSubject option:selected").text().toLowerCase();
        $("#tbodyQuiz tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    }

});
/*Show Form  Add Quiz*/
$(document).on("click", "#createNewQuiz", function (e) {
    e.preventDefault();
    listQuestionIdLoaded = [];
    $('#formQuiz').trigger("reset");
    localStorage.clear();
    $("#quizModal").modal("show");
    $("#quizId").text('0');
    $("#btnAddOrUpdate").text("Create")
    fillData("/user/question/getAll?size=1000","");
    questionIdList = [];

})
    $(document).on("click", ".btnEditQuiz", function (e) {
        e.preventDefault();
        $("#quizModal").modal("show");
        $('#formQuiz').trigger("reset");
        localStorage.clear();
        $("#btnAddOrUpdate").text("Update");
        var trFirst = $(this).closest('tr');
        var quizId = trFirst.find("td:first");
        var quizTitle = quizId.next();
        var subject = quizTitle.next();
        var course = subject.next();
        var level = course.next();
        var duration = level.next();
        var totalMark = duration.next();
        $("#quizId").text(quizId.text());
        $("#txtQuiz").val(quizTitle.text());
        $("#duration").val(duration.text());
        $("#mark").val(totalMark.text());
        $('#selectLevel option').each(function () {
            if ($(this).text() == level.text())
                $(this).attr('selected', 'selected');
        });
        $('.nav-link').removeClass('active');
        $('a[aria-controls="' + subject.text().trim() + '"]').addClass('active show');
        $('.tab-pane').removeClass('active show');
        $('div[id="' + subject.text().trim() + '"]').addClass('active show');
        var courseId = trFirst.find("td:last")
        $('#course-' + courseId.text().trim()).attr('checked', true);
        var listQuestionOfTest = [];
        $.each(listTestIdAndQuestion, function (index) {
            if (listTestIdAndQuestion[index].testId == quizId.text()) {
                listQuestionOfTest = listTestIdAndQuestion[index].listQuestion;
            }
        });
        questionIdList = [];
        $.each(listQuestionOfTest, function (index1) {
            var question = $('#question' + listQuestionOfTest[index1].questionId + '');
            question.attr('checked', 'checked');
            questionIdList.push(listQuestionOfTest[index1].questionId);
        })
});
//Delete Quiz
$(document).on("click", ".btnDeleteQuiz", function (e) {
    var choise = confirm("Delete this now !");
    if (choise) {
        e.preventDefault();
        var trFirst = $(this).closest('tr');
        var quizId = trFirst.find("td:first");
        $.ajax({
            url: "/admin/quiz-management/delete",
            data: {"quizId": quizId.text()},
            type: "Get",
            dataType: 'text',
            success: function () {
                alert("Delete Quiz Ok");
                loadTableQuiz();
            },
            error: function () {
                alert("Delete Quiz Failed Because Exam are using");
            },
        });
    }
})

//Loading State for CheckBox
function loadStateForCheckBox(listQuestionIdLoaded) {
    if (listQuestionIdLoaded != undefined) {
        console.log("Loading state check box")
        $.each(listQuestionIdLoaded, function (index) {
            var checked = JSON.parse(localStorage.getItem('question' + listQuestionIdLoaded[index] + ''));//data state checkBox is checked and saved
            try {
                document.getElementById('question' + listQuestionIdLoaded[index] + '').checked = checked;
            } catch (e) {
            }
        })
    } else {
        console.log("List ID question - state check box loaded is null")
    }
}

//Function Save StateCheckBox
function saveStateCheckBox(questionId) {
    var checkbox = document.getElementById('question'+questionId+'');
    localStorage.setItem('question'+questionId+'', checkbox.checked);
    console.log("Saved state CheckeBox" + questionId)

}

//Event push or remove element Of checkbox
$(document).on("click", ".questionId", function () {
    var ischecked = $(this).is(':checked');
    if (ischecked) {
        questionIdList.push($(this).val());
    }
    if (!ischecked) {
        Array.prototype.remove = function (v) {
            this.splice(this.indexOf(v) == -1 ? this.length : this.indexOf(v), 1);
        }
        questionIdList.remove($(this).val());
    }
})

/*Show Form  Demo Quiz*/
$(document).on("click", "#btnViewDemo", function (e) {
    e.preventDefault();
    var _csrf = $('meta[name="_csrf"]').attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var testText = $("#txtQuiz").val();
    var duration = $("#duration").val();
    var totalMark = $("#mark").val();
    var levelId = $("#selectLevel").val();
    var courseId = $("input[name='courseId']:checked").val();
    if (!courseId) {
        alert("Please select a Course ?");
        return false;
    }
    if (testText == "") {
        alert("Please Input Quiz title");
        return false;
    }
    if (duration == "" || duration < 0 || duration > 200) {
        alert("Please Check Duration again !");
        return false;
    }
    if (totalMark == "" || totalMark < 0 || totalMark > 200) {
        alert("Please Check TotalMark again !");
        return false;
    }
    if (levelId == 0) {
        alert("Please Select a Level !");
        return false;
    }
    if (levelId == 0) {
        alert("Please Select Level !");
        return false;
    }
    var dataDemo = quizJson("", "", "", "", "", "", questionIdList)
    $("#demoQuiz").modal("show");
    $.ajax({
        url: "/admin/quiz-management/demoQuiz",
        method: "POST",
        contentType: 'application/json;',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, _csrf);
        },
        data:
            JSON.stringify(dataDemo),
        statusCode: {
            200: function (result) {
                var levelText = $('#selectLevel option:selected').text();
                var courseText = $("input[name='courseId']:checked").next().text();
                var data = '<div style="text-align: center;color:#6f42c1;font-size: larger"><p  >Title: ' + testText + ' | Level: ' + levelText + ' | Course: ' + courseText + ' </p>' +
                    '<p >Time: ' + duration + '.minutes  | TotalMark: ' + totalMark + '.points</p><hr></div>'
                $("#contentDemoQuiz").html('')
                $("#contentDemoQuiz").append(data)
                $("#contentDemoQuiz").append(result.responseText)
            },
        },
        error: function () {
        }
    });
})
function quizJson(testId, testText, duration, totalMark, courseId, levelId) {

    return data = {
        "testId": testId,
        "testText": testText,
        "duration": duration,
        "totalMark": totalMark,
        "courseId": courseId,
        "levelId": levelId,
        "questionIdList": questionIdList,
    }
}

function loadTableQuiz() {
    questionIdList = [];
    $.ajax({
        url: "/admin/quiz-management/loadTableQuiz",
        method: "GET",
        statusCode: {
            200: function (result) {
                buildTableQuiz(result);
            },
            500: function () {
                console.log("Can't load table");
            }
        }
    })
}
function buildTableQuiz(result) {
    var dataSources = [];
    var len = 0;
    $.each(result, function (index, value) {
        len = len + 1;
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
        var questionOfTest = {
            "testId": value.testId,
            "listQuestion": value.questions
        }
        var aRowQuiz = buildRowTableQuiz(value.testId, value.testText, subjectName, value.course.courseName, levelName, value.duration, value.totalMark, value.course.courseId);
        listTestIdAndQuestion.push(questionOfTest);
        dataSources.push(aRowQuiz);
    })
    $('#pagingQuiz').pagination({
        dataSource: dataSources,
        // locator: 'items',
        pageSize: 4,
        showPrevious: true,
        showNext: true,
        className: 'paginationjs-theme-blue paginationjs-small',
        ajax: {
            beforeSend: function () {
                $('#pagingQuiz').html("")
                $('#tbodyQuiz').html('');
                $('#lenRow').html('');
                $('#tableQuiz').html('Loading data...');
            }
        },
        callback: function (data, pagination) {
            $('#tbodyQuiz').html(data);
            $('#lenRow').html('Records : ' + len + '');
        },
    })
}

$(document).on("click", ".btnPrint", function (e) {
    var choise = confirm("Create PDF to Print Now ?");
    if (choise) {
        e.preventDefault();
        var trFirst = $(this).closest('tr');
        var quizId = trFirst.find("td:first");
        $.ajax({
            url: "/admin/quiz-management/print",
            data: {"quizId": quizId.text()},
            type: "Get",
            dataType: 'text',
            success: function (result) {
                waitingDialog.show(result),
                setTimeout(function () {
                    waitingDialog.hide();

                    }, 2000);
            },
            error: function (result) {
                alert(result );
            },
        });
    }
})

$(document).on("click", "#showMessage", function () {
    waitingDialog.hide();
});
function buildRowTableQuiz(testId, testText, subjectName, courseName, levelName, duration, totalMark, courseId) {
    return '<tr>\n' +
        '            <td class="quizId" hidden="hidden">' + testId + '</td>\n' +
        '            <td >' + testText + '</td>\n' +
        '            <td >' + subjectName + '</td>\n' +
        '            <td >' + courseName + '</td>\n' +
        '            <td >' + levelName + '</td>\n' +
        '            <td >' + duration + '</td>\n' +
        '            <td >' + totalMark + '</td>\n' +
        '            <td>' +
        '                <a href="#"><i class="fa fa-eye"></i> </a> &nbsp;\n' +
        '                <a href="#" class="btnEditQuiz"><i class="fa fa-edit"></i></a>\n' +
        '                &nbsp;\n' +
        '                <a href="#" class="btnDeleteQuiz"><i class="fa fa-trash"></i></a>' +
        '                &nbsp;\n' +
        '               <a href="#"  class="btnPrint" ><i class="fa fa-print"></i></a>' +
        '                &nbsp;\n' +
        '            </td>' +
        '            <td  hidden="hidden">' + courseId + '</td>' +
        '        </tr>'
}

function openNewTab(){
  setTimeout(function () {
     window.open("viewPDF")
  },1500);
}

$(document).on("click", "#btnAddOrUpdate", function (e) {
    e.preventDefault()
    var _csrf = $('meta[name="_csrf"]').attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var testId = $("#quizId").text()//For Update
    var testText = $("#txtQuiz").val();
    var duration = $("#duration").val();
    var totalMark = $("#mark").val();
    var levelId = $("#selectLevel").val();
    var courseId = $("input[name='courseId']:checked").val();
    if (!courseId) {
        alert("Please select a Course ?");
        return false;
    }
    if (testText == "") {
        alert("Please Input Quiz title");
        return false;
    }
    if (duration == "" || duration < 0 || duration > 200) {
        alert("Please Check Duration again !");
        return false;
    }
    if (totalMark == "" || totalMark < 0 || totalMark > 200) {
        alert("Please Check TotalMark again !");
        return false;
    }
    if (levelId == 0) {
        alert("Please Select a Level !");
        return false;
    }
    if (levelId == 0) {
        alert("Please Select Level !");
        return false;
    }
    var data = quizJson(testId, testText, duration, totalMark, courseId, levelId, questionIdList)
    $.ajax({
        url: "/admin/quiz-management/createQuiz",
        method: "POST",
        contentType: 'application/json;',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, _csrf);
        },
        data:
            JSON.stringify(data),

        statusCode: {
            200: function (result) {
                if (testId == 0) {
                    alert("Create Quiz Completely !")
                } else {
                    alert("Update Quiz Completely !")
                }

                console.log("Insert Quiz OK")
                questionIdList = [];
                loadTableQuiz()
                $("#quizModal").modal("hide");
            },
        },
        error: function () {
            alert("Create Quiz Failed !")
        }
    });
})

/*Start Show table Question*/
/**
 *
 * @param url
 * @param size
 * @param page
 * @param sort
 * @param query
 * @return table Question
 */
function fillData(url,query) {
    $('#questionTable').find("tbodyQuestion").html('<img src="/images/ajax-loader.gif">');
    var dataSources = [];
    $.ajax({
        url: url + query,
        method: "GET",
        success: function (result) {
            var html = "";

            try {
                listQuestionIdLoaded = [];
                $.each(result.content, function (index, value) {
                    listQuestionIdLoaded.push(value.questionId);

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
                    html = bulidRowTableQuestion(value.questionId, value.questionText, subjectName, value.course.courseName, levelName);
                    dataSources.push(html)
                });
                $('#paging').pagination({
                    dataSource: dataSources,
                    locator: 'items',
                    pageSize: 4,
                    showPrevious: true,
                    showNext: true,
                    className: 'paginationjs-theme-red paginationjs-small',
                    ajax: {
                        beforeSend: function () {
                            $('#paging').html("")
                            $("#tbodyQuestion").html('');
                            $('#tbodyQuestion').html('Loading data...');
                        }
                    },
                    callback: function (data, pagination) {
                        $('#tbodyQuestion').html(data)
                        try {
                            loadStateForCheckBox(listQuestionIdLoaded);
                        } catch (e) {

                        }
                    },
                })
            } catch (e) {
                console.log("can't build question table  " + e)
            }
        }
    });
}

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
function bulidRowTableQuestion(questionId, questionText, subjectName, courseName, levelName) {
    var rowFill = '<tr>' +
        '<td >' + questionText + ' </td>' +
        '<td >' + subjectName + '</td>' +
        '<td >' + courseName + '</td>' +
        '<td >' + levelName + '</td>' +
        '<td  >' +
        '<input id="question'+questionId+'"  class="questionId"  type="checkbox" value="'+questionId+'" onClick="saveStateCheckBox('+questionId+')"> </td>' +
        '</tr>';
    return rowFill;
}

/*End Show table Question*/


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

function displaySubjectTab() {
    $.ajax({
        url: "/subjects/getAllSubject",
        method: "GET",
        success: function (result) {
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
        '                                                    <a class="nav-link ' + active + '" data-toggle="tab" href="#' + name + '" role="tab" aria-controls="' + name + '">' + name + '</a>\n' +
        '                                                </li>';
}

function buildTabContent(name, active) {
    return '<div class="tab-pane ' + active + '" id="' + name + '" role="tabpanel" >\n' +
        '                                                    \n' +
        '\n' +
        '                                                </div>'

}

function buildRadioCourse(id, courseId, courseName) {
    $('#' + id).append('<div class="form-check form-check-inline">\n' +
        '  <input class="form-check-input" type="radio" name="courseId" id="course-' + courseId + '" value="' + courseId + '">\n' +
        '  <label class="form-check-label" for="course-' + courseId + '">' + courseName + '</label>\n' +
        '</div>');

}

/*END GET SUBJECT AND COURSE TO SHOW*/
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





    var waitingDialog = waitingDialog || (function ($) {
        'use strict';
        // Creating modal dialog's DOM
        var $dialog = $(
            '<div class="modal fade" id="showMessage" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
            '<div class="modal-dialog modal-m">' +
            '<div class="modal-content">' +
            '<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
            '<div class="modal-body">' +
            '<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
            '</div>' +
            '</div></div></div>');

        return {
            show: function (message, options) {
                // Assigning defaults
                if (typeof options === 'undefined') {
                    options = {};
                }
                if (typeof message === 'undefined') {
                    message = 'Loading';
                }
                var settings = $.extend({
                    dialogSize: 'm',
                    progressType: '',
                    onHide: null // This callback runs after the dialog was hidden
                }, options);
                // Configuring dialog
                $dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
                $dialog.find('.progress-bar').attr('class', 'progress-bar');
                if (settings.progressType) {
                    $dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
                }
                $dialog.find('h3').text(message);
                // Adding callbacks
                if (typeof settings.onHide === 'function') {
                    $dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
                        settings.onHide.call($dialog);
                    });
                }
                // Opening dialog
                $dialog.modal();
            },
            /**
             * Closes dialog
             */
            hide: function () {
                $dialog.modal('hide');
            }
        };

    })(jQuery);