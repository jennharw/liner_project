# liner_project


라이너 백엔드 사전과제 

## 라이너 하이라이트 관련 API 개발

DB 설계 

- User 와 Highlight 1: N, Highlight 과 Page N : 1 관계 

- User 와 Theme N:1  관계 - 하나의 Theme 에 여러 사람 사용 가능
- Theme 과 ThemeColor 1:N 관계

- Highlight 와 ThemeColor 1:N 관계 



![image](https://user-images.githubusercontent.com/73508138/166233107-195edc02-3594-4f9d-9948-9a81caf9d16e.png)



API 설계


1. 하이라이트 저장  (text 최대 6000 자)

2. 하이라이트 수정 ( 현재 테마 내 색 중 하나)

3. 페이지 내 하이라이트 가져오기 ( 수정시간의 역순)

4. 유저 하이라이트 가져오기 ( 각 페이지 최대 6개 )

5. 하이라이트 삭제

6. 유저의 하이라이트 테마 변경 

7. 유저의 하이라이트 테마 생성

전면 수정

- 색상 수가 늘어나면 색상 추가
- 색상 수가 줄어들면 기본 색에 매칭 

테마 중복 조합


9. 유저의 하이라이트 테마 삭제 

- 삭제된 테마의 매칭되지 않은 색은 기본 테마1의 첫번째 색으로 



