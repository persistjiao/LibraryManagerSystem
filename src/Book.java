import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Book {
	private int num;
	private String name;
	private String author;
	private String publisher;
	private float price;
	private String storagetime;
	private int total;
	private int standingcrop;	
	AddBFrame af=new AddBFrame(this);
	QueryFrame qf=new QueryFrame(this);	
	DeleteBFrame df=new DeleteBFrame(this);
	UpdateBFrame uf=new UpdateBFrame(this);
	BorrowBFrame bf=new BorrowBFrame(this);
	public void addBook(){		
		af.launchFrame();		
	}
	public void queryBook(){
		qf.lauchFrame();
	}	
	public void deleteBook(){
		df.lauchFrame();
	}
	public void updateBook(){
		uf.lauchFrame();
	}
	public void borrowBook(){
		bf.lauchFrame();
	}
}

class AddBFrame extends JFrame{
	private static final int WIDTH=400;
	private static final int HEIGHT=550;
	private static final int initx=0;
	private static final int inity=30;
	private static final int lw=80;
	private static final int lh=30;
	private static final int tfw=300;
	private static final int tfh=30;
	private static final int rp=20;	
	Book book;
	public AddBFrame(Book book){
		this.book=book;
	}
	
	public void launchFrame(){		
		this.setTitle("录入图书");
		this.setLocation(300, 40);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);			
		
		int x=initx;
		int y=inity;		
		Font f1=new Font("宋体",Font.BOLD,15);
		Font f2=new Font("宋体",Font.PLAIN,22);
				
		Label num=new Label("书号：",Label.CENTER);
		num.setFont(f1);		
		num.setBounds(x,y,lw,lh);				
		TextField pn=new TextField();
		pn.setBounds(lw, y, tfw, tfh);
		pn.setFont(f2);		
		this.add(num);
		this.add(pn);			
				
		Label name=new Label("书名：",Label.CENTER);
		name.setFont(f1);		
		name.setBounds(x,y+(lh+rp),lw,lh);				
		TextField pna=new TextField();
		pna.setBounds(lw, y+(lh+rp), tfw, tfh);
		pna.setFont(f2);
		String bname=pna.getText();
		this.add(name);
		this.add(pna);				
		
		Label a=new Label("作者：",Label.CENTER);
		a.setFont(f1);		
		a.setBounds(x,y+2*(lh+rp),lw,lh);				
		TextField pa=new TextField();
		pa.setBounds(lw,y+2*(lh+rp), tfw, tfh);
		pa.setFont(f2);
		String author=pa.getText();
		this.add(a);
		this.add(pa);
				
		Label p=new Label("出版社：",Label.CENTER);
		p.setFont(f1);		
		p.setBounds(x,y+3*(lh+rp),lw,lh);				
		TextField pp=new TextField();
		pp.setBounds(lw,y+ 3*(lh+rp), tfw, tfh);
		pp.setFont(f2);
		String press=pp.getText();
		this.add(p);
		this.add(pp);
				
		Label pr=new Label("单价：",Label.CENTER);
		pr.setFont(f1);		
		pr.setBounds(x,y+4*(lh+rp),lw,lh);				
		TextField ppr=new TextField();
		ppr.setBounds(lw, y+4*(lh+rp), tfw, tfh);
		ppr.setFont(f2);
		//如何把string类型的值转为double类型
		String price=ppr.getText();
		this.add(pr);
		this.add(ppr);				
		
		Label s=new Label("入库时间：");
		s.setFont(f1);		
		s.setBounds(x,y+5*(lh+rp),lw,lh);				
		TextField ps=new TextField();
		ps.setBounds(lw, y+5*(lh+rp), tfw, tfh);
		ps.setFont(f2);
		String entertime=ps.getText();
		this.add(s);
		this.add(ps);
				
		Label t=new Label("总量：",Label.CENTER);
		t.setFont(f1);		
		t.setBounds(x,y+6*(lh+rp),lw,lh);				
		TextField pt=new TextField();
		pt.setBounds(lw, y+6*(lh+rp), tfw, tfh);
		pt.setFont(f2);
		//String类型如何转换成int类型
		String sum=pt.getText();
		this.add(t);
		this.add(pt);		
				
		Label c=new Label("现存量：",Label.CENTER);
		c.setFont(f1);		
		c.setBounds(x,y+7*(lh+rp),lw,lh);				
		TextField pc=new TextField();
		pc.setBounds(lw, y+7*(lh+rp), tfw, tfh);
		pc.setFont(f2);
		//String类型如何转换成int类型
		String nowsum=pc.getText();
		this.add(c);
		this.add(pc);			
		
