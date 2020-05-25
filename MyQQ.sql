create table user(
	id int(10) auto_increment primary key,
    username varchar(16) unique,
    password varchar(16),
    ip varchar(30)
);

create table friends(
	id int(10) auto_increment primary key,
	username varchar(16) ,
	friend varchar(16),
    foreign key(username) references user(username)
)