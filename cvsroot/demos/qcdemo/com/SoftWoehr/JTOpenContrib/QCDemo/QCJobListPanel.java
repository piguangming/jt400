/*
 * QCJobListPanel.java
 *
 * Created on July 14, 2000, 9:07 PM
 *
 * This is free open source software distributed under the IBM Public License found
 * on the World Wide Web at http://oss.software.ibm.com/developerworks/opensource/license10.html
 * Copyright *C* 2000, Jack J. Woehr, PO Box 51, Golden, CO 80402-0051 USA jax@well.com
 * Copyright *C* 2000, International Business Machines Corporation and others. All Rights Reserved.
 */

package com.SoftWoehr.JTOpenContrib.QCDemo;

/** Holds Job List display.
 * @author jax
 * @version 1.0
 */
public class QCJobListPanel extends javax.swing.JPanel implements com.SoftWoehr.JTOpenContrib.QCDemo.QCServiceClient {

  private QCMgr manager;
  private java.lang.String serverName;
  private com.SoftWoehr.JTOpenContrib.QCDemo.QCServiceRecord serviceRecord;
  private com.ibm.as400.vaccess.ErrorDialogAdapter errorDialogAdapter;

  /** Creates new form QCJobListPanel */
  public QCJobListPanel() {
    initComponents ();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the FormEditor.
   */
  private void initComponents () {//GEN-BEGIN:initComponents
    vJobList1 = new com.ibm.as400.vaccess.VJobList ();
    aS400DetailsPane1 = new com.ibm.as400.vaccess.AS400DetailsPane ();
    jPanel1 = new javax.swing.JPanel ();
    systemLabel = new javax.swing.JLabel ();
    systemTextField = new javax.swing.JTextField ();
    listButton = new javax.swing.JButton ();
    jobNameTextField = new javax.swing.JTextField ();
    jobNumberTextField = new javax.swing.JTextField ();
    jobUserTextField = new javax.swing.JTextField ();
    jobNameLabel = new javax.swing.JLabel ();
    jobNumberLabel = new javax.swing.JLabel ();
    jobUserLabel = new javax.swing.JLabel ();
    jobListLabel = new javax.swing.JLabel ();

    setLayout (new java.awt.BorderLayout ());



    add (aS400DetailsPane1, java.awt.BorderLayout.CENTER);

    jPanel1.setLayout (new java.awt.GridLayout (3, 3));
    jPanel1.setToolTipText ("Shows a job list from the system you choose.");

      systemLabel.setText ("System");
      systemLabel.setToolTipText ("Enter the system name at the right.");
      systemLabel.setHorizontalAlignment (javax.swing.SwingConstants.RIGHT);

      jPanel1.add (systemLabel);

      systemTextField.setToolTipText ("Enter the name of the system for which you want a job list.");

      jPanel1.add (systemTextField);

      listButton.setToolTipText ("Click here to get the list of jobs you selected.");
      listButton.setText ("Get Job LIst");
      listButton.addActionListener (new java.awt.event.ActionListener () {
        public void actionPerformed (java.awt.event.ActionEvent evt) {
          listButtonActionPerformed (evt);
        }
      }
      );

      jPanel1.add (listButton);

      jobNameTextField.setToolTipText ("Enter the job name or *ALL.");
      jobNameTextField.setText ("*ALL");

      jPanel1.add (jobNameTextField);

      jobNumberTextField.setToolTipText ("Enter the job number or *ALL.");
      jobNumberTextField.setText ("*ALL");

      jPanel1.add (jobNumberTextField);

      jobUserTextField.setToolTipText ("Enter the user name or *ALL.");
      jobUserTextField.setText ("*ALL");

      jPanel1.add (jobUserTextField);

      jobNameLabel.setText ("Job Name");
      jobNameLabel.setToolTipText ("Above, enter the job name or *ALL.");
      jobNameLabel.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);

      jPanel1.add (jobNameLabel);

      jobNumberLabel.setText ("Job Number");
      jobNumberLabel.setToolTipText ("Above, enter the job number or *ALL.");
      jobNumberLabel.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);

      jPanel1.add (jobNumberLabel);

