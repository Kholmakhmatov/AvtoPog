window.onload
{
        let seccesMessage = document.getElementById("successMessage");
        toast = document.querySelector(".toast");
    (closeIcon = document.querySelector(".close")),
        (progress = document.querySelector(".progress"));

    let timer1, timer2;
    setTimeout(() => {
        progress.classList.remove("active");
        seccesMessage.style.display="none";
    }, 10000);
    closeIcon.addEventListener("click", () => {
        toast.classList.remove("active");

        setTimeout(() => {
            progress.classList.remove("active");
            seccesMessage.style.display="none";
        }, 300);

        clearTimeout(timer1);
        clearTimeout(timer2);
    });

}
