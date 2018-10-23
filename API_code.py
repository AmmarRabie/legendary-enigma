import datetime
from functools import wraps

import jwt
from flask import Flask, jsonify, request, Response, json, make_response
from flask_sqlalchemy import SQLAlchemy
from jwt.exceptions import InvalidTokenError
# from flask_jwt import JWT, jwt_required, current_identity
from werkzeug.security import generate_password_hash, check_password_hash

import DatabaseDefinition as DD
import QueryHelper as qh
from DatabaseDefinition import StoredProcedures

# TODO: Read all comments

### for postgress sql ### file_path = os.path.abspath(os.getcwd()) + "/" + "flask_test.db"

app = Flask(__name__)
# app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres:///'+file_path
# app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///mnt/C/sqlite/hhhh.db'
### for postgress sql ###app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres://postgres:1973197355@localhost/test_psql'


# TODO: change the server name(AMMAR\SQLEXPRESS) and the database name(studyStation_ammarTest) according to your situation
app.config[
    'SQLALCHEMY_DATABASE_URI'] = 'mssql+pyodbc://AMMAR\SQLEXPRESS/StudyStation2?driver=ODBC+Driver+11+for+SQL+Server'
app.config['SECRET_KEY'] = '__TheSecretKey__'

db = SQLAlchemy(app)

userEmail_table = db.engine.execute("Select email from Student")  # table with all emails
userEmail_List = []
for email in userEmail_table:
    userEmail_List.append(email['email'])
del userEmail_table


def student_token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None

        if 'x-access-token' in request.headers:
            token = request.headers['x-access-token']

        if not token:
            return jsonify({'mes': 'Token is missing!'}), 401

        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
            current_student = db.engine.execute('getStudentData ?', [data['email']]).first()
            print(current_student['email'])

            if (current_student['ban'] is 1):
                return jsonify({'mes': 'sorry you are banned from admin!'}), 401

        except:
            return jsonify({'mes': 'Token is invalid!'}), 401

        return f(current_student, *args, **kwargs)

    return decorated


def moderator_token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None

        if 'x-access-token' in request.headers:
            token = request.headers['x-access-token']

        if not token:
            return jsonify({'mes': 'Token is missing!'}), 401

        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
            current_moderator = db.engine.execute('getModeratorData ?', [data['email']]).first()
            if not current_moderator:
                return jsonify({'mes': 'access denied!'}), 401  # this mean he is student but not a moderator

            if (current_moderator['ban'] is 1):
                return jsonify({'mes': 'sorry you are banned from admin!'}), 401

        except:
            return jsonify({'mes': 'Token is invalid!'}), 401

        return f(current_moderator, *args, **kwargs)

    return decorated


def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None

        if 'x-access-token' in request.headers:
            token = request.headers['x-access-token']

        if not token:
            return jsonify({'mes': 'Token is missing!'}), 401

        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
            current_user = db.engine.execute('getModeratorData ?', [data['email']]).first()
            if current_user:
                return f(current_user, *args, **kwargs)  # moderator
            current_user = db.engine.execute('getStudentData ?', [data['email']]).first()

            if (current_user['ban'] is 1):
                return jsonify({'mes': 'you are banned from admin!'}), 401
            # if current_user:
            #     return f(current_user, *args, **kwargs) # student
        except InvalidTokenError as e:
            print(e.__str__())
            return jsonify({'mes': 'Token is invalid!'}), 401
        # except KeyError
        return f(current_user, *args, **kwargs)

    return decorated


################################ error handling template ##############################################
class InvalidUsage(Exception):
    status_code = 400

    def __init__(self, message, status_code=None, payload=None):
        Exception.__init__(self)
        self.message = message
        if status_code is not None:
            self.status_code = status_code
        self.payload = payload

    def to_dict(self):
        rv = dict(self.payload or ())
        rv['message'] = self.message
        return rv


@app.errorhandler(InvalidUsage)
def handle_invalid_usage(error):
    response = jsonify(error.to_dict())
    response.status_code = error.status_code
    return response


##############################################################################





