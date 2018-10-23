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
    public partial class Tags : Form
    { DataTable dt1, dt2, dt3;

        private void comboBox1_SelectedIndexChanged(object sender, EventArgs e)
        {
            dt2 = new Controller().ControlledTags(ModCB.Text);

            ConCB.DataSource = dt2;
            ConCB.DisplayMember = "tagName";

            dt3 = new Controller().UnControlledTags(ModCB.Text);

            UnConCB.DataSource = dt3;
            UnConCB.DisplayMember = "name";


        }

        private void Remove_Click(object sender, EventArgs e)
        {
            new Controller().RemoveTagFromModerator(ModCB.Text, ConCB.Text);
            dt2 = new Controller().ControlledTags(ModCB.Text);

            ConCB.DataSource = dt2;
            ConCB.DisplayMember = "tagName";

            dt3 = new Controller().UnControlledTags(ModCB.Text);

            UnConCB.DataSource = dt3;
            UnConCB.DisplayMember = "name";
        }

        private void Add_Click(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(UnConCB.Text))
            {
                new Controller().AddTagToModerator(ModCB.Text, UnConCB.Text);
                dt2 = new Controller().ControlledTags(ModCB.Text);

                ConCB.DataSource = dt2;
                ConCB.DisplayMember = "tagName";

                dt3 = new Controller().UnControlledTags(ModCB.Text);

                UnConCB.DataSource = dt3;
                UnConCB.DisplayMember = "name";
            }
        }

        public Tags()
        {
            InitializeComponent();
            dt1 = new Controller().GetModerators();

            ModCB.DataSource = dt1;
           ModCB.DisplayMember = "stdemail";
           ModCB.Text= dt1.Rows[0]["stdemail"].ToString();

            dt2 = new Controller().ControlledTags(ModCB.Text);

            ConCB.DataSource = dt2;
           ConCB.DisplayMember = "tagName";

            dt3= new Controller().UnControlledTags(ModCB.Text);

            UnConCB.DataSource = dt3;
           UnConCB.DisplayMember = "name";


        }

        private void Tags_Load(object sender, EventArgs e)
        {

        }
    }
}
