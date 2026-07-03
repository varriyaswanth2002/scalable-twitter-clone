<!DOCTYPE html>
<html lang="en">
<link rel="icon" type="image/png" href="/static/images/favicon.ico/">
<head>
   <link rel="stylesheet" type="text/css" href="/static/css/signup.css">
   <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

</head>

<body>
  <div class="signup-container">
    <form class="signup-form">
      <h2>Login to Your Account</h2>
      <p> Welcome to the Twitter </p>

      <input id="signup-email" type="email" name="email" placeholder="Email"><br>

      <input id="signup-password" type="password" name="name" placeholder="Password"><br>
      <p style="color:red;display:none" id="signup-error"></p>
      <button type="button" id="btn-signup">LOGIN</button>
    </form>
  </div>

  <script>
    function validateSignupForm(){
       var email=$("#signup-email").val();
       var password=$("#signup-password").val();
       var error="";

       if(!email)
       {
        error+=" Email is empty. ";
       }
       if(!password){
         error+=" Password is empty. ";
       }
       if(password.length<=3){
         error+="Password length must be greater than 3 characters";
       }
       $("#signup-error").html(error);//adding the error to the id which is just above the submit button in the html code
       if(error.length>0){
          return false;
       }
       return true;
    }

    $("#btn-signup").click(function(){
      var isFormValid=validateSignupForm();
      if(isFormValid){
         $("#signup-error").hide();

         var email=$("#signup-email").val();
         var password=$("#signup-password").val();
         var user={"email":email, "password":password}
         $.ajax({
           type: "POST",
           url: "/login/welcome",
           data: JSON.stringify(user),
           success: function(response){
             if(!!response){
               if(response.isLoggedIn === true){
                 location.href = "/welcome";
               }
               else{
                 $("#signup-password").val("");//re-setting the email to be blank if wrong combination
                 $("#signup-error").html(response.message);
                 $("#signup-error").show();
               }
             }
           },
           contentType:"application/json"
         });
      }
      else{
         $("#signup-error").show();
      }
    });
  </script>
</body>

</html>