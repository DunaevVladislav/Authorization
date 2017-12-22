//отправляет запрос на регистрацию при нажатии кнопки
$(document).ready(function(){
    $('#submitForm').click(function () {
        $('#errorList').remove();
        var password = $('#password').val();
        var repPassowrd = $('#repPassword').val();

        var form = $('#divForm');

        if (password.length < 7){
            form.append('<div id="errorList">');
            $('#errorList').append('<span>Длина пароля должна быть больше 7 символов</span><br>');
            form.append('</div>')
        }else if (password !== repPassowrd){
            form.append('<div id="errorList">');
            $('#errorList').append('<span>Введенные пароли не совпадают</span><br>');
            form.append('</div>')
        }else{

            var data = {
                'login': $('#login').val(),
                'email': $('#email').val(),
                'password': getHash(password),
                'repPassword': getHash(repPassowrd)
            };
            sendRequest(document.location.href + '/../addUser', data, '#divForm', document.location.href+'/../');
        }

    });


});