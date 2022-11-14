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


// ----------------------------------------------------------------------------------
// 프로필 수정

const profileImage = document.getElementById("profile-image");
const deleteImage = document.getElementById("delete-image");
const imageInput = document.getElementById("image-input");

// 초기 프로필 이미지 상태를 저장하는 변수
// (true : 업로드된 이미지 있음, false : 기본 이미지)
let initCheck;

// 이미지가 업로드 되었거나 삭제되었음을 나타내는 변수
// (-1 : 초기값(취소), 0 : 프로필 삭제(x버튼 클릭), 1 : 새 이미지 업로드)
let deleteCheck = -1;

const originalImage = profileImage.getAttribute("src");

if(profileImage != null) {      // 프로필 수정 화면일 때

    // 해당 화면 진입 시 프로필 이미지 상태를 저장(initCheck)
    if(profileImage.getAttribute("src") == "/resources/images/user.png") {
        // 기본이미지인 경우
        initCheck = false;
    } else {
        initCheck = true;
    }


    // 이미지가 선택되었을 때 미리보기

    // * input type="file" 요소는 값이 없을 때 ''(빈칸)
    // * input type="file" 요소는 이전에 선택한 파일이 있어도 취소하면 다시 ''(빈칸)
    // * input type="file" 요소로 파일을 선택하면 change 이벤트가 발생한다.

    imageInput.addEventListener("change", e => {        // 파일인풋의 값이 변하면
        // e.target : 이벤트가 발생한 요소 (== imageInput)
        // * 화살표 함수에서 this는 window 객체를 의미

        // 선택된 파일의 목록
        console.log(e.target.files);
        console.log(e.target.files[0]);

        // 페이크패스 때문에 이미지 미리보기에 필요한 경로얻어오기에 제한이있음
        // 해결
        // 1. 서버나 클라우드에 업로드후 경로를 가져옴
        // 2. 브라우져에 임시 업로드후 경로를 가져옴

        // 선택된 파일이 있을 경우
        if(e.target.files[0] != undefined) {
            const reader = new FileReader();
            // FileReader (파일 읽는 객체)
            // - 웹 어플리케이션이
            //   비동기적으로 데이터를 읽기 위하여
            //   읽을 파일을 가리키는 File 객체
    
            // - 읽어드린 파일을 사용자 컴퓨터에 저장할 수 있다.
    
    
            // input[type='file']에 파일을 선택시 파일의 정보만 가져옴
    
            // FileReader.readAsDataURL("파일정보")
            // -> 지정된 파일을 읽기 시작함
            reader.readAsDataURL(e.target.files[0]);
    
            // FileReader.onload : 파일 읽기가 완료되었을 때 (고전이벤트 모델 형식)
            reader.onload = event => {
                console.log(event.target);
    
                // event.target.result : 읽어진 이미지 결과(실제 이미지 파일)의 경로
    
    
                profileImage.setAttribute("src", event.target.result);
                deleteCheck = 1;
            }
        } else {            // 선택된 파일이 없을 경우 (취소가 눌러진 경우)

            // 초기 이미지로 다시 변경
            profileImage.setAttribute("src", originalImage);
            deleteCheck = -1;
        }
    });

    // x버튼 클릭 시 -> 기본이미지로 변경
    deleteImage.addEventListener("click", e=>{
                                    // 기본 이미지 경로
        profileImage.setAttribute("src", "/resources/images/user.png");
        imageInput.value = "";
        deleteCheck = 0;
    });
}


function profileValidate(){

    // 이미지가 없다 -> 있음
    if(!initCheck && deleteCheck == 1) {
        return true;
    }

    // 있음 -> 없음 (x 버튼)
    if(initCheck && deleteCheck == 0) {
        return true;
    }

    // 있음 -> 있음 (새로운 이미지 업로드)
    if(initCheck && deleteCheck == 1) {
        return true;
    }

    alert("이미지 변경 후 클릭하세요");
    return false;
}