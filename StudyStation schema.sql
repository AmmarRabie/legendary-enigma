DROP DATABASE StudyStation
CREATE DATABASE StudyStation
go

use StudyStation
go

--declare @f int
--set f 50
 --------------------------------------------- build tables, strcure only ----------------------------------------------
CREATE TABLE Student (
	name varchar(50) NOT NULL,
	password varchar(max) NOT NULL,
	regestrationDate date NOT NULL default getdate(),
	email varchar(50) NOT NULL,
	depName varchar(50) NOT NULL,
	facName varchar(50) NOT NULL,
	uniName varchar(50) NOT NULL,
	primary key (email)
)
GO


CREATE TABLE Moderator (
	stdEmail varchar(50),
	promotionDate date NOT NULL default getdate(),
	primary key (stdEmail)
)
GO


CREATE TABLE Faculty (
	name varchar(50) NOT NULL,
	universityName varchar(50) NOT NULL,
	primary key(name,universityName)
)
GO


CREATE TABLE Tag (
	name varchar(20) NOT NULL,
	description varchar(max) NOT NULL,
	primary key (name)
)
GO


CREATE TABLE Department (
	name varchar(50) NOT NULL,
	facName varchar(50) NOT NULL,
	uniName varchar(50) NOT NULL,
	primary key (name,facName,uniName)
)
GO


CREATE TABLE Course_Label(
	code varchar(100) NOT NULL, -- make a check that it should start with fn,un( like facName_uniName_%)
	depFacName varchar(50) NOT NULL,
	depUniName varchar(50) NOT NULL,
	description varchar(max),
	label varchar(25),
	primary key (code,depFacName,depUniName)
)
GO


CREATE TABLE Course_Notes (
	creatorEmail varchar(50),
	courseLabelCode varchar(100),
	facName varchar(50),
	uniName varchar(50),
	primary key (creatorEmail,courseLabelCode,facName,uniName)
)
GO


CREATE TABLE Course_Contents(
	creatorEmail varchar(50) NOT NULL,
	courseLabelCode varchar(100) NOT NULL,
	facName varchar(50) NOT NULL,
	uniName varchar(50) NOT NULL,
	link varchar(512) NOT NULL, -- shuold i exetned more ?!
	primary key (creatorEmail,courseLabelCode,facName,uniName,link)
)
GO


CREATE TABLE Selfstudy_Course (
	creatorEmail varchar(50) NOT NULL,
	label varchar(25) NOT NULL,
	primary key(creatorEmail,label) -- 
)
GO


CREATE TABLE Selfstudy_Course_Content (
	creatorEmail varchar(50),
	label varchar(25),
	link varchar(512),
	primary key(creatorEmail,label,link)
)
GO


CREATE TABLE Question(
	askerEmail varchar(50) NOT NULL,
	creationDate datetime NOT NULL,	-- here we should also put the time as this is  key, it might be many questions of the same student at the same day, so we should cosider time
	content varchar(max) NOT NULL,
	primary key(askerEmail,creationDate)

)
GO


CREATE TABLE Answer (
	replierEmail varchar(50) NOT NULL,
	replyingDate datetime NOT NULL,
	content varchar(max) NOT NULL,
	askerEmail varchar(50) NOT NULL,
	QuestionCreationDate datetime NOT NULL,
	primary key (replierEmail,replyingDate)
)
GO


CREATE TABLE Follow (
	followerEmail varchar(50) NOT NULL,
	followeeEmail varchar(50) NOT NULL,
	primary key (followerEmail,followeeEmail)
)
GO


CREATE TABLE Votes_A_Question(
	askerEmail varchar(50) NOT NULL,
	questionCreationDate datetime NOT NULL,
	voterEmail varchar(50) NOT NULL,
	primary key (askerEmail,questionCreationDate,voterEmail)

)
GO


CREATE TABLE Votes_An_Answer (
	voterEmail varchar(50) NOT NULL,
	replierEmail varchar(50) NOT NULL,
	replyingDate datetime NOT NULL,
	primary key (voterEmail,replierEmail,replyingDate)
)
GO


CREATE TABLE Votes_Course_Note (
	voterEmail varchar(50) NOT NULL,
	ownerEmail varchar(50) NOT NULL,
	courseLabelCode varchar(100) NOT NULL,
	factName varchar(50) NOT NULL,
	uniName varchar(50) NOT NULL,
	primary key (voterEmail,ownerEmail,courseLabelCode,factName,uniName)
)
GO


