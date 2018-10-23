using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data;
using System.Windows.Forms;

namespace WindowsFormsApplication9
{
    public class Controller
    {
        DBManager dbMan;
        public Controller()
        {
            dbMan = new DBManager();
        }


        public void TerminateConnection()
        {
            dbMan.CloseConnection();
        }
        public DataTable GetSNames()
        {

            string StoredProcedureName = StoredProcedures.GetStudentNames;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }
        public DataTable GetStudents()
        {

            string StoredProcedureName = StoredProcedures.GetStudents;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }
        public DataTable GetbanStudents()
        {

            string StoredProcedureName = StoredProcedures.GetbanStudents;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }


        public DataTable GetAdmin(string username,string password)
        {

            string StoredProcedureName = StoredProcedures.GetAdmin;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@username", username);
            Parameters.Add("@password", password);
            return dbMan.ExecuteReader(StoredProcedureName, Parameters);
        }
        public int BanStudent(string email)
        {
            string StoredProcedureName = StoredProcedures.BanStudent;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@stdemail", email);
            
            return dbMan.ExecuteNonQuery(StoredProcedureName, Parameters);
        }
        public int unBanStudent(string email)
        {
            string StoredProcedureName = StoredProcedures.unBanStudent;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@stdemail", email);

            return dbMan.ExecuteNonQuery(StoredProcedureName, Parameters);
        }
        public int AddTagToModerator(string mod ,string tg)
        {
            string StoredProcedureName = StoredProcedures.AddTagToModerator;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@mod", mod);
            Parameters.Add("@tag", tg);

            return dbMan.ExecuteNonQuery(StoredProcedureName, Parameters);
        }
        public int RemoveTagFromModerator(string mod, string tg)
        {
            string StoredProcedureName = StoredProcedures.RemoveTagFromModerator;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@mod", mod);
            Parameters.Add("@tag", tg);

            return dbMan.ExecuteNonQuery(StoredProcedureName, Parameters);
        }
        public DataTable StudentsNotMod()
        {

            string StoredProcedureName = StoredProcedures.StudentsNotMod;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }
        public DataTable Enrolled()
        {

            string StoredProcedureName = StoredProcedures.returnenrolled;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }
        public DataTable SelfStudy()
        {

            string StoredProcedureName = StoredProcedures.returnv4ssc;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }
        public DataTable Question()
        {

            string StoredProcedureName = StoredProcedures.returnv4question;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }
        public DataTable Answer()
        {

            string StoredProcedureName = StoredProcedures.returnv4answer;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }
        public DataTable GetModerators()
        {

            string StoredProcedureName = StoredProcedures.GetModerators;
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }
        public DataTable UnControlledTags(string g)
        {

            string StoredProcedureName = StoredProcedures.UnControlledTags;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@moderatoreml", g);
            return dbMan.ExecuteReader(StoredProcedureName, Parameters);
        }

        public DataTable ControlledTags(string g)
        {

            string StoredProcedureName = StoredProcedures.ControlledTags;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@moderatoreml", g);
            return dbMan.ExecuteReader(StoredProcedureName, Parameters);
        }
        public DataTable MaxAnswered()
        {

            string StoredProcedureName = StoredProcedures.MaxAnswered;
            
            return dbMan.ExecuteReader(StoredProcedureName, null);
        }



        public string MaxEmail()
        {
            if (MaxAnswered() != null)
            {
                return MaxAnswered().Rows[0]["replierEmail"].ToString();
            }
            return null;
               
        }

        public DataTable StudentByEmail()
        {
            if (MaxEmail() != null)
            {
                string StoredProcedureName = StoredProcedures.StudentByEmail;
                Dictionary<string, object> Parameters = new Dictionary<string, object>();
                Parameters.Add("@email", MaxEmail());
                return dbMan.ExecuteReader(StoredProcedureName, Parameters);
            }
            return null;
        }




        public int AddModerator(string email)
        {
            string StoredProcedureName = StoredProcedures.AddModerator;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@Mdemail", email);

            return dbMan.ExecuteNonQuery(StoredProcedureName, Parameters);
        }
        public int GetCourseNo()
        {
            string StoredProcedureName = StoredProcedures.getCourses;
          

            object x=dbMan.ExecuteScalar(StoredProcedureName, null);
            if (x != null)
                return (int)x;
            return 0; 
            
        }

        public int GetStudentsNo()
        {
            string StoredProcedureName = StoredProcedures.getstudentno;


            object x = dbMan.ExecuteScalar(StoredProcedureName, null);
            if (x != null)
                return (int)x;
            return 0;




        }
        public int GetQuestionNo()
        {
            string StoredProcedureName = StoredProcedures.getquestion;


            object x = dbMan.ExecuteScalar(StoredProcedureName, null);
            if (x != null)
                return (int)x;
            return 0;

        }

        public int GetTagsNo()
        {
            string StoredProcedureName = StoredProcedures.gettagsno;


            object x = dbMan.ExecuteScalar(StoredProcedureName, null);
            if (x != null)
                return (int)x;
            return 0;

        }

        public int RemoveModerator(string email)
        {
            string StoredProcedureName = StoredProcedures.RemoveModerator;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@Mdemail", email);

            return dbMan.ExecuteNonQuery(StoredProcedureName, Parameters);
        }

        public int ChangePassword(string admin,string password)
        {
            string StoredProcedureName = StoredProcedures.ChangePassword;
            Dictionary<string, object> Parameters = new Dictionary<string, object>();
            Parameters.Add("@admin", admin);
            Parameters.Add("@newpass", password);

            return dbMan.ExecuteNonQuery(StoredProcedureName, Parameters);
        }






    }
}
