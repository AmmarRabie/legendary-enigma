USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[deleteCourseNotes]    Script Date: 12/21/2017 7:51:31 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE[dbo].[deleteCourseNotes] 
	-- Add the parameters for the stored procedure here
	@eMail varchar(50),
	@labelCode varchar(50),
	@courseFacName varchar(50),
	@courseUniName varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	delete Course_Notes
	where creatorEmail=@eMail and courseLabelCode=@labelCode and facName=@courseFacName and uniName=@courseUniName
END
