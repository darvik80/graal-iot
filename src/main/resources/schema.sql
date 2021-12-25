CREATE TABLE IF NOT EXISTS port_code_mapping (
	id integer identity primary key,
	device_id integer,
	port_code varchar(30),
	destination integer
);
