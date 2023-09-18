const inputs = document.querySelectorAll(".input");

function focusFunc() {
  let parent = this.parentNode.parentNode;
  parent.classList.add("focus");
}

function blurFunc() {
  let parent = this.parentNode.parentNode;
  if (this.value == "") {
    parent.classList.remove("focus");
  }
}

function haveNums(input) {
  return /\d/.test(input.value);
}

function haveUpperCaseLetters(input) {
  return /[A-Z]/.test(input.value);
}

function haveLowerCaseLetters(input) {
  return /[a-z]/.test(input.value);
}

inputs.forEach((input) => {
  input.addEventListener("focus", focusFunc);
  input.addEventListener("blur", blurFunc);
});

var signupBtn = document.getElementById("signupBtn");
var passwordInput = document.getElementById("passwordInput");
var confirmPasswordInput = document.getElementById("confirmPasswordInput");
var passwordMessage = document.getElementById("passwordMessage");
var usernameInput=document.getElementById("username");
function check() {
  password = passwordInput.value;
  confirmPassword = confirmPasswordInput.value;
  username=usernameInput.value;

  passwordMessage.innerHTML = "";
  passwordMessage.style.color = "red";
  signupBtn.setAttribute("disabled", true);
  // Проверка наличия чисел в пароле
  if (!haveNums(passwordInput)) {
    passwordMessage.innerHTML += "Password requires a numbers<br>";
  }
  if (!haveUpperCaseLetters(passwordInput)) {
    passwordMessage.innerHTML += "Password requires a upper letters<br>";
  }
  if (!haveLowerCaseLetters(passwordInput)) {
    passwordMessage.innerHTML += "Password requires a lower letters<br>";
  }
  if (username.length < 8) {
    passwordMessage.innerHTML += "Username must be 8 or more characters<br>";

  } else if (username.length > 16) {
    passwordMessage.innerHTML += "Username must be 16 or less characters<br>";
  }
  if (password.length < 8) {
    passwordMessage.innerHTML += "Password must be 8 or more characters<br>";

  } else if (password.length > 16) {
    passwordMessage.innerHTML += "Password must be 16 or less characters<br>";
  }

  if (password != confirmPassword) {
    passwordMessage.innerHTML += "Passwords do not match<br>";
  }

  if (passwordMessage.textContent.length === 0) {
    passwordMessage.innerHTML += "Passwords match<br>";
    passwordMessage.style.color = "green";
    signupBtn.removeAttribute("disabled");
  }
}
