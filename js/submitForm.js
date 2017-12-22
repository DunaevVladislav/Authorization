//отправляет POST-запрос на url path, с данными data
//выводит ответ в output и редиректит по пути pathOut в случае отсутсвия ответа
function sendRequest(path, data, output, pathOut) {
    $.ajax({
        url: path,
        type: 'post',
        data: data,
        dataType: 'json',
        contentType:'application/x-www-form-urlencoded',
        beforeSend: function(){
            removeResponse(output)
        },
        success: function (response) {
            inputResponse(response, output, pathOut);
        },
        error: function () {
            window.location.href = pathOut;
        }
    });
}

//выводит ответ response в output и редиректит по пути pathOut в случае пустого ответа
function inputResponse(response, output, pathOut) {
    if (response.length === 0){
        window.location.href = pathOut;
        return;
    }
    var form = $(output);
    form.append('<div id="errorList">');
    response.forEach(function (item) {
        $('#errorList').append('<span>' + item + '</span><br>');

    });
    form.append('</div>')
}

//удаляет ответ сервера
function removeResponse(output){
    $('#errorList').remove();
}
