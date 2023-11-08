create table black_list
(
    id                    bigint not null auto_increment,
    invalid_refresh_token varchar(255),
    primary key (id)
) engine=InnoDB;
