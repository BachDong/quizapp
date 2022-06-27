$(document).ready(function (e) {



});

// $(document).on("click",'#btnSearch',function (e) {
//     e.preventDefault();
//     var subjectId = $('#slcSubject').find(":selected").val();
//     var title = $('#txtSearch').val();
//     search(subjectId,title);
// });


function search(subjectId,title) {
    showData("/posts/search",5,0,"DESC","&subjectId="+subjectId+"&title="+title);
}

// function displaySubject() {
//     $.ajax({
//         url:"/subjects/getAllSubject",
//         method:"GET",
//         success:function (result) {
//             var slc =  $('#slcSubject');
//             slc.html("");
//             slc.append('<option value="0" selected>All...</option>');
//             $.each(result,function (index,value) {
//                 slc.append($('<option>',
//                     {
//                         value: value.subjectId,
//                         text : value.subjectName
//                     }));
//             })
//         }
//
//     })
// }


// $(document).on("click","#createNewPost",function (e) {
//     $('#postId').val("0");
//     $('#modelTitle').text("Create new post");
//     $('#postTitle').val("");
//     quill.root.innerHTML = "";
//     $('#btnAddOrUpdate').text("Create");
//     $('#postModal').modal('show');
// });
//
// $(document).on("submit",'#frmAddOrUpdatePost',function (e) {
//     e.preventDefault();
//     var postContent = quill.root.innerHTML;
//     var postTitle = $('#postTitle').val();
//     var courseId = $("input[name='courseId']:checked").val();
//     var _csrf = $("input[name='_csrf']").val();
//     var postId = $('#postId').val();
//     $.ajax({
//         url:"/admin/post-management/add",
//         method:"POST",
//         data:{postContent:postContent,postTitle:postTitle,courseId:courseId,_csrf:_csrf,postId:postId},
//         success:function (result) {
//             if(result.status===true){
//                 $('#errShow').text("");
//                 $('#postModal').modal('hide');
//                 showData("/posts/getAll",5,0,"DESC","");
//             }else{
//                 $('#errShow').text(result.msg);
//             }
//         }
//
//     })
//
// });

function showData(url,size,page,sort,query) {
    $('#postTable').find("tbody").html('<img src="/images/ajax-loader.gif">');
    $.ajax({
        url:url+"?size="+size+"&page="+page+"&sort="+sort+query,
        method:"GET",
        success:function (result) {
            var html = "";
            $.each(result.content,function (index,value) {
                var subjectName = "";
                if(value.course.subject.subjectName==null){
                    subjectName = value.course.subject;
                }else{
                    subjectName = value.course.subject.subjectName;
                }
                html += buildTrTablePost(value.postId,value.title,subjectName,value.course.courseName,value.user.userName,value.createDate,value.updateDate);
            });
            $('#postTable').find("tbody").html(html);
            $('#pagination').html(buildPagination(result.totalPages,page));
        }
    });
}

// $(document).on("click",".btnEditPost",function (e) {
//     e.preventDefault();
//
//     var postId = $(this).attr("data-id");
//     $.ajax({
//         url:"/posts/"+postId,
//         method:"GET",
//         success:function (result) {
//             $('#postId').val(postId);
//             $('#modelTitle').text("Update post : "+result.title);
//             $('#postTitle').val(result.title);
//             quill.root.innerHTML = result.content;
//             $('#course-'+result.course.courseId).prop("checked",true);
//             $('.nav-link').removeClass("active");
//             $("a[aria-controls='"+result.course.subject.subjectName+"']" ).addClass("active");
//             $('.tab-pane').removeClass("active");
//             $("div[id='"+result.course.subject.subjectName+"']" ).addClass("active");
//             $('#btnAddOrUpdate').text("Update");
//             $('#postModal').modal('show');
//         }
//     });
//
// });
//
// $(document).on("click",".btnDeletePost",function (e) {
//     e.preventDefault();
//
//     var postId = $(this).attr("data-id");
//     var result = confirm("Do you want to delete postId : "+postId+" ?");
//     if (result) {
//         $.ajax({
//             url:"/admin/post-management/delete/"+postId,
//             method:"GET",
//             success:function (result) {
//                 alert(result.msg);
//                 showData("/posts/getAll",5,0,"DESC","")
//             }
//         });
//     }
//
// });
//
// $(document).on("click",".page-link",function (e) {
//     e.preventDefault();
//     var page = $(this).attr("data-page");
//     var subjectId = $('#slcSubject').find(":selected").val();
//     var title = $('#txtSearch').val();
//     if(subjectId!=0 || title!=""){
//         showData("/posts/search",5,page,"DESC","&subjectId="+subjectId+"&title="+title);
//     }else{
//         showData("/posts/getAll",5,page,"DESC","");
//     }
//
// });
//
// $(document).on("click","#clearSearch",function (e) {
//     e.preventDefault();
//     var page = 0;
//     var subjectId = $('#slcSubject').val(0);
//     var title = $('#txtSearch').val("");
//     showData("/posts/getAll",5,page,"DESC","");
// });

