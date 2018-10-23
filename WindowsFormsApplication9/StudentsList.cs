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
    public partial class StudentsList : Form
    {
        DataTable dt,dt2;
        public StudentsList()
        {
            InitializeComponent();
             dt = new Controller().GetStudents();
            
            StudentListCB.DataSource = dt;
            StudentListCB.DisplayMember = "email";
            dt2 = new Controller().GetbanStudents();

            unbantext.DataSource = dt2;
            unbantext.DisplayMember = "email";
        }

        private void StudentsList_Load(object sender, EventArgs e)
        {

        }

        private void Ban_Click(object sender, EventArgs e)
        {
           string email= StudentListCB.Text;
            new Controller().BanStudent(email);
            dt = new Controller().GetStudents();
            StudentListCB.DataSource = dt;
            StudentListCB.DisplayMember = "email";
            StudentListCB.Refresh();
            dt2 = new Controller().GetbanStudents();
            unbantext.DataSource = dt2;
            unbantext.DisplayMember = "email";
            unbantext.Refresh();
        }

        private void unban_Click(object sender, EventArgs e)
        {
            string email = unbantext.Text;
            new Controller().unBanStudent(email);
            dt2 = new Controller().GetbanStudents();
            unbantext.DataSource = dt2;
            unbantext.DisplayMember = "email";
            unbantext.Refresh();
            dt = new Controller().GetStudents();
            StudentListCB.DataSource = dt;
            StudentListCB.DisplayMember = "email";
            StudentListCB.Refresh();
        }
    }
}
