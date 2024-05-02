create table if not exists youtube.advertisement
(
    ad_id int unsigned not null
        primary key
);

create table if not exists youtube.total_views_count
(
    total_views_id    bigint auto_increment
        primary key,
    created_at        timestamp default CURRENT_TIMESTAMP null,
    total_views_day   bigint    default 0                 null,
    total_views_week  bigint    default 0                 null,
    total_views_month bigint    default 0                 null
);

create table if not exists youtube.users
(
    user_id    int unsigned auto_increment
        primary key,
    username   varchar(30)                        null,
    email      varchar(50)                        not null,
    password   varchar(255)                       not null,
    role_type  int                                not null,
    created_at datetime default CURRENT_TIMESTAMP not null,
    constraint email
        unique (email)
);

create table if not exists youtube.refresh_token
(
    id            int unsigned auto_increment
        primary key,
    user_id       int unsigned not null,
    refresh_token varchar(255) not null,
    constraint refresh_token_users_user_id_fk
        foreign key (user_id) references youtube.users (user_id)
);

create table if not exists youtube.video
(
    video_id          int unsigned auto_increment
        primary key,
    title             varchar(30)                               not null,
    duration          int unsigned    default '0'               not null,
    content           varchar(50)                               not null,
    created_at        timestamp       default CURRENT_TIMESTAMP null,
    updated_at        timestamp       default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    author            varchar(50)                               not null,
    video_total_views bigint unsigned default '0'               null,
    constraint video_ibfk_2
        foreign key (author) references youtube.users (email)
);

create table if not exists youtube.balance_accounts
(
    balance_accounts_id bigint auto_increment
        primary key,
    user_id             int unsigned                        not null,
    video_id            int unsigned                        not null,
    views_settlement    bigint    default 0                 null,
    ad_views_settlement bigint    default 0                 null,
    total_settlement    bigint    default 0                 null,
    created_at          timestamp default CURRENT_TIMESTAMP null,
    constraint user_id
        foreign key (user_id) references youtube.users (user_id),
    constraint video_id
        foreign key (video_id) references youtube.video (video_id)
);

create table if not exists youtube.user_history
(
    play_id         int unsigned auto_increment
        primary key,
    user_id         int unsigned                 null,
    video_id        int unsigned                 null,
    viewing_time    int unsigned default '0'     not null,
    video_timestamp timestamp    default (now()) not null,
    constraint play_history_ibfk_1
        foreign key (user_id) references youtube.users (user_id),
    constraint play_history_ibfk_2
        foreign key (video_id) references youtube.video (video_id)
);

create table if not exists youtube.video_ad
(
    video_ad_id  int unsigned auto_increment
        primary key,
    video_id     int unsigned                        not null,
    ad_timestamp timestamp default CURRENT_TIMESTAMP null,
    ad_id        int unsigned                        not null,
    constraint video_ad_advertisement_ad_id_fk
        foreign key (ad_id) references youtube.advertisement (ad_id),
    constraint video_ad_ibfk_2
        foreign key (video_id) references youtube.video (video_id)
);

create index advertisement_detail_ibfk_2
    on youtube.video_ad (video_id);