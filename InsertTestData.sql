use StudyStation2
go


--insert into Faculty Values
--('F11','U1'),
--('F21','U1'),
--('F31','U1'),
--('F41','U1'),
--('F51','U1'),
---- U2
--('F12','U2'),
--('F22','U2'),
--('F32','U2'),
--('F42','U2'),
--('F52','U2')

--go


--insert into Department Values
---- F11
--('D111','F11','U1'),
--('D211','F11','U1'),
--('D311','F11','U1'),
--('D411','F11','U1'),
--('D511','F11','U1'),
--('D611','F11','U1'),
--('D711','F11','U1'),
--('D811','F11','U1'),
---- F21
--('D111','F21','U1'),
--('D211','F21','U1'),
--('D311','F21','U1'),
--('D411','F21','U1'),
--('D511','F21','U1'),
--('D611','F21','U1'),
--('D711','F21','U1'),
--('D811','F21','U1')

--go
-- inserted from post man

--{
--	"name":"AmmarEl sayed",
--	"email":"Ammmarr@gh.edu.eg",
--	"pass":"123456",
--	"dep":"D511",
--	"uni":"U1",
--	"fac":"F11"
--}



--{
--	"name":"Ramy sayed",
--	"email":"RamyMohammed@gh.edu.eg",
--	"pass":"12345689",
--	"dep":"D111",
--	"uni":"U1",
--	"fac":"F11"
--}



--{
--	"name":"Loai Ali",
--	"email":"Loai@gh.edu.eg",
--	"pass":"12345689",
--	"dep":"D211",
--	"uni":"U1",
--	"fac":"F11"
--}

--{
--	"name":"Mahmoud Youssri ",
--	"email":"Youssri@gh.edu.eg",
--	"pass":"12345gg689",
--	"dep":"D211",
--	"uni":"U1",
--	"fac":"F21"
--}


--insert into Moderator Values

--('Ammmarr@gh.edu.eg',SYSDATETIME());


--insert into Tag Values
--('LA','Linear alhebra'),
--('DB','Data base'),
--('CG','computer Graphics'),
--('PS','Power System'),
--('OOP','object oriendted'),
--('DM','DiscreteMath');
--go


--insert into Course_Label values
--('000','F11','U1','course0','C0'),
--('001','F11','U1','course1','C1'),
--('002','F21','U1','course0','C2')

--insert into Faculty values ('F61','U2') 

--insert into Course_Label values
--('005','F11','U1','course0','C10'),
--('003','F11','U1','course3','C3'),
--('000','F41','U1','course4','C4'),
--('002','F41','U1','course5','C5'),
--('007','F51','U1','course7','C7'),
--('008','F61','U2','course8','C8')
--GO


--INSERT INTO Course_Notes VALUES
--('RamyMohammed@gh.edu.eg','000','F11','U1',SYSDATETIME()),
--('Loai@gh.edu.eg','001','F11','U1',SYSDATETIME()),
--('Ammmarr@gh.edu.eg','005','F11','U1',SYSDATETIME())


--INSERT INTO Course_Contents VALUES
--('RamyMohammed@gh.edu.eg','000','F11','U1','https://www.youtube.com/watch?v=fFlwojL5Zfc',SYSDATETIME(),'cnt 1'),
--('RamyMohammed@gh.edu.eg','000','F11','U1','https://www.youtube.com/watch?v=9vjCXXlgEiw',SYSDATETIME() ,'cnt 2' ) ,
--('Loai@gh.edu.eg','001','F11','U1','https://www.youtube.com/watch?v=ZNObiptSMSI&list=PL08903FB7ACA1C2FB',SYSDATETIME() , 'cnt 3' ),
--('Loai@gh.edu.eg','001','F11','U1','https://www.youtube.com/watch?v=dwSqHhMl32Y&index=4&list=PL08903FB7ACA1C2FB',SYSDATETIME() , 'cnt 4' );
--insert into Selfstudy_Course values
--('RamyMohammed@gh.edu.eg','000',SYSDATETIME()),
--('RamyMohammed@gh.edu.eg','001',SYSDATETIME()),
--('Loai@gh.edu.eg','002',SYSDATETIME()),
--('Ammmarr@gh.edu.eg','003',SYSDATETIME()),
--('Ammmarr@gh.edu.eg','004',SYSDATETIME())
--insert into Selfstudy_Course_Content values
--('RamyMohammed@gh.edu.eg','000','https://www.youtube.com/watch?v=fFlwojL5Zfc',SYSDATETIME()),
--('RamyMohammed@gh.edu.eg','001','https://www.youtube.com/watch?v=9vjCXXlgEiw',SYSDATETIME()),
--('Loai@gh.edu.eg','002','https://www.youtube.com/watch?v=ZNObiptSMSI&list=PL08903FB7ACA1C2FB',SYSDATETIME()),
--('Ammmarr@gh.edu.eg','003','https://www.youtube.com/watch?v=dwSqHhMl32Y&index=4&list=PL08903FB7ACA1C2FB',SYSDATETIME());

