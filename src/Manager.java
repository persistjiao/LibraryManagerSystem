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

public class  Manager {
	LibraryMClient lmc;
	private static String admi="BManager";
	private static String password="tushuguanli123";
	ALFrame alf=new ALFrame(this);
	
	public Manager(){
		
	}
    public Manager(LibraryMClient lmc){
		this.lmc=lmc;
	}
	public static String getAdmi(){
		return admi;
	}
	public static String getPassword(){
		return password;
	}	
	public void ALoad(){
		alf.lunchframe();
	}	
}
class ALFrame extends Frame{
	Manager manager;
	BorrowRevertBFrame brf=new BorrowRevertBFrame(this);
	
	public ALFrame(Manager manager){
		this.manager=manager;		
	}
	public void lunchframe(){		
		this.setTitle("管理员登录");
		this.setBounds(500, 220, 360, 420);
		this.setLayout(null);
		
		//如何知道数据库中有多少条记录，根据数据库信息找出共有多少种书以及现存共多少本书，和所有书的总价格
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
		
//		JButton br=new JButton("借阅与归还");
//		br.addActionListener(new BorrowRevertBook());
//		br.setBounds(30,170,300,40);		
//		this.add(br);
		
		JButton q=new JButton("查询图书");
		q.setBounds(30,220,300,40);
		q.addActionListener(new QueryBook());
		this.add(q);
		
		JButton d=new JButton("删除图书");
		d.setBounds(30,270,300,40);
		d.addActionListener(new DeleteBook());
		this.add(d);
		
		JButton a=new JButton("录入图书");
		a.setBounds(30,320,300,40);
		a.addActionListener(new AddBook());
		this.add(a);
		
		JButton u=new JButton("修改图书");
		u.setBounds(30,370,300,40);
		u.addActionListener(new UpdateBook());
		this.add(u);
		
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				manager.alf.dispose();	}			
		});
		this.setResizable(false);		
		setVisible(true);
	}
	class BorrowRevertBook implements ActionListener{		
		public void actionPerformed(ActionEvent e) {
			brf.lunchFrame();
		}		
	}
	class QueryBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.queryBook();
		}		
	}
	class DeleteBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.deleteBook();
		}		
	}
	class AddBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.addBook();
		}		
	}
	class UpdateBook implements ActionListener{
		Book b=new Book();
		public void actionPerformed(ActionEvent e) {	
			b.updateBook();
		}		
	}
	
}

class BorrowRevertBFrame extends JFrame 
{
    JTable table;
    JScrollPane pane;
    TableModel model;  
    ALFrame al;
    //数据   这应该要用到循环，逐条读出数据库的信息，赋值给data，问题是如何知道数据库表共有多少条信息
    Object[][] data= 
    	{
    		{1,"程序设计1","persist1","2015-8-23","45","未归还" },
    		{2,"程序设计2","persist2","2015-8-23","45","未归还" },
    		{3,"程序设计3","persist3","2015-8-23","45","未归还" },
    		{4,"程序设计4","persist4","2015-8-23","45","未归还" }
    		};        
    //列名
    Object[] clomnName = {"书号","书名","用户名","借阅时间","电话号码","归还"};
    public BorrowRevertBFrame(ALFrame al)
    {
        this.al=al;        
    }
    public void lunchFrame()    {    	  
    	
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
             	data[i][4]=rs.getString("phonenum");
             	data[i][5]=rs.getString("bn"); 
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
        
        //初始化下拉列表框
        JComboBox box = new JComboBox();
        box.addItem("未归还");
        box.addItem("确认归还"); 
        box.setEditable(true);
        box.addItemListener(new ItemListener(){  
        	public void itemStateChanged(ItemEvent e) {		
        		String bn;
				if(box.getSelectedIndex()==1){		
					bn=""+e.getItem();					
					try{    		 
			             Class.forName("com.mysql.jdbc.Driver").newInstance();
			             String url ="jdbc:mysql://localhost:3306/library";
			             Connection conn= DriverManager.getConnection(url,"root","123456");
			             Statement stmt=conn.createStatement();
			             String sql="delete  from brinfo where bn='"+bn+"'";	
			             stmt.execute(sql);
			             stmt.close();
			             conn.close();
			          
			        }catch(Exception e1){
			            e1.printStackTrace();
			        }
			     
					System.out.print("删除该条记录");
				}				
			}        	
        });
        //获取性别这一列对象
        TableColumn d = table.getColumn("归还");        
        //将下拉列表框放入表格编辑器
        DefaultCellEditor dce = new DefaultCellEditor(box);         
        d.setCellEditor(dce);
        
        //初始化JScrollPane
        pane = new JScrollPane();
        //将table添加到JScrollPane上 这里使用下面的第一种方法不能添加
        //使用第一个先获得JViewPort对象，然后再添加
        pane.getViewport().add(table);
        this.setTitle("借阅与归还");
        this.getContentPane().add(pane);
        //setSize(600,300);//设置Frame的大小
        this.setBounds(350,200,600,300);
        setResizable(false);//设置窗口不可以调整大小      
        setVisible(true);//设置窗口显示出来
    }

}










