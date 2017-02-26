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

//����Աֻ��һ��Ĭ�ϵģ����Բ鿴������Ϣ�������ϸ��Ϣ�Լ����ߵ���ϸ��Ϣ��ɾ��ͼ�飬ͼ��黹����
//ͼ��Ļ��ܣ���Ŀ�۸�Ӧ����ʾ��ʲô�ط�������Ա�Ͷ��ߵ�¼��Ľ�����ʾͼ����������ͼ�������ܼ۸�

public class  Reader {
	LibraryMClient lmc;
	//�����ݿ�Ͳ��ö�������������Ϣ	
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
		this.setTitle("�û���¼");
		this.setBounds(500, 220, 360, 420);
		this.setLayout(null);
		//�����鷳�Ϳ�����һ�α������ݿ���ҳ���Ҳ��������Ϊ��̬����
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
		
		Font f=new Font("��������",Font.BOLD,20);		
		String str="            ����ͼ��"+bnum+" ��\n               ��"+bsum+" ��\n     �ܼ۸�"+psum+" yuan";
		JTextArea ta=new JTextArea(str,1,2);	
		ta.setFont(f);
		ta.setForeground(Color.RED);
		ta.setBounds(20, 40, 320,110);
		ta.setBackground(Color.CYAN);
		ta.setEditable(false);
		this.add(ta);		
		
		JButton q=new JButton("��ѯͼ��");
		q.addActionListener(new QueryBook());
		q.setBounds(30,170,300,40);		
		this.add(q);
		
		JButton b=new JButton("����ͼ��");
		b.setBounds(30,220,300,40);
		b.addActionListener(new BorrowBook());
		this.add(b);
		
		JButton bi=new JButton("������Ϣ");
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
    //����   ��ǰ��Ĳ���һ�����������ݿ���Ϣ����ֵ��data����
    Object[][] data = {
    		{"000001","�������1","persist1","2015-8-23","30" },
    		{"000002","�������2","persist2","2015-8-23","30" },
    		{"000003","�������3","persist3","2015-8-23","30"},
    		{"000004","�������4","persist4","2015-8-23","30" }
    		};
    //����
    Object[] clomnName = {"���","����","�û���","����ʱ��","��������"};
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
        //��ʼ��TableModel��ʹ������data������clomnName
        model = new DefaultTableModel(data, clomnName);
        //ʹ�������model��Ϊ������ʼ��JTabel
        table = new JTable(model);      
        pane = new JScrollPane(); 
        pane.getViewport().add(table);
        this.setTitle("������黹");
        this.getContentPane().add(pane);
        //setSize(600,300);//����Frame�Ĵ�С
        this.setBounds(350,200,600,300);
        setResizable(false);//���ô��ڲ����Ե�����С      
        setVisible(true);//���ô�����ʾ����
    }

}










