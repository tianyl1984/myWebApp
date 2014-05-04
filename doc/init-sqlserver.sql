create table tb_user (
   id                   char(32)             not null,
   score                float                null,
   name                 varchar(255)         null,
   email                varchar(255)         null
)
go

alter table tb_user
   add constraint PK_tb_user primary key nonclustered (id)
go

create table tb_group (
   id                   char(32)             not null,
   name                 varchar(255)         null,
   code                 varchar(255)         null
)
go

alter table tb_group
   add constraint PK_tb_group primary key nonclustered (id)
go

create table tb_group_user (
   id_user              char(32)             not null,
   id_group             char(32)             not null
)
go

alter table tb_group_user
   add constraint FK_tb_group_user_user foreign key (id_user)
      references tb_user (id)
go
alter table tb_group_user
   add constraint FK_tb_group_user_group foreign key (id_group)
      references tb_group (id)
go

