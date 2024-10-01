CREATE TABLE `parties` (
    `party_id` int(2),
    `party_name` varchar(255)
    PRIMARY KEY (`party_id`)
);

CREATE TABLE `candidates` (
    `candidate_id` int(4),
    `party_id` int(2),
    `member_id` int(3),
    `initials` char(3),
    `first_name` varchar(255),
    `last_name` varchar(255),
    `gender` varchar(6),
    `locality` varchar(255),
    PRIMARY KEY (`candidate_id`),
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`)
);

CREATE TABLE `constituencies` (
    `constituency_id` int(2),
    `constituency_name` varchar(255),
    PRIMARY KEY (`constituency_id`)
);

CREATE TABLE `constituency_candidates` (
    `constituency_id` int(2),
    `candidate_id` int(4),
    FOREIGN KEY (`constituency_id`) REFERENCES `constituencies`(`constituency_id`),
    FOREIGN KEY (`candidate_id`) REFERENCES `candidates`(`candidate_id`)
);

CREATE TABLE `constituency_votes` (
    `constituency_id` int(2),
    `party_id` int(2),
    `candidate_id` int(4),
    `amount_votes` int(8),
    FOREIGN KEY (`constituency_id`) REFERENCES `constituencies`(`constituency_id`),
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`),
    FOREIGN KEY (`candidate_id`) REFERENCES `candidates`(`candidate_id`)
);

CREATE TABLE `municipalities` (
    `municipality_id` int(3),
    `constituency_id` int(2),
    `constituency_name` varchar(255),
    PRIMARY KEY (`municipality_id`),
    FOREIGN KEY (`constituency_id`) REFERENCES `constituencies`(`constituency_id`)
);

CREATE TABLE `municipality_votes` (
    `municipality_id` int(3),
    `party_id` int(2),
    `candidate_id` int(4),
    `amount_votes` int(8),
    FOREIGN KEY (`municipality_id`) REFERENCES `municipalities`(`municipality_id`),
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`),
    FOREIGN KEY (`candidate_id`) REFERENCES `candidates`(`candidate_id`)
);

CREATE TABLE `polling_stations` (
    `polling_station_id` int(5),
    `munipality_id` int(3),
    `polling_station_name` varchar(255),
    PRIMARY KEY (`polling_station_id`),
    FOREIGN KEY (`municipality_id`) REFERENCES `municipalities`(`municipality_id`)
);

CREATE TABLE `polling_station_votes` (
    `polling_station_id` int(5),
    `party_id` int(2),
    `candidate_id` int(4),
    `amount_votes` int(8),
    FOREIGN KEY (`polling_station_id`) REFERENCES `polling_stations`(`polling_station_id`),
    FOREIGN KEY (`party_id`) REFERENCES `parties`(`party_id`),
    FOREIGN KEY (`candidate_id`) REFERENCES `candidates`(`candidate_id`)
);