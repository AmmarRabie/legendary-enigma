use StudyStation
go



--alter table Course_Contents add creationDate datetime default getdate() not null
--go


--alter table Course_Notes add creationDate datetime default getdate() not null
--go


alter table Course_Contents add label varchar(50)
go