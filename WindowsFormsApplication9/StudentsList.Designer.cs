namespace WindowsFormsApplication9
{
    partial class StudentsList
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.StudentListCB = new System.Windows.Forms.ComboBox();
            this.Ban = new System.Windows.Forms.Button();
            this.unbantext = new System.Windows.Forms.ComboBox();
            this.unban = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // StudentListCB
            // 
            this.StudentListCB.FormattingEnabled = true;
            this.StudentListCB.Location = new System.Drawing.Point(54, 83);
            this.StudentListCB.Name = "StudentListCB";
            this.StudentListCB.Size = new System.Drawing.Size(277, 24);
            this.StudentListCB.TabIndex = 0;
            // 
            // Ban
            // 
            this.Ban.Location = new System.Drawing.Point(368, 83);
            this.Ban.Name = "Ban";
            this.Ban.Size = new System.Drawing.Size(75, 23);
            this.Ban.TabIndex = 1;
            this.Ban.Text = "Ban";
            this.Ban.UseVisualStyleBackColor = true;
            this.Ban.Click += new System.EventHandler(this.Ban_Click);
            // 
            // unbantext
            // 
            this.unbantext.FormattingEnabled = true;
            this.unbantext.Location = new System.Drawing.Point(54, 314);
            this.unbantext.Name = "unbantext";
            this.unbantext.Size = new System.Drawing.Size(277, 24);
            this.unbantext.TabIndex = 2;
            // 
            // unban
            // 
            this.unban.Location = new System.Drawing.Point(368, 315);
            this.unban.Name = "unban";
            this.unban.Size = new System.Drawing.Size(75, 23);
            this.unban.TabIndex = 3;
            this.unban.Text = "unban";
            this.unban.UseVisualStyleBackColor = true;
            this.unban.Click += new System.EventHandler(this.unban_Click);
            // 
            // StudentsList
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(544, 525);
            this.Controls.Add(this.unban);
            this.Controls.Add(this.unbantext);
            this.Controls.Add(this.Ban);
            this.Controls.Add(this.StudentListCB);
            this.Name = "StudentsList";
            this.Text = "StudentsList";
            this.Load += new System.EventHandler(this.StudentsList_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ComboBox StudentListCB;
        private System.Windows.Forms.Button Ban;
        private System.Windows.Forms.ComboBox unbantext;
        private System.Windows.Forms.Button unban;
    }
}