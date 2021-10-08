# 2021 HUFS Graduation Project

## 💊 Naver CLOVA OCR API를 이용한 처방 및 복용 데이터 관리 어플

### 🎯 기능적 요구사항
- 처방받은 약 및 건강보험심사평가원에 등록된 **모든 약의 성분과 효능 및 주의사항을 제공**
- 처방전을 촬영하고 OCR을 적용하면 인식한 내용을 바탕으로 **'나만의 처방 데이터'를 만들 수 있음**
- 병원을 방문할 때마다 처방 데이터를 기록함으로써 **언제**, **어떤 병원에서**, **어떤 약을** 처방받았는지 조회 가능
- 처방 기록에 대한 메모 기능을 통해서 **어떤 이유** 때문에 이 약을 처방받았는지 기록할 수 있음
- 투약 이력을 조회하는 기능을 통해서 **실제 복용 내역을 기록 및 조회**할 수 있음

### 🛠 문제점 및 고려사항
- OCR의 느린 적용 속도를 개선할 방법 필요
- 데이터베이스의 용량을 줄이기 위한 방법 필요

### ✍ 사용 기술 및 개발 환경
- Android OS
- Naver CLOVA OCR API
- Postman
- SQLite

### 🤝 팀 구성
| Participants | Roles | Skills |
|:------------:|:-----:|:------:|
| 송진호 | 안드로이드 앱 개발 | C, Java, Python(NumPy, Matplotlib, TensorFlow-Keras), HTML, CSS, Javascript, Node.js(Express.js), Flask, MySQL, SQLite
| 류준형 | UI 설계 & DB 설계 및 구현 | C, Java, Javascript(jQuery), MySQL, PHP, Python(NumPy, Matplotlib, TensorFlow-Keras), Flask