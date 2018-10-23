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
    public partial class Tablesaa : Form
    {
        DataTable dt;
        public Tablesaa()
        {
            InitializeComponent();
        }

        private void Show_Click(object sender, EventArgs e)
        {
            if (comboBox1.SelectedIndex == 0)
               dt= new Controller().Enrolled();
            if (comboBox1.SelectedIndex == 1)
                dt = new Controller().SelfStudy();
            if (comboBox1.SelectedIndex == 2)
                dt = new Controller().Question();
            if (comboBox1.SelectedIndex == 3)
                dt = new Controller().Answer();

            dataGridView1.DataSource = dt;
            dataGridView1.Refresh();
        }
    }
}
