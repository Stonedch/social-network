function check() {
    if (document.getElementById("password").value ==
        document.getElementById("confirm_password").value) {
        document.getElementById("submit").disabled = false;
    } else {
        document.getElementById("submit").disabled = true;
    }
}
