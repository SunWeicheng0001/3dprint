use 3dprint3;

create table model(
	model_id int unsigned not null auto_increment,
	model_name varchar(128) not null,
	model_description varchar(128) default '',
	model_length double default 0,
	model_height double default 0,
	model_width double default 0,
	model_style varchar(128) default '',
	model_bought int default 0,
	model_downloaded int default 0,
	user_id int unsigned not null,
	
	addtime datetime default now(),
	updatetime datetime default now(),
	status varchar(128) default 'normal',

	primary key(model_id),
	foreign key(user_id) references user(user_id)
);

insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��1', 'model\'s description', 1, 1, 9, 'ʱ��', 1);
insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��2', 'model\'s description', 2, 2, 8, 'ʱ��', 1);
insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��3', 'model\'s description', 1, 3, 7, 'ʱ��', 2);
insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��4', 'model\'s description', 2, 4, 6, 'ʵ��', 2);
insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��5', 'model\'s description', 1, 5, 5, 'ʱ��', 2);
insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��6', 'model\'s description', 2, 6, 4, 'ʵ��', 4);
insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��7', 'model\'s description', 1, 7, 3, 'ʱ��', 4);
insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��8', 'model\'s description', 2, 8, 2, '��Լ', 4);
insert into model(model_name, model_description, model_length, model_height, model_width, model_style, user_id)
values ('ģ��9', 'model\'s description', 1, 9, 1, 'ʱ��', 4);