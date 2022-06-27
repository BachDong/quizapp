var listPath = [];
$(document).ready(function () {

    $("#submitButton").click(function (event) {
        listPath = [];
        // Stop default form Submit.
        event.preventDefault();
        $("#result").html('');
        // Call Ajax Submit.
        ajaxSubmitForm();
    });
});
function ajaxSubmitForm() {
    // Get form
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    $("#submitButton").prop("disabled", true);
    var _csrf = $('meta[name="_csrf"]').attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, _csrf);
        },
        type: "POST",
        enctype: 'multipart/form-data',
        url: "/rest/uploadMultiFiles",
        data: data,
        // prevent jQuery from automatically transforming the data into a query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function (result, textStatus, jqXHR) {
            listPath = result;
            $("#result").html(result);
            $("#submitButton").prop("disabled", false);
            $('#fileUploadForm')[0].reset();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $("#result").html(jqXHR.responseText);
            console.log("ERROR : ", jqXHR.responseText);
            $("#submitButton").prop("disabled", false);

        }
    });

}
$(document).on("click", "#btn-import", function (e) {
    listPath = [];
    $("#modalImportExcel").modal('show');
    $('#i_fileViaURL').val('');
    $("#result").html('');
})

$(document).on("click", "#btn-import-now", function (e) {
    e.preventDefault();
    var _csrf = $('meta[name="_csrf"]').attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    var urlLocalOrDownload=$('#i_fileViaURL').val();
   var  upfile=$('#upfile').val();
    if (urlLocalOrDownload!="" && upfile=="") {
        listPath.push(urlLocalOrDownload);
    }
    if( $("#result").text()==""&& urlLocalOrDownload==""){
        alert("You must choise solution to import !")
        return false;
    }

    $("#messageImport").text("");
    $("#messageImport").text("Please waiting to import....");
    var Paths={
            "listPath": listPath
        }
    $.ajax({
        url: "/admin/question-management/importExcelQuestion",
        contentType: 'application/json;',
        dataType: 'json',
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, _csrf);
        },
        data:
            JSON.stringify(Paths),
        method: "Post",
        statusCode: {
            200: function (result) {
                fillData("/user/question/getAll", 5, 0, "DESC", "");
                $("#messageImport").text('');
                alert('Import Success ' + result + ' record(s)!');
                $("#modalImportExcel").modal('hide');
            },
            500: function () {
                alert("Sorry Can't Import !")
                $("#messageImport").text("Please check again file excel.....");
            },
            400: function () {
                alert("Please check again your excel !")
                $("#messageImport").text("Please check again file excel.....");
            }
        },
    });

})