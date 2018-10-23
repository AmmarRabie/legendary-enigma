use StudyStation
go

Create table QuestionTag (
askerEmail varchar(50),
creationDate datetime,
tagName varchar(20),
primary key(askerEmail,creationDate,tagName),
foreign key (askerEmail,creationDate)references Question(askerEmail,creationDate),
foreign key (tagName)references Tag(name));