		Button b=new Button("保存");	
		b.setFont(f1);
		b.setBounds(250, y+8*(lh+rp)+30,100,30);
		b.addActionListener(new ConserveBook(book,pn,pna,pa,pp,ppr,ps,pt,pc));
		this.add(b);
						
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {
				book.af.dispose();	}			
		});
		this.setBackground(Color.CYAN);
		this.setResizable(false);		
		setVisible(true);	
	}
	
	class ConserveBook implements ActionListener{
		Book book;
		TextField tf1=new TextField();
		TextField tf2=new TextField();
		TextField tf3=new TextField();
		TextField tf4=new TextField();
		TextField tf5=new TextField();
		TextField tf6=new TextField();
		TextField tf7=new TextField();
		TextField tf8=new TextField();
		public ConserveBook(Book book,TextField tf1,TextField tf2,TextField tf3,TextField tf4,TextField tf5,TextField tf6,TextField tf7,TextField tf8){
			this.book=book;
			this.tf1=tf1;
			this.tf2=tf2;
			this.tf3=tf3;
			this.tf4=tf4;
			this.tf5=tf5;
			this.tf6=tf6;
			this.tf7=tf7;
			this.tf8=tf8;
		}
		public void actionPerformed(ActionEvent e) {		
			//插入数据库			
			 try{
	               Class.forName("com.mysql.jdbc.Driver");
	               String url ="jdbc:mysql://localhost:3306/library";
	               Connection conn= DriverManager.getConnection(url,"root","123456");
	               Statement stmt=conn.createStatement();          
          
	              // String sql="insert into book  values ('0','"+tf2.getText()+"','"+tf3.getText()+"','"+tf4.getText()+"','"+tf5.getText()+"','"+tf6.getText()+"','"+tf7.getText()+"','"+tf8.getText()+"')";
	              // '"+tf1.getText()+"'
	               String sql="insert into book(bname,author,press,price,entertime,sum,nowsum)  "
		               		+ "values ('"+tf2.getText()+"','"+tf3.getText()+"','"+tf4.getText()+"',"
		            		+Integer.parseInt(tf5.getText())+",'"+tf6.getText()+"',"+Integer.parseInt(tf7.getText())+","
		               		+Integer.parseInt(tf8.getText())+")";

	               stmt.executeUpdate(sql);                
	               stmt.close();
	               conn.close();
	            
	          }catch(Exception e1){
	              e1.printStackTrace();
	          }
			tf1.setText("");
			tf2.setText("");
			tf3.setText("");
			tf4.setText("");
			tf5.setText("");
			tf6.setText("");
			tf7.setText("");
			tf8.setText("");			
		}		
	}	
}
class QueryFrame extends Frame{
	Book book;
	
	public QueryFrame(Book book){
		this.book=book;		
	}
	Button[] b=new Button[100];	
	String[] bnames=new String[100];
	InfoBookFrame ibf=new InfoBookFrame(this);
	public void lauchFrame(){	
		
		Panel p1=new Panel();
		Panel p2=new Panel();
		this.setTitle("查询图书");
		this.setLocation(300, 40);
		this.setSize(300, 400);
		this.setLayout(null);
		//p.setLayout(new GridLayout(7,2,15,15));
		Font f=new Font("楷体",Font.BOLD,25);
		Label tl=new Label("查询图书",Label.CENTER);
		tl.setFont(f);
		p1.add(tl);
		p1.setBounds(0,25,300,50);
		this.add(p1);	
		
		JScrollPane jsp=new JScrollPane(p2);
		jsp.setBounds(0,80,300,1000);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		GridLayout box=new GridLayout(100,1);
		p2.setLayout(box);
		try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url ="jdbc:mysql://localhost:3306/library";
            Connection conn= DriverManager.getConnection(url,"root","123456");
            Statement stmt=conn.createStatement();
            String sql="select * from book";
            ResultSet rs=stmt.executeQuery(sql);
            int i=0;
            while(rs.next()) {
            	bnames[i]=rs.getString("bname");            
            	b[i]=new Button(bnames[i]);
    			b[i].setBackground(Color.WHITE);    			
    			b[i].addActionListener(new QueryInfo(bnames[i]));			
    			p2.add(b[i]);
    			i++;
            }
                       
            rs.close();
            stmt.close();
            conn.close();
         
       }catch(Exception e){
           e.printStackTrace();
       }
					