###################################### try thisngs #####################################################
@app.route('/tryHeaders')
def __testHeaders__():
    # print(res)
    # resp = res
    m = resp = Response(response=json.dumps({"arrayKey": [{"ammar": 'value'}, {"hhhh": 'heeeh'}]}), status=200,
                        mimetype='application/json')
    print("type of resp is ", type(resp))
    # resp.headers['Ammar'] = 'Nice flask nice'
    try:
        print(request.headers['ammar'])
    except KeyError as e:
        print(e.__str__())
        print(e)
        return "send header 'ammar' and give it a value"
    res = jsonify({"access_token": "kfSDKFgkSGOOSjcOOFCJDUISofijcsodjvOIDJOSVR0T939RGIVKFSV"})
    res.headers['cz'] = 'Nice flask nice'
    print("type of jsonify is ", type(res))
    return res


@app.route('/tryError')
def get_error():
    raise InvalidUsage({"myMesg1": 'error...', "myMesg2": 'error...'}, status_code=410)


@app.route('/tryPostJson', methods=['post'])
def __testPost__():
    var = request.get_json(silent=True)
    print("var type is ", type(var))
    iThinkjson = var['key1']['GlossDiv']['GlossList']['GlossEntry']['ID']
    print("iThinkjson type is ", type(iThinkjson), "and value is = ", iThinkjson)
    return jsonify(var)


@app.route('/tryInsertTryCatch', methods=['post'])
def __testIsert__():
    req = request.get_json(silent=True)
    try:
        email = req['email']
        print(email, " the type is ", type(email))
        email = qh.put_quote(str(email))
        print("email after cast = ", email, " and the type = ", type(email))
        password = qh.put_quote(req['pass'])
        dep = qh.put_quote(req['dep'])
        fac = qh.put_quote(req['fac'])
        uni = qh.put_quote(req['uni'])
    except KeyError as ke:
        print("error in input " + ke.__str__())
        raise InvalidUsage("data error, cant find the data to insert")

    try:
        id = db.engine.execute("insert into {}({},{},{},{},{}) values ({},{},{},{},{})".format(
            DD.Student.table,
            DD.Student.email, DD.Student.password, DD.Student.dep_name, DD.Student.fac_name, DD.Student.uni_name,
            email, password, dep, fac, uni
        ))
    except:
        raise InvalidUsage("can't insert in the database")

    return jsonify(id)


#######################################################################################################################



# START___________________________________ get followees ___________________________________tested = true
@app.route('/followees')
@student_token_required
def getFollowees(current_student):
    followees = db.engine.execute('getFollowees ?', current_student['email'])
    output = []
    for followee in followees:
        # one dictionary for each followee
        followee_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        followee_data['name'] = followee['name']
        followee_data['email'] = followee['email']
        followee_data['dep'] = followee['depName']
        followee_data['fac'] = followee['facName']
        followee_data['uni'] = followee['uniName']
        # in the end of each iteration append the dictionary to the list
        output.append(followee_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'followees': output}), 200


# END===================================== get followees ==================================




# START___________________________________ news feed ___________________________________tested = false
@app.route('/newsfeed')
@student_token_required
def getNewsfeed(current_student):
    news = db.engine.execute(StoredProcedures.getContentsFollowees(followerEmail=current_student['email']))
    output = []
    for new in news:
        # one dictionary for each followee
        new_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        new_data['content_link'] = new['link']
        new_data['creatorEmail'] = new['email']
        new_data['creatorName'] = new['name']
        new_data['contentLabel'] = new['label']
        new_data['creationDate'] = new['creationDate']
        new_data['creatorDep'] = new['depName']
        new_data['creatorFac'] = new['facName']
        new_data['creatorUni'] = new['uniName']
        # in the end of each iteration append the dictionary to the list
        output.append(new_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'newsfeed': output})


# END===================================== news feed ==================================


