$(document).ready(function (e) {

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


                if(index%2===0){
                   strc += "<div style='margin-bottom: 5px' class=\"row\">\n" +
                       "  <div style='margin-bottom: 5px' class=\"col-sm-6\">\n" +
                       "    <div class=\"card\">\n" +
                       "      <div class=\"card-body\">\n" +
                       "        <h5 class=\"card-title\">"+value.subjectName+"</h5>\n" +
                       "        <p class=\"card-text\"></p>\n" +
                       "        <a href=\"/subjects/view/"+value.subjectId+"\" class=\"btn btn-primary\">Go to subject</a>\n" +
                       "      </div>\n" +
                       "    </div>\n" +
                       "  </div>\n";
                        if(index===rsLength){
                            strc += "</div>";
                        }

                }else{
                    strc += "<div class=\"col-sm-6\">\n" +
                        "    <div class=\"card\">\n" +
                        "      <div class=\"card-body\">\n" +
                        "        <h5 class=\"card-title\">"+value.subjectName+"</h5>\n" +
                        "        <p class=\"card-text\"></p>\n" +
                        "        <a href=\"/subjects/view/"+value.subjectId+"\" class=\"btn btn-primary\">Go to subject</a>\n" +
                        "      </div>\n" +
                        "    </div>\n" +
                        "  </div>\n" +
                        "</div>";
                }

            });
           $('#container').html(strc);
       }

   })
});
