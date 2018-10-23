class Student:
    table = 'student'

    name = 'student.name'
    password = 'student.password'
    regestrationDate = 'student.regestrationDate'
    email = 'student.email'
    dep_name = 'student.dep_name'
    fac_name = 'student.fac_name'
    uni_name = 'student.uni_name'

class Department:
    table = ' Department '

    name = ' Department.name '
    fac_name = ' Department.fac_name '
    uni_name = ' Department.uni_name '

class Faculty:
    table = 'faculty'

    name = 'faculty.name'
    universityName = ' faculty.universityName '


class StoredProcedures:
    @staticmethod
    def getContentsFollowees(followerEmail,max = None,fromdate = None):
        print(followerEmail)
        statment = 'exec getContentsFollowees @followerEmail = \'' + followerEmail + '\''
        if max is not None and fromdate is not None:
            statment += '@max ? @fromDate ?'
            return statment,followerEmail,max,fromdate
        if max is not None:
            statment += '@max ? '
            return statment, followerEmail, max
        if fromdate is not None:
            statment += '@fromDate '
            return statment, followerEmail, fromdate
        return statment

insertStatment = 'insert into Student(name,password,email,depName,facName,uniName) Values (\'{}\',\'{}\',\'{}\',\'{}\',\'{}\',\'{}\')'