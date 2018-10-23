using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace WindowsFormsApplication9
{
    public partial class tables : Form
    {
        string admin,password;
        DataTable dt = new Controller().StudentByEmail();
        public tables()
        {
            InitializeComponent();

            if (dt != null)
            {

                name.Text = dt.Rows[0]["name"].ToString();
                email.Text = dt.Rows[0]["email"].ToString();
                department.Text = dt.Rows[0]["depName"].ToString();
                faculty.Text = dt.Rows[0]["facName"].ToString();
                universe.Text = dt.Rows[0]["uniName"].ToString();
            }

            question.Text=new Controller().GetQuestionNo().ToString ();
            tag.Text = new Controller().GetTagsNo().ToString();
            students.Text = new Controller().GetStudentsNo().ToString();
            courses.Text = new Controller().GetCourseNo().ToString();


        }

        private void StudentsList_Click(object sender, EventArgs e)
        {
            new StudentsList().Show();
            //this.Hide();
            //this.Close();
        }

        private void Moderators_Click(object sender, EventArgs e)
        {
            new Moderators().Show();
           // this.Hide();
            //this.Close();
        }

        private void Tags_Click(object sender, EventArgs e)
        {
            new Tags().Show();
            //this.Hide();
            //this.Close();
        }
        public void setadmin (string x,string y)
        {
            admin = x;
            password = y;
        }

        private void textBox2_TextChanged(object sender, EventArgs e)
        {

        }

        private void label4_Click(object sender, EventArgs e)
        {

        }

        private void label3_Click(object sender, EventArgs e)
        {

        }

        private void MainScreen_Load(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
             new Tablesaa().Show();
            
            
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ChangePassword CP = new ChangePassword();
            CP.setadmin(admin,password);
            CP.Show();

        }
    }
}
