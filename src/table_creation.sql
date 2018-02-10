CREATE TABLE `users` (
  `userName` varchar(25) NOT NULL,
  `pWord` varbinaryclients NOT NULL,
  `email` varchar(50) NOT NULL,
  `fName` varchar(20) NOT NULL,
  `lName` varchar(25) NOT NULL,
  PRIMARY KEY (`userName`)
);

CREATE TABLE `clients` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fName` varchar(20) NOT NULL,
  `lName` varchar(25) NOT NULL,
  #For these columns, a value of true means eligible for laundry that day.
  `monday` bool NOT NULL DEFAULT TRUE COMMENT 'True means eligible for laundry.',
  `tuesday` bool NOT NULL DEFAULT TRUE,
  `wednesday` bool NOT NULL DEFAULT TRUE,
  `thursday` bool NOT NULL DEFAULT TRUE,
  `friday` bool NOT NULL DEFAULT TRUE,
  `saturday` bool NOT NULL DEFAULT TRUE,
  `sunday` bool NOT NULL DEFAULT TRUE,
  `today` bool NOT NULL DEFAULT TRUE COMMENT 'This will save the state of a clients laundry for the current day. At midnight, all clients "today" statuses will reset to TRUE.'
);

CREATE TABLE `laundry_loads` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `client_id` int NOT NULL,
  `drop_off` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `drop_off_sig` varChar(15) NOT NULL,
  `load_complete` datetime,
  `load_complete_sig` varChar(15),
  `pick_up` datetime,
  `pick_up_sig` varChar(15),
  FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `clients_archive` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fName` varchar(20) NOT NULL,
  `lName` varchar(25) NOT NULL,
  #For these columns, a value of true means eligible for laundry that day.
  `monday` bool NOT NULL DEFAULT TRUE COMMENT 'True means eligible for laundry.',
  `tuesday` bool NOT NULL DEFAULT TRUE,
  `wednesday` bool NOT NULL DEFAULT TRUE,
  `thursday` bool NOT NULL DEFAULT TRUE,
  `friday` bool NOT NULL DEFAULT TRUE,
  `saturday` bool NOT NULL DEFAULT TRUE,
  `sunday` bool NOT NULL DEFAULT TRUE,
  `today` bool NOT NULL DEFAULT TRUE COMMENT 'This will save the state of a clients laundry for the current day. At midnight, all clients "today" statuses will reset to TRUE.'
);

CREATE TABLE `laundry_loads_archive` (
  `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `client_id` int NOT NULL,
  `drop_off` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `load_complete` datetime,
  `pick_up` datetime,
  FOREIGN KEY(client_id) REFERENCES clients_archive(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TRIGGER archive_laundry_loads BEFORE DELETE ON laundry_loads
	FOR EACH ROW 
		INSERT INTO laundry_loads_archive (id, client_id, drop_off, load_complete, pick_up)
			VALUES (OLD.id, OLD.client_id, OLD.drop_off, OLD.load_complete, OLD.pick_up);	
            
CREATE TRIGGER archive_clients BEFORE DELETE ON clients
	FOR EACH ROW
		INSERT INTO clients_archive (id, fName, lName, monday, tuesday, wednesday, thursday, friday, saturday, sunday)
			VALUES (OLD.id, OLD.fName, OLD.lName, OLD.monday, OLD.tuesday, OLD.wednesday, OLD.thursday, OLD.friday, OLD.saturday, OLD.sunday);
