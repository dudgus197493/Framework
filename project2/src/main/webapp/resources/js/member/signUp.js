

// JS 객체를 이용한 유효성 검사 결과 저장 객체
// JS 객체 = {K:V, K:V, K:V, ...} (Map 형식)

// 변수명.key 또는 변수명["key"] 를 이용하면 객체 속성 접근 가능
const checkObj = {
    "memberEmail" : false,
    "memberPw" : false,
    "memberPwConfirm" : false,
    "memberNickname" : false,
    "memberTel" : false
};
checkObj["key"] = "이렇게도 추가?";
console.log(checkObj);

// 회원 가입 양식이 제출 되었을 때
document.getElementById("signUp-frm").addEventListener("submit", function(event){
    // 해당 인풋에 클래스 리스트에 confirm이 추가되어있으면 제출
    // --> 브라우저 개발자도구에서 악의적으로 class를 추가할 가능성이 있음

    // 이메일이 유효한가?
    // if(!emailCheck) { // 유효하지 않은 경우
    //     alert("이메일이 유효하지 않습니다.");
    //     event.preventDefault();
    // } --> 기존 생각한 방식

    // checkObj에 속성 중 하나라도 false가 있다면 제출 이벤트 제거
    
    // for in 구문 : 객체의 key값을 순서대로 접근하는 반복문
    // [작성법]
    // for(let 변수명 in 객체명 )
    // -> 객체에서 순서대로 key를 하나씩 꺼내 왼쪽 변수에 저장

    for(let key in checkObj) {

        let str;

        // checkObj 속성 하나를 꺼내 값을 검사했는데 false인 경우
        if(!checkObj[key]) { // 유효하지 않을 때
            
            switch(key) {
            case "memberEmail": str = "이메일이 유효하지 않습니다."; break;
            case "memberPw": str = "비밀번호가 유효하지 않습니다."; break;
            case "memberPwConfirm": str = "비밀번호 확인이 유효하지 않습니다."; break;
            case "memberNickname": str = "닉네임이 유효하지 않습니다."; break;
            case "memberTel": str = "전화번호가 유효하지 않습니다."; break;
            }

            alert(str);  // 대화상자 출력
            // 유효하지 않은 입력으로 포커스 이동
            document.getElementById(key).focus(); 

            // form 제출 막기
            event.preventDefault();

            // 더이상 비교 x
            return;
        }
    }
})

// 이메일 유효성 검사
const memberEmail = document.getElementById("memberEmail");
const emailMessage = document.getElementById("emailMessage");

// 'keyup' 이벤트로 사용시 마우스, 프로그램으로 입력되는것을 인식하지 못함
// input 이벤트 : input 태그에 입력이 되었을 경우(모든 입력 인식)
memberEmail.addEventListener("input", function(){

    // 문자가 입력되지 않은 경우
    // 빈 인풋에 띄어쓰기를 하면 초기화가 됨
    if(memberEmail.value.trim().length == 0) {
        emailMessage.innerText = "메일을 받을 수 있는 이메일을 입력해주세요.";
        memberEmail.value = "";

        // confirm, error 클래스 제거 -> 검정 글씨로 만들기
        memberEmail.classList.remove("confirm", "error");

        // 유효성 검사 확인 객체에 현재 상태 저장
        checkObj.memberEmail = false;
        return;
    }

    // 정규표현식을 이용한 유효성 검사
    const regEx = /^[A-Za-z\d\-\_]{4,}@[가-힣\w\-\_]+(\.\w+){1,3}$/;

    if(regEx.test(memberEmail.value)) {     // 유효한 경우

        emailMessage.innerText = "유효한 이메일 형식입니다."
        emailMessage.classList.add("confirm");
        emailMessage.classList.remove("error");

        checkObj.memberEmail = true;
    } else {                                // 유효하지 않은 경우
        emailMessage.innerText = "잘못된 이메일 형식입니다."
        emailMessage.classList.add("error");
        emailMessage.classList.remove("confirm");

        checkObj.memberEmail = false;
    }
});


// 비밀번호 유효성 검사
const memberPw = document.getElementById("memberPw");
const memberPwConfirm = document.getElementById("memberPwConfirm");
const pwMessage = document.getElementById("pwMessage");