# START___________________________________ SIGNUP UP _______________________________________tested = true
@app.route('/signup', methods=['post'])
def insert():
    # take inputs
    data_json = request.get_json()
    registration_date = datetime.datetime.utcnow().date()

    try:
        name = data_json['name']
        dep = data_json['dep']
        fac = data_json['fac']
        uni = data_json['uni']
        email = data_json['email']
    except:
        return jsonify({'mes': "please enter all data"}), 401
    if email in userEmail_List:
        return jsonify({"mes: ": "this email is already exist"}), 400
    pas = generate_password_hash(data_json['pass'], method='sha256')

    query = DD.insertStatment.format(name, pas, email, dep, fac, uni)
    db.engine.execute(query)
    userEmail_List.append(email)
    # TODO: Change the 1 weak to 24 hours only, this is only for quick test:
    token = jwt.encode(
        {'email': email, "pass": pas, 'exp': datetime.datetime.utcnow() + datetime.timedelta(hours=10000)},
        app.config['SECRET_KEY'])
    return jsonify(
        {'token': token.decode('UTF-8'), 'mes': "new student has been created", "date": registration_date}), 201


# END===================================== SIGHN UP ==================================


# START___________________________________ LOG IN ________________________________________tested = true
@app.route('/login')
def login():
    auth = request.authorization

    if not auth or not auth.username or not auth.password:
        return jsonify({'mes': "you should enter username and password"}), 400

    user_data = db.engine.execute('getStudentData ?', [auth.username]).first()

    if not user_data:
        return jsonify({'mes': "this email is incorrect"}), 401

    if check_password_hash(user_data.password, auth.password):
        token = jwt.encode({'email': user_data.email, 'pass': user_data['password'],
                            'exp': datetime.datetime.utcnow() + datetime.timedelta(hours=2)}, app.config['SECRET_KEY'])
        return jsonify({'token': token.decode('UTF-8')}),200

    return jsonify({'mes': "this password is incorrect"}), 402


# END==================================  LOG IN ===============================




# @app.route('/users/<userEmail>/<password>')
# def getUserData(userEmail, password):
#     query_Exist = \
#         "SELECT Count(1) From student Where student.email = \'" + userEmail + "\';"
#
#     if (db.engine.execute(query_Exist) == 0):
#         return "invalid email"
#
#     query_Exist = \
#         "SELECT Count(1) From student Where student.email = \'" + userEmail + "\' AND password = \'" + password + "\';"
#
#     if (db.engine.execute(query_Exist) == 0):
#         return "access denied"
#
#     query = '''select * from student Where student.email = \'" + userEmail + "\';" '''
#     user = db.engine.execute(query)
#     user_data = {}
#     user_data['name'] = user['name']
#     user_data['email'] = user['email']
#     user_data['dep'] = user['dep_name']
#     user_data['fac'] = user['fac_name']
#     return jsonify(user_data)
#
#
# @app.route('/users/<userEmail>', methods=["POST"])
# def insertStudent(userEmail):
#     query_Exist = \
#         "SELECT Count(*) as c From student Where student.email = \'" + userEmail + "\';"
#
#     db_engine_execute = db.engine.execute(query_Exist)
#     count = 0
#     for user in db_engine_execute:
#         count = user['c']
#         print(count)
#
#     if (count == 1):
#         return "this email already exist"
#
#     query_insert = '''Insert Into Student(name,password,email,dep_name,fac_name,uni_name) Values ('AmmarAlsayed','15975315975355',' ''' + userEmail + ''' ' ,'CMP','faculty of engineering','cairo university') '''
#
#     result = db.engine.execute(query_insert)
#     # print(result)
#
#     password = request.json['pass']
#     content = request.get_json(silent=True)
#     print(content)
#     return jsonify(content)



@app.route('/profile')
@token_required
def getProfile(current_user):
    """
    this fun to return the profile depending on user type and email parameter in url
    :param current_user: the user passed from token, it should be either moderator or student
    :return:
    """
    #if 'email' in request.args:
    email = request.args.get('email', current_user['email'])
    user_data = {}  # to put our projection
    user_data['email'] = email
    user_data['dep'] = current_user['depName']
    user_data['fac'] = current_user['facName']
    user_data['uni'] = current_user['uniName']
    user_data['registrationDate'] = current_user['regestrationDate']
    user_data['name'] = current_user['name']
    user_data['role'] = 'Student'
    if 'promotionDate' in current_user:
        user_data['role'] = 'Moderator'
        user_data['promotionDate'] = current_user['promotionDate']
    return jsonify(user_data),200