		this.addWindowListener(new WindowAdapter() {
			 
			public void windowClosing(WindowEvent e) {
 				book.qf.dispose();
				
			}			
		});		
		this.setBackground(Color.CYAN);
		this.setResizable(false);		
		this.setVisible(true);
	}
	class QueryInfo implements ActionListener{
		String bname;
		Button b1;
		public QueryInfo(String bname){
			this.bname=bname;
		}
		public void actionPerformed(ActionEvent e) {						
			ibf.launchFrame(bname);							
		}	
	}
}
	
class InfoBookFrame extends Frame{
		private static final int WIDTH=400;
		private static final int HEIGHT=450;
		private static final int initx=0;
		private static final int inity=30;
		private static final int lw=80;
		private static final int lh=30;
		private static final int tfw=300;
		private static final int tfh=30;
		private static final int rp=20;

		QueryFrame qb;
		public InfoBookFrame(QueryFrame qb){
			this.qb=qb;
		}
		public void launchFrame(String bname){		
			Panel panel=new Panel();
			this.setLocation(300, 40);
			this.setSize(WIDTH, HEIGHT);
			panel.setLayout(null);
			int x=initx;
			int y=inity;		
			Font f1=new Font("宋体",Font.BOLD,15);
			Font f2=new Font("宋体",Font.PLAIN,22);
			  try{
	               Class.forName("com.mysql.jdbc.Driver").newInstance();
	               String url ="jdbc:mysql://localhost:3306/library";
	               Connection conn= DriverManager.getConnection(url,"root","123456");
	               Statement stmt=conn.createStatement();	               
	               String sqlr="select * from book where bname='"+bname+"'";
	              
	               ResultSet rs=stmt.executeQuery(sqlr);
	               while(rs.next()) { 
	            	   
	            Label num=new Label("书号：",Label.CENTER);
	   			num.setFont(f1);		
	   			num.setBounds(x,y,lw,lh);				
	   			TextField pn=new TextField();
	   			pn.setBounds(lw, y, tfw, tfh);
	   			pn.setFont(f2);	   			
	   			pn.setText(rs.getString("id"));
	   			panel.add(num);
	   			panel.add(pn);			
	   					
	   			Label name=new Label("书名：",Label.CENTER);
	   			name.setFont(f1);		
	   			name.setBounds(x,y+(lh+rp),lw,lh);				
	   			TextField pna=new TextField();
	   			pna.setBounds(lw, y+(lh+rp), tfw, tfh);
	   			pna.setFont(f2);
	   			pna.setText(rs.getString("bname"));
	   			panel.add(name);
	   			panel.add(pna);				
	   			
	   			Label a=new Label("作者：",Label.CENTER);
	   			a.setFont(f1);		
	   			a.setBounds(x,y+2*(lh+rp),lw,lh);				
	   			TextField pa=new TextField();
	   			pa.setBounds(lw,y+2*(lh+rp), tfw, tfh);
	   			pa.setFont(f2);
	   			pa.setText(rs.getString("author"));
	   			panel.add(a);
	   			panel.add(pa);
	   					
	   			Label p=new Label("出版社：",Label.CENTER);
	   			p.setFont(f1);		
	   			p.setBounds(x,y+3*(lh+rp),lw,lh);				
	   			TextField pp=new TextField();
	   			pp.setBounds(lw,y+ 3*(lh+rp), tfw, tfh);
	   			pp.setFont(f2);
	   			pp.setText(rs.getString("press"));
	   			panel.add(p);
	   			panel.add(pp);
	   					
	   			Label pr=new Label("单价：",Label.CENTER);
	   			pr.setFont(f1);		
	   			pr.setBounds(x,y+4*(lh+rp),lw,lh);				
	   			TextField ppr=new TextField();
	   			ppr.setBounds(lw, y+4*(lh+rp), tfw, tfh);
	   			ppr.setFont(f2);
	   			ppr.setText(""+rs.getString("price"));
	   			panel.add(pr);
	   			panel.add(ppr);				
	   			
	   			Label s=new Label("入库时间：");
	   			s.setFont(f1);		
	   			s.setBounds(x,y+5*(lh+rp),lw,lh);				
	   			TextField ps=new TextField();
	   			ps.setBounds(lw, y+5*(lh+rp), tfw, tfh);
	   			ps.setFont(f2);
	   			ps.setText(rs.getString("entertime"));
	   			panel.add(s);
	   			panel.add(ps);
	   					
	   			Label t=new Label("总量：",Label.CENTER);
	   			t.setFont(f1);		
	   			t.setBounds(x,y+6*(lh+rp),lw,lh);				
	   			TextField pt=new TextField();
	   			pt.setBounds(lw, y+6*(lh+rp), tfw, tfh);
	   			pt.setFont(f2);
	   			pt.setText(""+rs.getShort("sum"));
	   			panel.add(t);
	   			panel.add(pt);		
	   					
	   			Label c=new Label("现存量：",Label.CENTER);
	   			c.setFont(f1);		
	   			c.setBounds(x,y+7*(lh+rp),lw,lh);				
	   			TextField pc=new TextField();
	   			pc.setBounds(lw, y+7*(lh+rp), tfw, tfh);
	   			pc.setFont(f2);
	   			pc.setText(""+rs.getShort("nowsum"));
	   			panel.add(c);
	   			panel.add(pc);	
	   			this.add(panel);
	   			}		
	               rs.close();
	               stmt.close();
	               conn.close();
	               
	          }catch(Exception e){
	              e.printStackTrace();
	          }																				
			this.addWindowListener(new WindowAdapter() {			
				public void windowClosing(WindowEvent e) {
					qb.ibf.dispose();	
					}			
			});
			this.setBackground(Color.CYAN);
			this.setResizable(false);		
			setVisible(true);
			}	
		}
