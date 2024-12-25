DROP TABLE IF EXISTS Member CASCADE; /* 없어도 되지만 안전하게 */
CREATE TABLE Member (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY, -- 자동으로 유일한 값을 생성해주는 식별자 컬럼으로 설정 (각 행 삽입마다 자동숫자증가, 안해주면 NULL)
  name VARCHAR(255), -- 선언시: 공백없음
  PRIMARY KEY (id) -- 설정관련: 공백있는 걸 권장
);

--SELECT * FROM Member;

--INSERT INTO Member(name) VALUES('spring');
