create table cafe
(
    close_time                   time(6),
    open_time                    time(6),
    created_at                   datetime(6),
    id                           bigint not null auto_increment,
    owner_id                     bigint,
    updated_at                   datetime(6),
    business_registration_number varchar(255),
    cafe_image_url               varchar(255),
    detail_address               varchar(255),
    name                         varchar(255),
    road_address                 varchar(255),
    telephone_number             varchar(255),
    introduction                 tinytext,
    primary key (id)
) engine=InnoDB;

create table cafe_coupon_design
(
    deleted         bit,
    cafe_id         bigint,
    created_at      datetime(6),
    id              bigint not null auto_increment,
    updated_at      datetime(6),
    back_image_url  varchar(255),
    front_image_url varchar(255),
    stamp_image_url varchar(255),
    primary key (id)
) engine=InnoDB;

create table cafe_policy
(
    deleted         bit,
    expire_period   integer,
    max_stamp_count integer,
    cafe_id         bigint,
    created_at      datetime(6),
    id              bigint not null auto_increment,
    updated_at      datetime(6),
    reward          varchar(255),
    primary key (id)
) engine=InnoDB;

create table cafe_stamp_coordinate
(
    stamp_order           integer,
    x_coordinate          integer,
    y_coordinate          integer,
    cafe_coupon_design_id bigint,
    created_at            datetime(6),
    id                    bigint not null auto_increment,
    updated_at            datetime(6),
    primary key (id)
) engine=InnoDB;

create table coupon
(
    deleted          bit,
    expired_date     date,
    cafe_id          bigint,
    coupon_design_id bigint,
    coupon_policy_id bigint,
    created_at       datetime(6),
    customer_id      bigint,
    id               bigint not null auto_increment,
    updated_at       datetime(6),
    status           enum ('ACCUMULATING','EXPIRED','REWARDED'),
    primary key (id)
) engine=InnoDB;

create table coupon_design
(
    created_at      datetime(6),
    id              bigint not null auto_increment,
    updated_at      datetime(6),
    back_image_url  varchar(255),
    front_image_url varchar(255),
    stamp_image_url varchar(255),
    primary key (id)
) engine=InnoDB;

create table coupon_policy
(
    expired_period  integer,
    max_stamp_count integer,
    created_at      datetime(6),
    id              bigint not null auto_increment,
    updated_at      datetime(6),
    reward_name     varchar(255),
    primary key (id)
) engine=InnoDB;

create table coupon_stamp_coordinate
(
    stamp_order      integer,
    x_coordinate     integer,
    y_coordinate     integer,
    coupon_design_id bigint,
    created_at       datetime(6),
    id               bigint not null auto_increment,
    updated_at       datetime(6),
    primary key (id)
) engine=InnoDB;

create table customer
(
    customer_id  bigint      not null auto_increment,
    dtype        varchar(31) not null,
    nickname     varchar(255),
    phone_number varchar(255),
    primary key (customer_id)
) engine=InnoDB;

create table favorites
(
    is_favorites bit,
    cafe_id      bigint,
    created_at   datetime(6),
    customer_id  bigint,
    id           bigint not null auto_increment,
    updated_at   datetime(6),
    primary key (id)
) engine=InnoDB;

create table owner
(
    created_at         datetime(6),
    id                 bigint not null auto_increment,
    oauth_id           bigint,
    updated_at         datetime(6),
    email              varchar(255),
    encrypted_password varchar(255),
    login_id           varchar(255),
    name               varchar(255),
    oauth_provider     enum ('KAKAO','NAVER'),
    phone_number       varchar(255),
    primary key (id)
) engine=InnoDB;

create table register_customer
(
    customer_id        bigint not null,
    oauth_id           bigint,
    email              varchar(255),
    encrypted_password varchar(255),
    login_id           varchar(255),
    oauth_provider     enum ('KAKAO','NAVER'),
    primary key (customer_id)
) engine=InnoDB;

create table reward
(
    used        bit,
    cafe_id     bigint,
    created_at  datetime(6),
    customer_id bigint,
    id          bigint not null auto_increment,
    updated_at  datetime(6),
    name        varchar(255),
    primary key (id)
) engine=InnoDB;

create table sample_back_image
(
    created_at datetime(6),
    id         bigint not null auto_increment,
    updated_at datetime(6),
    image_url  varchar(255),
    primary key (id)
) engine=InnoDB;

