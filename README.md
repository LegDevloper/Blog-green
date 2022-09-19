# MyBatis DB연결 세팅

### 설정방법
- MyBatisConfig 파일 필요
- resources/mapper/*.xml 파일 필요
- Users 엔티티 필요
- UsersDao 인터페이스 생성 필요

### 페이징 갯수 변경
- boards.xml 에 paging쿼리문 ceil()부분
- boards.xml 에 findAll쿼리문 FETCH NEXT부분
- BoardsService.java에 startNum 변경

### MariaDB 사용자 생성 및 권한 주기
```sql
CREATE USER 'green'@'%' IDENTIFIED BY 'green1234';
CREATE DATABASE greendb;
GRANT ALL PRIVILEGES ON greendb.* TO 'green'@'%';
```

### 테이블 생성
```sql
--유저테이블
create table users(
    id int primary KEY AUTO_INCREMENT,
    username varchar(20) UNIQUE,
    password varchar(20),
    email varchar(50),
    createdAt TIMESTAMP
);
--게시물테이블
create table boards(
    id int primary KEY AUTO_INCREMENT,
    title varchar(150),
    content longtext,
    usersId int,
    createdAt TIMESTAgreendbboardsMP
);
--좋아요기능
create table loves(
    id int primary KEY AUTO_INCREMENT,
    usersId int,
    boardsId int,
    createdAt TIMESTAMP,
    UNIQUE uk_loves (usersId,boardsId)
);
```

### 더미데이터 추가
```sql
insert into users(username, password, email, createdAt) values('ssar', '1234', 'ssar@nate.com', NOW());
insert into users(username, password, email, createdAt) values('cos', '1234', 'cos@nate.com', NOW());
insert into users(username, password, email, createdAt) values('hong', '1234', 'hong@nate.com', NOW());
COMMIT;
```

### UTF8 설정
- MariaDB설치시 UF8설정을 안했다면 아래 코드도 쿼리문에 추가

```sql
ALTER TABLE users CONVERT TO CHARACTER SET UTF8;
ALTER TABLE boards CONVERT TO CHARACTER SET utf8;
```