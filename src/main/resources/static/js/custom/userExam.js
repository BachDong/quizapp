var subject_id = 0;
var subject_name = "";
$(document).ready(function (e) {
    var sidebarListItem = $('#sidebar-listItem');
    sidebarListItem.text("");
    var subjectIdGlobal = 0;
    var subjectNameGlobal = "";
    $.ajax({
        url:"/subjects/getAllSubject",
        method:"GET",
        success:function (result) {
            var ul =  $('#navbarNav').find("ul");
            ul.empty();
            var strc = "";
            var rsLength = (result.length);
            $.each(result,function (index,value) {

                ul.append(" <li class=\"nav-item active show\">\n" +
                    "                            <a class=\"nav-link\" href=\"/subjects/view/"+value.subjectId+"#\">"+value.subjectName+" </a>\n" +
                    "                        </li>");

                sidebarListItem.append("<span subject-id='"+value.subjectId+"' href=\"#\"  class=\"list-group-item subject-link\"  style=\"background-color: #343C49;margin-bottom: 1px\">\n" +
                    "            "+value.subjectName+"\n" +
                    "        </span>");
            });

        }

    });

    $(document).on("click",'.subject-link',function (e) {
        e.preventDefault();
        var subjectId = $(this).attr("subject-id");
        var subjectName = $(this).text();
        subject_id = subjectId;
        subject_name = subjectName;
        showExam(subjectId,subjectName);


    });

    function showExam(subjectId,subjectName) {
        $("#tblExam").show();
        $("#welcomeText").hide();
        $.ajax({
            url:"/exams/getAll/subjectId/"+subjectId,
            method:"GET",
            success:function (result) {
                var tbody = $('#tblExam').find("tbody");
                tbody.text("");

                var str = "";
                var date;
                var testId;

                if(result.length<1){
                    tbody.text("No exam here!");
                }

                $.each(result,function (index,value) {
                    date = new Date(value.examId.dateTime);
                    testId = value.examId.testId;
                    var dateStr = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
                    str=buildTrExamTable(testId,value.test.testText,subjectName,value.mark,value.test.totalMark,dateStr,dateStr);
                    tbody.append(str);
                });




            }

        })
    }

    $(document).on("click",".deteteExam",function (e) {
        e.preventDefault();
        var tr = $(this).closest('tr');
        var testId = tr.find(">:first-child").attr("test-id");
        var _csrf = $('meta[name="_csrf"]').attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var date = tr.find("td").eq(2).attr("data-date");
        $.ajax({
            url:"/exams/delete",
            method:"POST",
            data:"testId="+testId+"&dateTime="+date,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, _csrf);
            },
            success:function (result) {
                if(result.status==true){
                    alert("Delete successfully!");
                    var subjectId = subject_id;
                    var subjectName = subject_name.trim();
                    showExam(subjectId,subjectName);
                }
            }

        })
    });

    function buildTrExamTable(testId,testText,subject,mark,markTotal,date,dateDb) {
        return '<tr>\n' +
            '                                <th test-id="'+testId+'" id="testText'+testId+'" scope="row">'+testText+'</th>\n' +
            '                                <td>'+subject+'</td>\n' +
            '                                <td>'+mark+' / '+markTotal+'</td>\n' +
            '                                <td data-date="'+dateDb+'">'+date+'</td>\n' +
            '                                <td><a class="deteteExam" href="">Delete</a> </td>\n' +
            '                            </tr>';
    }

    function getTestText(testId) {
        var testText = "";
        $.ajax({
            url:"/tests/testText/"+testId,
            method:"GET",
            success:function (result) {
                testText = result.msg;
            }
        });
        return testText;
    }
});

