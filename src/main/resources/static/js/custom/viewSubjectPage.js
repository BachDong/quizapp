$(document).ready(function (e) {

    var path = window.location.pathname.split("/");
    var subjectId = path[path.length-1];
    var sidebarListItem = $('#sidebar-listItem');
    sidebarListItem.text("");
    $.ajax({
        url:"/subjects/getSubject/"+subjectId,
        method:"GET",
        success:function (result) {
            $('#postTitle').text("Subject : "+result.subjectName);
            $('#postContent').text("Please choose post to display");

            if(result.courses.length<1){
                sidebarListItem.append("<span href=\"#\"  class=\"list-group-item \"  style=\"background-color: #343C49\">\n" +
                    "            No course\n" +
                    "        </span>");
            }

            $.each(result.courses,function (index,value) {
                sidebarListItem.append("<span id='"+value.courseName+"' href=\"#\"  class=\"list-group-item \"  style=\"background-color: #343C49\">\n" +
                    "            "+value.courseName+"\n" +
                    "        </span>" +
                    "<span id='quiz-"+value.courseName+"' style=\"background-color: purple\" class='list-group-item'>Quiz "+value.courseName+"</span>");

                $.ajax({
                    url:"/posts/courseId/"+value.courseId,
                    method:"GET",
                    success:function (resultPost) {
                        $.each(resultPost,function (indexPost,valuePost) {
                            $('#'+value.courseName).after("<a href=\"#\" class=\"list-group-item link-post\" post-id='"+valuePost.postId+"'>\n" +
                                "                        <i class=\"fa fa-comment-o\"></i> "+valuePost.title+"\n" +
                                "                    </a>")



                        })
                    }

                });

                $.ajax({
                    url:"/tests/courseId/"+value.courseId,
                    method:"GET",
                    success:function (resultTest) {
                        if(resultTest.length===0){
                            $('#quiz-'+value.courseName).remove();
                        }

                        $.each(resultTest,function (indexTest,valueTest) {
                            $('#quiz-'+value.courseName).after("<a href=\"#\" class=\"list-group-item link-test\" test-id='"+valueTest.testId+"'>\n" +
                                "                        <i class=\"fa fa-check\"></i> "+valueTest.testText+"\n" +
                                "                    </a>")
                        });
                    }

                });

            });
        }

    });
});

$(document).on("click",".link-post",function (e) {
    e.preventDefault();
    var postId = $(this).attr('post-id');
    displayPost(postId);
    var postNext = $('#postNext');
    var postPrev = $('#postPrevious');
    if(typeof $(this).next().attr("post-id")!=="undefined"){
        postNext.attr("post-id",$(this).next().attr("post-id"));
        postNext.show();
    }else{
        postNext.hide();
    }

    if(typeof $(this).prev().attr("post-id")!=="undefined"){
        postPrev.attr("post-id",$(this).prev().attr("post-id"));
        postPrev.show();
    }else{
        postPrev.hide();
    }

});

var sec;
var time = 0;
$(document).on("click",".link-test",function (e) {
    e.preventDefault();
    clearInterval(time);
    $('#timeLeft').text("Preparing ...");
    $('#submitTest').prop("disabled",false);
    $('#submitTest').text("Submit");


    setTimeout(function () {

    },2000);

    var testId = $(this).attr("test-id");
    $.ajax({
        url:"/tests/getById/"+testId,
        method:"GET",
        beforeSend:function () {
            $('#postNext').hide();
            $('#postPrevious').hide();
            $('#postContent').html("");
            $('#submitTest').show();
        },
        success:function (result) {
            loadingPopup(false);
            $('#testContent').attr("test-id",result.testId)
            $('#postTitle').html(result.testText + " - Level : "+result.level.levelName+"      <span style='margin-left: 150px'>Time : "+result.duration+" minute(s)</span>");
            $('#testContent').html("");
            var no = 0;
            $.each(result.questions,function (index,value) {
               $("#testContent").append(buildQuestionCard(++no,value.questionId,value.questionText));
               $.each(value.answers,function (indexAnswer,valueAnswer) {
                   $("#testAnswerList"+value.questionId).append(buildLineAnswer(value.questionId,valueAnswer.answerId,valueAnswer.answerText,valueAnswer.sequence));
               })
            });
            loadingPopup(false);

            sec = result.duration*60;
            time = setInterval(myTimer, 1000);
            var date;
            var timeHS;
            function myTimer() {
                date = new Date(null);
                date.setSeconds(sec); // specify value for SECONDS here
                timeHS = date.toISOString().substr(11, 8);
                $('#timeLeft').text(timeHS + "");
                sec--;
                if (sec == -1) {
                    clearInterval(time);
                    submitTest();
                }
            }

        }

    });



});

$(document).on("click","#postNext,#postPrevious",function (e) {
    e.preventDefault();
    var postId = $(this).attr('post-id');
    var postPrev = $('#postPrevious');
    var postNext = $('#postNext');
    if(typeof $("a[post-id='"+postId+"']").next().attr("post-id")!=="undefined"){
        postNext.attr("post-id",$("a[post-id='"+postId+"']").next().attr("post-id"));
        postNext.show();
    }else{
        postNext.hide();
    }

    if(typeof $("a[post-id='"+postId+"']").prev().attr("post-id")!=="undefined"){
        postPrev.attr("post-id",$("a[post-id='"+postId+"']").prev().attr("post-id"));
        postPrev.show();
    }else{
        postPrev.hide();
    }
    displayPost(postId);
});

$(document).on("click","#submitTest",function (e) {
   e.preventDefault();
   submitTest();

});

