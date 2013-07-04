create table tb_user (
    id char(32),
    score float,
    name varchar(255),
    email varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table tb_group (
    id char(32),
    name varchar(255),
    code varchar(255),
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table tb_group_user (
    id_user char(32),
    id_group char(255),
    primary key (id_user,id_group)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

