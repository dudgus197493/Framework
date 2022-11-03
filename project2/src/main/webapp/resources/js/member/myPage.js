// 비밀번호 변경 유효성 검사 ------------------------------------------------------------------------
const changePwForm = document.getElementById("changePwForm");

if(changePwForm != null) { // changePwForm 요소가 존재할 때
    changePwForm.addEventListener("submit", function(event)
    
    {   

        // ** 이벤트 핸들러 매개변수 event || e **
        // -> 현재 발생한 이벤트 정보를 가지고 있는 event 객체가 전달됨.
        console.log(event);

        // 비밀번호 변경에 사용되는 input 요소 모두 얻어오기
        const currentPw = document.getElementById("currentPw");
        const newPw = document.getElementById("newPw");
        const newPwConfirm = document.getElementById("newPwConfirm");

        // 현재 비밀번호가 작성되지 않았을 때
        if(currentPw.value.trim().length == 0) {
            alertAndFocus(currentPw, "현재 비밀번호를 입력해주세요.");

            // return false; 
            // --> 인라인 이벤트 모델 onsubmit = "return 함수명()"; 에서만 가능

            event.preventDefault(); // event 요소를 이동 시키는것은 권장 x
            // -> 이벤트를 수행하지 못하게하는 함수
            // --> 기본 이벤트 삭제
            return;
        }

        // 새 비밀번호가 작성되지 않았을 때
        if(newPw.value.trim().length == 0) {
            
            alertAndFocus(newPw, "새 비밀번호를 입력해주세요.");
            
            // form 제출 막기
            event.preventDefault(); 
            return;
        }

        // 새 비밀번호 확인이 작성되지 않았을 때 
        if(newPwConfirm.value.trim().length == 0) {
            // 경고 메세지
            alertAndFocus(newPwConfirm, "새 비밀번호 확인을 입력해주세요.");
            
            // form 제출 막기
            event.preventDefault();
            return;
        }

        // 비밀번호 정규식 검사 ------------------------------------------------------------------------------------

        // ---------------------------------------------------------------------------------------------------------

        // 새 비밀번호, 새 비밀번호 확인이 같은지 검사
        if(newPw.value != newPwConfirm.value)  {
            alert("새 비밀번호가 일치하지 않습니다.");

            // 새 비밀번호 확인 인풋에 포커스
            newPwConfirm.focus();
            event.preventDefault();     // 기본 이벤트 제거
            return;                     // 함수 종료
        }
    });

    // 경고창 출력 + 포커스이동 + 값 삭제
    function alertAndFocus(input, str) {
        alert(str);
        input.focus();
        input.value = "";
    }
}


// 회원 탈퇴 유효성 검사 -----------------------------------------------------------------------------------------
// - 인라인 이벤트 모델 또는 표준 이벤트 모델

// 1) 비밀번호 미작성 -> "비밀번호를 입력해주세요" alert 출력 후 
//                       포커스 이동 (내용도 같이 삭제)

// 2) 동의 체크가 되지 않은 경우 -> "탈퇴 동의하시면 체크를 눌러주세요." alert 출력 후
//                       포커스 이동

// 3) 1번, 2번이 모두 유효할 때
//    정말 탈퇴를 진행할 것인지 확인하는 confirm 출력
//    (확인 클릭 -> 탈퇴 / 취소 -> 탈퇴 취소)

// form 가져오기
const memberDeleteForm = document.getElementById("memberDeleteForm");

// form에 submit 이벤트 리스너 달기
if(memberDeleteForm != null) {
    memberDeleteForm.addEventListener("submit", function(event) {

        console.log(this); // 이벤트 리스너 안에서의 this 는 event.currentTarget과 같다.

        // 비밀번호 input 가져오기, 약관 checkbox 가져오기
        const memberPw = document.getElementById("memberPw");
        const agree = document.getElementById("agree");

        // 비밀번호 미작성 시
        if(memberPw.value.trim().length == 0) {
            // input 포커스, alert 메세지 출력
            alertAndFocus(memberPw, "비밀번호를 입력해주세요");

            // input 초기화
            memberPw.value = "";
            
            // form 제출 막기
            event.preventDefault();

            // 함수 종료
            return;
        } 

        // 동의 체크가 되지 않은 경우
        if(!agree.checked) {
            alertAndFocus(agree, "탈퇴 동의하시면 체크를 눌러주세요.");

            // form 제출 막기
            event.preventDefault();

            // 함수 종료
            return;
        }

        // 3) 1번, 2번이 모두 유효할 때
        //    정말 탈퇴를 진행할 것인지 확인하는 confirm 출력
        //    (확인 클릭 -> 탈퇴 / 취소 -> 탈퇴 취소)
        if(!confirm("정말 탈퇴하시겠습니까?")) {
            event.preventDefault();
        }
    })
}


// 인라인 모델로 탈퇴처리
function inlineValidate() {

    // 비밀번호 input 가져오기, 약관 checkbox 가져오기
    const memberPw = document.getElementById("memberPw");
    const agree = document.getElementById("agree");

    // 비밀번호 미작성 시
    if(memberPw.value.trim().length == 0) {
        // input 포커스, alert 메세지 출력
        alertAndFocus(memberPw, "비밀번호를 입력해주세요");

        // input 초기화
        memberPw.value = "";

        // 함수 종료
        return false;
    } 

    // 동의 체크가 되지 않은 경우
    if(!agree.checked) {
        alertAndFocus(agree, "탈퇴 동의하시면 체크를 눌러주세요.");

        // 함수 종료
        return false;
    }

    // 3) 1번, 2번이 모두 유효할 때
    //    정말 탈퇴를 진행할 것인지 확인하는 confirm 출력
    //    (확인 클릭 -> 탈퇴 / 취소 -> 탈퇴 취소)
    if(!confirm("정말 탈퇴하시겠습니까?")) {
        return false;
    }

    return true;
}

function alertAndFocus(input, message) {
    alert(message);
    input.focus();
}