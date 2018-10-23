from flask import Flask, jsonify, request, Response, json,make_response
from flask_sqlalchemy import SQLAlchemy
import jwt
from jwt.exceptions import InvalidTokenError
# from flask_jwt import JWT, jwt_required, current_identity
from werkzeug.security import safe_str_cmp, generate_password_hash, check_password_hash
import DatabaseDefinition as DD
import QueryHelper as qh
import datetime
import uuid
from functools import wraps

# TODO: Read all comments

### for postgress sql ### file_path = os.path.abspath(os.getcwd()) + "/" + "flask_test.db"

app = Flask(__name__)
# app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres:///'+file_path
# app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///mnt/C/sqlite/hhhh.db'
### for postgress sql ###app.config['SQLALCHEMY_DATABASE_URI'] = 'postgres://postgres:1973197355@localhost/test_psql'


# TODO: change the server name(AMMAR\SQLEXPRESS) and the database name(studyStation_ammarTest) according to your situation
app.config['SQLALCHEMY_DATABASE_URI'] = 'mssql+pyodbc://LAPTOP-UTJ2F2N1\SQLEXPRESS/StudyStation?driver=ODBC+Driver+11+for+SQL+Server'
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
            return jsonify({'message' : 'Token is missing!'}), 401

        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
            current_student = db.engine.execute('getStudentData ?',[data['email']]).first()
            print(current_student['email'])
        except:
            return jsonify({'message' : 'Token is invalid!'}), 401

        return f(current_student, *args, **kwargs)
    return decorated

def moderator_token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None

        if 'x-access-token' in request.headers:
            token = request.headers['x-access-token']

        if not token:
            return jsonify({'message' : 'Token is missing!'}), 401

        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
            current_moderator = db.engine.execute('getModeratorData ?',[data['email']]).first()
            if not current_moderator:
                return jsonify({'message': 'access denied!'}), 401 # this mean he is student but not a moderator
        except:
            return jsonify({'message' : 'Token is invalid!'}), 401


        return f(current_moderator, *args, **kwargs)
    return decorated

def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None

        if 'x-access-token' in request.headers:
            token = request.headers['x-access-token']

        if not token:
            return jsonify({'message' : 'Token is missing!'}), 401

        try:
            data = jwt.decode(token, app.config['SECRET_KEY'])
            current_user = db.engine.execute('getModeratorData ?',[data['email']]).first()
            if current_user:
                return f(current_user, *args, **kwargs) # moderator
            current_user = db.engine.execute('getStudentData ?', [data['email']]).first()
            # if current_user:
            #     return f(current_user, *args, **kwargs) # student
        except InvalidTokenError as e:
            print(e.__str__())
            return jsonify({'message' : 'Token is invalid!'}), 401
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



#START___________________________________ get followees ___________________________________tested = true
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
    return jsonify({'followees': output})
#END===================================== get followees ==================================



#START___________________________________ SIGNUP UP _______________________________________tested = true
@app.route('/signup', methods=['post'])
def insert():
    # take inputs
    data_json = request.get_json()
    registration_date = datetime.datetime.utcnow().date()
    name = data_json['name']
    dep = data_json['dep']
    fac = data_json['fac']
    uni = data_json['uni']
    email = data_json['email']
    if email in userEmail_List:
        return jsonify({"message: ":"this email is already exist"}),400
    pas = generate_password_hash(data_json['pass'], method='sha256')

    query = DD.insertStatment.format(name, pas, email, dep, fac, uni)
    db.engine.execute(query)
    userEmail_List.append(email)
    token = jwt.encode({'email':email,"pass":pas,'exp':datetime.datetime.utcnow() + datetime.timedelta(hours=2)},app.config['SECRET_KEY']) # one year expired
    return jsonify({'token':token.decode('UTF-8'),'message': "new student has been created","date": registration_date}), 201
#END===================================== SIGHN UP ==================================
#/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
# mady by ramy mohammed   12/9/2017//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#START___________________________________ searchStudent_______________________________________tested = true
@app.route('/searchstudent/<studentname>', methods=['get'] )
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

#END===================================== SesarchStudent ==================================


#START___________________________________ get student by email _______________________________________tested = true
@app.route('/getstudentbyemail/<studentemail>', methods=['get'] )
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

#END===================================== SesarchStudent ==================================
#START___________________________________ getQuestionBytag_______________________________________tested = true
@app.route('/getquestionbytag/<tagname>', methods=['get'] )
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


#END===================================== getQuestionBYTag ========================================================


#START___________________________________ get tags_______________________________________tested = true
@app.route('/gettags', methods=['get'] )
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


#END=====================================get tags ==================================



#START___________________________________ searchquestion   by part of the question_______________________________________tested = true
@app.route('/searchquestion/<title>', methods=['get'] )
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


#END===================================== searchquestion ========================================================



#START___________________________________ searchquestionByTag   by tag and part of the question_______________________________________tested = true
@app.route('/searchquestionbytag/<tag>/<title>', methods=['get'] )
def searchquestionbytag(tag,title):
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


#END===================================== searchquestionByTag ========================================================

