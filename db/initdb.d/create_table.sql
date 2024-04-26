
use youtube;

CREATE TABLE users (
    user_id    INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(10) NOT NULL,
    email      VARCHAR(20) UNIQUE NOT NULL,
    password   VARCHAR(20) NOT NULL,
    role_type  INT(2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL
);

create table video
(
    video_id   int unsigned auto_increment primary key,
    title      varchar(30)                         not null,
    duration   int                                 not null,
    content    varchar(50)                         not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    author     varchar(20)                         not null,
    video_total_view int unsigned                  not null,
    user_id    int unsigned                        not null,
    constraint video_ibfk_1
            foreign key (author) references users (email)
);

create table video_history
(
    video_history_id     int unsigned auto_increment primary key,
    play_date            timestamp default CURRENT_TIMESTAMP null,
    video_id             int unsigned                    not null,
    video_ad_view_count  int unsigned  default 0         not null,
    constraint video_history_ibfk_1
        foreign key (video_id) references video (video_id)
);


create table refresh_token
(
    id            int unsigned auto_increment primary key,
    user_id       int unsigned not null,
    refresh_token varchar(255) not null
);

create table user_history
(
    play_id        int unsigned auto_increment  primary key,
    user_id        int unsigned                        null,
    video_id       int unsigned                        null,
    play_timestamp timestamp default CURRENT_TIMESTAMP null,
    constraint play_history_ibfk_1
        foreign key (user_id) references users (user_id),
    constraint play_history_ibfk_2
        foreign key (video_id) references video (video_id)
);