class DeleteBFrame extends Frame{	
	Book book=null;	
	Button[] b=new Button[100];
	public DeleteBFrame(Book book){
		this.book=book;			
	}	
	public void lauchFrame(){
		Panel p1=new Panel();
		Panel p2=new Panel();
		this.setTitle("删除图书");
		this.setLocation(300, 40);
		this.setSize(300, 400);
		this.setLayout(null);
		Font f=new Font("楷体",Font.BOLD,25);
		Label tl=new Label("删除图书",Label.CENTER);
		tl.setFont(f);
		p1.add(tl);
		p1.setBounds(0,25,300,50);
		this.add(p1);	
		
		JScrollPane jsp=new JScrollPane(p2);
		jsp.setBounds(0,80,300,1000);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		GridLayout box=new GridLayout(100,1);
		p2.setLayout(box);
		try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url ="jdbc:mysql://localhost:3306/library";
            Connection conn= DriverManager.getConnection(url,"root","123456");
            Statement stmt=conn.createStatement();
            String sql="select * from book";
            ResultSet rs=stmt.executeQuery(sql);
            int i=0;
            while(rs.next()) {
            	String bname=rs.getString("bname");            
            	b[i]=new Button(bname);
    			b[i].setBackground(Color.WHITE);    			
    			b[i].addActionListener(new DeleteBook(bname));			
    			p2.add(b[i]);
    			i++;
            }
                       
            rs.close();
            stmt.close();
            conn.close();
         
       }catch(Exception e){
           e.printStackTrace();
       }					
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {				
				book.df.dispose();	
				}			
		});		
		this.setBackground(Color.CYAN);
		this.setResizable(false);		
		setVisible(true);
}
	
	class DeleteBook extends Frame implements ActionListener{			
		String bname;
		public DeleteBook(String bname){
			this.bname=bname;
		}
		public void actionPerformed(ActionEvent e) {
			 try{
	               Class.forName("com.mysql.jdbc.Driver");
	               String url ="jdbc:mysql://localhost:3306/library";
	               Connection conn= DriverManager.getConnection(url,"root","123456");
	               Statement stmt=conn.createStatement();
	               String sql="delete from book where bname='"+bname+"'";
	               stmt.executeUpdate(sql);            
	             
	               stmt.close();
	               conn.close();
	            
	          }catch(Exception e1){
	              e1.printStackTrace();
	          }
		    JOptionPane.showMessageDialog(null, "确认删除","删除", JOptionPane.ERROR_MESSAGE);
		}		
	}
			
	}
	