# /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
# /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
# mady by ramy mohammed   12/9/2017//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
# START___________________________________ searchStudent _______________________________________tested = true
@app.route('/searchstudent/<studentname>', methods=['get'])
def searchstudent(studentname):
    # take inputs
    students = db.engine.execute('searchStudent ?', studentname)
    output = []
    for student in students:
        # one dictionary for each followee
        student_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        student_data['name'] = student['name']
        student_data['email'] = student['email']
        student_data['dep'] = student['depName']
        student_data['fac'] = student['facName']
        student_data['uni'] = student['uniName']
        # in the end of each iteration append the dictionary to the list
        output.append(student_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'students': output})


# END===================================== SesarchStudent ==================================


# START___________________________________ get student by email _______________________________________tested = true
@app.route('/getstudentbyemail/<studentemail>', methods=['get'])
def getstudentbyemail(studentemail):
    # take inputs
    students = db.engine.execute('getStudentData ?', studentemail)
    output = []
    for student in students:
        # one dictionary for each followee
        student_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        student_data['name'] = student['name']
        student_data['email'] = student['email']
        student_data['dep'] = student['depName']
        student_data['fac'] = student['facName']
        student_data['uni'] = student['uniName']
        # in the end of each iteration append the dictionary to the list
        output.append(student_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'students': output})


# END===================================== SesarchStudent ==================================
# START___________________________________ getQuestionBytag_______________________________________tested = true
@app.route('/getquestionbytag/<tagname>', methods=['get'])
def getquestionbytag(tagname):
    # take inputs
    questions = db.engine.execute('getQuestionsByTag ?', tagname)
    output = []
    for question in questions:
        # one dictionary for each followee
        question_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        question_data['askerEmail'] = question['askerEmail']
        question_data['creationDate'] = question['creationDate']
        question_data['content'] = question['content']
        # in the end of each iteration append the dictionary to the list
        output.append(question_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'questions': output})


# END===================================== getQuestionBYTag ========================================================


# START___________________________________ get tags_______________________________________tested = true
@app.route('/gettags', methods=['get'])
def gettags():
    # take inputs
    tags = db.engine.execute("{call dbo.getTags}")
    output = []
    for tag in tags:
        # one dictionary for each followee
        tag_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        tag_data['name'] = tag['name']
        tag_data['description'] = tag['description']
        # in the end of each iteration append the dictionary to the list
        output.append(tag_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'tags': output})


# END=====================================get tags ==================================



# START___________________________________ searchquestion   by part of the question_______________________________________tested = true
@app.route('/searchquestion/<title>', methods=['get'])
def searchquestion(title):
    # take inputs
    questions = db.engine.execute('searchQuestion ?', title)
    output = []
    for question in questions:
        # one dictionary for each followee
        question_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        question_data['askerEmail'] = question['askerEmail']
        question_data['creationDate'] = question['creationDate']
        question_data['content'] = question['content']
        # in the end of each iteration append the dictionary to the list
        output.append(question_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'questions': output})


# END===================================== searchquestion ========================================================



# START___________________________________ searchquestionByTag   by tag and part of the question_______________________________________tested = true
@app.route('/searchquestionbytag/<tag>/<title>', methods=['get'])
def searchquestionbytag(tag, title):
    # take inputs
    params = (tag, title)
    questions = db.engine.execute('{CALL searchQuestionsByTag (?,?)}', (tag, title))
    output = []
    for question in questions:
        # one dictionary for each followee
        question_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        question_data['askerEmail'] = question['askerEmail']
        question_data['creationDate'] = question['creationDate']
        question_data['content'] = question['content']
        # in the end of each iteration append the dictionary to the list
        output.append(question_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'questions': output})


# END===================================== searchquestionByTag ========================================================


