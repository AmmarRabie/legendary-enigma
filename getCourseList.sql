USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[getCourseList]    Script Date: 12/21/2017 7:52:38 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[getCourseList] 
	-- Add the parameters for the stored procedure here
	@facName varchar(50),
	@uniName varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
select *
from  Course_Label
where  Course_Label.depUniName=@uniName AND Course_Label.depFacName=@facName
END
