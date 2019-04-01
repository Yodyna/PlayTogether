create table advertisement (id bigint not null, city varchar(32), date datetime, date_ofcreate datetime, description varchar(512), sport varchar(4), street varchar(32), url varchar(64), user_id bigint, primary key (id)) engine=MyISAM;
create table hibernate_sequence (next_val bigint) engine=MyISAM;
insert into hibernate_sequence values ( 1 );
create table user (id bigint not null auto_increment, username varchar(32) not null, password varchar(64) not null, primary key (id)) engine=MyISAM;
create table user_roles (user_id bigint not null, roles_id bigint not null, primary key (user_id, roles_id)) engine=MyISAM;
create table user_role (id bigint not null, description varchar(128), role varchar(32), primary key (id)) engine=MyISAM;
alter table advertisement add constraint FK6l68mvl7eypahv3tmaf7ndwie foreign key (user_id) references user (id);
alter table user_roles add constraint FK5i6gd32hnpr2nyf5edlvl9nhw foreign key (roles_id) references user_role (id);
alter table user_roles add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id);