package testvkapi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MainJFrame extends javax.swing.JFrame {

    private static final String getUserInfo = "https://api.vk.com/method/users.get?user_ids=%userid&fields=city,bdate&access_token=d13f7790d13f7790d13f7790bcd15fd2abdd13fd13f77908b6261c2eb3669f984516d60&v=5.71";
    private static final String getUsersFriends = "https://api.vk.com/method/friends.get?user_id=%userid&access_token=d13f7790d13f7790d13f7790bcd15fd2abdd13fd13f77908b6261c2eb3669f984516d60";

    /**
     * Запрос к серверу
     */
    public static String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Информация о пользователе
     */
    private static User userinfo(String user_id) {
        String otv = getUserInfo.replace("%userid", user_id);
        otv = getHTML(otv);
        User user = parseJson(otv);
        return user;
    }

    /**
     * Парсинг JSON-строки с форматом даты "день.месяц.год"
     */
    private static User parseJson(String jsonString) {
        User user = null;
        JSONObject infoUser = null;

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonString);
            JSONObject jsonObject = (JSONObject) obj;
            infoUser = (JSONObject) ((JSONArray) jsonObject.get("response")).get(0);

            user = new User();
            user.setFirstname((String) infoUser.get("first_name"));
            user.setSurname((String) infoUser.get("last_name"));

            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String tempDate = (String) infoUser.get("bdate");
            if (tempDate != null) {
                Date date = format.parse(tempDate);
                user.setBithdate(date);
                user.setNoyear(false);
            }

            JSONObject city = (JSONObject) infoUser.get("city");
            if (city != null) {
                user.setCity((String) city.get("title"));
            }

            return user;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (java.text.ParseException e) {
            parseJsonOnError(infoUser, user);
            return user;
        }
    }

    /**
     * Парсинг JSON-строки с форматом даты "день.месяц"
     */
    private static void parseJsonOnError(JSONObject infoUser, User user) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM");
            String stringDate = (String) infoUser.get("bdate");
            if (stringDate != null) {
                Date tempDate = dateFormat.parse(stringDate);
                user.setBithdate(tempDate);
                user.setNoyear(true);
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает список друзей пользователя
     */
    private static ArrayList<Long> getFriends(String id) {
        ArrayList<Long> listFriends = null;
        try {
            String url = getUsersFriends.replace("%userid", id);
            String jsonString = getHTML(url);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonString);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jar = (JSONArray) jsonObject.get("response");
            listFriends = jar;
            return listFriends;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Преобразование ID общих друзей в объекты
     */
    private static ArrayList<User> transforMutualFriendsOfId(List<Long> listMutualFriendsID) {
        ArrayList<User> mutualFriends = new ArrayList<User>();
        for (Long idUser : listMutualFriendsID) {
            User user = userinfo(idUser.toString());
            mutualFriends.add(user);
        }
        return mutualFriends;
    }

    public MainJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonFindMutualFriends = new javax.swing.JButton();
        jTextFieldUser1 = new javax.swing.JTextField();
        jTextFieldUser2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPaneIOU = new javax.swing.JScrollPane();
        jTextAreaInfoOfUsers = new javax.swing.JTextArea();
        jScrollPaneMF = new javax.swing.JScrollPane();
        jTextAreaMutualFriends = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ID пользователя №1:");

        jLabel2.setText("ID пользователя №2:");

        jButtonFindMutualFriends.setText("Найти");
        jButtonFindMutualFriends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFindMutualFriendsActionPerformed(evt);
            }
        });

        jTextFieldUser1.setText("124964420");

        jTextFieldUser2.setText("94335692");

        jLabel3.setText("Общие друзья");

        jLabel4.setText("Информация о пользователях");

        jTextAreaInfoOfUsers.setColumns(20);
        jTextAreaInfoOfUsers.setRows(5);
        jScrollPaneIOU.setViewportView(jTextAreaInfoOfUsers);

        jTextAreaMutualFriends.setColumns(20);
        jTextAreaMutualFriends.setRows(5);
        jScrollPaneMF.setViewportView(jTextAreaMutualFriends);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUser1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUser2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonFindMutualFriends)
                .addContainerGap(134, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(101, 101, 101))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPaneIOU)
                .addGap(18, 18, 18)
                .addComponent(jScrollPaneMF, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextFieldUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldUser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButtonFindMutualFriends, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneIOU, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(jScrollPaneMF))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonFindMutualFriends.getAccessibleContext().setAccessibleName("jButtonFindMutualFriends");
        jButtonFindMutualFriends.getAccessibleContext().setAccessibleDescription("");
        jTextFieldUser1.getAccessibleContext().setAccessibleName("jTextFieldUser1");
        jTextFieldUser1.getAccessibleContext().setAccessibleDescription("");
        jTextFieldUser2.getAccessibleContext().setAccessibleName("jTextFieldUser2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Вывод информации о пользователях
     */
    private void outputInfoOfUsers(User user_1, User user_2) {
        jTextAreaInfoOfUsers.append(user_1.getFirstname() + "\n");
        jTextAreaInfoOfUsers.append(user_1.getSurname() + "\n");
        if (user_1.getBithdate() != null) {
            if (user_1.getNoyear()) {
                jTextAreaInfoOfUsers.append(formatDateInStringNoYear(user_1.getBithdate()) + "\n");
            } else {
                jTextAreaInfoOfUsers.append(formatDateInString(user_1.getBithdate()) + "\n");
            }
        }
        jTextAreaInfoOfUsers.append(user_1.getCity() + "\n");

        jTextAreaInfoOfUsers.append("\n");

        jTextAreaInfoOfUsers.append(user_2.getFirstname() + "\n");
        jTextAreaInfoOfUsers.append(user_2.getSurname() + "\n");
        if (user_2.getBithdate() != null) {
            if (user_2.getNoyear()) {
                jTextAreaInfoOfUsers.append(formatDateInStringNoYear(user_2.getBithdate()) + "\n");
            } else {
                jTextAreaInfoOfUsers.append(formatDateInString(user_2.getBithdate()) + "\n");
            }
        }
        jTextAreaInfoOfUsers.append(user_2.getCity() + "\n");
    }

    /**
     * Кнопка "Найти"
     */
    private void jButtonFindMutualFriendsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFindMutualFriendsActionPerformed
        String user_id_1 = jTextFieldUser1.getText();
        String user_id_2 = jTextFieldUser2.getText();

        outputInfoOfUsers(userinfo(user_id_1), userinfo(user_id_2));

        List<Long> ids_friends_user_1 = getFriends(user_id_1);
        List<Long> ids_friends_user_2 = getFriends(user_id_2);

        ArrayList<Long> listMutualFriendsID = new ArrayList<Long>();

        for (Long id_fr_user_1 : ids_friends_user_1) {
            for (Long id_fr_user_2 : ids_friends_user_2) {
                if (id_fr_user_1.equals(id_fr_user_2)) {
                    listMutualFriendsID.add(id_fr_user_1);
                }
            }
        }

        ArrayList<User> mutualfriends = transforMutualFriendsOfId(listMutualFriendsID);

        for (User fr : mutualfriends) {
            jTextAreaMutualFriends.append(fr.getFirstname() + "\n");
            jTextAreaMutualFriends.append(fr.getSurname() + "\n");
            if (fr.getBithdate() != null) {
                if (fr.getNoyear()) {
                    jTextAreaMutualFriends.append(formatDateInStringNoYear(fr.getBithdate()) + "\n");
                } else {
                    jTextAreaMutualFriends.append(formatDateInString(fr.getBithdate()) + "\n");
                }
            }
            jTextAreaMutualFriends.append(fr.getCity() + "\n");
            jTextAreaMutualFriends.append("\n");
        }
    }//GEN-LAST:event_jButtonFindMutualFriendsActionPerformed

    /**
     * Форматирование даты для вывода в текстовом поле
     */
    private static String formatDateInString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String str = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + '.' + String.valueOf(cal.get(Calendar.MONTH) + 1) + '.' + String.valueOf(cal.get(Calendar.YEAR));
        return str;
    }

    /**
     * Форматирование даты без года для вывода в текстовом поле
     */
    private static String formatDateInStringNoYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String str = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + '.' + String.valueOf(cal.get(Calendar.MONTH) + 1);
        return str;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFindMutualFriends;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPaneIOU;
    private javax.swing.JScrollPane jScrollPaneMF;
    private javax.swing.JTextArea jTextAreaInfoOfUsers;
    private javax.swing.JTextArea jTextAreaMutualFriends;
    private javax.swing.JTextField jTextFieldUser1;
    private javax.swing.JTextField jTextFieldUser2;
    // End of variables declaration//GEN-END:variables
}
