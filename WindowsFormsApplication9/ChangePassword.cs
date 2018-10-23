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
    
    public partial class ChangePassword : Form
    {
        string admin,password;
        public ChangePassword()
        {
            InitializeComponent();
        }
        public void  setadmin(string x,string y )
        {
            admin = x;
            password = y;
        }

        private void ChangePassword_Load(object sender, EventArgs e)
        {

        }

        private void change_Click(object sender, EventArgs e)
        {
            if (changetextbox.Text != null)
            {
                DataTable dt = new Controller().GetAdmin(admin, password);
                string x = dt.Rows[0]["username"].ToString();
                new Controller().ChangePassword(x, changetextbox.Text);
                MessageBox.Show("The password changed");
            }
            else
                MessageBox.Show("enter the new password");


        }
    }
}
