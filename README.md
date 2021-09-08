# 2021 HUFS Graduation Project

## 💊 Naver CLOVA OCR을 이용한 처방 데이터 관리 애플리케이션

### 🎯 기능적 요구사항
- 약의 성분과 효능 및 주의사항을 제공
- 처방전을 촬영하고 OCR을 적용하면 인식한 내용을 바탕으로 '나만의 복용 내역'을 만들 수 있음
- 복용 기록에 대한 메모 기능을 추가함으로써 내가 무슨 이유 때문에 이 약을 복용했는지 알 수 있음

### 🛠 문제점 및 고려사항
- 보통 처방전에는 약의 이름과 약에 대한 부연 설명이 옆에 적혀있는데, OCR로 글자를 인식할 때 약의 이름과 부연 설명을 구별하기 위한 방법이 필요함
- 약국마다 처방전 형식이 전부 다르기 때문에 약에 대한 부연 설명도 전부 다를 수 밖에 없는데, 약의 제목과 부연 설명이 다른 경우에도 데이터베이스에 같은 종류의 약을 별도의 약으로 판단하여 저장하는 것을 방지하는 방법 필요

### ✍ 사용 기술 및 개발 환경
- Android OS
- Naver CLOVA OCR API
- Postman
- SQLite

### 🤝 팀 구성
| Participants | Roles | Skills |
|:------------:|:-----:|:------:|
| 송진호 | 안드로이드 개발 | C, Java, Python(NumPy, Matplotlib, TensorFlow-Keras), HTML, CSS, Javascript, Node.js(Express.js), Flask, MySQL, SQLite
| 류준형 | DB 설계 및 구현 | C, Java, Javascript(jQuery), MySQL, PHP, Python(NumPy, Matplotlib, TensorFlow-Keras), Flask