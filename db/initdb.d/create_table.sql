use youtube;

CREATE TABLE `users` (
    `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(10) NOT NULL,
    `email` VARCHAR(50) NOT NULL,
    `password` VARCHAR(30) NOT NULL
);

CREATE TABLE `video` (
    `video_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(255) NOT NULL,
    `length` INT NOT NULL,
    `user_id` INT UNSIGNED,
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
);

CREATE TABLE `ad` (
    `ad_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `ad_length` INT NOT NULL,
    `ad_views` INT NOT NULL,
    `ad_type` VARCHAR(255) NOT NULL
);

CREATE TABLE `role` (
    `role_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(20) NOT NULL
);

CREATE TABLE `user_role` (
    `user_id` INT UNSIGNED,
    `role_id` INT UNSIGNED,
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`),
    FOREIGN KEY (`role_id`) REFERENCES role(`role_id`),
    PRIMARY KEY (`user_id`, `role_id`)
);

CREATE TABLE `video_ad` (
    `video_id` INT UNSIGNED,
    `ad_id` INT UNSIGNED,
    FOREIGN KEY (`video_id`) REFERENCES video(`video_id`),
    FOREIGN KEY (`ad_id`) REFERENCES ad(`ad_id`),
    PRIMARY KEY (`video_id`, `ad_id`)
);

CREATE TABLE `play_history` (
    `play_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT UNSIGNED,
    `video_id` INT UNSIGNED,
    `play_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`),
    FOREIGN KEY (`video_id`) REFERENCES video(`video_id`)
);

CREATE TABLE `statistics` (
    `stats_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `video_id` INT UNSIGNED,
    `views_count` INT NOT NULL DEFAULT 0,
    FOREIGN KEY (`video_id`) REFERENCES video(`video_id`)
);

CREATE TABLE `settlement` (
    `settlement_id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT UNSIGNED,
    `amount` DECIMAL(10, 2) NOT NULL,
    `request_status` ENUM('pending', 'approved', 'rejected') DEFAULT 'pending',
    FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
);


CREATE TABLE `refresh_token` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT UNSIGNED NOT NULL,
    `refresh_token` VARCHAR(255) NOT NULL
);
