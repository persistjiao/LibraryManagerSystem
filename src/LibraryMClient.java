import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class LibraryMClient {
	public static String username=null;
	public static String userpassword=null;
	public static String adminame,admipassword;
	public static String phonenum;
	List<Book> books=new ArrayList<Book>();

	public static void main(String[] args) {		
	    LMFrame lm=new LMFrame();
	    lm.launchFrame();
	}

}
class LMFrame extends Frame{
	
	private static final int WIDTH=800;
	private static final int HEIGHT=600;
	public void launchFrame(){
		this.setTitle("后台管理系统");
		this.setLocation(300, 40);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		
		Font f1=new Font("宋体",Font.BOLD,60);		
		Label title=new Label("后台管理系统",Label.CENTER);
		title.setFont(f1);		
		title.setBounds(200,100,400,60);		
		this.add(title);
		Font f2=new Font("宋体",Font.BOLD,30);
		Label username=new Label("用户名：");
		username.setFont(f2);		
		username.setBounds(200, 300, 130, 40);
		this.add(username);
		Label password=new Label(" 密码：");
		password.setFont(f2);		
		password.setBounds(200, 360, 130, 40);
		this.add(password);		
				
		TextField uPut=new TextField();
		uPut.setBounds(330, 300, 250, 40);
		uPut.setFont(f2);
		this.add(uPut);
		
		TextField pPut=new TextField();		
		pPut.setBounds(330, 360, 250, 40);
		pPut.setFont(f2);
		pPut.setEchoChar('*');
		this.add(pPut);
		
		Font f3=new Font("宋体",Font.BOLD,20);
		Button admland=new Button("管理员登录");	
		admland.setFont(f3);
		admland.setBounds(210, 450,105, 35);
		admland.addActionListener(new AdmiLand(uPut,pPut));
		this.add(admland);
		
		Button userland=new Button("读者登录");	
		userland.setFont(f3);
		userland.setBounds(350, 450,90, 35);
		userland.addActionListener(new UserLand(uPut,pPut));
		this.add(userland);
		
		Button enrol=new Button("注册");	
		enrol.setFont(f3);
		enrol.setBounds(480, 450,90, 35);
		enrol.addActionListener(new Enrol());
		this.add(enrol);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);	}			
		});
		this.setBackground(Color.orange);
		this.setResizable(false);		
		setVisible(true);	
	}
}
class AdmiLand extends JFrame implements ActionListener{	
	LibraryMClient  lmc;
	TextField tf1,tf2;	
	public AdmiLand(LibraryMClient  lmc){
		this.lmc=lmc;
	}
	public AdmiLand(TextField tf1,TextField tf2){
		this.tf1=tf1;
		this.tf2=tf2;
	}
	public void actionPerformed(ActionEvent e) {	
		//连接数据库，读出管理员的信息，比较是否相同
		try{
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://localhost:3306/library";
            Connection conn= DriverManager.getConnection(url,"root","123456");
            Statement stmt=conn.createStatement();
            String sql="select * from admin";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()) {
            	lmc.adminame=rs.getString("auser");
            	lmc.admipassword=rs.getString("apwd");             	
            }              
            rs.close();
            stmt.close();
            conn.close();
         
       }catch(Exception e1){
           e1.printStackTrace();
       }
    
		if(!tf1.getText().equals(lmc.adminame)&&!tf2.getText().equals(lmc.admipassword)){
		    JOptionPane.showMessageDialog(null, "请检查用户名或密码是否错误","错误", JOptionPane.ERROR_MESSAGE);
		}	
		else{
			Manager m=new Manager();
			m.ALoad();
		}		
	}	
}
class UserLand extends JFrame implements ActionListener{
	LibraryMClient  lmc;
	TextField tf1,tf2;	
	public UserLand(LibraryMClient  lmc){
		this.lmc=lmc;
	}
	public UserLand(TextField tf1,TextField tf2){
		this.tf1=tf1;
		this.tf2=tf2;
	}
	public void actionPerformed(ActionEvent e) {
		//按照用户名查找出对应的密码，如果密码相同则登录，密码不同且找不到该用户，则登录失败
		try{
			boolean find=false;
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:mysql://localhost:3306/library";
            Connection conn= DriverManager.getConnection(url,"root","123456");
            Statement stmt=conn.createStatement();
            String sql="select * from user";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next()) {
            	lmc.username=rs.getString("username");
            	lmc.userpassword=rs.getString("upwd"); 
            	lmc.phonenum=rs.getString("phonenum");
            	if(tf1.getText().equals(lmc.username)&&tf2.getText().equals(lmc.userpassword)){
            		Reader r=new Reader();
        			r.RLoad();
        			find=true;
        			break;
        		}	        			
            }   
           if(!find){
    		    JOptionPane.showMessageDialog(null, "请检查用户名或密码是否错误","错误", JOptionPane.ERROR_MESSAGE);
    		}	
            rs.close();
            stmt.close();
            conn.close();
         
       }catch(Exception e1){
           e1.printStackTrace();
       }    			
	}
	
}
class Enrol implements ActionListener{	
	public void actionPerformed(ActionEvent e) {		
		
	}
	
}




