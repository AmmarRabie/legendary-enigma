USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[getCourseList]    Script Date: 21/12/2017 06:43:05 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
Create PROCEDURE [dbo].[getCourseList]
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
