$(".follow-member").click( function(event){
    var button = this;
    var memberId = event.currentTarget.getAttribute("member-id");
    var isFollowed = $(button).hasClass("followed-button");
    if(isFollowed) {
        $.ajax({
            type: "POST",
            url: "/user/unfollow-member/" + memberId,
            success: function (response) {
                if (!!response) {
                    $(button).removeClass("followed-button");//this will remove the class added to the button, inspect and check the class name for follow and followed buttons to get clarity!
                    button.querySelector("span").innerText = "Follow";
                    alert(response);
                }
            },
            contentType: 'application/json'
        });
    }
    else {
        $.ajax({
            type: "POST",
            url: "/user/follow-member/" + memberId,
            success: function (response) {
                if (!!response) {
                    $(button).addClass("followed-button");//this added class will help in finding if a button is already followed or not
                    button.querySelector("span").innerText = "Followed";
                    alert(response);
                }
            },
            contentType: 'application/json'
        });
    }
});