class UpdateBFrame extends Frame{	
	Book book=null;
	//UpdateBook1 db1=new UpdateBook1();
	//UpdateBook2 db2=new UpdateBook2();
	Button[] b=new Button[100];
	String[] bnames=new String[100];
	public UpdateBFrame(Book book){
		this.book=book;			
	}	
	public void lauchFrame(){
		Panel p1=new Panel();
		Panel p2=new Panel();
		this.setTitle("修改图书");
		this.setLocation(300, 40);
		this.setSize(300, 400);
		this.setLayout(null);
		Font f=new Font("楷体",Font.BOLD,25);
		Label tl=new Label("修改图书",Label.CENTER);
		tl.setFont(f);
		p1.add(tl);
		p1.setBounds(0,25,300,50);
		this.add(p1);	
		
		JScrollPane jsp=new JScrollPane(p2);
		jsp.setBounds(0,80,300,1000);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		GridLayout box=new GridLayout(100,1);
		p2.setLayout(box);
		try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url ="jdbc:mysql://localhost:3306/library";
            Connection conn= DriverManager.getConnection(url,"root","123456");
            Statement stmt=conn.createStatement();
            String sql="select * from book";
            ResultSet rs=stmt.executeQuery(sql);
            int i=0;
            
            while(rs.next()) {
            	
				bnames[i]=rs.getString("bname");            
            	b[i]=new Button(bnames[i]);
    			b[i].setBackground(Color.WHITE);    			
    			b[i].addActionListener(new UpdateBook1(bnames[i]));			
    			p2.add(b[i]);
    			i++;
            }
                       
            rs.close();
            stmt.close();
            conn.close();
         
       }catch(Exception e){
           e.printStackTrace();
       }					
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {				
				book.uf.dispose();	
				}			
		});		
		this.setBackground(Color.CYAN);
		this.setResizable(false);		
		setVisible(true);
}
	
	class UpdateBook1 implements ActionListener{
		String bname;
		private  final int WIDTH=400;
		private  final int HEIGHT=550;
		private  final int initx=0;
		private  final int inity=30;
		private  final int lw=80;
		private  final int lh=30;
		private  final int tfw=300;
		private  final int tfh=30;
		private  final int rp=20;
		UpdateBFrame ub;		
		public UpdateBook1(String bname){			
			this.bname=bname;
		}		
			    
		public void actionPerformed(ActionEvent e) {	
			JFrame jf=new JFrame("更新图书信息");	   					
			Panel panel=new Panel();
			jf.setLocation(300, 40);
			jf.setSize(WIDTH, HEIGHT);
			panel.setLayout(null);
			int x=initx;
			int y=inity;		
			Font f1=new Font("宋体",Font.BOLD,15);
			Font f2=new Font("宋体",Font.PLAIN,22);
		    try{
		           Class.forName("com.mysql.jdbc.Driver").newInstance();
		           String url ="jdbc:mysql://localhost:3306/library";
		           Connection conn= DriverManager.getConnection(url,"root","123456");
		           Statement stmt=conn.createStatement();	               
		           String sqlr="select * from book where bname='"+bname+"'";
		              
		           ResultSet rs=stmt.executeQuery(sqlr);
		           while(rs.next()) { 
		            	   
		            Label num=new Label("书号：",Label.CENTER);
		   			num.setFont(f1);		
		   			num.setBounds(x,y,lw,lh);				
		   			TextField pn=new TextField();
		   			pn.setBounds(lw, y, tfw, tfh);
		   			pn.setFont(f2);	   			
		   			pn.setText(rs.getString("id"));
		   			panel.add(num);
		   			panel.add(pn);			
		   					
		   			Label name=new Label("书名：",Label.CENTER);
		   			name.setFont(f1);		
		   			name.setBounds(x,y+(lh+rp),lw,lh);				
		   			TextField pna=new TextField();
		   			pna.setBounds(lw, y+(lh+rp), tfw, tfh);
		   			pna.setFont(f2);
		   			pna.setText(rs.getString("bname"));
		   			panel.add(name);
		   			panel.add(pna);				
		   			
		   			Label a=new Label("作者：",Label.CENTER);
		   			a.setFont(f1);		
		   			a.setBounds(x,y+2*(lh+rp),lw,lh);				
		   			TextField pa=new TextField();
		   			pa.setBounds(lw,y+2*(lh+rp), tfw, tfh);
		   			pa.setFont(f2);
		   			pa.setText(rs.getString("author"));
		   			panel.add(a);
		   			panel.add(pa);
		   					
		   			Label p=new Label("出版社：",Label.CENTER);
		   			p.setFont(f1);		
		   			p.setBounds(x,y+3*(lh+rp),lw,lh);				
		   			TextField pp=new TextField();
		   			pp.setBounds(lw,y+ 3*(lh+rp), tfw, tfh);
		   			pp.setFont(f2);
		   			pp.setText(rs.getString("press"));
		   			panel.add(p);
		   			panel.add(pp);
		   					
		   			Label pr=new Label("单价：",Label.CENTER);
		   			pr.setFont(f1);		
		   			pr.setBounds(x,y+4*(lh+rp),lw,lh);				
		   			TextField ppr=new TextField();
		   			ppr.setBounds(lw, y+4*(lh+rp), tfw, tfh);
		   			ppr.setFont(f2);
		   			ppr.setText(""+rs.getString("price"));
		   			panel.add(pr);
		   			panel.add(ppr);				
		   			
		   			Label s=new Label("入库时间：");
		   			s.setFont(f1);		
		   			s.setBounds(x,y+5*(lh+rp),lw,lh);				
		   			TextField ps=new TextField();
		   			ps.setBounds(lw, y+5*(lh+rp), tfw, tfh);
		   			ps.setFont(f2);
		   			ps.setText(rs.getString("entertime"));
		   			panel.add(s);
		   			panel.add(ps);
		   					
		   			Label t=new Label("总量：",Label.CENTER);
		   			t.setFont(f1);		
		   			t.setBounds(x,y+6*(lh+rp),lw,lh);				
		   			TextField pt=new TextField();
		   			pt.setBounds(lw, y+6*(lh+rp), tfw, tfh);
		   			pt.setFont(f2);
		   			pt.setText(""+rs.getShort("sum"));
		   			panel.add(t);
		   			panel.add(pt);		
		   					
		   			Label c=new Label("现存量：",Label.CENTER);
		   			c.setFont(f1);		
		   			c.setBounds(x,y+7*(lh+rp),lw,lh);				
		   			TextField pc=new TextField();
		   			pc.setBounds(lw, y+7*(lh+rp), tfw, tfh);
		   			pc.setFont(f2);
		   			pc.setText(""+rs.getShort("nowsum"));
		   			panel.add(c);
		   			panel.add(pc);	
		   			
					Button b=new Button("保存");	
					b.setFont(f1);
					b.setBounds(250, y+8*(lh+rp)+30,100,30);
					b.addActionListener(new UpdateBook2(bname,pn,pna,pa,pp,ppr,ps,pt,pc));
					panel.add(b);
		   			jf.add(panel);
		   			}		
		               rs.close();
		               stmt.close();
		               conn.close();
		               
		          }catch(Exception e1){
		              e1.printStackTrace();
		          }																			

				jf.setBackground(Color.CYAN);
				jf.setResizable(false);		
				jf.setVisible(true);
				}				
		}		
	}
		
