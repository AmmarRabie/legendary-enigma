USE [StudyStation2]
GO
create PROCEDURE [dbo].[AddModerator] @Mdemail varchar(50)
AS
begin
insert into Moderator
values
(@Mdemail,(select CONVERT(date,GETDATE())))
END
go

create procedure [dbo].[addtag2mod] @mod varchar(50), @tag varchar(50)

AS
BEGIN
insert into In_Charge_of
values
(@mod,@tag)
End 
go

create PROCEDURE [dbo].[BanStudent] @stdemail varchar(50)
AS
BEGIN
update Student
set ban=1
where email=@stdemail
END
go

create procedure [dbo].[changepassword] @admin varchar(50), @newpass varchar(50)
as 
begin
update Admins
set passwrd=@newpass
where username=@admin
end 
go
create PROCEDURE [dbo].[GetbanStudents]
AS
BEGIN
select email
from Student
where ban=1
END
go


create procedure [dbo].[countstudents] 

AS
BEGIn
return(
select  count(*)
from  Student
)
end
go

create PROCEDURE [dbo].[controlledtags] @moderatoreml varchar(50)
AS
BEGIN
 
select  tagName
from In_Charge_of
where moderatorEmail=@moderatoreml
END
go

create PROCEDURE [dbo].[CheckAdmin] @username varchar(50), @password varchar(50)
AS
BEGIN
SELECT *
from Admins 
where username=@username and passwrd=@password
END
go

create PROCEDURE [dbo].[GetModerators]
AS
begin
 
select stdemail
from Moderator;
END
go

create PROCEDURE [dbo].[getModeratorData]
	-- Add the parameters for the stored procedure here
	@email varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT s.name,s.email,s.password,s.depName,s.facName,s.uniName,s.regestrationDate,m.promotionDate from Student s,Moderator m
	where  s.email = m.stdEmail AND m.stdEmail = @email
END
go

create PROCEDURE [dbo].[getFollowees] 
	-- Add the parameters for the stored procedure here
	@followerUserEmail varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	--SELECT <@Param1, sysname, @p1>, <@Param2, sysname, @p2>
	SELECT s.name,s.email,s.depName,s.facName,s.uniName
        FROM  Student as s, Follow as f  
        WHERE s.email = f.followeeEmail AND 
        f.followerEmail =  @followerUserEmail
END
go

create PROCEDURE [dbo].[getCourses] 
	-- Add the parameters for the stored procedure here
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	select count(*)
	from Course_Label
	
END
go


create PROCEDURE [dbo].[maxanswered] 
AS 
begin
(
select replierEmail,count(*)as c
from Answer
group by replierEmail
)
order by c desc;
end
go

create PROCEDURE [dbo].[gettagsno] 
	-- Add the parameters for the stored procedure here
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	select count(*)
	from tag
	
END
go

create PROCEDURE [dbo].[GetStudents]
AS
BEGIN
select email
from Student
where ban=0
END
go

create PROCEDURE [dbo].[getstudentno] 
	-- Add the parameters for the stored procedure here
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
select count(*)
	from Student

END
 go

create PROCEDURE [dbo].[getstudentnames]
AS
BEGIN
SELECT name
From Student
END
go

create PROCEDURE [dbo].[getStudentData]
	-- Add the parameters for the stored procedure here
	@email varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT * from Student where Student.email = @email
END
 go
create PROCEDURE [dbo].[getquestion] 
	-- Add the parameters for the stored procedure here
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	

    -- Insert statements for procedure here
	select count(*)
	from Question
	
END
go

create PROCEDURE [dbo].[uncontrolledtags] @moderatoreml varchar(50)
AS
BEGIN
select name
from Tag
except 
select  tagName
from In_Charge_of
where moderatorEmail=@moderatoreml
END
go

create  PROCEDURE [dbo].[unBanStudent] @stdemail varchar(50)
AS
BEGIN
update Student
set ban=0
where email=@stdemail
END
go

create PROCEDURE [dbo].[StudentsNotMod]
AS
begin
select email
from Student
EXCEPT 
select stdemail
from Moderator;
END
go

create procedure [dbo].[removetag2mod] @mod varchar(50), @tag varchar(50)

AS
BEGIN
delete from In_Charge_Of
where moderatorEmail=@mod and tagName=@tag
End 

go
create PROCEDURE [dbo].[RemoveModerator] @Mdemail varchar(50)
AS
begin
delete from Moderator
where stdEmail=@Mdemail
END
go



use StudyStation2
go 

create procedure returnenrolled
as
begin
select*
from Enrolled
end
 go

 create procedure returnv4ssc
as
begin
select*
from Vote_Selfstudy_Course
end
 go

 create procedure returnv4question
as
begin
select*
from Votes_A_Question
end
 go

  create procedure returnv4answer
as
begin
select*
from Votes_An_Answer
end
 go





