var frmElem = document.querySelector('#frm');
var submitBtnElem = document.querySelector('#submitBtn');

submitBtnElem.addEventListener('click',function (){

   if(frmElem.upw.value.length < 5) {
      alert('비밀번호를 5자 이상 작성해 주세요.');
   } else if(frmElem.changedUpw.value.length < 5) {
      alert('변경하실 비밀번호를 5자 이상 작성해 주세요.');
   } else if(frmElem.changedUpw.value != frmElem.changedUpwConfirm.value) {
      alert('변경 비밀번호와 확인비밀번호가 다릅니다.');
   } else {
      frmElem.submit();
   }
});