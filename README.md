# ChimPlanet-mgt-bot
침플레닛 관련 관리자 기능을 제공하는 Discord Bot 입니다.

※ credentials.json, StoredCredential 파일이 필요하신 경우 이 [문서](https://github.com/ChimPlanet/google-drive-setting)를 참고하시기 바랍니다.

## Command (!<명령어> <보조 명령> <명령어, 파일 이름>)
  + !스케줄러 로그 목록
    + 스케줄러가 실행된 후 남긴 로그의 목록을 출력한다.
  + !스케줄러 로그 <파일 이름>
    + 로그 파일을 읽을 수 있도록 Discord 채팅에 출력한다.
  + !스케줄러 실행
    + 스케줄러를 강제로 실행한다.

## 실행 필수 파일
  + /src/main/java/resources
    + application.properties
    + credentials.json
  + /token
    + StoredCredential