# START___________________________________ getquestionAnswers_______________________________________tested = true
@app.route('/getquestionanswers/<askerEmail>/<creationDate>', methods=['get'])
def getquestionanswers(askerEmail, creationDate):
    # take inputs
    answers = db.engine.execute('{CALL getQuestionAnswers (?,?)}', (askerEmail, creationDate))
    output = []
    for answer in answers:
        # one dictionary for each followee
        answer_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        answer_data['replierEmail'] = answer['replierEmail']
        answer_data['replyingDate'] = answer['replyingDate']
        answer_data['content'] = answer['content']
        answer_data['askerEmail'] = answer['askerEmail']
        answer_data['QuestionCreationDate'] = answer['QuestionCreationDate']

        # in the end of each iteration append the dictionary to the list
        output.append(answer_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'answers': output})


# END===================================== getquestionAnswers_______________________________________

#START___________________________________ check answer vote_______________________________________tested = true
@app.route('/checkanswervote/<voterEmail>/<replierEmail>/<replingDate>', methods=['get'] )
def checkanswervote(voterEmail,replierEmail,replingDate):
    # take inputs
    answervotes = db.engine.execute('{CALL checkAnswerVote (?,?,?)}', (voterEmail,replierEmail, replingDate))
    output = []
    for answervote in answervotes:
        # one dictionary for each followee
        answervote_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        answervote_data['replierEmail'] = answervote['replierEmail']
        answervote_data['replyingDate'] = answervote['replyingDate']
        answervote_data['voterEmail'] = answervote['voterEmail']

        # in the end of each iteration append the dictionary to the list
        output.append(answervote_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'answervotes': output})


#------------------------------------------------------------------------------------------------------------------------

#END===================================== getquestionAnswers_______________________________________



 #START___________________________________ inswertAnswer _______________________________________tested = true
@app.route('/insertanswer', methods=['POST'])
def insertanswer():

    answer_date = datetime.datetime.utcnow().date();

    data_json = request.get_json()

    AskerEmail = data_json['AskerEmail']
    CreationDate = data_json['CreationDate']
    ReplierEmail = data_json['ReplierEmail']
    Content =data_json['Content']

    query = ''' insert into Answer values ( '{ReplierEmail}','{answer_date}','{Content}','{AskerEmail}','{CreationDate}') 
     '''.format_map({
        'ReplierEmail': ReplierEmail,
        'AskerEmail': AskerEmail,
        'CreationDate': CreationDate,
        'Content':Content,
        'answer_date':answer_date
    })
    print('query = ' + query)

    db.engine.execute(query)
    output =  db.engine.execute('select @@RowCount')
    if(output[0] <= 0):
        return jsonify({'mes': 'cant insert answer'}),400
    return jsonify({'mes':'answer sucess'}),200
# END===================================== insert answer==================================
#



# START___________________________________ voteanswer _______________________________________tested = true
@app.route('/voteanswer', methods=['POST'])
def voteanswer():
    data_json = request.get_json()

    VoterEmail = data_json['VoterEmail']
    replingDate = data_json['replingDate']
    replierEmail = data_json['replierEmail']
    query = ''' insert into Votes_An_Answer values ( '{VoterEmail}','{replierEmail}','{replingDate}') 
     '''.format_map({
        'VoterEmail': VoterEmail,
        'replierEmail': replierEmail,
        'replingDate': replingDate,
    })
    print('query = ' + query)
    output = db.engine.execute(query)
    print(output)
    # print(output[0])
    # if ( output[0] > 0 ):
    #     return jsonify({'message':'deleted'})
    return jsonify("votes sucess")
# END===================================== voteanswer==================================


# START___________________________________ votequestion _______________________________________tested = true
@app.route('/votequestion', methods=['POST'])
def votequestion():
    data_json = request.get_json()

    AskerEmail = data_json['AskerEmail']
    CreationDate = data_json['CreationDate']
    VoterEmail = data_json['VoterEmail']
    query = ''' insert into Votes_A_Question values ( '{AskerEmail}','{CreationDate}','{VoterEmail}') 
     '''.format_map({
        'VoterEmail': VoterEmail,
        'AskerEmail': AskerEmail,
        'CreationDate': CreationDate,
    })
    print('query = ' + query)
    output = db.engine.execute(query)
    print(output)
    # print(output[0])
    # if ( output[0] > 0 ):
    #     return jsonify({'message':'deleted'})
    return jsonify({'mes':'votes sucess'})
