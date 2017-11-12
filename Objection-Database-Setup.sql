
DROP DATABASE objection_database;
CREATE DATABASE objection_database; 


use objection_database; 

-- this will create the context table 
-- primary key is the case_ID, no foregin keys. 
-- one to many relationship with witnesses
CREATE TABLE context (
case_id int PRIMARY KEY AUTO_INCREMENT, 
context VARCHAR(300) NOT NULL
);

-- this will create the witness table
-- primary key is the witness_ID, foregin key is case_ID. 
-- one to many relationship with questions
CREATE TABLE witnesses (
  	witness_id int PRIMARY KEY AUTO_INCREMENT, 
	case_id int, 
    first_name VARCHAR(30), 
    last_name VARCHAR(30),
    side VARCHAR(15), 
    affidavit MEDIUMTEXT, 
    
    INDEX(witness_id), 
    FOREIGN KEY (case_id)
		REFERENCES context(case_id)
  );

-- this will create the questions table
-- primary key is the question_ID, foregin key is witness_ID. 
-- one to many relationship with correct_objection
CREATE TABLE questions (
question_id int PRIMARY KEY AUTO_INCREMENT, 
witness_id int, 
previous_question_id int, 
side varchar(12), 
question varchar(500), 
answer varchar(1500),

INDEX(question_id),
FOREIGN KEY (witness_id)
	REFERENCES witnesses (witness_id)
);


-- this will create the objection table
-- primary key is the objection_id and is NOT autogenerated but instead pulled from Federal rules of evidence, no foreign key
-- one to many relationship with correct_objection table

CREATE TABLE objection (
	objection_rule_number int PRIMARY KEY,
	objection_type VARCHAR(20),
    explanation VARCHAR(2000),
	
	INDEX(objection_rule_number)
	);

-- this will create the correct_objection table
-- primary key is the correct_objection_ID, functions also as a bridging table using two foreign keys question_id and objection_id. 
-- one to many relationship with witnesses, one to many relationship with objections
    
CREATE TABLE correct_objection (
	correct_objection_id int PRIMARY KEY AUTO_INCREMENT, 
	objection_rule_number int, 
	question_id int, 
	correct_reason VARCHAR(500),
	correct_time VARCHAR(20), 
	
INDEX(Objection_rule_number), 
INDEX(question_id), 
FOREIGN KEY (objection_rule_number)
	REFERENCES objection (objection_rule_number),
FOREIGN KEY (question_id)
	REFERENCES questions (question_id)
	);

CREATE TABLE users (
email VARCHAR(40) PRIMARY KEY, 
admin BOOLEAN, 
highscore INT, 
firstname VARCHAR(30), 
lastname VARCHAR(30),

INDEX(email)
); 

