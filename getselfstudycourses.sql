USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[getSelfStudyCourses]    Script Date: 12/21/2017 7:54:27 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[getSelfStudyCourses]
	-- Add the parameters for the stored procedure here
	@email varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT * from Selfstudy_Course where creatorEmail = @email
END
