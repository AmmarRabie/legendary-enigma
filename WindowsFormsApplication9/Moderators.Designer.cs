namespace WindowsFormsApplication9
{
    partial class Moderators
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
            this.AddCB = new System.Windows.Forms.ComboBox();
            this.RemoveCB = new System.Windows.Forms.ComboBox();
            this.Add = new System.Windows.Forms.Button();
            this.Remove = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // AddCB
            // 
            this.AddCB.FormattingEnabled = true;
            this.AddCB.Location = new System.Drawing.Point(43, 87);
            this.AddCB.Name = "AddCB";
            this.AddCB.Size = new System.Drawing.Size(190, 24);
            this.AddCB.TabIndex = 0;
            this.AddCB.SelectedIndexChanged += new System.EventHandler(this.AddCB_SelectedIndexChanged);
            // 
            // RemoveCB
            // 
            this.RemoveCB.FormattingEnabled = true;
            this.RemoveCB.Location = new System.Drawing.Point(34, 234);
            this.RemoveCB.Name = "RemoveCB";
            this.RemoveCB.Size = new System.Drawing.Size(199, 24);
            this.RemoveCB.TabIndex = 1;
            // 
            // Add
            // 
            this.Add.Location = new System.Drawing.Point(313, 88);
            this.Add.Name = "Add";
            this.Add.Size = new System.Drawing.Size(75, 23);
            this.Add.TabIndex = 2;
            this.Add.Text = "Add";
            this.Add.UseVisualStyleBackColor = true;
            this.Add.Click += new System.EventHandler(this.Add_Click);
            // 
            // Remove
            // 
            this.Remove.Location = new System.Drawing.Point(313, 234);
            this.Remove.Name = "Remove";
            this.Remove.Size = new System.Drawing.Size(75, 23);
            this.Remove.TabIndex = 3;
            this.Remove.Text = "Remove";
            this.Remove.UseVisualStyleBackColor = true;
            this.Remove.Click += new System.EventHandler(this.Remove_Click);
            // 
            // Moderators
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(474, 439);
            this.Controls.Add(this.Remove);
            this.Controls.Add(this.Add);
            this.Controls.Add(this.RemoveCB);
            this.Controls.Add(this.AddCB);
            this.Name = "Moderators";
            this.Text = "Moderators";
            this.Load += new System.EventHandler(this.Moderators_Load);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ComboBox AddCB;
        private System.Windows.Forms.ComboBox RemoveCB;
        private System.Windows.Forms.Button Add;
        private System.Windows.Forms.Button Remove;
    }
}