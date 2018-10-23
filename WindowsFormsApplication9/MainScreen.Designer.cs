namespace WindowsFormsApplication9
{
    partial class tables
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
            this.StudentsList = new System.Windows.Forms.Button();
            this.Moderators = new System.Windows.Forms.Button();
            this.Tags = new System.Windows.Forms.Button();
            this.name = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.department = new System.Windows.Forms.TextBox();
            this.email = new System.Windows.Forms.TextBox();
            this.universe = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.faculty = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.question = new System.Windows.Forms.TextBox();
            this.tag = new System.Windows.Forms.TextBox();
            this.courses = new System.Windows.Forms.TextBox();
            this.students = new System.Windows.Forms.TextBox();
            this.label11 = new System.Windows.Forms.Label();
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // StudentsList
            // 
            this.StudentsList.Location = new System.Drawing.Point(47, 295);
            this.StudentsList.Name = "StudentsList";
            this.StudentsList.Size = new System.Drawing.Size(189, 52);
            this.StudentsList.TabIndex = 0;
            this.StudentsList.Text = "StudentsList";
            this.StudentsList.UseVisualStyleBackColor = true;
            this.StudentsList.Click += new System.EventHandler(this.StudentsList_Click);
            // 
            // Moderators
            // 
            this.Moderators.Location = new System.Drawing.Point(47, 368);
            this.Moderators.Name = "Moderators";
            this.Moderators.Size = new System.Drawing.Size(189, 54);
            this.Moderators.TabIndex = 1;
            this.Moderators.Text = "Moderators";
            this.Moderators.UseVisualStyleBackColor = true;
            this.Moderators.Click += new System.EventHandler(this.Moderators_Click);
            // 
            // Tags
            // 
            this.Tags.Location = new System.Drawing.Point(393, 295);
            this.Tags.Name = "Tags";
            this.Tags.Size = new System.Drawing.Size(187, 52);
            this.Tags.TabIndex = 2;
            this.Tags.Text = "Tags";
            this.Tags.UseVisualStyleBackColor = true;
            this.Tags.Click += new System.EventHandler(this.Tags_Click);
            // 
            // name
            // 
            this.name.HideSelection = false;
            this.name.Location = new System.Drawing.Point(232, 25);
            this.name.Name = "name";
            this.name.ReadOnly = true;
            this.name.Size = new System.Drawing.Size(138, 22);
            this.name.TabIndex = 3;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(21, 25);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(155, 17);
            this.label1.TabIndex = 4;
            this.label1.Text = "Most Active Student -->";
            // 
            // department
            // 
            this.department.Location = new System.Drawing.Point(47, 77);
            this.department.Name = "department";
            this.department.ReadOnly = true;
            this.department.Size = new System.Drawing.Size(138, 22);
            this.department.TabIndex = 6;
            this.department.TextChanged += new System.EventHandler(this.textBox2_TextChanged);
            // 
            // email
            // 
            this.email.Location = new System.Drawing.Point(408, 25);
            this.email.Name = "email";
            this.email.ReadOnly = true;
            this.email.Size = new System.Drawing.Size(176, 22);
            this.email.TabIndex = 7;
            // 
            // universe
            // 
            this.universe.Location = new System.Drawing.Point(408, 77);
            this.universe.Name = "universe";
            this.universe.ReadOnly = true;
            this.universe.Size = new System.Drawing.Size(176, 22);
            this.universe.TabIndex = 8;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(405, 58);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(76, 17);
            this.label3.TabIndex = 9;
            this.label3.Text = "Universary";
            this.label3.Click += new System.EventHandler(this.label3_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(405, 5);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(41, 17);
            this.label4.TabIndex = 10;
            this.label4.Text = "email";
            this.label4.Click += new System.EventHandler(this.label4_Click);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(229, 57);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(53, 17);
            this.label5.TabIndex = 11;
            this.label5.Text = "Faculty";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(229, 5);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(43, 17);
            this.label6.TabIndex = 12;
            this.label6.Text = "name";
            // 
            // faculty
            // 
            this.faculty.Location = new System.Drawing.Point(232, 77);
            this.faculty.Name = "faculty";
            this.faculty.ReadOnly = true;
            this.faculty.Size = new System.Drawing.Size(138, 22);
            this.faculty.TabIndex = 13;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(44, 58);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(82, 17);
            this.label2.TabIndex = 14;
            this.label2.Text = "Department";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(21, 203);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(140, 17);
            this.label7.TabIndex = 16;
            this.label7.Text = "Numbers Of Courses";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(21, 143);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(149, 17);
            this.label8.TabIndex = 17;
            this.label8.Text = "Numbers of Questions";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(267, 203);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(120, 17);
            this.label9.TabIndex = 18;
            this.label9.Text = "Numbers Of Tags";
            // 
            // label10
            // 
            this.label10.AutoSize = true;
            this.label10.Location = new System.Drawing.Point(267, 143);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(144, 17);
            this.label10.TabIndex = 19;
            this.label10.Text = "Numbers Of Students";
            // 
            // question
            // 
            this.question.Location = new System.Drawing.Point(24, 163);
            this.question.Name = "question";
            this.question.ReadOnly = true;
            this.question.Size = new System.Drawing.Size(138, 22);
            this.question.TabIndex = 20;
            // 
            // tag
            // 
            this.tag.Location = new System.Drawing.Point(270, 223);
            this.tag.Name = "tag";
            this.tag.ReadOnly = true;
            this.tag.Size = new System.Drawing.Size(138, 22);
            this.tag.TabIndex = 21;
            // 
            // courses
            // 
            this.courses.Location = new System.Drawing.Point(27, 223);
            this.courses.Name = "courses";
            this.courses.ReadOnly = true;
            this.courses.Size = new System.Drawing.Size(138, 22);
            this.courses.TabIndex = 22;
            // 
            // students
            // 
            this.students.Location = new System.Drawing.Point(270, 163);
            this.students.Name = "students";
            this.students.ReadOnly = true;
            this.students.Size = new System.Drawing.Size(138, 22);
            this.students.TabIndex = 23;
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(6, 263);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(578, 17);
            this.label11.TabIndex = 24;
            this.label11.Text = "---------------------------------------------------------------------------------" +
    "---------------------------------";
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(393, 368);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(187, 54);
            this.button1.TabIndex = 25;
            this.button1.Text = "ChangePassword";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(241, 335);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(146, 37);
            this.button2.TabIndex = 26;
            this.button2.Text = "Tables";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // tables
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(604, 450);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.label11);
            this.Controls.Add(this.students);
            this.Controls.Add(this.courses);
            this.Controls.Add(this.tag);
            this.Controls.Add(this.question);
            this.Controls.Add(this.label10);
            this.Controls.Add(this.label9);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.faculty);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.universe);
            this.Controls.Add(this.email);
            this.Controls.Add(this.department);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.name);
            this.Controls.Add(this.Tags);
            this.Controls.Add(this.Moderators);
            this.Controls.Add(this.StudentsList);
            this.Name = "tables";
            this.Text = "MainScreen";
            this.Load += new System.EventHandler(this.MainScreen_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button StudentsList;
        private System.Windows.Forms.Button Moderators;
        private System.Windows.Forms.Button Tags;
        private System.Windows.Forms.TextBox name;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox department;
        private System.Windows.Forms.TextBox email;
        private System.Windows.Forms.TextBox universe;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.TextBox faculty;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label label9;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.TextBox question;
        private System.Windows.Forms.TextBox tag;
        private System.Windows.Forms.TextBox courses;
        private System.Windows.Forms.TextBox students;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
    }
}

