USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[getMyStudy]    Script Date: 21/12/2017 06:38:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

Create PROCEDURE [dbo].[getMyStudy]

	-- Add the parameters for the stored procedure here
     @Email	varchar(50)
AS
BEGIN
	
	
	
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	(select Student.depName,Course_Notes.facName,Course_Notes.uniName,label,description, 'Course Notes' AS type,Course_Notes.courseLabelCode
	from Course_Notes,Student,Course_Label
	Where @Email=Student.email AND courseLabelCode=code AND Course_Notes.facName=depFacName AND Course_Notes.uniName=depUniName
	)
	UNION
	(select depName,facName,uniName,label,'No Description' AS description,'Self Study 'AS type, 'self-study-course' as courseLabelCode
	 from   Selfstudy_Course,Student
	 where @Email=student.email And creatorEmail=Student.email
	)

END