      jobUserLabel.setText ("User");
      jobUserLabel.setToolTipText ("Above, enter the user name or *ALL.");
      jobUserLabel.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);

      jPanel1.add (jobUserLabel);


    add (jPanel1, java.awt.BorderLayout.SOUTH);

    jobListLabel.setText ("A list of jobs on the server.");
    jobListLabel.setToolTipText ("Fill in the fields at the bottom and press the button to get a list of jobs.");
    jobListLabel.setHorizontalAlignment (javax.swing.SwingConstants.CENTER);


    add (jobListLabel, java.awt.BorderLayout.NORTH);

  }//GEN-END:initComponents

  private void listButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listButtonActionPerformed
    // Add your handling code here:

    // Could take a while.
    java.awt.Cursor cursor = getCursor();
    setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));

    // Be sure we have a server and job list.
    reinstanceServerFromTextEntry();
    reinstanceJobList();

    // If all is well, go get it.
    if (null != vJobList1 && null != serviceRecord) {
      setJobConstraints();
      loadJobs();
      setDetailsPaneRoot();
    }

    // The wait is over.
    setCursor(cursor);
    repaint(500);
  }//GEN-LAST:event_listButtonActionPerformed

  private synchronized void reinstanceServerFromTextEntry() {
    String newName = systemTextField.getText();
    if (null == serverName) {
      serverName = "";
    }
    if (null != newName) {
      if (!newName.equals("")) {
        if (!newName.equals(serverName)) {
          serverName = newName;
          if (null != serviceRecord) {

            try { // let go of the service from manager
              manager.freeService(serviceRecord);
            }
            catch (Exception e) {
              e.printStackTrace();
            }
            serviceRecord = null;
          }
          try {
            serviceRecord = manager.getService("qcmgr:" + serverName + "/COMMAND", this);
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }
  private synchronized void reinstanceJobList () {
    vJobList1 = new com.ibm.as400.vaccess.VJobList(serviceRecord.as400);
    if (null != errorDialogAdapter) {
      vJobList1.addErrorListener(errorDialogAdapter);
    }
  }

  private void setDetailsPaneRoot() {
    if (null != vJobList1) {
      try {
        aS400DetailsPane1.setRoot(vJobList1);
      }
      catch (java.beans.PropertyVetoException e) {
        e.printStackTrace();
      }
    }
  }

  private void loadJobs() {
    vJobList1.load();
  }

  private void setJobConstraints() {
    try {
      vJobList1.setName(jobNameTextField.getText());
      vJobList1.setNumber(jobNumberTextField.getText());
      vJobList1.setUser(jobUserTextField.getText());
    }
    catch (java.beans.PropertyVetoException e) {
      e.printStackTrace();
    }
  }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.ibm.as400.vaccess.VJobList vJobList1;
  private com.ibm.as400.vaccess.AS400DetailsPane aS400DetailsPane1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JLabel systemLabel;
  private javax.swing.JTextField systemTextField;
  private javax.swing.JButton listButton;
  private javax.swing.JTextField jobNameTextField;
  private javax.swing.JTextField jobNumberTextField;
  private javax.swing.JTextField jobUserTextField;
  private javax.swing.JLabel jobNameLabel;
  private javax.swing.JLabel jobNumberLabel;
  private javax.swing.JLabel jobUserLabel;
  private javax.swing.JLabel jobListLabel;
  // End of variables declaration//GEN-END:variables

  /** Sets the QCMgr object which will handle AS400 instances for this client.
   * @param mgr A QCMgr object.
   */
  public void setManager(QCMgr mgr) {
    manager = mgr;
  }

  /** Connects the ErrorDialogAdapter with any as400.vaccess components present.
   * @param eda An instance of an ErrorDialogAdapter already associated
   * with a suitable Frame.
   */
  public void propagateEDA(com.ibm.as400.vaccess.ErrorDialogAdapter eda) {
    errorDialogAdapter=eda;
    aS400DetailsPane1.addErrorListener(eda);
    vJobList1.addErrorListener(eda);
  }
  /** Required implementantion of the QCServiceClient interface. Releases the
   * AS400 object provided by the server.
   * @param sr The service record which represents the AS400 to be
   * relinquished.
   */
  public void relinquish(QCServiceRecord sr) {
    serviceRecord = null;
  }
}
