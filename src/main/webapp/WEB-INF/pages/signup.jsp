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
      <h2>Create Your Account</h2>
      <p> Welcome to the Twitter </p>

      <input id="signup-name" type="text" name="name" placeholder="Full Name"><br>

      <input id="signup-email" type="email" name="email" placeholder="Email"><br>

      <input id="signup-password" type="password" name="name" placeholder="Password"><br>
      <p style="color:red;display:none" id="signup-error"></p>
      <button type="button" id="btn-signup">Sign Up</button>
    </form>
  </div>

  <script>
    function validateSignupForm(){
       var name=$("#signup-name").val();
       var email=$("#signup-email").val();
       var password=$("#signup-password").val();
       var error="";
       if(!name){
         error+=" Name is empty. ";
       }
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
       $("#signup-error").html(error);
       if(error.length>0){
          return false;
       }
       return true;
    }

    $("#btn-signup").click(function(){
      var isFormValid=validateSignupForm();
      if(isFormValid){
         $("#signup-error").hide();
         var name=$("#signup-name").val();
         var email=$("#signup-email").val();
         var password=$("#signup-password").val();
         var user={"name":name, "email":email, "password":password}
         $.ajax({
           type: "POST",
           url: "/signup",
           data: JSON.stringify(user),
           success: function(response){
             if(!!response){
               $("#signup-name").val("");
               $("#signup-email").val("");
               $("#signup-password").val("");
               alert(response.message);
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