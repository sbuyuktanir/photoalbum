create sequence hibernate_sequence start with 1 increment by 1;

create table album_photos
(
    album_id      int8 not null,
    position      int4 not null,
    photo_id      int8,
    assignment_ts timestamp,
    primary key (album_id, position)
);

create table albums
(
    id          int8        not null,
    version     int4        not null,
    creation_ts timestamp   not null,
    album_name  varchar(64) not null,
    restricted  boolean,
    primary key (id)
);

create table countries
(
    id       int8        not null,
    version  int4        not null,
    iso2code varchar(2)  not null,
    name     varchar(64) not null,
    primary key (id)
);

create table persons
(
    id         int8         not null,
    version    int4         not null,
    user_name  varchar(255) not null,
    first_name varchar(32)  not null,
    last_name  varchar(64)  not null,
    nick_name  varchar(32)  not null,
    primary key (id)
);

create table photographer_emails
(
    photographer_id int8         not null,
    email_address   varchar(128) not null,
    email_type      varchar(1) check (email_type in ('B', 'P')) not null
        );

create table photographers
(
    id                     int8         not null,
    version                int4         not null,
    user_name              varchar(255) not null,
    first_name             varchar(32)  not null,
    last_name              varchar(64)  not null,
    billing_city           varchar(64)  not null,
    billing_street_number  varchar(64)  not null,
    billing_zip_code       varchar(16)  not null,
    business_area_code     int4,
    business_country_code  int4,
    business_serial_number varchar(16),
    mobile_area_code       int4,
    mobile_country_code    int4,
    mobile_serial_number   varchar(16),
    studio_city            varchar(64),
    studio_street_number   varchar(64),
    studio_zip_code        varchar(16),
    billing_country_id     int8,
    studio_country_id      int8,
    primary key (id)
);

create table photos
(
    id              int8        not null,
    version         int4        not null,
    creation_ts     timestamp   not null,
    file_name       varchar(64) not null,
    width           int4 check (width >= 0 and width <= 32767),
    height          int4 check (height >= 0 and height <= 32767),
    photo_name      varchar(64) not null,
    orientation     varchar(1),
    photographer_id int8,
    primary key (id)
);

create table tagged_persons
(
    id        int8 not null,
    rating    int4,
    person_id int8 not null,
    photo_id  int8 not null,
    primary key (id)
);

alter table album_photos
    add constraint FK_album_photos_2_photos foreign key (photo_id) references photos;

alter table album_photos
    add constraint FK_album_photos_2_album foreign key (album_id) references albums;

alter table photographer_emails
    add constraint FK_photographer_emails foreign key (photographer_id) references photographers;

alter table photographers
    add constraint FK_photographers_billing_country foreign key (billing_country_id) references countries;

alter table photographers
    add constraint FK_photographers_studio_country foreign key (studio_country_id) references countries;

alter table photos
    add constraint FK_photo_photographer foreign key (photographer_id) references photographers;

alter table tagged_persons
    add constraint FK_tagged_persons_2_person foreign key (person_id) references persons;

alter table tagged_persons
    add constraint FK_tagged_persons_2_photo foreign key (photo_id) references photos;

