# <center> **로또 번호 확인 앱 (Lotto Number Checker)**</center>
## 📱 프로젝트 소개
이 앱은 로또 당첨 번호 정보를 제공하는 안드로이드 애플리케이션입니다. 최신 안드로이드 기술을 실습하고 학습하기 위한 프로젝트로, Jetpack Compose와 Clean Architecture를 기반으로 개발되었습니다.

## ✨ 주요 기능
- 로또 당첨번호 확인
- QR 코드 스캔을 통한 당첨 확인
- 주변 로또 판매점 찾기
- 로또 번호 생성
- 홈 화면 위젯 제공
- 폴더블 화면 상태 감지
- 다국어 지원 (한국어/영어)

## 🛠 사용 기술
### 모듈 구성
 프로젝트는 다음과 같이 모듈화 되어 있습니다.
- **app**: 메인 애플리케이션 모듈
    - app: 메인 모듈
    - presentation: UI 및 viewModel
    - domain: useCase 및 repository 인터페이스
    - data: domain의 인터페이스 구현체 및 di
- **QrScan**: QR 코드 스캔 기능 모듈 (Git Submodule)
- **Camera**: 카메라 처리 모듈 (Git Submodule)
- **DisplayDetector**: 폴더블 상태 감지 모듈 (Git Submodule)
#### Git Submodules
외부 모듈은 Git Submodule로 관리되며, 다음 명령어로 초기화할 수 있습니다:
```bash
git submodule update --init --recursive
```

### Architecture
- Clean Architecture
- MVI Pattern (Orbit)
- 모듈화 아키텍처

### Libraries
- **UI**: Jatpack Compose
- **DI**: Hilt
- **비동기 처리**: Coroutine, Flow
- **로컬 데이터베이스**: Room
- **네트워크**: Retrofit
- **애니메이션**: Lottie
- **위젯**: Glance
- **페이징**: Paging3
- **Qr Scan**: MLKit
- **카메라**: Camera2
- **지도**: Naver Map API

## 🚀 시작하기
1. 프로젝트를 클론하고 서브모듈을 초기화합니다
```bash
git clone https://github.com/developryu/Lotto.git
git submodule update --init --recursive
```

2. local.properties 파일에 네이버 지도 API 키를 추가합니다
```properties
naver_map_client_id=[your_client_id]
naver_map_client_secret=[your_client_secret]
```

3. 안드로이드 스튜디오에서 프로젝트를 실행합니다

## 📱 스크린샷
<table>
    <tr>
        <td align="center">
            <img src="https://github.com/developryu/Lotto/blob/main/screenshot/home.png?raw=true" width="300" height="350"/>
            <br>
            <b>메인 페이지</b>
        </td>
        <td align="center">
            <img src="https://github.com/developryu/Lotto/blob/main/screenshot/qrscan.png?raw=true" width="300" height="350"/>
            <br>
            <b>QR 코드 당첨 확인</b>
        </td>
        <td align="center">
            <img src="https://github.com/developryu/Lotto/blob/main/screenshot/qrscan_result.png?raw=true" width="300" height="350"/>
            <br>
            <b>QR 코드 스캔 결과</b>
        </td>
        <td align="center">
            <img src="https://github.com/developryu/Lotto/blob/main/screenshot/draw_history.png?raw=true" width="300" height="350"/>
            <br>
            <b>로또 당첨 번호 전체 보기</b>
        </td>
    </tr>
    <tr>
        <td align="center">
            <img src="https://github.com/developryu/Lotto/blob/main/screenshot/map.png?raw=true" width="300" height="350"/>
            <br>
            <b>내 주변 로또 판매점</b>
        </td>
        <td align="center">
            <img src="https://github.com/developryu/Lotto/blob/main/screenshot/map_list.png?raw=true" width="300" height="350"/>
            <br>
            <b>판매점 목록</b>
        </td>
        <td align="center">
            <img src="https://github.com/developryu/Lotto/blob/main/screenshot/generator_number.png?raw=true" width="300" height="350"/>
            <br>
            <b>로또 번호 생성</b>
        </td>
        <td align="center">
            <img src="https://github.com/developryu/Lotto/blob/main/screenshot/widget.png?raw=true" width="300" height="350"/>
            <br>
            <b>Glance위젯</b>
        </td>
    </tr>
    
</table>
<table>
    <tr>
        <td align="center">
                <img src="https://github.com/developryu/Lotto/blob/main/screenshot/foldable_detector.gif?raw=true" width="300" height="250"/>
                <br>
                <b>폴더블 접힘</b>
        </td>
    </tr>
</table>

## 🌍 지원 언어
- 한국어 (Korean)
- 영어 (English)

## 📋 요구사항
- Android minSDK: 28
- Android targetSDK: 35

## 👨‍💻 개발자 정보
류호일(ryuhoil0712@gmail.com)
