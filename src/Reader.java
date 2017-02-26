import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

//管理员只有一个默认的，可以查看借阅信息，书的详细信息以及读者的详细信息，删除图书，图书归还管理
//图书的汇总，数目价格应该显示在什么地方，管理员和读者登录后的界面显示图书种数，总图书量，总价格

public class  Reader {
	LibraryMClient lmc;
	//有数据库就不用定义下面两条信息	
	RLFrame rlf=new RLFrame(this);
	
	public Reader(){
		
	}
    public Reader(LibraryMClient lmc){
		this.lmc=lmc;
	}	
	public void RLoad(){
		rlf.lunchframe();
	}	
}
class RLFrame extends Frame{
	Reader reader;
	BorrowInfoBFrame bif=new BorrowInfoBFrame(this);	
	public RLFrame(Reader reader){
		this.reader=reader;		
	}
	public void lunchframe(){		
		this.setTitle("用户登录");
		this.setBounds(500, 220, 360, 420);
		this.setLayout(null);
		//不嫌麻烦就可以再一次遍历数据库查找出，也可以设置为静态变量
		int bnum=0;
		int bsum=0;
		int psum=0;
		try{
	           Class.forName("com.mysql.jdbc.Driver");
	           String url ="jdbc:mysql://localhost:3306/library";
	           Connection conn= DriverManager.getConnection(url,"root","123456");
	           Statement stmt=conn.createStatement();	               
	          String sqlr="select * from book ";		           
	          ResultSet rs=stmt.executeQuery(sqlr);
	       
	           while(rs.next()) {       	   	        	   	
	        	   	 bnum+=1; 
	        	   	 bsum+=rs.getShort("sum");
	        	   	 psum+=rs.getShort("price");
	           }	         
	           rs.close();
         stmt.close();
         conn.close();      
    }catch(Exception e1){
        e1.printStackTrace();
    }
		
		Font f=new Font("华文中宋",Font.BOLD,20);		
		String str="            共有图书"+bnum+" 种\n               共"+bsum+" 本\n     总价格："+psum+" yuan";
		JTextArea ta=new JTextArea(str,1,2);	
		ta.setFont(f);
		ta.setForeground(Color.RED);
		ta.setBounds(20, 40, 320,110);
		ta.setBackground(Color.CYAN);
		ta.setEditable(false);
		this.add(ta);		
		
		JButton q=new JButton("查询图书");
		q.addActionListener(new QueryBook());
		q.setBounds(30,170,300,40);		
		this.add(q);
		
		JButton b=new JButton("借阅图书");
		b.setBounds(30,220,300,40);
		b.addActionListener(new BorrowBook());
		this.add(b);
		
		JButton bi=new JButton("借阅信息");
		bi.setBounds(30,270,300,40);
		bi.addActionListener(new BorrowInfoBook());
		this.add(bi);	
				
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				reader.rlf.dispose();	}			
		});
		this.setResizable(false);		
		setVisible(true);
	}
	class BorrowInfoBook implements ActionListener{		
		public void actionPerformed(ActionEvent e) {
			bif.lunchFrame();
		}		
	}
	class QueryBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.queryBook();
		}		
	}	
	class BorrowBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.borrowBook();
		}		
	}
	
}

class BorrowInfoBFrame extends JFrame 
{
    JTable table;
    JScrollPane pane;
    TableModel model;  
    RLFrame rl;
    //数据   跟前面的操作一样，遍历数据库信息，赋值给data即可
    Object[][] data = {
    		{"000001","程序设计1","persist1","2015-8-23","30" },
    		{"000002","程序设计2","persist2","2015-8-23","30" },
    		{"000003","程序设计3","persist3","2015-8-23","30"},
    		{"000004","程序设计4","persist4","2015-8-23","30" }
    		};
    //列名
    Object[] clomnName = {"书号","书名","用户名","借阅时间","借阅天数"};
    public BorrowInfoBFrame(RLFrame rl)
    {
        this.rl=rl;        
    }
    public void lunchFrame()
    {
    	 try{    		 
             Class.forName("com.mysql.jdbc.Driver").newInstance();
             String url ="jdbc:mysql://localhost:3306/library";
             Connection conn= DriverManager.getConnection(url,"root","123456");
             Statement stmt=conn.createStatement();
             String sql="select * from brinfo";
             ResultSet rs=stmt.executeQuery(sql);
             int i=0;           
             while(rs.next()) { 
             	//System.out.print(rs.getString("bn"));
             	data[i][0]=rs.getString("bn");
             	data[i][1]=rs.getString("bname");
             	data[i][2]=rs.getString("username");
             	data[i][3]=rs.getShort("borrowtime");             	
             	data[i][4]="30";
             	i++;           	
             }   
             
             rs.close();
             stmt.close();
             conn.close();
          
        }catch(Exception e){
            e.printStackTrace();
        }
        //初始化TableModel，使用数据data，列名clomnName
        model = new DefaultTableModel(data, clomnName);
        //使用上面的model作为参数初始化JTabel
        table = new JTable(model);      
        pane = new JScrollPane(); 
        pane.getViewport().add(table);
        this.setTitle("借阅与归还");
        this.getContentPane().add(pane);
        //setSize(600,300);//设置Frame的大小
        this.setBounds(350,200,600,300);
        setResizable(false);//设置窗口不可以调整大小      
        setVisible(true);//设置窗口显示出来
    }

}