CREATE TABLE Vote_Selfstudy_Course (
	voterEmail varchar(50) NOT NULL,
	courseOwnerEmail varchar(50) NOT NULL,
	courseLabel varchar(25) NOT NULL,
	primary key (voterEmail,courseOwnerEmail,courseLabel)

)
GO


CREATE TABLE Enrolled(
	enrolledEmail varchar(50) NOT NULL,
	creatorEmail varchar(50) NOT NULL,
	courseLabel varchar(25) NOT NULL,
	primary key (enrolledEmail,creatorEmail,courseLabel)
)
GO


CREATE TABLE In_Charge_Of (
	moderatorEmail varchar(50) NOT NULL,
	tagName varchar(20) NOT NULL,
	primary key (moderatorEmail,tagName)
)
GO


CREATE TABLE Classified_Into(
	courseCode varchar(100) NOT NULL,
	facultyName varchar(50) NOT NULL,
	courseUniversity varchar(50) NOT NULL,
	tagName varchar(20) NOT NULL,
	primary key (courseCode,facultyName,courseUniversity,tagName)
)
GO


CREATE TABLE Related(
	courseCreatorEmail varchar(50) NOT NULL,
	courseLabel varchar(25) NOT NULL,
	tagName varchar(20) NOT NULL,
	primary key (courseCreatorEmail,courseLabel,tagName)
)
GO
 --------------------------------------------- END ----------------------------------------------


-----------------------------------------------put the forien keys and other constraints ----------------------------------------------

--=== [HINT]: You can only make a foreign key using the complete primary key of the table you are referencing or candiate key
ALTER TABLE Student ADD CONSTRAINT Student_FK_Department FOREIGN KEY(depName,facName,uniName) REFERENCES Department(name,facName,uniName)
on update cascade
on delete cascade -- if this department deleted from the database, so any students belong to this dep is should be fired :)
GO


ALTER TABLE Department ADD CONSTRAINT Department_FK_Faculty FOREIGN KEY(facName,uniName) REFERENCES Faculty(name,universityName)
ON UPDATE CASCADE
ON DELETE CASCADE
GO


ALTER TABLE Course_Label ADD CONSTRAINT Course_Label_FK_Faculty FOREIGN KEY(depFacName,depUniName) REFERENCES Faculty(name,universityName)
ON UPDATE CASCADE
ON DELETE CASCADE
GO


ALTER TABLE Course_Notes ADD CONSTRAINT Course_Notes_FK_Student FOREIGN KEY(creatorEmail) REFERENCES [Student]([email])
ON UPDATE CASCADE
ON DELETE CASCADE
GO



ALTER TABLE Course_Notes ADD CONSTRAINT [Course_Notes_FK_Course_Label] FOREIGN KEY (courseLabelCode,facName,uniName) REFERENCES Course_Label(code,depFacName,depUniName)
--ON UPDATE CASCADE
--ON DELETE no action
GO


ALTER TABLE Course_Contents ADD CONSTRAINT Course_Contents_FK_Course_Notes FOREIGN KEY (creatorEmail,courseLabelCode,facName,uniName) REFERENCES Course_Notes(creatorEmail,courseLabelCode,facName,uniName)
ON UPDATE CASCADE
GO


ALTER TABLE Selfstudy_Course  ADD CONSTRAINT Selfstudy_Course_FK_Student FOREIGN KEY (creatorEmail) REFERENCES Student(email)
ON UPDATE CASCADE
GO


ALTER TABLE Selfstudy_Course_Content  ADD CONSTRAINT Selfstudy_Course_Content_FK_Selfstudy_Course FOREIGN KEY (creatorEmail,label) REFERENCES Selfstudy_Course(creatorEmail,label)
ON UPDATE CASCADE
GO


ALTER TABLE Question  ADD CONSTRAINT Question_FK_Student FOREIGN KEY (askerEmail) REFERENCES Student(email)
ON UPDATE CASCADE
GO


ALTER TABLE Answer  ADD CONSTRAINT Answer_FK_Student FOREIGN KEY (replierEmail) REFERENCES Student(email)
ON UPDATE CASCADE
GO