#START___________________________________ getquestionAnswers_______________________________________tested = true
@app.route('/getquestionanswers/<askerEmail>/<creationDate>', methods=['get'] )
def getquestionanswers(askerEmail,creationDate):
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


#END===================================== getquestionAnswers_______________________________________

#START___________________________________ check answer vote_______________________________________tested = true
@app.route('/checkanswervote/<replierEmail>/<replingDate>', methods=['get'] )
def checkanswervote(replierEmail,replingDate):
    # take inputs
    answervotes = db.engine.execute('{CALL checkAnswerVote (?,?)}', (replierEmail, replingDate))
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


#END===================================== getquestionAnswers_______________________________________


#START___________________________________ SIGNUP UP _______________________________________tested = true
@app.route('/votesAnswer', methods=['post'])
def voteAnswer():
    # take inputs
    data_json = request.get_json()
    registration_date = datetime.datetime.utcnow().date()

    name = data_json['name']
    dep = data_json['dep']
    fac = data_json['fac']
    uni = data_json['uni']
    email = data_json['email']




    return jsonify({'token':token.decode('UTF-8'),'message': "new student has been created","date": registration_date}), 201
#END===================================== SIGHN UP ==================================
#
#//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
#///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


#START___________________________________ LOG IN ________________________________________tested = true
@app.route('/login')
def login():
    auth = request.authorization

    if not auth or not auth.username or not auth.password:
        return make_response('Could not verify', 401, {'WWW-Authenticate' : 'Basic realm="Login required!"'})

    user_data = db.engine.execute('getStudentData ?',[auth.username]).first()

    if not user_data:
        return make_response('Could not verify', 401, {'WWW-Authenticate' : 'Basic realm="Login required!"'})

    if check_password_hash(user_data.password, auth.password):
        token = jwt.encode({'email' : user_data.email,'pass':user_data['password'], 'exp' : datetime.datetime.utcnow() + datetime.timedelta(hours=2)}, app.config['SECRET_KEY'])
        return jsonify({'token' : token.decode('UTF-8')})

    return make_response('Could not verify', 401, {'WWW-Authenticate' : 'Basic realm="Login required!"'})
#END==================================  LOG IN ===============================




@app.route('/users/<userEmail>/<password>')
def getUserData(userEmail, password):
    query_Exist = \
        "SELECT Count(1) From student Where student.email = \'" + userEmail + "\';"

    if (db.engine.execute(query_Exist) == 0):
        return "invalid email"

    query_Exist = \
        "SELECT Count(1) From student Where student.email = \'" + userEmail + "\' AND password = \'" + password + "\';"

    if (db.engine.execute(query_Exist) == 0):
        return "access denied"

    query = '''select * from student Where student.email = \'" + userEmail + "\';" '''
    user = db.engine.execute(query)
    user_data = {}
    user_data['name'] = user['name']
    user_data['email'] = user['email']
    user_data['dep'] = user['dep_name']
    user_data['fac'] = user['fac_name']
    return jsonify(user_data)


@app.route('/users/<userEmail>', methods=["POST"])
def insertStudent(userEmail):
    query_Exist = \
        "SELECT Count(*) as c From student Where student.email = \'" + userEmail + "\';"

    db_engine_execute = db.engine.execute(query_Exist)
    count = 0
    for user in db_engine_execute:
        count = user['c']
        print(count)

    if (count == 1):
        return "this email already exist"

    query_insert = '''Insert Into Student(name,password,email,dep_name,fac_name,uni_name) Values ('AmmarAlsayed','15975315975355',' ''' + userEmail + ''' ' ,'CMP','faculty of engineering','cairo university') '''

    result = db.engine.execute(query_insert)
    # print(result)

    password = request.json['pass']
    content = request.get_json(silent=True)
    print(content)
    return jsonify(content)



# TODO: update this method to return all data to be directly used in apps (the profile need more queries if i just retrun the ids)
@app.route('/profile')
@token_required
def getCurrUserProfile(current_user):
    """
    this fun to return the profile depending on user type
    :param current_user: the user passed from token, it should be either moderator or student
    :return:
    """
    user_data = {} # to put our projection
    user_data['email'] = current_user['email']
    user_data['dep'] = current_user['depName']
    user_data['fac'] = current_user['facName']
    user_data['uni'] = current_user['uniName']
    user_data['registrationDate'] = current_user['regestrationDate']
    user_data['name'] = current_user['name']
    if 'promotionDate' in current_user:
        user_data['promotionDate'] =  current_user['promotionDate']
    return jsonify(user_data)




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
    query = '''select distinct name from Department Where uniName = '{}' AND facName = '{}' '''.format(university_name,faculty_name)
    print(query)
    departments = db.engine.execute(query)
    output = []
    for dep in departments:
        # one dictionary for each followee
        dep_data = {}
        dep_data['department'] = dep[0]

        output.append(dep_data)
    return jsonify({'departments': output})


if __name__ == "__main__":
    # use it when you want to run the api from the application
    app.run(debug=True, host='0.0.0.0')
    # use it when you want to run the api from your browser on pc
    # app.run(debug=True)
