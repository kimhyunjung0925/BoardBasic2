let cmtNewFrmElem = document.querySelector('#cmtNewFrm');
if(cmtNewFrmElem) {
    let newSubmitBtnElem = cmtNewFrmElem.querySelector('input[type=submit]');
    newSubmitBtnElem.addEventListener('click', function (e) {
        e.preventDefault();

        if (cmtNewFrmElem.ctnt.value.length === 0) {
            alert('댓글 내용을 작성 해 주세요.');
        }

        var param = {
            iboard: cmtListContainerElem.dataset.iboard,
            ctnt: cmtNewFrmElem.ctnt.value
        };

        var url = '/board/cmt?proc=ins'
        fetch(url, {
            method: 'post',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(param)
        }).then(function (res) {
            return res.json();
        }).then(function (data) {
            switch (data.result) {
                case 0:
                    alert('댓글 달기를 할 수 없습니다.')
                    break;
                case 1:
                    cmtNewFrmElem.ctnt.value = '';
                    cmtListContainerElem.innerHTML = null;
                    getList();
                    break;
            }
        }).catch(function (err) {
            console.log(err);
            alert('댓글 달기에 실패하였습니다.');
        });
    });
}

//---------------댓글 수정
//댓글 수정 창 뜨면 취소 눌렀을 때 창 꺼지는 것
var cmtModContainerElem = document.querySelector('.cmtModContainer');
var btnCancelElem = cmtModContainerElem.querySelector('#btnCancel');
btnCancelElem.addEventListener('click',function () {
    cmtModContainerElem.style.display = 'none';
    var selectedTrElem = document.querySelector('.cmt_selected');
    selectedTrElem.classList.remove('cmt_selected');

});

var cmtModFrmElem = cmtModContainerElem.querySelector('#cmtModFrm');
var submitBtnElem = cmtModFrmElem.querySelector('input[type=submit]');
submitBtnElem.addEventListener('click', function (e){
    e.preventDefault();
    var url = '/board/cmt?proc=upd';

    var param = {
        'icmt' : cmtModFrmElem.icmt.value,
        'ctnt' : cmtModFrmElem.ctnt.value
    };

    fetch(url, {
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(param)
    }).then(function (res){
        return res.json();
    }).then(function (data){
        switch (data.result){
            case 0:
                alert('댓글 수정을 할 수 없습니다.');
                break;
            case 1:
                modCtnt(param.ctnt);
                btnCancelElem.dispatchEvent(new Event('click'));
                break;
        }
    }).catch(function (err){
        console.log(err);
        alert('댓글 수정에 실패하였습니다.');
    });
});

function modCtnt(ctnt){
    var selectedTrElem = document.querySelector('.cmt_selected');
    var tdCtntElem = selectedTrElem.children[0];
    tdCtntElem.innerText = ctnt;
}

//--------댓글리스트
var cmtListContainerElem = document.querySelector('#cmtListContainer');
var loginUserPk = cmtListContainerElem.dataset.loginuserpk === '' ? 0: Number(cmtListContainerElem.dataset.loginuserpk);

