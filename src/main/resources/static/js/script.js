// console.log("hii..");

// const toggleButton = () => {
//     if ($('.sidebar').is(":visible")) {
//         $('.sidebar').css("display", "none");
//         $('.content').css("margin-left", "0%")
//     } else {
//         $('.sidebar').css("display", "block");
//         $('.content').css("margin-left", "22%")
//     }
// }

$(document).ready(function () {
    $('.crossbtn').on('click',function () {
        // alert("hiii");
        if ($('.sidebar').is(":visible")) {
            $('.sidebar').css("display", "none");
            $('.content').css("margin-left", "0%")
        } else {
            $('.sidebar').css("display", "block");
            $('.content').css("margin-left", "22%")
        }
    });
});

$(document).ready(function () {
    $('.crossbtn').on('touchstart',function () {
        // alert("hiii");
        if ($('.sidebar').is(":visible")) {
            $('.sidebar').css("display", "none");
            $('.content').css("margin-left", "0%")
        } else {
            $('.sidebar').css("display", "block");
            $('.content').css("margin-left", "22%")
        }
    });
});




const search = () => {
    //console.log("hiii");

    let query = $("#search").val();

    if (query == '') {
        $(".search").hide();
    } else {
        let url = `http://localhost:8383/search/${query}`;
        fetch(url)
            .then((responce) => {
                return responce.json();
            })
            .then((data) => {
                console.log(data);

                let text = '<div class="list-group">'
                data.forEach(contact => {
                    text += `<a href="/user/${contact.contactId}/contact" class="list-group-item list-group-item-action">${contact.name}</a>`
                });

                text += '</div>'
                $('.search').html(text)
                $('.table').css('top', '50px')
                $('.page-nav').css('top', '50px')
            });


        $(".search").show();
    }
}