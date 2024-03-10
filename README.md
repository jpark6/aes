# AES Encrypt/Decrypt Tool
- development 
  > Spring Boot 2.7.18  
  > Kotlin 8  
  > gradle
  > 

- Usage
  - 텍스트 암복호화
    - Arguments:
      - type[enc/dec] - ENC: 암호화 / DEC: 복호화 (대소문자 구분 안함)
      - mode[cbc/ecb] - AES 방식(CBC: iv 사용, ECB: 사용안함) (대소문자 구분 안함)
      - key length[128/192/256] - 키 사이즈
    - Command:
      ```shell
      java -jar aes-0.0.1.jar [enc/dec] [cbc/ecb] [128/192/256] text
      ```

  - 랜덤 문자열 생성(키 생성)
    - Arguments:
      - cmd[randstr] - 랜덤 문자열 생성 (대소문자 구분 안함)
      - length[>0] - 문자열 길이
    - Command:
      ```shell
      java -jar aes-0.0.1.jar randstr length
      ```
      
  - KEY/IV 암복호화  
    secret.yml 파일에 secret.aesKey, secret.iv 암호화해서 저장  
    아래 명령어로 암호화해서 secret.yml에 저장  
    - Arguments:
      - cmd[enckey/deckey/enciv/deciv] - KEY암복호화 / IV암복호화 (대소문자 구분 안함)
      - key or iv [text] - KEY / IV
      ```shell
      java jar aes-1.0.0.jar [enckey/deckey] key
      java jar aes-1.0.0.jar [enciv/deciv] iv
      ```