--insert into Question values
--('RamyMohammed@gh.edu.eg',SYSDATETIME(),'whats triggers'),
--('RamyMohammed@gh.edu.eg',convert(datetime,'18-06-12 10:34:09 PM',5),'whats eigen values'),
--('Loai@gh.edu.eg',SYSDATETIME(),'whats eigen vectors'),
--('Loai@gh.edu.eg',convert(datetime,'18-06-13 10:34:09 PM',5),'whats oop'),
--('Ammmarr@gh.edu.eg',SYSDATETIME(),'how are you'),
--('Ammmarr@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5),'whats eigen values')

--insert into Answer values
--('RamyMohammed@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5),'yes1','Loai@gh.edu.eg',convert(datetime,'18-06-13 10:34:09 PM',5)),
--('Ammmarr@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5),'yes1','Loai@gh.edu.eg',convert(datetime,'18-06-13 10:34:09 PM',5)),
--('RamyMohammed@gh.edu.eg',convert(datetime,'18-06-16 10:34:09 PM',5),'yes1','Ammmarr@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5)),
--('Loai@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5),'yes1','Loai@gh.edu.eg',convert(datetime,'18-06-13 10:34:09 PM',5))

--insert into Follow values
--('RamyMohammed@gh.edu.eg','Ammmarr@gh.edu.eg'),
--('RamyMohammed@gh.edu.eg','Loai@gh.edu.eg'),
--('Ammmarr@gh.edu.eg','RamyMohammed@gh.edu.eg'),
--('Ammmarr@gh.edu.eg','Loai@gh.edu.eg')
--insert into Votes_A_Question values
--('Loai@gh.edu.eg',convert(datetime,'18-06-13 10:34:09 PM',5),'Ammmarr@gh.edu.eg'),
--('Loai@gh.edu.eg',convert(datetime,'18-06-13 10:34:09 PM',5),'RamyMohammed@gh.edu.eg'),
--('Ammmarr@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5),'RamyMohammed@gh.edu.eg'),
--('Ammmarr@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5),'Loai@gh.edu.eg')

--insert into Votes_An_Answer values 
--('Ammmarr@gh.edu.eg','RamyMohammed@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5)),
--('Loai@gh.edu.eg','RamyMohammed@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5)),
--('Loai@gh.edu.eg','Ammmarr@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5)),
--('RamyMohammed@gh.edu.eg','Ammmarr@gh.edu.eg',convert(datetime,'18-06-15 10:34:09 PM',5))
--insert into Votes_Course_Note values
--('Loai@gh.edu.eg','RamyMohammed@gh.edu.eg','000','F11','U1'),
--('Ammmarr@gh.edu.eg','RamyMohammed@gh.edu.eg','000','F11','U1'),
--('RamyMohammed@gh.edu.eg','Loai@gh.edu.eg','001','F11','U1'),
--('Ammmarr@gh.edu.eg','Loai@gh.edu.eg','001','F11','U1')
--insert into Vote_Selfstudy_Course values 
--('Loai@gh.edu.eg','RamyMohammed@gh.edu.eg','000'),
--('Ammmarr@gh.edu.eg','RamyMohammed@gh.edu.eg','000'),
--('Ammmarr@gh.edu.eg','Loai@gh.edu.eg','002'),
--('RamyMohammed@gh.edu.eg','Loai@gh.edu.eg','002')
--insert into Enrolled values 
--('Ammmarr@gh.edu.eg','RamyMohammed@gh.edu.eg','000'),
--('Loai@gh.edu.eg','RamyMohammed@gh.edu.eg','000'),
--('Ammmarr@gh.edu.eg','Loai@gh.edu.eg','002'),
--('RamyMohammed@gh.edu.eg','Loai@gh.edu.eg','002')
--insert into In_Charge_Of values 
--('Ammmarr@gh.edu.eg','LA'),
--('Ammmarr@gh.edu.eg','CG'),
--('Ammmarr@gh.edu.eg','DB'),
--('Ammmarr@gh.edu.eg','PS')
--INSERT INTO Classified_Into values
--('005','F11','U1','LA'),
--('005','F11','U1','PS'),
--('008','F61','U2','OOP'),
--('008','F61','U2','CG')
--INSERT INTO Related VALUES
--('RamyMohammed@gh.edu.eg','000','CG'),
--('RamyMohammed@gh.edu.eg','000','LA'),
--('Loai@gh.edu.eg','002','PS'),
--('Loai@gh.edu.eg','002','OOP')
--INSERT INTO QuestionTag VALUES
--('RamyMohammed@gh.edu.eg',convert(datetime,'18-06-12 10:34:09 PM',5),'PS'),
--('RamyMohammed@gh.edu.eg',convert(datetime,'18-06-12 10:34:09 PM',5),'LA'),
--('Loai@gh.edu.eg',convert(datetime,'18-06-13 10:34:09 PM',5),'CG'),
--('Loai@gh.edu.eg',convert(datetime,'18-06-13 10:34:09 PM',5),'OOP')
--INSERT INTO Admins VALUES
--('YOSSRY','7ODA')