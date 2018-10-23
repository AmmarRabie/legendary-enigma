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
    public partial class Login : Form
    {
        string admin;
        string password;
        public Login()
        {
            InitializeComponent();
        }

        private void Login_Load(object sender, EventArgs e)
        {

        }

        private void LoginButton_Click(object sender, EventArgs e)
        {
           DataTable  dt = new Controller().GetAdmin(textBox1.Text,textBox2.Text);
            if (dt!=null)
            {
                admin = textBox1.Text;
                password = textBox2.Text;
            tables  MS=  new tables();
                MS.setadmin(admin,password);
                MS.Show();
               // this.Hide();
                //this.Close();
            }
            else
            {
                MessageBox.Show("wrong username or password");
                textBox1.Text = "";
                textBox2.Text = "";
            }


            
        }
    }
}
