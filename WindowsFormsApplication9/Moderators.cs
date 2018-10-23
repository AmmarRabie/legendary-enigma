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
    public partial class Moderators : Form
    {
        DataTable dtadd,dtrem;

        private void Moderators_Load(object sender, EventArgs e)
        {

        }

        private void Add_Click(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(AddCB.Text))
            {
                new Controller().AddModerator(AddCB.Text);

                dtadd = new Controller().StudentsNotMod();
                AddCB.DataSource = dtadd;
                AddCB.DisplayMember = "email";
                AddCB.Refresh();

                dtrem = new Controller().GetModerators();
                RemoveCB.DataSource = dtrem;
                RemoveCB.DisplayMember = "stdemail";
                RemoveCB.Refresh();
            }
        }

        private void Remove_Click(object sender, EventArgs e)
        {
            new Controller().RemoveModerator(RemoveCB.Text);

            dtadd = new Controller().StudentsNotMod();
            AddCB.DataSource = dtadd;
            AddCB.DisplayMember = "email";
            AddCB.Refresh();

            dtrem = new Controller().GetModerators();
            RemoveCB.DataSource = dtrem;
            RemoveCB.DisplayMember = "stdemail";
            RemoveCB.Refresh();
        }

        private void AddCB_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        public Moderators()
        {
            InitializeComponent();
            dtadd = new Controller().StudentsNotMod();

            AddCB.DataSource = dtadd;
            AddCB.DisplayMember = "email";

            dtrem = new Controller().GetModerators();
            RemoveCB.DataSource = dtrem;
            RemoveCB.DisplayMember = "stdemail";

        }


    }
    }