ALTER TABLE Answer  ADD CONSTRAINT Answer_FK_Question FOREIGN KEY (askerEmail,QuestionCreationDate) REFERENCES Question(askerEmail,creationDate)
ON UPDATE no action
ON DELETE no action
GO


ALTER TABLE Follow  ADD CONSTRAINT Follow_FK_Student1 FOREIGN KEY (followerEmail) REFERENCES Student(email)
ON UPDATE CASCADE
GO

ALTER TABLE Follow  ADD CONSTRAINT Follow_FK_Student2 FOREIGN KEY (followeeEmail) REFERENCES Student(email)

GO

ALTER TABLE Votes_A_Question  ADD CONSTRAINT Votes_A_Question_FK_Question FOREIGN KEY (askerEmail,questionCreationDate) REFERENCES Question(askerEmail,creationDate)
ON UPDATE CASCADE
GO


ALTER TABLE Votes_A_Question  ADD CONSTRAINT Votes_A_Question_FK_Student FOREIGN KEY (voterEmail) REFERENCES Student(email)

GO





ALTER TABLE Votes_An_Answer  ADD CONSTRAINT Votes_An_Answer_FK_Student FOREIGN KEY (voterEmail) REFERENCES Student(email)

GO
ALTER TABLE Votes_An_Answer  ADD CONSTRAINT Votes_An_Answer_FK_Answer FOREIGN KEY (replierEmail,replyingDate) REFERENCES Answer(replierEmail,replyingDate)

GO




ALTER TABLE Votes_Course_Note  ADD CONSTRAINT Votes_Course_Note_FK_Student FOREIGN KEY (voterEmail) REFERENCES Student(email)
ON UPDATE CASCADE
GO
ALTER TABLE Votes_Course_Note  ADD CONSTRAINT Votes_Course_Note_FK_Course_Notes FOREIGN KEY (ownerEmail,courseLabelCode,factName,uniName) REFERENCES Course_Notes(creatorEmail,courseLabelCode,facName,uniName)

GO




ALTER TABLE Vote_Selfstudy_Course  ADD CONSTRAINT Vote_Selfstudy_Course_FK_Student FOREIGN KEY (voterEmail) REFERENCES Student(email)

GO
ALTER TABLE Vote_Selfstudy_Course  ADD CONSTRAINT Vote_Selfstudy_Course_FK_Selfstudy_Course FOREIGN KEY (courseOwnerEmail,courseLabel) REFERENCES Selfstudy_Course(creatorEmail,label)

GO




ALTER TABLE Enrolled  ADD CONSTRAINT Enrolled_FK_Student FOREIGN KEY (enrolledEmail) REFERENCES Student(email)

GO
ALTER TABLE Enrolled  ADD CONSTRAINT Enrolled_FK_Selfstudy_Course FOREIGN KEY (creatorEmail,courseLabel) REFERENCES Selfstudy_Course(creatorEmail,label)

GO








ALTER TABLE In_Charge_Of ADD CONSTRAINT In_Charge_Of_FK_Moderator FOREIGN KEY (moderatorEmail) REFERENCES Moderator(stdEmail)
--ON UPDATE CASCADE
GO
ALTER TABLE In_Charge_Of ADD CONSTRAINT In_Charge_Of_FK_Tag FOREIGN KEY (tagName) REFERENCES Tag(name)
--ON UPDATE CASCADE
GO





ALTER TABLE Classified_Into  ADD CONSTRAINT Classified_Into_FK_Course_Label FOREIGN KEY (courseCode,facultyName,courseUniversity) REFERENCES Course_Label(code,depFacName,depUniName)
ON UPDATE CASCADE
GO
ALTER TABLE Classified_Into  ADD CONSTRAINT Classified_Into_FK_Tag FOREIGN KEY (tagName) REFERENCES Tag(name)
ON UPDATE CASCADE
GO


ALTER TABLE Related  ADD CONSTRAINT Related_FK_Selfstudy_Course FOREIGN KEY (courseCreatorEmail,courseLabel) REFERENCES Selfstudy_Course(creatorEmail,label)
ON UPDATE CASCADE
GO
ALTER TABLE Related  ADD CONSTRAINT Related_FK_Tag FOREIGN KEY (tagName) REFERENCES Tag(name)
ON UPDATE CASCADE
GO
