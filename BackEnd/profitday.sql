create schema profitday;
use profitday;

create table user(
	user_id int unsigned Auto_increment primary key not null,
    email varchar(30) unique not null,
    location varchar(100),
    gender varchar(10),
    birthday date,
    income_range varchar(20),
    personal_char varchar(50),
    family_char varchar(50),
    sign_up_date timestamp default current_timestamp
) CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table faq(
	faq_id int Auto_increment primary key not null,
    question varchar(200),
    answer varchar(500)
) CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table inquiry(
	inquiry_id int Auto_increment primary key not null,
    user_id varchar(20),
    서비스ID text,
    title varchar(200),
    contents varchar(500)
) CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table log(
	log_id int Auto_increment primary key not null,
    user_id int,
    service_id int,
    log_date timestamp default current_timestamp
) CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

create table service_like(
	like_id int Auto_increment primary key not null,
    user_id int,
    service_id int,
    like_date timestamp default current_timestamp
) CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