# END===================================== votequestion==================================

#
# //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
# ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
# ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


@app.route('/departments')  # get table of all universities application have
@student_token_required
def myDepartments(current_student):
    query = '''select distinct name from Department Where uniName = '{}' AND facName = '{}' '''.format(
        current_student['uniName'],
        current_student['facName'])
    print(query)
    departments = db.engine.execute(query)
    output = []
    for dep in departments:
        # one dictionary for each followee
        dep_data = {}
        dep_data['department'] = dep[0]

        output.append(dep_data)
    return jsonify({'departments': output})


# =========================================== No authoriztion ===========================================================
@app.route('/universities')  # get table of all universities application have
def getUniversities():
    query = 'select distinct ' + DD.Faculty.universityName + ' from ' + DD.Faculty.table

    universities = db.engine.execute(query)
    output = []
    for uni in universities:
        # one dictionary for each followee
        uni_data = {}
        uni_data['university'] = uni[0]
        output.append(uni_data)
    return jsonify({'universities': output})


@app.route('/facultiesOfUni/<university_name>', methods=['GET'])  # get table of all faculties of a specific uni
def getFaculties(university_name):
    query = 'select distinct ' + DD.Faculty.name + ' from ' + DD.Faculty.table + ' where ' + DD.Faculty.universityName + ' = ' + qh.put_quote(
        university_name)

    faculties = db.engine.execute(query)
    output = []
    for fac in faculties:
        # one dictionary for each followee
        fac_data = {}
        fac_data['faculty'] = fac[0]
        output.append(fac_data)
    return jsonify({'faculties': output})


@app.route('/departmentsOfFac/<university_name>/<faculty_name>')  # get table of all universities application have
def get_departments(university_name, faculty_name):
    query = '''select distinct name from Department Where uniName = '{}' AND facName = '{}' '''.format(university_name,
                                                                                                       faculty_name)
    print(query)
    departments = db.engine.execute(query)
    output = []
    for dep in departments:
        # one dictionary for each followee
        dep_data = {}
        dep_data['department'] = dep[0]

        output.append(dep_data)
    return jsonify({'departments': output})


# START___________________________________ allcourseList ___________________________________tested = true
@app.route('/allcourseList')
@moderator_token_required
def allcourseList(current_moderator):
    """
    get all course list
    :param current_student:
    :return:
    """
    courses = db.engine.execute("select * from Course_Label")
    output = []
    for c in courses:
        c_data = {}
        c_data['code'] = c['code']
        c_data['depFacName'] = c['depFacName']
        c_data['depUniName'] = c['depUniName']
        c_data['description'] = c['description']
        c_data['label'] = c['label']
        output.append(c_data)
    return jsonify({'courses': output})
# END===================================== allcourseList ==================================




# START******************************************************    Loai    ********************************************************************************************

