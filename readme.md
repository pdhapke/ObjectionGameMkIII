## About
This repository is the working program structure for an objection game to be hosted online. Students will be able to access the website and used it to practice mock trial or federal rules of evidence. It uses a java backend with Maven managment; Spring Beans and Spring MVC serving; JPA and javax persistence to interface with mySQL (Database setup script included); junit; and jstl templating. 

## Structure
The java model structure was designed with persistence in mind to establish a parity between the database tables in order to preserve the one to many relationships and add table items individually. Using separate entities later GUIs will also be able to update using drop down lists of relationships reducing the size of each question entry. Packages are separated into definitional groups and functional groups (beans) for easier bean configuration. 

Planned
- [ ] Admin
	- [x] Persistence Controller
	- [ ] GUI 
	- [ ] Authentication

- [ ] Game
	- [x] Controller
	- [x] Persistence Controller
	- [ ] GUI
	- [ ] Objection Timing
	- [ ] Practice Type

- [x] Database
	- [x] Relationship table
	- [x] SQL setup
	- [ ] Database Entries
- [ ] Structure
	- [x] Parallel Java Model
	- [x] Added JPA to object model

- [ ] Testing
	- [x] Preliminary Main() testing
	- [ ] Extended junit testing 

- [ ] Spring
	- [x] MVC utilities
	- [x] Bean utilities
	- [ ] MVC
	- [ ] Beans
	