create table sample_front_image
(
    created_at datetime(6),
    id         bigint not null auto_increment,
    updated_at datetime(6),
    image_url  varchar(255),
    primary key (id)
) engine=InnoDB;

create table sample_stamp_coordinate
(
    stamp_order          integer,
    x_coordinate         integer,
    y_coordinate         integer,
    created_at           datetime(6),
    id                   bigint not null auto_increment,
    sample_back_image_id bigint,
    updated_at           datetime(6),
    primary key (id)
) engine=InnoDB;

create table sample_stamp_image
(
    created_at datetime(6),
    id         bigint not null auto_increment,
    updated_at datetime(6),
    image_url  varchar(255),
    primary key (id)
) engine=InnoDB;

create table stamp
(
    coupon_id  bigint,
    created_at datetime(6),
    id         bigint not null auto_increment,
    updated_at datetime(6),
    primary key (id)
) engine=InnoDB;

create table temporary_customer
(
    customer_id bigint not null,
    primary key (customer_id)
) engine=InnoDB;

create table visit_history
(
    stamp_count integer not null,
    cafe_id     bigint,
    created_at  datetime(6),
    customer_id bigint,
    id          bigint  not null auto_increment,
    updated_at  datetime(6),
    primary key (id)
) engine=InnoDB;

alter table coupon
    add constraint UK_pysmwvb6mxft5ecat7qc9fr2w unique (coupon_design_id);
alter table coupon
    add constraint UK_e7irxxxhrmoa16lyw1b4857oh unique (coupon_policy_id);
alter table customer
    add constraint UK_rosd2guvs3i1agkplv5n8vu82 unique (phone_number);
alter table cafe
    add constraint FKjmhmwpl2qo6olo6uu8k9wuqed
        foreign key (owner_id)
            references owner (id);
alter table cafe_coupon_design
    add constraint FK9p5ooklyx21r1orjl9m8m2ise
        foreign key (cafe_id)
            references cafe (id);
alter table cafe_policy
    add constraint FKp6lg1cur82wk5oe2abhw9xssw
        foreign key (cafe_id)
            references cafe (id);
alter table cafe_stamp_coordinate
    add constraint FK96sbxq3avnw1v2vhs7rn3n0wq
        foreign key (cafe_coupon_design_id)
            references cafe_coupon_design (id);
alter table coupon
    add constraint FKikrgfufc6m55rorf3aer857ux
        foreign key (cafe_id)
            references cafe (id);
alter table coupon
    add constraint FKflo5yxa4jv8c86frlq65443d9
        foreign key (coupon_design_id)
            references coupon_design (id);
alter table coupon
    add constraint FKjnahg3a5otcynjdmxl7cq8p5k
        foreign key (coupon_policy_id)
            references coupon_policy (id);
alter table coupon
    add constraint FKhdsrf5sywjt5uh4po9g4h0qk1
        foreign key (customer_id)
            references customer (customer_id);
alter table coupon_stamp_coordinate
    add constraint FK87rmtx0edcurkt727vpsq6xnb
        foreign key (coupon_design_id)
            references coupon_design (id);
alter table favorites
    add constraint FKfv5m9oby4kukp5df1mjcp8ubb
        foreign key (cafe_id)
            references cafe (id);
alter table favorites
    add constraint FKoyt0sxepwr5sys7a8w334epdg
        foreign key (customer_id)
            references customer (customer_id);
alter table register_customer
    add constraint FKo86p65nwx0rgly9rv4ilw0xc9
        foreign key (customer_id)
            references customer (customer_id);
alter table reward
    add constraint FKdqng5418xlqtmonlatkunudd2
        foreign key (cafe_id)
            references cafe (id);
alter table reward
    add constraint FKq4a0acffv4i9l135k43n7dwr9
        foreign key (customer_id)
            references customer (customer_id);
alter table sample_stamp_coordinate
    add constraint FKed9uy7avcvd4c27k7ug6f7f3t
        foreign key (sample_back_image_id)
            references sample_back_image (id);
alter table stamp
    add constraint FKmtict4k20alprsw09x2tfvdgx
        foreign key (coupon_id)
            references coupon (id);
alter table temporary_customer
    add constraint FKclqkafnemlkpnb0iojfbgms6c
        foreign key (customer_id)
            references customer (customer_id);
alter table visit_history
    add constraint FK1dcn8j39322uea3105ilc6tds
        foreign key (cafe_id)
            references cafe (id);
alter table visit_history
    add constraint FK3a0ps2neuswc89qdj333wmh8v
        foreign key (customer_id)
            references customer (customer_id);
