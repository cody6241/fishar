create table t_user(
       user_id int auto_increment primary key,       
       user_name varchar(255),       
       credits int,       
       password varchar(255),       
       last_visit datetime,       
       last_ip varchar(255)       
)engine =innodb;

create table t_login_log(
       login_log_id int auto_increment primary key,
       user_id int,       
       ip varchar(255),       
       login_datetime datetime       
)engine=innodb;