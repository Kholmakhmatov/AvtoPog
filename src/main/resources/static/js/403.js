function reconnect(){
  setTimeout(function(){
    document.querySelector('.first').classList.toggle('num-four');
    document.querySelector('.second').classList.toggle('num-zero');
    document.querySelector('.third').classList.toggle('num-three');
    document.querySelector('.container').classList.toggle('_403');
    document.querySelector('.redirect').classList.toggle('visible');
    document.querySelector('.redirect2').classList.toggle('visible');
  }, 500);
}
function connect(){

  window.location.replace("/");
}
function back(){
 window.history.go(-1);
}


window.addEventListener('load', reconnect);