# START___________________________________ GetMyStudy ___________________________________!tested
@app.route('/mystudy')
@student_token_required
def getMyStudy(current_student):
    email = request.args.get('email', current_student['email'])
    mYstudy = db.engine.execute('getMyStudy ?', email)
    output = []
    for course in mYstudy:
        # one dictionary for each followee
        course_data = {}
        # if there is no key called 'name' in followee_data (happen here as it is empty), it will create one for us
        course_data['depName'] = course['depName']
        course_data['facName'] = course['facName']
        course_data['uniName'] = course['uniName']
        course_data['label'] = course['label']
        course_data['description'] = course['description']
        course_data['type'] = course['type']
        course_data['coures-label'] = course['courseLabelCode']
        # in the end of each iteration append the dictionary to the list
        output.append(course_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary

    return jsonify({'My Courses': output})


# END===================================== get myStudy==================================





# START___________________________________ deleteSelfStudyCourse _______________________________________tested = true
@app.route('/deleteSelfStudyCourse/<label>', methods=['POST'])
@student_token_required
def deleteSelfStudyCourse(current_student, label):
    # y= db.engine.execute('{deleteSelfStudyCourse(?,?)}',(current_student['email'],label,outparam('rowsEffected',int)))
    query = ''' delete Selfstudy_Course where creatorEmail='{eMail}' and label='{courseLabel}'
     '''.format_map({
        'eMail': current_student['email'],
        'courseLabel': label
    })
    print('query = ' + query)
    db.engine.execute(query) # select @@ROWCOUNT as c
    output = db.engine.execute("select @@ROWCOUNT as c").first()
    if (output['c'] > 0):
        return jsonify({'mes':" {} courses deleted successfully".format(output['c'])}),200
    return jsonify({'mes':"no courses deleted"}),200
# END===================================== deleteSelfStudyCourse==================================



# start_____________________________deleteSelfStudyCourseMod___________________________________
@app.route('/deleteCourseNote/<label>', methods=['POST'])
@moderator_token_required
def deleteCourseNoteFromModerator(current_moderator, label):
    data_json = request.get_json()
    if 'email' not in data_json:
        return jsonify({'mes':"can't find email, data shortage"}),400
    query = '''delete Selfstudy_Course where creatorEmail='{eMail}' and label='{courseLabel}' 
     '''.format_map({
        'eMail': data_json['email'],
        'courseLabel': label
    })
    print('query = ' + query)
    db.engine.execute(query)
    output = db.engine.execute("select @@ROWCOUNT as c").first()
    print(output)
    if (output['c'] > 0):
        return jsonify({'mes':" {} courses deleted successfully".format(output['c'])}),200
    return jsonify({'mes':"no courses deleted"}),200
# END===================================== deleteSelfStudyCourseMod==================================



# START___________________________________ courseList ___________________________________tested = true
@app.route('/courseList')
@student_token_required
def courseList(current_student):
    """
    get course list in his faculty
    :param current_student:
    :return:
    """
    courses = db.engine.execute('{CALL getCourseList(?,?) }', (current_student['facName'], current_student['uniName']))
    output = []
    for course in courses:
        # one dictionary for each course
        course_data = {}
        # if there is no key called 'name' in course_data (happen here as it is empty), it will create one for us
        course_data['description'] = course['description']
        course_data['label'] = course['label']
        course_data['code'] = course['code']
        course_data['facName'] = course['depFacName']
        course_data['uniName'] = course['depUniName']
        # in the end of each iteration append the dictionary to the list
        output.append(course_data)
    # after the loop is finished return the response to the caller (browser, app or any one make the connection to this url)
    # our response is a string json response, so we jsonify the output list of dictionary
    return jsonify({'courses': output})
# END===================================== courseList ==================================



# START___________________________________ update student info ___________________________________tested = true
@app.route('/updateStudent', methods=['POST'])
@student_token_required
def updateStudent(current_student):
    name = current_student['name']
    dep = current_student['depName']
    fac = current_student['facName']
    uni = current_student['uniName']
    password = current_student['password']

    data_json = request.get_json()

    if 'password' in data_json:
        password = generate_password_hash(data_json['password'], method='sha256')

    if 'name' in data_json:
        name = data_json['name']

    if 'dep' in data_json:
        dep = data_json['dep']

    if 'uni' in data_json:
        uni = data_json['uni']

    if 'fac' in data_json:
        fac = data_json['fac']

    query = '''update  Student set name='{}',password='{}',depName='{}',facName='{}',uniName='{}'  where Student.email ='{}' '''.format(
        name, password, dep, fac, uni, current_student['email'])
    print(query)

    try:
        db.engine.execute(query)
    except:
        return jsonify({'mes': "can't update with these values"}), 400

        #  db.engine.execute('{CALL updateStudent(?,?,?,?,?,?)}',(current_student['email'], name, password, dep, fac, uni))
    return jsonify({'mes': "Updated"}), 200
# END===================================== update info ==================================




#START====================================get selfStudy Courses =====================================
@app.route('/getSelfStudyCourses/<email>',methods=['Get'])
def getSelfStudyCoursesWithToken(email):
    courses = db.engine.execute('{CALL getSelfStudyCourses(?)}', email)
    output=[]
    for course in courses:
        course_data={}
        course_data['label']=course['label']
        course_data['creatorEmail']=course['creatorEmail']
        output.append(course_data)
        return  jsonify({'courses':output})


#END=======================================GET SELFSTUDY COURSEs========================================

#START====================================get  Course Notes =====================================
@app.route('/getCourseNotes/<email>',methods=['Get'])

def getCourseNotesWithToken(email):
    courses = db.engine.execute('{CALL getCourseNotes(?) }', (email))
    output=[]
    for course in courses:
        course_data={}
        course_data['courseLabelCode']=course['courseLabelCode']
        course_data['facName']=course['facName']
        course_data['uniName']=course['uniName']
        course_data['creatorEmail']=course['creatorEmail']
        course_data['label']=course['label']
        course_data['description']=course['description']
        output.append(course_data)
        return  jsonify({'courses':output})


#END=======================================GET  COURSE notes========================================

#START===============================insertSelfStudyCourseVideo==================================================
@app.route('/insertSelfStudyCourseVideo',methods=['POST'])
def insertSelfStudyCourseVideo(current_student):
    data_json = request.get_json()
    label=data_json['label']
    link=data_json['link']
    query='''insert into Selfstudy_Course_Content ('{}','{}','{}')'''.format(current_student['email'],label,link)
    try:
        db.engine.execute(query)
    except:
        return jsonify({'mes': "can't insert this item again"}), 400
    c = db.engine.execute("select @@RowCount as c").first()
    if (c[0] > 0):
        return jsonify({'mes': "content inserted succefully"}),200
    return jsonify({'mes': "can't insert this item again"}), 400
#END==================================insertSelfStudyCourseVideo====================================================

#START===============================insertSelfStudyCourseVideo==================================================
@app.route('/insertCourseNotesVideo',methods=['POST'])
def insertCourseNotesVideo(current_student):
    data_json = request.get_json()
    facName=data_json['facName']
    link=data_json['link']
    uniName=data_json['uniName']

    courseLabelCode=data_json['courseLabelCode']
    query='''insert into Course_Contents('{}','{}','{}','{}','{}')'''.format(current_student['email'],courseLabelCode,facName,uniName,link)
    try:
        db.engine.execute(query)
    except:
        return jsonify({'mes': "can't insert this item again"}), 400
    c = db.engine.execute("select @@RowCount as c").first()
    if (c[0] > 0):
        return jsonify({'mes': "content inserted succefully"}),200
    return jsonify({'mes': "can't insert this item again"}), 400
#END==================================insertSelfStudyCourseVideo====================================================



#START============================InsertCourseNotes===================================================================
@app.route('/insertCourseNotes',methods=['POST'])
@student_token_required
def insertCourseNotes(current_student):

 data_json=request.get_json()
 courseLabelCode=data_json['courseLabelCode']
 facName=data_json['facName']
 uniName=data_json['uniName']
 query='''insert into Course_Notes values ('{}','{}','{}','{}')'''.format(current_student['email'],courseLabelCode,facName,uniName)
 db.engine.execute(query)

  #  db.engine.execute('{CALL updateStudent(?,?,?,?,?,?)}',(current_student['email'], name, password, dep, fac, uni))

 return jsonify('mes',"Course_Notes inserted succefully"),200

#END=================================InsertCourseNotes================================================================

#START============================InsertSelfStudy Course===================================================================
@app.route('/insertSelfStudyCourse',methods=['POST'])
@student_token_required
def insertSelfStudyCourse(current_student):

 data_json=request.get_json()

 label=data_json['label']

 query='''insert into SelfStudy_Course values ('{}','{}')'''.format(current_student['email'],label)
 db.engine.execute(query)

  #  db.engine.execute('{CALL updateStudent(?,?,?,?,?,?)}',(current_student['email'], name, password, dep, fac, uni))

 return jsonify('mes',"SelfStudy_Course inserted succefully"),200

#END=================================InsertCourseNotes================================================================





# END***************************************     Loai    ******************************************************************************************







if __name__ == "__main__":
    # use it when you want to run the api from the application
    app.run(debug=True, host='0.0.0.0')
    # use it when you want to run the api from your browser on pc
    # app.run(debug=True)
