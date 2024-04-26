
use youtube;

create table refresh_token
(
    id            int unsigned auto_increment
        primary key,
    user_id       int unsigned not null,
    refresh_token varchar(255) not null
);

create table users
(
    user_id    int unsigned auto_increment
        primary key,
    username   varchar(10)                        not null,
    email      varchar(20)                        not null,
    password   varchar(20)                        not null,
    role_type  int                                not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    constraint email
        unique (email)
);

create table video
(
    video_id          int unsigned auto_increment
        primary key,
    title             varchar(30)                            not null,
    duration          int                                    not null,
    content           varchar(50)                            not null,
    created_at        timestamp    default CURRENT_TIMESTAMP null,
    updated_at        timestamp    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    author            varchar(20)                            not null,
    video_total_views int unsigned default '0'               null,
    video_ad_views    int unsigned default '0'               not null,
    constraint video_ibfk_2
        foreign key (author) references users (email)
);

create table user_history
(
    play_id         int unsigned auto_increment
        primary key,
    user_id         int unsigned             null,
    video_id        int unsigned             null,
    viewing_time    int unsigned default '0' not null,
    video_timestamp int unsigned default '0' not null,
    constraint play_history_ibfk_1
        foreign key (user_id) references users (user_id),
    constraint play_history_ibfk_2
        foreign key (video_id) references video (video_id)
);

create table video_history
(
    video_history_id int unsigned auto_increment
        primary key,
    video_id         int unsigned                        not null,
    play_date        timestamp default CURRENT_TIMESTAMP not null,
    constraint video_history_ibfk_1
        foreign key (video_id) references video (video_id)
);