class UpdateBook2 implements ActionListener{
	String bname;		
	TextField tf1=new TextField();
	TextField tf2=new TextField();
	TextField tf3=new TextField();
	TextField tf4=new TextField();
	TextField tf5=new TextField();
	TextField tf6=new TextField();
	TextField tf7=new TextField();
	TextField tf8=new TextField();
	public UpdateBook2(String bname,TextField tf1,TextField tf2,TextField tf3,TextField tf4,TextField tf5,TextField tf6,TextField tf7,TextField tf8){
     	this.bname=bname;
		this.tf1=tf1;
		this.tf2=tf2;
		this.tf3=tf3;
		this.tf4=tf4;
		this.tf5=tf5;
		this.tf6=tf6;
		this.tf7=tf7;
		this.tf8=tf8;
	}
	public void actionPerformed(ActionEvent e) {		
		//插入数据库			
			try{
		             Class.forName("com.mysql.jdbc.Driver");
		             String url ="jdbc:mysql://localhost:3306/library";
		             Connection conn= DriverManager.getConnection(url,"root","123456");
		             Statement stmt=conn.createStatement();         
	          
		             String sql="update book set bname='"+tf2.getText()+"',"
		             		+ "author='"+tf3.getText()+"',press='"+tf4.getText()+"',"
		             				+ "price='"+tf5.getText()+"',"
		             						+ "entertime='"+tf6.getText()+"',"
		             								+ "sum='"+tf7.getText()+"',"
		             										+ "nowsum='"+tf8.getText()+"' where bname='"+bname+"'";		            
		             stmt.executeUpdate(sql);                
		             stmt.close();
		             conn.close();
		            
		        }catch(Exception e1){
		            e1.printStackTrace();
		        }
			
	}				
}	
class BorrowBFrame extends JFrame{	
	Book book=null;	
	Button[] b=new Button[100];
	String[] bnames=new String[100];
	public BorrowBFrame(Book book){
		this.book=book;			
	}	
	public void lauchFrame(){
		Panel p1=new Panel();
		Panel p2=new Panel();
		this.setTitle("借阅图书");
		this.setLocation(300, 40);
		this.setSize(300, 400);
		this.setLayout(null);
		Font f=new Font("楷体",Font.BOLD,25);
		Label tl=new Label("借阅图书",Label.CENTER);
		tl.setFont(f);
		p1.add(tl);
		p1.setBounds(0,25,300,50);
		this.add(p1);	
		
		JScrollPane jsp=new JScrollPane(p2);
		jsp.setBounds(0,80,300,1000);
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		GridLayout box=new GridLayout(100,1);
		p2.setLayout(box);
		try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String url ="jdbc:mysql://localhost:3306/library";
            Connection conn= DriverManager.getConnection(url,"root","123456");
            Statement stmt=conn.createStatement();
            String sql="select * from book";
            ResultSet rs=stmt.executeQuery(sql);
            int i=0;
            
            while(rs.next()) {            	
				bnames[i]=rs.getString("bname");            
            	b[i]=new Button(bnames[i]);
    			b[i].setBackground(Color.WHITE);    			
    			b[i].addActionListener(new BorrowBook1(bnames[i]));			
    			p2.add(b[i]);
    			i++;
            }
                       
            rs.close();
            stmt.close();
            conn.close();
         
       }catch(Exception e){
           e.printStackTrace();
       }					
		this.addWindowListener(new WindowAdapter() {			
			public void windowClosing(WindowEvent e) {				
				book.uf.dispose();	
				}			
		});		
		this.setBackground(Color.CYAN);
		this.setResizable(false);		
		setVisible(true);
}
	
	class BorrowBook1 implements ActionListener{
		String bname;
		private  final int WIDTH=400;
		private  final int HEIGHT=550;
		private  final int initx=0;
		private  final int inity=30;
		private  final int lw=80;
		private  final int lh=30;
		private  final int tfw=300;
		private  final int tfh=30;
		private  final int rp=20;
		UpdateBFrame ub;		
		public BorrowBook1(String bname){			
			this.bname=bname;
		}		
			    
		public void actionPerformed(ActionEvent e) {	
			JFrame jf=new JFrame("更新图书信息");	   					
			Panel panel=new Panel();
			jf.setLocation(300, 40);
			jf.setSize(WIDTH, HEIGHT);
			panel.setLayout(null);
			int x=initx;
			int y=inity;		
			Font f1=new Font("宋体",Font.BOLD,15);
			Font f2=new Font("宋体",Font.PLAIN,22);
		    try{
		           Class.forName("com.mysql.jdbc.Driver").newInstance();
		           String url ="jdbc:mysql://localhost:3306/library";
		           Connection conn= DriverManager.getConnection(url,"root","123456");
		           Statement stmt=conn.createStatement();	               
		           String sqlr="select * from book where bname='"+bname+"'";
		              
		           ResultSet rs=stmt.executeQuery(sqlr);
		           while(rs.next()) { 
		            	   
		            Label num=new Label("书号：",Label.CENTER);
		   			num.setFont(f1);		
		   			num.setBounds(x,y,lw,lh);				
		   			TextField pn=new TextField();
		   			pn.setBounds(lw, y, tfw, tfh);
		   			pn.setFont(f2);	   			
		   			pn.setText(rs.getString("id"));
		   			panel.add(num);
		   			panel.add(pn);			
		   					
		   			Label name=new Label("书名：",Label.CENTER);
		   			name.setFont(f1);		
		   			name.setBounds(x,y+(lh+rp),lw,lh);				
		   			TextField pna=new TextField();
		   			pna.setBounds(lw, y+(lh+rp), tfw, tfh);
		   			pna.setFont(f2);
		   			pna.setText(rs.getString("bname"));
		   			panel.add(name);
		   			panel.add(pna);				
		   			
		   			Label a=new Label("作者：",Label.CENTER);
		   			a.setFont(f1);		
		   			a.setBounds(x,y+2*(lh+rp),lw,lh);				
		   			TextField pa=new TextField();
		   			pa.setBounds(lw,y+2*(lh+rp), tfw, tfh);
		   			pa.setFont(f2);
		   			pa.setText(rs.getString("author"));
		   			panel.add(a);
		   			panel.add(pa);
		   					
		   			Label p=new Label("出版社：",Label.CENTER);
		   			p.setFont(f1);		
		   			p.setBounds(x,y+3*(lh+rp),lw,lh);				
		   			TextField pp=new TextField();
		   			pp.setBounds(lw,y+ 3*(lh+rp), tfw, tfh);
		   			pp.setFont(f2);
		   			pp.setText(rs.getString("press"));
		   			panel.add(p);
		   			panel.add(pp);
		   					
		   			Label pr=new Label("单价：",Label.CENTER);
		   			pr.setFont(f1);		
		   			pr.setBounds(x,y+4*(lh+rp),lw,lh);				
		   			TextField ppr=new TextField();
		   			ppr.setBounds(lw, y+4*(lh+rp), tfw, tfh);
		   			ppr.setFont(f2);
		   			ppr.setText(""+rs.getString("price"));
		   			panel.add(pr);
		   			panel.add(ppr);				
		   			
		   			Label s=new Label("入库时间：");
		   			s.setFont(f1);		
		   			s.setBounds(x,y+5*(lh+rp),lw,lh);				
		   			TextField ps=new TextField();
		   			ps.setBounds(lw, y+5*(lh+rp), tfw, tfh);
		   			ps.setFont(f2);
		   			ps.setText(rs.getString("entertime"));
		   			panel.add(s);
		   			panel.add(ps);
		   					
		   			Label t=new Label("总量：",Label.CENTER);
		   			t.setFont(f1);		
		   			t.setBounds(x,y+6*(lh+rp),lw,lh);				
		   			TextField pt=new TextField();
		   			pt.setBounds(lw, y+6*(lh+rp), tfw, tfh);
		   			pt.setFont(f2);
		   			pt.setText(""+rs.getShort("sum"));
		   			panel.add(t);
		   			panel.add(pt);		
		   					
		   			Label c=new Label("现存量：",Label.CENTER);
		   			c.setFont(f1);		
		   			c.setBounds(x,y+7*(lh+rp),lw,lh);				
		   			TextField pc=new TextField();
		   			pc.setBounds(lw, y+7*(lh+rp), tfw, tfh);
		   			pc.setFont(f2);
		   			pc.setText(""+rs.getShort("nowsum"));
		   			panel.add(c);
		   			panel.add(pc);	
		   			
					Button b=new Button("借阅");	
					b.setFont(f1);
					b.setBounds(250, y+8*(lh+rp)+30,100,30);
					b.addActionListener(new BorrowBook2(bname));
					panel.add(b);
		   			jf.add(panel);
		   			}		
		               rs.close();
		               stmt.close();
		               conn.close();
		               
		          }catch(Exception e1){
		              e1.printStackTrace();
		          }																			

				jf.setBackground(Color.CYAN);
				jf.setResizable(false);		
				jf.setVisible(true);
				}				
		}	
	class BorrowBook2 implements ActionListener{
		String bname;
		public BorrowBook2(String bname){
			this.bname=bname;
		}
		public void actionPerformed(ActionEvent e) {
			Date date = new Date();
	        String format = "yyyy-MM-dd";
	        SimpleDateFormat sdf = new SimpleDateFormat(format);
	        String borrowtime = sdf.format(date);
	        String username=LibraryMClient.username;
	        String phonenum=LibraryMClient.phonenum;
	        String bn1=null;
		    String bname1=null;
			 try{
		           Class.forName("com.mysql.jdbc.Driver").newInstance();
		           String url ="jdbc:mysql://localhost:3306/library";
		           Connection conn= DriverManager.getConnection(url,"root","123456");
		           Statement stmt=conn.createStatement();	               
		           String sqlr="select * from book where bname='"+bname+"'";		           
		           ResultSet rs=stmt.executeQuery(sqlr);
		           
		           while(rs.next()) { 
		        	   
		        	    bn1=rs.getString("id");
		        	    bname1=rs.getString("bname");		         	   
		           }
		           rs.close();
		           String sql="insert into brinfo values('"+bn1+"','"+bname1+"','"+username+"','"+borrowtime+"','"+phonenum+"')";
	        	   stmt.executeUpdate(sql);
	               stmt.close();
	               conn.close();
	            
	          }catch(Exception e1){
	              e1.printStackTrace();
	          }
		}		
	}		
	}
	
		








