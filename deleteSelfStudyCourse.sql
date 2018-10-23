USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[deleteSelfStudyCourse]    Script Date: 12/21/2017 7:52:23 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[deleteSelfStudyCourse]
	-- Add the parameters for the stored procedure here
	@eMail varchar(50),
	@courseLabel varchar(50),
	@rowsEffected int OUTPUT

AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
    delete Selfstudy_Course
	where creatorEmail=@eMail and label=@courseLabel
	select @rowsEffected=@@ROWCOUNT
END
