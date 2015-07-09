drop database if exists taskcenter;
create database taskcenter;
use taskcenter;

create table if not exists user(
	user_id int unsigned not null auto_increment,
	user_name varchar(128) not null,
	primary key(user_id)
);

insert into user(user_name) values('������');
insert into user(user_name) values('����');
insert into user(user_name) values('��ӳ');

create table if not exists task(
	task_id int unsigned not null auto_increment,
	task_name varchar(128) not null,
	task_detail varchar(1280) not null,
	task_status varchar(128) not null default 'δ����',
	task_publisher int unsigned not null,
	addtime datetime default now(),
	updatetime datetime default now(),
	primary key(task_id),
	foreign key(task_publisher) references user(user_id)
);

#insert into task(task_name, task_detail, task_publisher)
#	values('����1', '����1����ϸ����', 1);
#insert into task(task_name, task_detail, task_publisher)
#	values('����2', '����2����ϸ����', 2);
#insert into task(task_name, task_detail, task_publisher)
#	values('����3', '����3����ϸ����', 3);
#insert into task(task_name, task_detail, task_publisher)
#	values('����4', '����4����ϸ����', 1);
#insert into task(task_name, task_detail, task_publisher, task_status)
#	values('����5', '����5����ϸ����', 1, '���ڽ�����');
#insert into task(task_name, task_detail, task_publisher, task_status)
#	values('����6', '����6����ϸ����', 1, '���ڽ�����');
#insert into task(task_name, task_detail, task_publisher, task_status)
#	values('����7', '����7����ϸ����', 1, 'δ����');

create table if not exists process(
	process_id int unsigned not null auto_increment,
	task_id int unsigned not null,
	user_id int unsigned not null,
	process_duration int unsigned not null default 1,
	addtime datetime default now(),
	updatetime datetime default now(),
	status varchar(128) default 'processing',
	primary key(process_id),
	foreign key(task_id) references task(task_id),
	foreign key(user_id) references user(user_id)
);

#insert into process(task_id, user_id, process_duration)
#	values(5, 1, 1);
#insert into process(task_id, user_id, process_duration)
#	values(6, 1, 2);