function submitTest() {
    clearInterval(time);
    $('#timeLeft').text("Done ...");

    var testDto = new Object();
    testDto.testId = parseInt($('#testContent').attr("test-id"));

    var questionList = []
    $('#testContent').find(".card").each(function (index) {
        var questionObj = new Object();

        var questionId = $(this).find(".card-header").attr("question-id");
        var answers = $('#testAnswerList'+questionId).find(".form-check");

        questionObj.questionId = parseInt(questionId);

        var sequenceList = [];
        answers.each(function (index) {
            var answerChoose = $(this).find(".form-check-input:checked").attr("answer-data");

            if(answerChoose!=null){
                sequenceList.push(answerChoose);
            }
        });

        questionObj.sequenceList = sequenceList;
        questionList.push(questionObj);
    });

    testDto.questionList = questionList;
    var json = JSON.stringify(testDto);

    ///

    var _csrf = $('meta[name="_csrf"]').attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, _csrf);
        },
        url: "/tests/doQuiz",
        type: "POST",
        contentType: "application/json",
        dataType: 'json',
        data:json,
        success:function (result) {
            if(result.status==false){
                alert(result.msg);
            }else{
                $('#resultModalTitle').text($('#postTitle').text());
                $('#score').text(result.msg.score);
                $('#rank').text(result.msg.rank);
                $('#countCorrectAnswer').html("<font color='green'>"+result.msg.correct+"</font>");
                $('#countWrongAnswer').html("<font color='red'>"+result.msg.wrong+"</font>");
                if(result.msg.status==true){
                    $('#status').html("<font color='green'>Pass</font>");
                    $('#userWrong').text("");
                }else{
                    $('#status').html("<font color='red'>Failed</font>");

                    var strs = "";
                    $('#userWrong').text("");
                    $.each(result.msg.userTestResults,function (index) {
                        var questionId = result.msg.userTestResults[index].questionId;
                        var userChoose = result.msg.userTestResults[index].userChoose.toString();
                        if(userChoose==""){
                            userChoose = "None";
                        }
                        var questionText = $('#testQuestion'+questionId).text() + "| <font color='gray'> You choose :</font> <font color='red'> "+userChoose+"</font>";
                        $('#userWrong').append(buildQuestionError(questionId,questionText));
                        var arr = result.msg.userTestResults[index].rightAnswer;
                        $.each(arr,function (indexAnswer,valueAnswer) {
                            $("#questionErrorAnswerList"+questionId).append(buildLineAnswerCorrect(questionId,valueAnswer.answerId,valueAnswer.answerText,valueAnswer.sequence));
                        })

                    });

                }
                $('#resultModal').modal('show');
                $('#submitTest').text("Submitted").attr("disabled",true);
                $('.form-check-input').attr("disabled",true);
            }
        }

    });

}

function buildQuestionError(questionId,questionText) {
    return "<div style='margin-bottom: 5px' class=\"card\">\n" +
        "                                <div style='color: black;font-weight: bold' class=\"card-header\" id='questionError-"+questionId+"'>\n" +
        "                                  "+questionText+"\n" +
        "                                </div>\n" +
        "                                <div class=\"card-body\" id='questionErrorAnswerList"+questionId+"'>\n" +
        "\n" +
        "                                </div>\n" +
        "                            </div>";
}

function displayPost(postId) {
    clearInterval(time);
    setTimeout(function () {

    },1000);
    $('#timeLeft').text("");
    $.ajax({
        url:"/posts/"+postId,
        method:"GET",
        beforeSend:function () {
            loadingPopup(true);
            $('#testContent').html("");
            $('#submitTest').hide();
        },
        success:function (result) {
            $('#postTitle').html(result.title);
            $('#postContent').html(result.content);
            loadingPopup(false);
        }

    });
}

function loadingPopup(opacity) {
    if(opacity){
        $('#homeContent').css("opacity",0.3);
        $('#homeContent').prop("disabled",true);
    }else{
        $('#homeContent').css("opacity",1);
        $('#homeContent').prop("disabled",false);
    }
}

function buildQuestionCard(no,questionId,questionText) {
    return "<div style='margin-bottom: 5px' class=\"card\">\n" +
        "                                <div style='color: black;font-weight: bold' class=\"card-header\" question-id='"+questionId+"' id=\"testQuestion"+questionId+"\">\n" +
        "                                    "+no+". "+questionText+"\n" +
        "                                </div>\n" +
        "                                <div class=\"card-body\" id=\"testAnswerList"+questionId+"\">\n" +
        "\n" +
        "                                </div>\n" +
        "                            </div>";
}

function buildLineAnswer(questionId,answerId,answerText,answerSequence) {
    return        "                                    <div class=\"form-check\">\n" +
        "                                        <input class=\"testAnswerQuestion-"+questionId+" form-check-input\" answer-data='"+answerSequence+"' type=\"checkbox\" value=\"\">\n" +
        "                                            "+answerSequence+" : "+answerText+"\n" +
        "                                        </label>\n" +
        "                                    </div>\n" ;
}

function buildLineAnswerCorrect(questionId,answerId,answerText,answerSequence) {
    return        "                                    <div class=\"form-check\">\n" +
        "                                        <label class=\"testAnswerQuestion-"+questionId+" form-check-input\" style='position: relative' answer-data='"+answerSequence+"' type=\"checkbox\" checked value=\"\">\n" +
        "                                            <i class='fa fa-check text-success'></i>"+answerSequence+" : "+answerText+"\n" +
        "                                        </label>\n" +
        "                                    </div>\n" ;
}