// function buildPagination(totalPage,currentPage) {
//     var str = "";
//     for(i=0;i<totalPage;i++){
//         var active = "";
//         if(currentPage==i){
//             active = "active";
//         }
//         str+=' <li class="page-item '+active+'"><a class="page-link" data-page="'+i+'">'+(i+1)+'</a></li>';
//     }
//     return str;
// }
//
// function buildTrTablePost(postId,title,subject,course,author,createdDate,updateDate) {
//     return '<tr>\n' +
//         '                                    <th scope="row">'+title+'</th>\n' +
//         '                                    <td>'+subject+'</td>\n' +
//         '                                    <td>'+course+'</td>\n' +
//         '                                    <td>'+author+'</td>\n' +
//         '                                    <td>'+createdDate+'</td>\n' +
//         '                                    <td>'+updateDate+'</td>\n' +
//         '                                    <td>\n' +
//         '                                        <div class="row">\n' +
//         '                                            <a data-id="" href=""><i class="fa fa-eye"></i> </a> &nbsp; <a data-id="'+postId+'" class="btnEditPost" href=""><i class="fa fa-edit"></i></a> &nbsp;<a data-id="'+postId+'" class="btnDeletePost" href=""><i class="fa fa-trash"></i></a>\n' +
//         '                                        </div>\n' +
//         '                                    </td>\n' +
//         '\n' +
//         '                                </tr>';
// }
//
// function displaySubjectTab() {
//     $.ajax({
//         url:"/subjects/getAllSubject",
//         method:"GET",
//         success:function (result) {
//             $.each(result,function (index,value) {
//                 var active = "";
//                 if(index===0){
//                     active = "active";
//                 }
//                 $('#tabSubject').find("ul").append(buildTab(value.subjectName,active));
//                 $('#tabSubject').find(".tab-content").append(buildTabContent(value.subjectName,active));
//                 $.each(value.courses,function (index1,value1) {
//                     buildRadioCourse(value.subjectName,value1.courseId,value1.courseName);
//                 })
//             })
//         }
//
//     })
// }
//
// function buildTab(name,active) {
//     return '<li role="presentation" class="nav-item">\n' +
//         '                                                    <a class="nav-link '+active+'" data-toggle="tab" href="#'+name+'" role="tab" aria-controls="'+name+'">'+name+'</a>\n' +
//         '                                                </li>';
// }
//
// function buildTabContent(name,active) {
//     return '<div class="tab-pane '+active+'" id="'+name+'" role="tabpanel" >\n' +
//         '                                                    \n' +
//         '\n' +
//         '                                                </div>'
//
// }
//
// function buildRadioCourse(id,courseId,courseName) {
//     $('#'+id).append('<div class="form-check form-check-inline">\n' +
//         '  <input class="form-check-input" type="radio" name="courseId" id="course-'+courseId+'" value="'+courseId+'">\n' +
//         '  <label class="form-check-label" for="course-'+courseId+'">'+courseName+'</label>\n' +
//         '</div>');
//
// }