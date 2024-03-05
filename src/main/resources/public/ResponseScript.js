function msg() {
    let val = document.getElementById("val").value;
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        document.getElementById("msg").innerHTML =
            this.responseText;
    };
    xhttp.open("GET", "/logservicefacade");
    xhttp.send();
}