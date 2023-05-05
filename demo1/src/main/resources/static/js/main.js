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
inputs.forEach((input) => {
  input.addEventListener("focus", focusFunc);
  input.addEventListener("blur", blurFunc);
});

let passwordInput = document.getElementById("passwordInput");
let confirmPasswordInput = document.getElementById("confirmPasswordInput");
let passwordMessage = document.getElementById("passwordMessage");

function check() {
  let password = passwordInput.value;
  let confirmPassword = confirmPasswordInput.value;

  if (password.length < 8) {
    passwordMessage.innerHTML = "Password must be at least 8 characters long";
  } else if (password.length > 16) {
    passwordInput.value = password.substring(0, 16);
    passwordMessage.innerHTML = "Password must be at most 16 characters long";
  } else {
    passwordMessage.innerHTML = "";
  }

  if (password.length === 0) {
    passwordMessage.innerHTML = "";
  }

  if (confirmPassword.length > 0 && password !== confirmPassword) {
    passwordMessage.innerHTML = "Passwords do not match";
  } else if (confirmPassword.length > 0 && password === confirmPassword) {
    passwordMessage.innerHTML = "Passwords match";
    passwordMessage.style.color = "green";
  }
}
