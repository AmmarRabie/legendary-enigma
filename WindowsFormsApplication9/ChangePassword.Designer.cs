namespace WindowsFormsApplication9
{
    partial class ChangePassword
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
            this.changetextbox = new System.Windows.Forms.TextBox();
            this.changebutton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // changetextbox
            // 
            this.changetextbox.Location = new System.Drawing.Point(206, 88);
            this.changetextbox.Name = "changetextbox";
            this.changetextbox.Size = new System.Drawing.Size(176, 22);
            this.changetextbox.TabIndex = 0;
            // 
            // changebutton
            // 
            this.changebutton.Location = new System.Drawing.Point(53, 88);
            this.changebutton.Name = "changebutton";
            this.changebutton.Size = new System.Drawing.Size(127, 23);
            this.changebutton.TabIndex = 1;
            this.changebutton.Text = "Change";
            this.changebutton.UseVisualStyleBackColor = true;
            this.changebutton.Click += new System.EventHandler(this.change_Click);
            // 
            // ChangePassword
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(453, 233);
            this.Controls.Add(this.changebutton);
            this.Controls.Add(this.changetextbox);
            this.Name = "ChangePassword";
            this.Text = "ChangePassword";
            this.Load += new System.EventHandler(this.ChangePassword_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox changetextbox;
        private System.Windows.Forms.Button changebutton;
    }
}