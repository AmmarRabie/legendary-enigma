-- ================================================
-- Template generated from Template Explorer using:
-- Create Procedure (New Menu).SQL
--
-- Use the Specify Values for Template Parameters 
-- command (Ctrl-Shift-M) to fill in the parameter 
-- values below.
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE contentsWithTag
	-- Add the parameters for the stored procedure here
	@mEmail varchar(50)
As
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	--select top 100 * from
	--(
			(select top 100
			cn.creatorEmail,cn.facName,cn.uniName,cn.courseLabelCode,cn.creationDate,
			cl.label,
			ci.tagName
			from Course_Notes cn,Course_Label cl,Classified_Into ci,Moderator m, In_Charge_Of inof
			where 
				 cn.courseLabelCode = cl.code AND cn.facName = cl.depFacName AND cn.uniName = cl.depUniName AND
				 ci.courseCode = cn.courseLabelCode AND ci.courseUniversity = cn.uniName AND ci.facultyName = cn.facName AND ci.tagName = inof.tagName AND
				 inof.moderatorEmail = m.stdEmail AND
				 m.stdEmail = @mEmail
			)
		
		--UNION
		--	(select top 100
		--	cnt.link, cnt.label, cnt.creationDate,s.name, s.depName,s.facName,s.uniName,s.email
		--	from Follow f, Selfstudy_Course_Content cnt, Student s
		--	where 
		--		  s.email = f.followeeEmail AND 
		--		  f.followerEmail = @followerEmail AND
		--		  f.followeeEmail = cnt.creatorEmail AND
		--		  cnt.creationDate > @fromDate
		--	)
	--)
	--as all_contents
	--order by all_contents.creationDate desc
END
GO
