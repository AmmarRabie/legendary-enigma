USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[getContentsFollowees]    Script Date: 18/12/2017 02:17:50 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[getContentsFollowees]
	-- Add the parameters for the stored procedure here
	@fromDate datetime = null,
	@followerEmail varchar(50),
	@max integer = 100
	
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	if @fromDate is null
	set @fromDate = dateadd(DAY,-2, GETDATE())
	if @max > 300
	set @max = 300
    select top 100 * from
	(
			(select top 100
			cnt.link, cnt.label, cnt.creationDate,s.name, s.depName,s.facName,s.uniName,s.email
			from Follow f, Course_Contents cnt, Student s
			where 
				  s.email = f.followeeEmail AND 
				  f.followerEmail = @followerEmail AND
				  f.followeeEmail = cnt.creatorEmail AND
				  cnt.creationDate > @fromDate
			)
		UNION
			(select top 100
			cnt.link, cnt.label, cnt.creationDate,s.name, s.depName,s.facName,s.uniName,s.email
			from Follow f, Selfstudy_Course_Content cnt, Student s
			where 
				  s.email = f.followeeEmail AND 
				  f.followerEmail = @followerEmail AND
				  f.followeeEmail = cnt.creatorEmail AND
				  cnt.creationDate > @fromDate
			)
	)
	as all_contents
	order by all_contents.creationDate desc
END
