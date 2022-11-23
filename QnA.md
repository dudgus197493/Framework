# 스프링관련 질문
## MultipartFile에 대하여
- MultipartResolver 라이브러리는 스프링에서 해당 기능을 구현하기 위한 추상메서드들을 구현한 라이브러리
- encType이 multipart/form-data인 form에서 데이터를 전달받을 때 파일에 관련된 데이터들을 MultipartFile이라는 객체에 담에 전달함
- type="file" 아닌 input의 데이터들은 ArgumentsResolver를 이용해 정상적으로 받을 수 있다.