USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[getModeratorData]    Script Date: 12/21/2017 7:54:46 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER PROCEDURE [dbo].[getModeratorData]
	-- Add the parameters for the stored procedure here
	@email varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	SELECT *from Student s,Moderator m
	where  s.email = m.stdEmail AND m.stdEmail = @email
END