if(cmtListContainerElem){
    function openModForm(icmt, ctnt) {
        cmtModContainerElem.style.display = 'flex';
        cmtModFrmElem.icmt.value = icmt;
        cmtModFrmElem.ctnt.value = ctnt;
    }

    function getList(){
        var iboard = cmtListContainerElem.dataset.iboard;
        var url = '/board/cmt?iboard=' + iboard;

        fetch(url).then(function (res){
            return res.json();
        }).then(function (data){
            console.log(data);
            displayCmt2(data);
        }).catch(function (err){
          console.log(err);
        })
    }

    function displayCmt2(data){
        var tableElem = document.createElement('table');
        tableElem.innerHTML = `
            <tr>
                <th>내용</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>비고</th>
            </tr>
        `; //템플릿 리터널
        cmtListContainerElem.appendChild(tableElem);


        console.log('loginUserPk :' + loginUserPk);

        data.forEach(function (item){
            var tr = document.createElement('tr');
            var ctnt = item.ctnt.replaceAll('<','&lt;').replaceAll('>','&gt');

            if(loginUserPk === item.writer ){
                var cmtWn = '(내댓글)'
            } else if(loginUserPk !== item.writer){
                var cmtWn =''
            }

            // if( === item.writer ){
            //     var boardWn = '(작성자)'
            // } else if( !== item.writer) {
            //     var boardWn = ''
            // }
                tr.innerHTML = `
                <td>${ctnt}</td>
                <td>${item.writerNm} ${cmtWn}</td>
                <td>${item.rdt}</td>
            `;
            tableElem.appendChild(tr);

            var lastTd = document.createElement('td');
            tr.appendChild(lastTd);

            if(loginUserPk === item.writer ) {
                var btnMod = document.createElement('button');
                btnMod.innerText = '수정';
                btnMod.addEventListener('click',function (){
                    tr.classList.add('cmt_selected');
                    var ctnt = tr.children[0].innerText;
                    openModForm(item.icmt, ctnt);
                });
//---------------------- 댓글삭제
                var btnDel = document.createElement('button');
                btnDel.innerText = '삭제';
                btnDel.addEventListener('click', function (){
                   if(confirm('삭제 하시겠습니까?')) {
                       var param = {
                           icmt: item.icmt
                       };

                       var url = '/board/cmt?proc=del';
                       fetch(url, {
                           method: 'post',
                           headers: {'Content-Type': 'application/json'},
                           body: JSON.stringify(param)
                       }).then(function (res) {
                           return res.json();
                       }).then(function (data){
                           switch (data.result) {
                               case 0:
                                   alert('댓글 삭제를 할 수 없습니다.');
                                   break;
                               case 1:
                                   tr.remove();
                                   break;
                           }
                       }).catch(function (err){
                           console.log(err);
                           alert('댓글 삭제에 실패하였습니다.');
                       });
                   }

                });
                lastTd.appendChild(btnMod);
                lastTd.appendChild(btnDel);
            }
        });
    }

    // function displayCmt(data){
    //     var tableElem = document.createElement('table');
    //
    //     var tr = document.createElement('tr');
    //     var th1 = document.createElement('th');
    //     th1.innerText = '내용';
    //     var th2 = document.createElement('th');
    //     th2.innerText = '작성자';
    //     var th3 = document.createElement('th');
    //     th3.innerText = '작성일';
    //     var th4 = document.createElement('th');
    //     th4.innerText = '비고';
    //     tr.appendChild(th1);
    //     tr.appendChild(th2);
    //     tr.appendChild(th3);
    //     tr.appendChild(th4);
    //
    //     tableElem.appendChild(tr);
    //     cmtListContainerElem.appendChild(tableElem);
    // }
     getList();

    /*로그인을 하지 않았을 때 댓글내용까지 찍어주지 않길래 보니
    input type=submit가 null이라서 오류가 터짐
    jsp에서 로그인을 하지 않았을 때 댓글을 달 수 없게 설정해놔서
    submit이 만들어지지 않아서 null이 뜨는듯?
    그래서 get list 다 찍고 if절 줘서 맨밑에 내려봄
    이방밥보다는 위에서 쉽게하는 방법 찾음!
     */

    //--------------댓글 달기
    //     let cmtNewFrmElem = document.querySelector('#cmtNewFrm');
    //     let newSubmitBtnElem = cmtNewFrmElem.querySelector('input[type=submit]');
    //     newSubmitBtnElem.addEventListener('click', function (e) {
    //         e.preventDefault();
    //
    //         if (cmtNewFrmElem.ctnt.value.length === 0) {
    //             alert('댓글 내용을 작성 해 주세요.');
    //         }
    //
    //         var param = {
    //             iboard: cmtListContainerElem.dataset.iboard,
    //             ctnt: cmtNewFrmElem.ctnt.value
    //         };
    //
    //         var url = '/board/cmt?proc=ins'
    //         fetch(url, {
    //             method: 'post',
    //             headers: {'Content-Type': 'application/json'},
    //             body: JSON.stringify(param)
    //         }).then(function (res) {
    //             return res.json();
    //         }).then(function (data) {
    //             switch (data.result) {
    //                 case 0:
    //                     alert('댓글 달기를 할 수 없습니다.')
    //                     break;
    //                 case 1:
    //                     cmtNewFrmElem.ctnt.value = '';
    //                     cmtListContainerElem.innerHTML = null;
    //                     getList();
    //                     break;
    //             }
    //         }).catch(function (err) {
    //             console.log(err);
    //             alert('댓글 달기에 실패하였습니다.');
    //         });
    //     });
    //
}