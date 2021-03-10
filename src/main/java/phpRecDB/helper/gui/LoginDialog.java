package phpRecDB.helper.gui;

import phpRecDB.helper.web.Credential;

import javax.swing.*;

public class LoginDialog {
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JPanel pnlContent;
    private JCheckBox cbIgnoreSsl;

    public JPanel getPnlContent() {
        return pnlContent;
    }

    public Credential getCredential() {
        Credential credential = new Credential();
        credential.setUsername(tfUsername.getText());
        credential.setPassword(String.valueOf(tfPassword.getPassword()));
        credential.setIgnoreSsl(cbIgnoreSsl.isSelected());
        return credential;
    }
}
