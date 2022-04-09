-- DROP table service,tag,service_tag,user_tag,FAQ,inquiry,blind_spot,suggestion,review;
create table service(
	service_id int unsigned Auto_increment primary key,
	서비스ID varchar(12) not null unique,
	부서명 varchar(20),
	서비스명 varchar(100),
	서비스목적 varchar(1000),
	선정기준 varchar(4000),
	소관기관명 varchar(30),
	신청기한 varchar(300),
	신청방법 varchar(2000),
	구비서류 varchar(500),
	지원내용 varchar(3000),
	지원대상 varchar(2000),
	접수기관명 varchar(50),
	지원유형 varchar(100),
	도_특별시_광역시 varchar(20),
	시_군_구 varchar(20)
);
create table tag(
	tag_id varchar(6) primary key,
	value varchar(100)
);
create table service_tag(
	service_tag_id int unsigned Auto_increment primary key,
	tag_id varchar(6),
	service_id int unsigned,
	foreign key (tag_id) references tag(tag_id),
	foreign key (service_id) references service(service_id)
);
create table user_tag(
	user_tag_id int unsigned Auto_increment primary key,
	tag_id varchar(6),
	user_id int unsigned,
	foreign key (tag_id) references tag(tag_id),
	foreign key (user_id) references user(user_id)
);
create table FAQ(
	faq_id int unsigned Auto_increment primary key,
	question varchar(500),
	answer varchar(500)
);
create table inquiry(
	inquiry_id int unsigned Auto_increment,
	user_id int unsigned not null,
	service_id int unsigned not null,
	title varchar(200) not null,
	contents varchar(2000) not null,
	primary key (inquiry_id),
	foreign key (user_id) references user(user_id),
	foreign key (service_id) references service(service_id)
);
create table blind_spot(
	blind_spot_id int unsigned Auto_increment,
	user_id int unsigned not null,
	title varchar(200) not null,
	contents varchar(2000) not null,
	primary key (blind_spot_id),
	foreign key (user_id) references user(user_id)
);
create table suggestion(
	suggestion_id int unsigned Auto_increment,
	user_id int unsigned not null,
	title varchar(200) not null,
	contents varchar(2000) not null,
	primary key (suggestion_id),
	foreign key (user_id) references user(user_id)
);
create table review(
	review_id int unsigned Auto_increment,
	user_id int unsigned not null,
	title varchar(200) not null,
	contents varchar(2000) not null,
	primary key (review_id),
	foreign key (user_id) references user(user_id)
);