// 비밀번호 입력 시
memberPw.addEventListener("input", function(){
    // 비밀번호가 입력되지 않은 경우
    if(memberPw.value.trim().length == 0) {
        pwMessage.innerText = "영어, 숫자, 특수문자(!,@,#,-,_) 6 ~ 20 글자 사이로 입력해주세요.";
        memberPw.value = "";
        pwMessage.classList.remove("confirm", "error"); // 검정 글씨로 변환

        checkObj.memberPw = false;
        return;
    }

    // 비밀번호 정규표현식으로 검사
    const regEx = /^[a-zA-z\d!@#\-_]{6,20}$/;
    // /^[a-z][a-zA-z\d!@#\-_]{5,19}$/; --> 앞 한글자를 제한할때 전체 길이의 범위를 1씩 줄임

    if(regEx.test(memberPw.value)){     // 유효한 비밀번호
        checkObj.memberPw = true;

        // 유효한 비밀번호 + 확인 작성 x
        if(memberPwConfirm.value.trim().length == 0) {
            pwMessage.innerText = "유효한 비밀번호 형식입니다.";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");
            
        } else { // 유효한 비밀번호 + 확인 작성 o -> 비밀번호, 비밀번호 확인 같은지 비교

            // 문제점 
            // 비밀번호 확인 인풋에서 유효성검사를 완료 후
            // 다시 비밀번호 인풋을 변경하면
            // 실제로는 다르지만 비밀번호 확인 유효성값은 true가 유지됨
        
            // 비밀번호가 입력 될 때
            if(memberPw.value == memberPwConfirm.value) { // 비밀번호 확인에 작성된 값과 일치하는 경우
                pwMessage.innerText = "비밀번호가 일치합니다.";
                pwMessage.classList.add("confirm");
                pwMessage.classList.remove("error");

                checkObj.memberPwConfirm = true;
        
            } else {    // 일치하지 않은 경우
        
                pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
                pwMessage.classList.add("error");
                pwMessage.classList.remove("confirm");

                checkObj.memberPwConfirm = false;
            }

        }
        
    } else {        // 유효하지 않음
        pwMessage.innerText = "비밀번호 형식이 유효하지 않습니다.";
        pwMessage.classList.add("error");
        pwMessage.classList.remove("confirm");
        checkObj.memberPw = false;
    }
});

// 비밀번호 확인 유효성 검사
memberPwConfirm.addEventListener("input", function(){

    // 비밀번호가 유효할 경우에만
    // 비밀번호 == 확인 같은지 비교
    if(checkObj.memberPw) {     // 비밀번호가 유효한 경우
        // 비밀번호, 비밀번호 확인 같은지 검사
        if(memberPw.value == memberPwConfirm.value){        // 같을 경우
            pwMessage.innerText = "비밀번호가 일치합니다.";
            pwMessage.classList.add("confirm");
            pwMessage.classList.remove("error");
            
            checkObj.memberPwConfirm = true;
        } else {                                            // 다를 경우
            pwMessage.innerText = "비밀번호가 일치하지 않습니다.";
            pwMessage.classList.add("error");
            pwMessage.classList.remove("confirm");
            
            checkObj.memberPwConfirm = false;
        }

    } else {    // 비밀번호가 유효하지 않은 경우
        checkObj.memberPwConfirm = false;
    }
});


// 닉네임 유효성 검사
const memberNickname = document.getElementById("memberNickname");
const nickMessage = document.getElementById("nickMessage");

memberNickname.addEventListener("input", function(){

    // 닉네임에 문자가 입력되지 않은 경우
    if(memberNickname.value.trim().length == 0) {
        nickMessage.innerText = "한글,영어,숫자로만 2~10글자";
        nickMessage.classList.remove("confirm", "error");
        memberNickname.value = "";

        checkObj.memberNickname = false;
        return;
    }

    // 닉네임 정규표현식 검사
    // \w == [A-za-z0-9_]
    const regEx = /^[가-힣\w]{2,10}$/;

    if(regEx.test(memberNickname.value)) {      // 유효한 경우
        
        // ** 닉네임 중복검사 코드 추가 예정 **
        nickMessage.innerText = "유효한 닉네임 형식 입니다."
        nickMessage.classList.add("confirm");
        nickMessage.classList.remove("error");

        checkObj.memberNickname = true;
    } else {                                    // 유효하지 않는 경우
        nickMessage.innerText = "유효하지 않는 닉네임 형식 입니다."
        nickMessage.classList.add("error");
        nickMessage.classList.remove("confirm");

        checkObj.memberNickname = false;
    }
});



// 전화번호 유효성 검사
const memberTel = document.getElementById("memberTel");
const telMessage = document.getElementById("telMessage");

memberTel.addEventListener("input", function(){
    // 문자가 입력되지 않은 경우
    if(memberTel.value.trim().length == 0) {
        telMessage.innerText = "전화번호를 입력해주세요. (-제외)";
        telMessage.classList.remove("confirm", "error");

        checkObj.memberTel = false;
        return;
    }

    // 전화번호 정규식표현식 검사
    // 정규식 객체 생성
    const regEx = /^0(1[01679]|2|[3-6][1-5]|70)[1-9]\d{2,3}\d{4}$/;

    // 010 1234 1234
    // 011  321 4321
    // 016  321 4321
    // 019  321 4321
    // 011  321 4321
    //  02 1234 1234
    // 031 1234 1234
    // 042 1234 1234
    // 051 1234 1234
    // 062 1234 1234
    // 070 1234 1234

    if(regEx.test(memberTel.value)) { // 유효한 경우
        telMessage.innerText = "유효한 전화번호 형식입니다.";
        telMessage.classList.add("confirm");
        telMessage.classList.remove("error");
        checkObj.memberTel = true;
    } else {                          // 유효하지 않은 경우
        telMessage.innerText = "유효하지 않은 전화번호 형식입니다.";
        telMessage.classList.remove("confirm");
        telMessage.classList.add("error");
        checkObj.memberTel = false;
    }
});