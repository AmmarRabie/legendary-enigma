USE [StudyStation]
GO
/****** Object:  StoredProcedure [dbo].[updateStudent]    Script Date: 12/21/2017 7:55:49 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[updateStudent]
	-- Add the parameters for the stored procedure here
	@email varchar(50)=null,
	@name varchar(50)=null,
	@password varchar(50)=null,
	@depName varchar(50)=null,
	@facName varchar(50)=null,
	@uniName varchar(50)=null
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
	if( @name  IS NOT NULL)
	BEGIN
	update Student
	set name=@name
	where email=@email
	END
	
	ELSE
	BEGIN
	update Student
	set name=name
	where email=@email
	END
	
	
	if( @password IS NOT NULL)
	BEGIN
	update Student
	set password=@password
	where email=@email
	
	END
	
	ELSE
	BEGIN
	update Student
	set password=password
	where email=@email
	END
	
	if (@facName IS NOT NULL )
	BEGIN
	update Student
	set facName=@facName
	where email=@email
	END

	ELSE
	BEGIN
	update Student
	set facName=facName
	where email=@email
	END
	
	
	if (@uniName IS NOT NULL)
	BEGIN
	update Student
	set uniName=@uniName
	where email=@email
	END
	
	ELSE
	BEGIN
	update Student
	set uniName=uniName
	where email=@email
	END
	
	
	if( @depName IS NOT NULL)
	BEGIN
	update Student
	set depName=@depName
	where email=@email
	END
	
	ELSE
	BEGIN
	update Student
	set depName=depName
	where email=@email
	END
	
     -- Insert statements for procedure here
	
	
	

END
