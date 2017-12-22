//отправляет запрос на авторизацию при нажатии кнопки
$(document).ready(function(){
    $('#submitForm').click(function () {
        var data = {
            'login': $('#login').val(),
            'password': getHash($('#password').val()),
            'remember':  $("#remember").prop("checked")
        };
        sendRequest(document.location.href + '/login', data, '#divForm', document.location.href);
    });

});